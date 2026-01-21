# ⚡ Guia Rápido para Agentes IA

## 🚨 REGRA #1 - ISO-8859-1

**SEMPRE use codificação ISO-8859-1 nos arquivos Java.**

Verificar antes de criar/editar:
```bash
file -i arquivo.java
# Deve mostrar: charset=iso-8859-1
```

---

## 🎯 Ordem de Leitura

1. **AGENTS.md** ← Leia primeiro (regras obrigatórias)
2. **LEIA-ME-PRIMEIRO.txt** ← Contexto geral
3. **DOCUMENTACAO.md** ← Arquitetura
4. **EXEMPLOS.md** ← Testes práticos

---

## 📋 Checklist Rápido

Antes de qualquer modificação:
- [ ] ✅ ISO-8859-1 confirmado
- [ ] ✅ Arquitetura em camadas respeitada
- [ ] ✅ Validações em 3 camadas
- [ ] ✅ JavaDoc adicionado
- [ ] ✅ Códigos HTTP corretos

---

## 🏗️ Arquitetura

```
Controller → Service → Repository → H2
   @Valid   Negócio      JPA      Database
```

---

## 🔍 Comandos Úteis

```bash
# Compilar
mvn clean compile

# Executar
mvn spring-boot:run

# Verificar codificação
find src -name "*.java" -exec file -i {} \;
```

---

## 🚫 NÃO Fazer

❌ UTF-8 ou outra codificação
❌ Controller → Repository direto
❌ Esquecer @Valid no Controller
❌ Métodos públicos sem JavaDoc
❌ Códigos HTTP errados

---

## 📞 Dúvidas?

Consulte **AGENTS.md** para detalhes completos.
