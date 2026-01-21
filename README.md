# 🚀 Catálogo de Endereços - Spring Boot 3.x

Aplicação Backend REST em **Java 21** com **Spring Boot 3.4** para gerenciar um catálogo de usuários com validações em múltiplas camadas.

## ⚡ Início Rápido

### Compilar e Executar
```bash
cd /workspaces/projetoSpring
mvn clean compile
mvn spring-boot:run
```

### Acessar
- 🌐 **API REST**: http://localhost:8080/api/usuarios
- 🗄️ **Console H2**: http://localhost:8080/h2-console (user: `sa`, password: vazio)

## 📋 Entidade Usuario

| Campo | Tipo | Obrigatório | Validação |
|-------|------|-----------|-----------|
| **id** | Long | Sim* | Auto-gerado |
| **nome** | String | Sim | Max 100 chars, não vazio |
| **idade** | Integer | Sim | Entre 1 e 99 |
| **endereco** | String | Não | Até 500 chars |

## 🔌 Endpoints da API

```http
POST   /api/usuarios                    → Criar usuário (201)
GET    /api/usuarios                    → Listar todos (200)
GET    /api/usuarios/{id}               → Obter por ID (200/404)
GET    /api/usuarios/buscar/nome?nome=X → Buscar por nome (200/404)
PUT    /api/usuarios/{id}               → Atualizar (200/404)
DELETE /api/usuarios/{id}               → Remover (204/404)
GET    /api/usuarios/estatisticas/total → Contar usuários (200)
```

## 🧪 Exemplo Rápido

```bash
# Criar
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"João Silva","idade":30,"endereco":"Rua X"}'

# Listar
curl http://localhost:8080/api/usuarios

# Atualizar
curl -X PUT http://localhost:8080/api/usuarios/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"João Novo","idade":31}'

# Remover
curl -X DELETE http://localhost:8080/api/usuarios/1
```

## 🏗️ Arquitetura em Camadas

```
REST Endpoints (UsuarioController)
        ↓ @Valid
Lógica de Negócio (UsuarioService)
        ↓ CRUD
Persistência (UsuarioRepository - Spring Data JPA)
        ↓ SQL
H2 Database
```

### Pacotes

```
com.endereco.catalogo/
├── controller/        → UsuarioController (7 endpoints REST)
├── service/          → UsuarioService (Lógica)
├── repository/       → UsuarioRepository (JPA)
├── model/            → Usuario (Entidade com validações)
├── exception/        → GlobalExceptionHandler (Erros)
└── App.java          → CatalogoEnderecoApplication
```

## ✅ Validações

### Nome
- ✅ `@NotBlank` - Obrigatório, não vazio
- ✅ `@Size(max=100)` - Máximo 100 caracteres

### Idade
- ✅ `@NotNull` - Obrigatório
- ✅ `@Min(1)` - Mínimo 1 ano
- ✅ `@Max(99)` - Máximo 99 anos

### Endereço
- ✅ Opcional (sem validação)

## 🛠️ Tecnologias

- **Java 21** (LTS)
- **Spring Boot 3.4.0**
- **Spring Data JPA** (Hibernate/ORM)
- **H2 Database** (em memória)
- **Jakarta Bean Validation**
- **Maven**

## 📚 Documentação Completa

- **[DOCUMENTACAO.md](DOCUMENTACAO.md)** - Arquitetura detalhada, validações, fluxos
- **[EXEMPLOS.md](EXEMPLOS.md)** - Exemplos de requisições e testes

## 🎯 Características

✅ Código bem comentado (Javadoc)  
✅ Validações em 3 camadas (Entidade, Controller, Service)  
✅ Tratamento centralizado de erros  
✅ H2 em memória (sem configuração)  
✅ Logs SQL habilitados  
✅ Arquitetura limpa e escalável  

## 🚀 Próximos Passos

- [ ] Testes unitários e integração
- [ ] Documentação Swagger/OpenAPI
- [ ] Autenticação (Spring Security)
- [ ] Paginação
- [ ] Deploy em produção

---

**Treinamento Spring Boot 3.x - Janeiro 2026**
