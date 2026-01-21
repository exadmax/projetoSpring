# GitHub Copilot - Instruções do Projeto

## ⚠️ REGRA CRÍTICA - CODIFICAÇÃO

**TODOS os arquivos Java e de configuração DEVEM usar codificação ISO-8859-1 (Latin-1).**

```
❌ NÃO usar: UTF-8, UTF-16, ou outras codificações
✅ SEMPRE usar: ISO-8859-1
```

**Ao gerar código:**
- Certifique-se de que a codificação do arquivo seja ISO-8859-1
- Use apenas caracteres compatíveis com Latin-1 (acentos portugueses básicos: á, é, í, ó, ú, ã, õ, ç)
- Evite emojis e caracteres Unicode avançados no código Java
- Comentários devem usar acentuação portuguesa compatível com ISO-8859-1

---

## 🏗️ Arquitetura do Projeto

Este é um **Spring Boot 3.4.0** com **Java 21** seguindo arquitetura em camadas:

```
Controller (REST API) 
    ↓
Service (Lógica de Negócio)
    ↓
Repository (Spring Data JPA)
    ↓
H2 Database
```

**Estrutura de pacotes:**
```
com.endereco.catalogo
├── controller/      # Endpoints REST
├── service/         # Regras de negócio
├── repository/      # Acesso a dados
├── model/           # Entidades JPA
└── exception/       # Tratamento de erros
```

---

## 📝 Padrões de Código

### Validações em Múltiplas Camadas

1. **Controller** - Validação de entrada com `@Valid`:
```java
@PostMapping
public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario) {
    return ResponseEntity.status(201).body(service.adicionarUsuario(usuario));
}
```

2. **Model** - Validações Bean Validation:
```java
@NotBlank(message = "O nome não pode estar vazio")
@Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
private String nome;
```

3. **Service** - Validações de negócio:
```java
public Usuario adicionarUsuario(Usuario usuario) {
    if (condicaoInvalida) {
        throw new IllegalArgumentException("Erro específico");
    }
    return repository.save(usuario);
}
```

### Nomenclatura

- **Classes**: `PascalCase` (ex: `UsuarioService`, `UsuarioController`)
- **Métodos**: `camelCase` (ex: `adicionarUsuario`, `obterTodos`)
- **Variáveis**: `camelCase` (ex: `nomeCompleto`, `idadeUsuario`)
- **Constantes**: `UPPER_SNAKE_CASE` (ex: `MAX_IDADE`)

### Anotações Spring

**Controller:**
```java
@RestController
@RequestMapping("/api/usuarios")
```

**Service:**
```java
@Service
```

**Repository:**
```java
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> { }
```

### Comentários JavaDoc

Adicione JavaDoc para classes e métodos públicos:

```java
/**
 * Adiciona um novo usuário ao catálogo.
 * 
 * @param usuario Objeto Usuario com dados validados
 * @return Usuario salvo com ID gerado
 * @throws IllegalArgumentException se validação falhar
 */
public Usuario adicionarUsuario(Usuario usuario) {
    // implementação
}
```

---

## 🚦 Códigos HTTP Corretos

- `200 OK` - GET e PUT bem-sucedidos
- `201 Created` - POST bem-sucedido
- `204 No Content` - DELETE bem-sucedido
- `400 Bad Request` - Erro de validação
- `404 Not Found` - Recurso não encontrado
- `500 Internal Server Error` - Erro inesperado

---

## 🚫 Evitar

1. **Codificação incorreta** - Sempre ISO-8859-1
2. **Violação de camadas** - Controller não deve acessar Repository diretamente
3. **Falta de validação** - Use `@Valid` no Controller
4. **Exceções não tratadas** - Use `GlobalExceptionHandler`
5. **Anotações erradas** - Use anotações específicas por camada

---

## ✅ Checklist ao Gerar Código

- [ ] Codificação ISO-8859-1 confirmada
- [ ] Seguir arquitetura em camadas
- [ ] Adicionar validações apropriadas
- [ ] Usar anotações Spring corretas
- [ ] Incluir JavaDoc em métodos públicos
- [ ] Usar códigos HTTP corretos
- [ ] Tratar exceções adequadamente
- [ ] Seguir padrões de nomenclatura

---

## 📚 Documentação

Para detalhes completos, consulte:
- `AGENTS.md` - Guia completo para agentes IA
- `DOCUMENTACAO.md` - Arquitetura detalhada
- `EXEMPLOS.md` - Exemplos de uso da API
- `README.md` - Visão geral do projeto

---

## 🎯 Prioridades

1. **ISO-8859-1 em todos os arquivos**
2. Seguir padrões arquiteturais existentes
3. Validações em múltiplas camadas
4. Documentação clara com JavaDoc
5. Tratamento adequado de exceções
