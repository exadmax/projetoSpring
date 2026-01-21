#!/bin/bash
# Script para verificar codificação ISO-8859-1 nos arquivos Java
# Uso: bash verificar-codificacao.sh

echo "🔍 Verificando codificação dos arquivos Java..."
echo ""

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Contador
total=0
incorretos=0

# Verifica todos os arquivos .java
while IFS= read -r -d '' file; do
    total=$((total + 1))
    encoding=$(file -b --mime-encoding "$file")
    
    if [ "$encoding" != "iso-8859-1" ] && [ "$encoding" != "us-ascii" ]; then
        echo -e "${RED}❌ INCORRETO:${NC} $file"
        echo -e "   Codificação atual: ${YELLOW}$encoding${NC}"
        echo -e "   Esperado: ${GREEN}iso-8859-1${NC}"
        echo ""
        incorretos=$((incorretos + 1))
    else
        echo -e "${GREEN}✅ OK:${NC} $file ($encoding)"
    fi
done < <(find src -name "*.java" -print0)

echo ""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "📊 RESUMO"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "Total de arquivos verificados: $total"
echo -e "Arquivos corretos: ${GREEN}$((total - incorretos))${NC}"

if [ $incorretos -eq 0 ]; then
    echo -e "${GREEN}✅ SUCESSO: Todos os arquivos estão com codificação correta!${NC}"
    exit 0
else
    echo -e "Arquivos incorretos: ${RED}$incorretos${NC}"
    echo ""
    echo -e "${YELLOW}⚠️  AÇÃO NECESSÁRIA:${NC}"
    echo "Para converter arquivos UTF-8 para ISO-8859-1, use:"
    echo "iconv -f UTF-8 -t ISO-8859-1 arquivo.java > arquivo_temp.java && mv arquivo_temp.java arquivo.java"
    exit 1
fi
