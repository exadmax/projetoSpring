# 📚 Documentação - Arquitetura e Validações

## 🏗️ Arquitetura Detalhada

### Fluxo de uma Requisição (Exemplo: POST)

```
1. Cliente envia JSON
   ↓
2. Spring deserializa → Usuario.class
   ↓
3. UsuarioController recebe requisição
   ├─ @Valid dispara validações de entidade
   ├─ @NotBlank, @Size, @NotNull, @Min, @Max são verificadas
   ↓ (Se falhar → 400 Bad Request)
4. UsuarioService.adicionarUsuario()
   ├─ Validações de negócio adicionais
   ├─ Regras específicas do domínio
   ↓ (Se falhar → IllegalArgumentException)
5. UsuarioRepository.save()
   ├─ Spring Data JPA gera INSERT SQL
   ├─ Hibernate executa no H2
   ↓
6. H2 Database
   ├─ Valida constraints (NOT NULL, LENGTH)
   ├─ Gera AUTO_INCREMENT para ID
   ├─ Retorna registro com ID
   ↓
7. Response: 201 CREATED com Usuario completo
```

### Camadas da Aplicação

```
┌────────────────────────────────────┐
│   CONTROLLER (HTTP Endpoints)      │
│  UsuarioController.java            │
│  - @PostMapping, @GetMapping, etc  │
│  - Validação com @Valid            │
│  - Respostas HTTP                  │
└────────────────────────────────────┘
              ↓
┌────────────────────────────────────┐
│   SERVICE (Lógica de Negócio)      │
│  UsuarioService.java               │
│  - adicionarUsuario()              │
│  - obterTodos()                    │
│  - atualizarUsuario()              │
│  - removerUsuario()                │
└────────────────────────────────────┘
              ↓
┌────────────────────────────────────┐
│  REPOSITORY (Persistência)         │
│  UsuarioRepository.java            │
│  - Spring Data JPA                 │
│  - findAll(), save(), deleteById() │
│  - findByNome() (customizado)      │
└────────────────────────────────────┘
              ↓
┌────────────────────────────────────┐
│  ORM (Hibernate + H2)              │
│  - Gera SQL                        │
│  - Gerencia transações             │
│  - Mapeia objetos para tabelas     │
└────────────────────────────────────┘
              ↓
┌────────────────────────────────────┐
│  DATABASE (H2)                     │
│  - Tabela usuarios                 │
│  - Armazenamento em memória        │
│  - Constraints (NOT NULL, LENGTH)  │
└────────────────────────────────────┘
```

### Estrutura de Pacotes

```
com.endereco.catalogo/
│
├── controller/
│   └── UsuarioController.java
│       ├── @PostMapping /api/usuarios
│       ├── @GetMapping /api/usuarios
│       ├── @GetMapping /api/usuarios/{id}
│       ├── @GetMapping /api/usuarios/buscar/nome
│       ├── @PutMapping /api/usuarios/{id}
│       ├── @DeleteMapping /api/usuarios/{id}
│       └── @GetMapping /api/usuarios/estatisticas/total
│
├── service/
│   └── UsuarioService.java
│       ├── adicionarUsuario(Usuario)
│       ├── obterTodosUsuarios()
│       ├── obterUsuarioPorId(Long)
│       ├── obterUsuarioPorNome(String)
│       ├── atualizarUsuario(Long, Usuario)
│       ├── removerUsuario(Long)
│       └── contarUsuarios()
│
├── repository/
│   └── UsuarioRepository.java
│       └── extends JpaRepository<Usuario, Long>
│           └── findByNome(String)
│
├── model/
│   └── Usuario.java
│       ├── @Entity @Table(name="usuarios")
│       ├── id: Long @Id @GeneratedValue
│       ├── nome: String @NotBlank @Size(max=100)
│       ├── idade: Integer @NotNull @Min(1) @Max(99)
│       └── endereco: String
│
├── exception/
│   └── GlobalExceptionHandler.java
│       ├── @RestControllerAdvice
│       ├── handleValidationException()
│       ├── handleIllegalArgumentException()
│       └── handleGenericException()
│
└── CatalogoEnderecoApplication.java
    └── @SpringBootApplication + main()
```

## ✅ Sistema de Validações

### 3 Camadas de Validação

#### Camada 1: Entidade (Jakarta Bean Validation)

As anotações na classe `Usuario` definem constraints que são verificadas automaticamente:

```java
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode estar vazio")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotNull(message = "A idade é obrigatória")
    @Min(value = 1, message = "A idade deve ser no mínimo 1")
    @Max(value = 99, message = "A idade deve ser no máximo 99")
    @Column(nullable = false)
    private Integer idade;

    @Column(length = 500)
    private String endereco;
}
```

**Validações Ativas:**
- `@NotBlank` - String não pode ser null, vazio ou apenas espaços
- `@Size(max=100)` - Comprimento máximo de 100 caracteres
- `@NotNull` - Valor não pode ser null
- `@Min(1)` - Valor mínimo de 1
- `@Max(99)` - Valor máximo de 99

#### Camada 2: Controller (@Valid)

O controller aplica validações antes de chamar o service:

```java
@PostMapping
public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody Usuario usuario) {
    Usuario usuarioCriado = usuarioService.adicionarUsuario(usuario);
    return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
}
```

- `@Valid` dispara todas as validações de entidade
- Falha → 400 Bad Request com detalhes de erro
- Sucesso → Método é executado

#### Camada 3: Service (Regras de Negócio)

