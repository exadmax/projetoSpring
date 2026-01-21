# 🧪 Exemplos de Requisições e Testes

## 🚀 Antes de Começar

**Certificar que a aplicação está rodando:**
```bash
mvn spring-boot:run
```

**Aplicação disponível em:**
- API: http://localhost:8080/api/usuarios
- Console H2: http://localhost:8080/h2-console

---

## 📝 1. CRIAR USUÁRIO (POST)

### ✅ Exemplo Válido

```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "idade": 30,
    "endereco": "Rua das Flores, 123"
  }'
```

**Resposta:**
```json
{
  "id": 1,
  "nome": "João Silva",
  "idade": 30,
  "endereco": "Rua das Flores, 123"
}
```

### ❌ Exemplo Inválido - Nome Vazio

```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"","idade":30}'
```

**Resposta (400 Bad Request):**
```json
{
  "timestamp": "2026-01-21T10:30:00.123Z",
  "status": 400,
  "error": "Validação Falhou",
  "message": "Dados inválidos fornecidos",
  "fields": {
    "nome": "O nome não pode estar vazio"
  }
}
```

### ❌ Exemplo Inválido - Nome > 100 caracteres

```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
    "idade": 30
  }'
```

**Resposta (400 Bad Request):**
```json
{
  "status": 400,
  "error": "Validação Falhou",
  "fields": {
    "nome": "O nome deve ter no máximo 100 caracteres"
  }
}
```

### ❌ Exemplo Inválido - Idade Ausente

```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"João"}'
```

**Resposta (400 Bad Request):**
```json
{
  "status": 400,
  "error": "Validação Falhou",
  "fields": {
    "idade": "A idade é obrigatória"
  }
}
```

### ❌ Exemplo Inválido - Idade Fora do Intervalo

```bash
# Idade 0 (menor que 1)
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"João","idade":0}'
```

**Resposta (400 Bad Request):**
```json
{
  "status": 400,
  "error": "Validação Falhou",
  "fields": {
    "idade": "A idade deve ser no mínimo 1"
  }
}
```

```bash
# Idade 100 (maior que 99)
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"João","idade":100}'
```

**Resposta (400 Bad Request):**
```json
{
  "status": 400,
  "error": "Validação Falhou",
  "fields": {
    "idade": "A idade deve ser no máximo 99"
  }
}
```

---

## 🔍 2. LISTAR TODOS OS USUÁRIOS (GET)

### Exemplo

```bash
curl http://localhost:8080/api/usuarios
```

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "idade": 30,
    "endereco": "Rua das Flores, 123"
  },
  {
    "id": 2,
    "nome": "Maria Santos",
    "idade": 25,
    "endereco": "Av. Paulista, 1000"
  }
]
```

---

## 🎯 3. OBTER POR ID (GET)

### ✅ Usuário Existe

```bash
curl http://localhost:8080/api/usuarios/1
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "idade": 30,
  "endereco": "Rua das Flores, 123"
}
```

### ❌ Usuário Não Existe

```bash
curl http://localhost:8080/api/usuarios/999
```

**Resposta (404 Not Found):**
```json
```
(Sem conteúdo)

---

## 🔎 4. BUSCAR POR NOME (GET)

### ✅ Nome Encontrado

```bash
curl "http://localhost:8080/api/usuarios/buscar/nome?nome=João Silva"
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "idade": 30,
  "endereco": "Rua das Flores, 123"
}
```

### ❌ Nome Não Encontrado

```bash
curl "http://localhost:8080/api/usuarios/buscar/nome?nome=Fulano"
```

**Resposta (404 Not Found):**
```json
```
(Sem conteúdo)

---

## ✏️ 5. ATUALIZAR USUÁRIO (PUT)

### ✅ Exemplo Válido

```bash
curl -X PUT http://localhost:8080/api/usuarios/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva Ferreira",
    "idade": 31,
    "endereco": "Rua das Flores, 123, Apt 101"
  }'
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "nome": "João Silva Ferreira",
  "idade": 31,
  "endereco": "Rua das Flores, 123, Apt 101"
}
```

### ✅ Atualizar Apenas Alguns Campos

```bash
curl -X PUT http://localhost:8080/api/usuarios/1 \
  -H "Content-Type: application/json" \
  -d '{"idade":32}'
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "idade": 32,
  "endereco": "Rua das Flores, 123"
}
```

### ❌ Usuário Não Existe

```bash
curl -X PUT http://localhost:8080/api/usuarios/999 \
  -H "Content-Type: application/json" \
  -d '{"nome":"Novo Nome"}'
