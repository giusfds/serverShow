#!/bin/bash

echo "========================================="
echo "Sistema de Processamento Distribuído"
echo "========================================="
echo ""

# Cores para output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Cria diretório bin se não existir
if [ ! -d "bin" ]; then
    echo -e "${BLUE}Criando diretório bin...${NC}"
    mkdir bin
fi

# Cria diretório received_files se não existir
if [ ! -d "received_files" ]; then
    echo -e "${BLUE}Criando diretório received_files...${NC}"
    mkdir received_files
fi

# Compila o código
echo -e "${BLUE}Compilando código Java...${NC}"
javac -d bin src/main/java/*.java

if [ $? -eq 0 ]; then
    echo -e "${GREEN}Compilação concluída com sucesso!${NC}"
    echo ""
    
    # Copia config.properties para bin
    cp src/main/java/config.properties bin/
    
    echo -e "${GREEN}Iniciando Menu Principal...${NC}"
    cd bin
    java Main
else
    echo -e "${RED}✗ Erro na compilação${NC}"
    exit 1
fi