O service implementa lógica adicional de validação:

```java
public Usuario adicionarUsuario(Usuario usuario) {
    // Validação extra: dupla checagem
    if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
        throw new IllegalArgumentException("O nome do usuário é obrigatório");
    }
    if (usuario.getIdade() == null) {
        throw new IllegalArgumentException("A idade do usuário é obrigatória");
    }
    return usuarioRepository.save(usuario);
}

public void removerUsuario(Long id) {
    if (!usuarioRepository.existsById(id)) {
        throw new IllegalArgumentException("Usuário com ID " + id + " não encontrado");
    }
    usuarioRepository.deleteById(id);
}
```

### Resposta de Erro (400 Bad Request)

```json
{
  "timestamp": "2026-01-21T10:30:00.123Z",
  "status": 400,
  "error": "Validação Falhou",
  "message": "Dados inválidos fornecidos",
  "fields": {
    "nome": "O nome deve ter no máximo 100 caracteres",
    "idade": "A idade deve ser no máximo 99"
  }
}
```

### Tabela de Validações

| Campo | Anotação | Tipo | Mensagem | Exemplo Inválido |
|-------|----------|------|----------|------------------|
| **nome** | `@NotBlank` | String | Obrigatório, não vazio | "", " ", null |
| **nome** | `@Size(max=100)` | String | Máximo 100 chars | "aaa...aaa" (101+) |
| **idade** | `@NotNull` | Integer | Obrigatório | null, omitido |
| **idade** | `@Min(1)` | Integer | Mínimo 1 | 0, -5 |
| **idade** | `@Max(99)` | Integer | Máximo 99 | 100, 150 |
| **endereco** | (nenhuma) | String | Opcional | Qualquer valor |

## 🗄️ Banco de Dados H2

### Configuração (application.properties)

```properties
# URL em memória (sem arquivo)
spring.datasource.url=jdbc:h2:mem:testdb

# Credenciais
spring.datasource.username=sa
spring.datasource.password=

# Recria tabelas a cada execução
spring.jpa.hibernate.ddl-auto=create-drop

# Mostra SQL gerado
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Console web habilitado
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### Tabela Gerada

```sql
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    idade INTEGER NOT NULL,
    endereco VARCHAR(500)
);
```

### Acessar Console

```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
User: sa
Password: (vazio)
```

## 🎯 Padrões de Design

### 1. MVC (Model-View-Controller)
- **Model**: `Usuario` (entidade)
- **Controller**: `UsuarioController` (endpoints)
- **View**: JSON (em REST, sem HTML)

### 2. Repository Pattern
- Abstração de acesso a dados
- Spring Data JPA gera implementação automaticamente
- Desacopla lógica de persistência

### 3. Service Layer Pattern
- Centraliza lógica de negócio
- Reutilizável em múltiplos controllers
- Facilita testes

### 4. Dependency Injection
- Spring gerencia ciclo de vida dos beans
- `@Autowired` injeta dependências
- Facilita testes e manutenção

## 🔄 Fluxo Completo: Criar Usuário

```bash
# 1. Cliente envia requisição
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"João","idade":30,"endereco":"Rua X"}'

# 2. Spring deserializa JSON → Usuario object

# 3. @Valid dispara validações
# ✓ nome não está vazio
# ✓ nome tem ≤ 100 chars
# ✓ idade não é null
# ✓ idade está entre 1-99

# 4. UsuarioController.criarUsuario() é chamado

# 5. UsuarioService.adicionarUsuario() executa
# ✓ Valida nome novamente
# ✓ Valida idade novamente

# 6. UsuarioRepository.save() persiste
# - Hibernate gera: INSERT INTO usuarios (nome, idade, endereco) VALUES (?, ?, ?)
# - H2 executa SQL
# - Gera ID automaticamente

# 7. Resposta retorna
HTTP/1.1 201 Created
Content-Type: application/json

{
  "id": 1,
  "nome": "João",
  "idade": 30,
  "endereco": "Rua X"
}
```

## 💬 Comentários no Código

Cada classe e método possui documentação Javadoc completa:

```java
/**
 * Serviço para operações com usuários.
 * 
 * Esta classe implementa a lógica de negócio relacionada aos usuários.
 * Ela funciona como intermediária entre o controlador REST e o repositório.
 */
@Service
public class UsuarioService {
    
    /**
     * Adiciona um novo usuário ao catálogo.
     * 
     * Este método recebe um objeto Usuario com dados validados
     * e o persiste no banco de dados H2.
     * 
     * @param usuario O usuário a ser adicionado
     * @return O usuário salvo com o ID gerado
     * @throws IllegalArgumentException Se dados obrigatórios forem nulos
     */
    public Usuario adicionarUsuario(Usuario usuario) {
        // ... implementação
    }
}
```

## 🚀 Tratamento de Erros

### GlobalExceptionHandler

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // Valida @Valid falha
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(...) { }
    
    // Regras de negócio falham
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(...) { }
    
    // Erros genéricos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(...) { }
}
```

**Respostas HTTP:**
- `200 OK` - Sucesso com conteúdo
- `201 Created` - Recurso criado
- `204 No Content` - Sucesso sem conteúdo (DELETE)
- `400 Bad Request` - Validação falhou
- `404 Not Found` - Recurso não existe
- `500 Internal Server Error` - Erro do servidor

---

**Leia também**: [EXEMPLOS.md](EXEMPLOS.md) para exemplos práticos de requisições.