```

**Resposta (404 Not Found):**
```json
```
(Sem conteúdo)

### ❌ Validação Falha

```bash
curl -X PUT http://localhost:8080/api/usuarios/1 \
  -H "Content-Type: application/json" \
  -d '{"idade":100}'
```

**Resposta (400 Bad Request):**
```json
{
  "status": 400,
  "error": "Validação Falhou",
  "fields": {
    "idade": "A idade deve ser no máximo 99"
  }
}
```

---

## 🗑️ 6. REMOVER USUÁRIO (DELETE)

### ✅ Usuário Removido com Sucesso

```bash
curl -X DELETE http://localhost:8080/api/usuarios/1
```

**Resposta (204 No Content):**
```
(Sem conteúdo)
```

### ❌ Usuário Não Existe

```bash
curl -X DELETE http://localhost:8080/api/usuarios/999
```

**Resposta (404 Not Found):**
```json
```
(Sem conteúdo)

---

## 📊 7. CONTAR USUÁRIOS (GET)

### Exemplo

```bash
curl http://localhost:8080/api/usuarios/estatisticas/total
```

**Resposta (200 OK):**
```json
3
```

---

## 🧪 Teste Completo (Script)

Criar arquivo `teste.sh`:

```bash
#!/bin/bash

# 1. Criar usuários
echo "=== Criando usuários ==="
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"João Silva","idade":30,"endereco":"Rua A"}' && echo ""

curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"Maria Santos","idade":25,"endereco":"Rua B"}' && echo ""

curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"Pedro Oliveira","idade":35}' && echo ""

# 2. Listar
echo -e "\n=== Listando todos ==="
curl http://localhost:8080/api/usuarios && echo ""

# 3. Obter por ID
echo -e "\n=== Obtendo ID 1 ==="
curl http://localhost:8080/api/usuarios/1 && echo ""

# 4. Contar
echo -e "\n=== Contando usuários ==="
curl http://localhost:8080/api/usuarios/estatisticas/total && echo ""

# 5. Atualizar
echo -e "\n=== Atualizando ID 1 ==="
curl -X PUT http://localhost:8080/api/usuarios/1 \
  -H "Content-Type: application/json" \
  -d '{"idade":31}' && echo ""

# 6. Remover
echo -e "\n=== Removendo ID 2 ==="
curl -X DELETE http://localhost:8080/api/usuarios/2 && echo ""

# 7. Verificar final
echo -e "\n=== Listando final ==="
curl http://localhost:8080/api/usuarios && echo ""
```

**Executar:**
```bash
chmod +x teste.sh
./teste.sh
```

---

## 🧑‍💻 Usando Postman/Thunder Client

1. **Importar Coleção:**
   - Crie nova Collection
   - Adicione as 7 requisições como exemplos acima

2. **Criar Ambiente:**
   - Variável: `baseUrl` = `http://localhost:8080`
   - Use `{{baseUrl}}/api/usuarios` nas requisições

3. **Executar Testes:**
   - Clique em "Send"
   - Visualize Response e Status Code

---

## 📱 Usando o Console H2

1. Acesse: http://localhost:8080/h2-console
2. Conecte com credenciais (user: `sa`, password: vazio)
3. Execute queries SQL:

```sql
-- Listar todos
SELECT * FROM usuarios;

-- Contar
SELECT COUNT(*) FROM usuarios;

-- Buscar por nome
SELECT * FROM usuarios WHERE nome = 'João Silva';

-- Buscar por idade
SELECT * FROM usuarios WHERE idade > 25;

-- Deletar
DELETE FROM usuarios WHERE id = 1;
```

---

## ⚠️ Casos de Erro Comuns

| Erro | Causa | Solução |
|------|-------|---------|
| **Connection refused** | App não está rodando | `mvn spring-boot:run` |
| **400 Bad Request** | Validação falhou | Ver campos em error |
| **404 Not Found** | ID não existe | Verificar ID no banco |
| **500 Server Error** | Erro interno | Verificar logs da app |

---

## 💡 Dicas

- Use `-i` em curl para ver headers: `curl -i http://localhost:8080/api/usuarios`
- Use `-v` para verbose: `curl -v http://localhost:8080/api/usuarios`
- Use `jq` para formatar JSON: `curl http://localhost:8080/api/usuarios | jq`
- Use Postman/Insomnia para GUI interativa
- Sempre verificar console H2 para confirmar dados no banco

---

**Leia também**: [DOCUMENTACAO.md](DOCUMENTACAO.md) para entender a arquitetura.
