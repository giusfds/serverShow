#!/bin/bash
# Script para testar a aplicação de forma rápida e completa

echo "=================================================="
echo "  SCRIPT DE TESTE - Sistema de Processamento"
echo "=================================================="
echo ""

# Cores para output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Função para verificar se Java está instalado
check_java() {
    echo -n "Verificando Java... "
    if command -v java &> /dev/null; then
        JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
        echo -e "${GREEN}OK${NC} (versão $JAVA_VERSION)"
        return 0
    else
        echo -e "${RED}ERRO${NC}"
        echo "Java não está instalado!"
        return 1
    fi
}

# Função para compilar
compile() {
    echo ""
    echo "📦 Compilando código Java..."
    cd src/main/java || exit
    
    if javac *.java 2> /tmp/compile_errors.txt; then
        echo -e "${GREEN}✅ Compilação bem-sucedida!${NC}"
        cd ../../..
        return 0
    else
        echo -e "${RED}❌ Erro na compilação:${NC}"
        cat /tmp/compile_errors.txt
        cd ../../..
        return 1
    fi
}

# Função para teste rápido
quick_test() {
    echo ""
    echo "🧪 Executando teste rápido..."
    echo ""
    echo "Este teste irá:"
    echo "  1. Iniciar o servidor"
    echo "  2. Aguardar 3 segundos"
    echo "  3. Iniciar o cliente"
    echo ""
    echo -e "${YELLOW}IMPORTANTE: Feche as janelas manualmente quando terminar os testes${NC}"
    echo ""
    read -p "Pressione ENTER para continuar..."
    
    cd src/main/java || exit
    
    # Inicia servidor em background
    echo "Iniciando servidor..."
    java ProcessingServer > /dev/null 2>&1 &
    SERVER_PID=$!
    
    # Aguarda servidor iniciar
    sleep 3
    
    # Inicia cliente
    echo "Iniciando cliente..."
    java ProcessingClient
    
    cd ../../..
}

# Função para verificar portas
check_ports() {
    echo ""
    echo "🔍 Verificando portas..."
    
    # Verifica porta 5000 (TCP)
    echo -n "  Porta 5000 (TCP): "
    if lsof -i :5000 &> /dev/null; then
        echo -e "${YELLOW}EM USO${NC}"
        lsof -i :5000 | grep LISTEN
    else
        echo -e "${GREEN}DISPONÍVEL${NC}"
    fi
    
    # Verifica porta 5001 (UDP)
    echo -n "  Porta 5001 (UDP): "
    if lsof -i :5001 &> /dev/null; then
        echo -e "${YELLOW}EM USO${NC}"
        lsof -i :5001
    else
        echo -e "${GREEN}DISPONÍVEL${NC}"
    fi
}

# Função para listar processos Java
list_java_processes() {
    echo ""
    echo "📋 Processos Java em execução:"
    ps aux | grep java | grep -v grep || echo "  Nenhum processo Java encontrado"
}

# Função para matar processos Java
kill_java() {
    echo ""
    echo "🔪 Matando todos os processos Java..."
    pkill -9 java
    echo -e "${GREEN}✅ Processos finalizados${NC}"
}

# Menu principal
show_menu() {
    echo ""
    echo "=================================================="
    echo "  MENU DE TESTES"
    echo "=================================================="
    echo "1. Verificar Java"
    echo "2. Compilar código"
    echo "3. Teste completo (servidor + cliente)"
    echo "4. Verificar portas em uso"
    echo "5. Listar processos Java"
    echo "6. Matar todos os processos Java"
    echo "7. Executar Main (menu interativo)"
    echo "0. Sair"
    echo "=================================================="
}

# Loop principal
main() {
    while true; do
        show_menu
        read -p "Escolha uma opção: " choice
        
        case $choice in
            1)
                check_java
                ;;
            2)
                compile
                ;;
            3)
                if check_java && compile; then
                    quick_test
                fi
                ;;
            4)
                check_ports
                ;;
            5)
                list_java_processes
                ;;
            6)
                kill_java
                ;;
            7)
                echo ""
                echo "Executando Main..."
                cd src/main/java || exit
                java Main
                cd ../../..
                ;;
            0)
                echo ""
                echo "👋 Até logo!"
                exit 0
                ;;
            *)
                echo -e "${RED}Opção inválida!${NC}"
                ;;
        esac
        
        echo ""
        read -p "Pressione ENTER para continuar..."
    done
}

# Execução
clear
check_java
if [ $? -ne 0 ]; then
    echo ""
    echo -e "${RED}Por favor, instale o Java JDK 8 ou superior${NC}"
    exit 1
fi

main
