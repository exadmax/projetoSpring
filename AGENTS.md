# 🤖 Guia para Agentes IA - Instruções Obrigatórias

## ⚠️ REGRAS CRÍTICAS - LEIA PRIMEIRO

### 🔴 1. CODIFICAÇÃO OBRIGATÓRIA: ISO-8859-1

**ATENÇÃO MÁXIMA**: Todo código Java e arquivos de configuração DEVEM usar codificação **ISO-8859-1**.

```
❌ PROIBIDO: UTF-8, UTF-16, ASCII estendido
✅ OBRIGATÓRIO: ISO-8859-1 (Latin-1)
```

**Verificações antes de criar/editar arquivos:**
- [ ] Confirmar codificação ISO-8859-1 no editor
- [ ] Verificar que acentos portugueses funcionam: á, é, í, ó, ú, ã, õ, ç
- [ ] Não usar caracteres especiais fora do Latin-1
- [ ] Evitar emojis ou símbolos Unicode avançados no código

**Como garantir ISO-8859-1:**
```bash
# Verificar codificação de um arquivo
file -i arquivo.java

# Converter UTF-8 para ISO-8859-1 (se necessário)
iconv -f UTF-8 -t ISO-8859-1 arquivo.java > arquivo_corrigido.java
```

---

## 📋 Estrutura do Projeto

Este é um projeto **Spring Boot 3.4.0 com Java 21**. A estrutura segue o padrão MVC em camadas:

```
src/main/java/com/endereco/catalogo/
├── CatalogoEnderecoApplication.java  # Classe principal
├── controller/
│   └── UsuarioController.java        # Endpoints REST
├── service/
│   └── UsuarioService.java           # Lógica de negócio
├── repository/
│   └── UsuarioRepository.java        # Acesso ao banco H2
├── model/
│   └── Usuario.java                  # Entidade JPA
└── exception/
    └── GlobalExceptionHandler.java   # Tratamento de erros
```

---

## 🎯 Padrões de Desenvolvimento

### 1. Arquitetura em Camadas

**Fluxo de requisição:**
```
Cliente → Controller → Service → Repository → Database
         ↓            ↓          ↓
       @Valid     Validação   JPA/H2
                  Negócio
```

**Responsabilidades:**
- **Controller**: Endpoints HTTP, validação de entrada (@Valid), respostas HTTP
- **Service**: Lógica de negócio, regras do domínio, orquestração
- **Repository**: Interface Spring Data JPA (não precisa implementação)
- **Model**: Entidades JPA com validações Bean Validation

### 2. Validações em 3 Camadas

#### Camada 1: Validação de Entidade (@Valid)
```java
@PostMapping
public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario) {
    // Spring valida automaticamente
}
```

Anotações disponíveis:
- `@NotNull`, `@NotBlank`, `@NotEmpty`
- `@Size(min=X, max=Y)`
- `@Min(value)`, `@Max(value)`
- `@Email`, `@Pattern(regexp)`

#### Camada 2: Validação de Negócio (Service)
```java
public Usuario adicionarUsuario(Usuario usuario) {
    // Validações customizadas
    if (condicaoInvalida) {
        throw new IllegalArgumentException("Mensagem de erro");
    }
    return repository.save(usuario);
}
```

#### Camada 3: Validação de Banco de Dados
```java
@Column(nullable = false, length = 100)
private String nome;
```

### 3. Tratamento de Exceções

Usar `GlobalExceptionHandler` para tratar erros de forma centralizada:

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String, String>> handleValidation(...) {
    // Retorna 400 Bad Request com detalhes
}
```

**Códigos HTTP corretos:**
- `200 OK` - Sucesso em GET, PUT
- `201 Created` - Sucesso em POST
- `204 No Content` - Sucesso em DELETE
- `400 Bad Request` - Erro de validação
- `404 Not Found` - Recurso não encontrado
- `500 Internal Server Error` - Erro inesperado

---

## 📝 Convenções de Código

### Nomenclatura

**Classes:**
```java
// PascalCase para classes
public class UsuarioService { }
public class EnderecoController { }
```

**Métodos:**
```java
// camelCase para métodos
public Usuario adicionarUsuario(Usuario usuario) { }
public List<Usuario> obterTodos() { }
```

**Variáveis:**
```java
// camelCase para variáveis
private String nomeCompleto;
private Integer idadeUsuario;
```

### Comentários JavaDoc

**Obrigatório em:**
- Classes públicas
- Métodos públicos
- Campos importantes

```java
/**
 * Adiciona um novo usuário ao catálogo.
 * 
 * Realiza validações de negócio adicionais antes de persistir
 * o usuário no banco de dados H2.
 * 
 * @param usuario Objeto Usuario com dados validados
 * @return Usuario salvo com ID gerado
 * @throws IllegalArgumentException se validação de negócio falhar
 */
