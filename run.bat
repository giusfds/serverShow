@echo off
echo =========================================
echo Sistema de Processamento Distribuido
echo =========================================
echo.

REM Cria diretório bin se não existir
if not exist "bin" (
    echo Criando diretorio bin...
    mkdir bin
)

REM Cria diretório received_files se não existir
if not exist "received_files" (
    echo Criando diretorio received_files...
    mkdir received_files
)

REM Compila o código
echo Compilando codigo Java...
javac -encoding UTF-8 -d bin -cp bin src\main\java\*.java

if %errorlevel% equ 0 (
    echo [OK] Compilacao concluida com sucesso!
    echo.
    
    REM Copia config.properties para bin
    copy src\main\java\config.properties bin\ >nul
    
    echo Iniciando Menu Principal...
    cd bin
    java -cp . Main
) else (
    echo [ERRO] Erro na compilacao
    pause
    exit /b 1
)