public Usuario adicionarUsuario(Usuario usuario) {
    // implementação
}
```

### Anotações Spring

**Controller:**
```java
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    @GetMapping
    public ResponseEntity<List<Usuario>> listar() { }
    
    @PostMapping
    public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario) { }
    
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, ...) { }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) { }
}
```

**Service:**
```java
@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository repository;
    
    // métodos de negócio
}
```

**Repository:**
```java
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Spring Data JPA gera implementação automaticamente
}
```

---

## 🔍 Checklist para Modificações

### Antes de criar/modificar código:

- [ ] **Codificação ISO-8859-1 confirmada**
- [ ] Leitura da documentação existente (README.md, DOCUMENTACAO.md, EXEMPLOS.md)
- [ ] Entendimento da camada afetada (Controller/Service/Repository/Model)
- [ ] Verificação de padrões existentes no código

### Durante a implementação:

- [ ] Seguir arquitetura em camadas
- [ ] Aplicar validações apropriadas em cada camada
- [ ] Usar anotações Spring corretas
- [ ] Adicionar JavaDoc em métodos públicos
- [ ] Tratar exceções adequadamente
- [ ] Usar códigos HTTP corretos
- [ ] **Manter codificação ISO-8859-1**

### Após implementação:

- [ ] Testar endpoint com exemplos do EXEMPLOS.md
- [ ] Verificar compilação: `mvn clean compile`
- [ ] Verificar testes (se existirem): `mvn test`
- [ ] Validar resposta HTTP e formato JSON
- [ ] **Confirmar arquivo ainda está em ISO-8859-1**

---

## 🚫 Erros Comuns a Evitar

### 1. Codificação Incorreta
```
❌ Criar arquivo em UTF-8
❌ Copiar código com caracteres Unicode incompatíveis
❌ Usar emojis no código Java
✅ Sempre usar ISO-8859-1
✅ Acentos portugueses básicos apenas
```

### 2. Violação de Camadas
```
❌ Controller acessando Repository diretamente
❌ Repository contendo lógica de negócio
✅ Controller → Service → Repository
```

### 3. Validações Inadequadas
```
❌ Sem validação no Controller (@Valid ausente)
❌ Validações de negócio no Controller
✅ @Valid no Controller + lógica no Service
```

### 4. Tratamento de Exceções
```
❌ Exceções não tratadas
❌ Retornar 200 OK em erro
✅ GlobalExceptionHandler centralizado
✅ Códigos HTTP apropriados
```

### 5. Anotações Spring
```
❌ Esquecer @Service, @RestController
❌ Usar @Component quando @Service é mais apropriado
✅ Usar anotações específicas por camada
```

---

## 📚 Referências Rápidas

### Dependências Maven (pom.xml)
- Spring Boot 3.4.0
- Java 21
- H2 Database (em memória)
- Jakarta Bean Validation
- Spring Data JPA

### Arquivos de Configuração
- `pom.xml` - Dependências e build
- `application.properties` - Configuração Spring Boot
- **Codificação**: ISO-8859-1 (critical!)

### Documentação do Projeto
1. `README.md` - Visão geral e início rápido
2. `DOCUMENTACAO.md` - Arquitetura e validações detalhadas
3. `EXEMPLOS.md` - Exemplos de requisições para todos os endpoints
4. `LEIA-ME-PRIMEIRO.txt` - Guia de navegação na documentação

---

## 🎓 Comandos Maven Essenciais

```bash
# Compilar o projeto
mvn clean compile

# Executar a aplicação
mvn spring-boot:run

# Executar testes
mvn test

# Criar JAR executável
mvn clean package

# Verificar dependências
mvn dependency:tree
```

---

## ⚡ Início Rápido para Novos Agentes

1. **Leia este arquivo primeiro** (AGENTS.md)
2. Leia `LEIA-ME-PRIMEIRO.txt` para contexto geral
3. Leia `DOCUMENTACAO.md` para entender arquitetura
4. Consulte `EXEMPLOS.md` para testar endpoints
5. **SEMPRE use ISO-8859-1** em novos arquivos
6. Siga padrões de código existentes
7. Teste suas mudanças antes de finalizar

---

## 🆘 Em Caso de Dúvida

**Prioridade de consulta:**
1. Este arquivo (AGENTS.md) - Regras obrigatórias
2. DOCUMENTACAO.md - Arquitetura e padrões
3. EXEMPLOS.md - Casos de uso práticos
4. Código existente - Exemplos reais no projeto

**Lembre-se:**
- ⚠️ **ISO-8859-1 é obrigatório**
- ⚠️ Siga a arquitetura em camadas
- ⚠️ Use validações em todas as camadas
- ⚠️ Documente código público com JavaDoc
- ⚠️ Teste suas modificações

---

## 🔄 Atualizações deste Guia

Se encontrar padrões ou regras não documentados aqui, **atualize este arquivo** para ajudar futuros agentes.

**Última atualização:** 2026-01-21
**Versão:** 1.0
