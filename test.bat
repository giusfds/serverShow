@echo off
REM Script para testar a aplicação de forma rápida e completa (Windows)

title Sistema de Processamento - Testes

:MENU
cls
echo ==================================================
echo   SCRIPT DE TESTE - Sistema de Processamento
echo ==================================================
echo.

REM Verificar Java
echo Verificando Java...
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [ERRO] Java nao esta instalado!
    echo Por favor, instale o Java JDK 8 ou superior
    pause
    exit /b 1
)
echo [OK] Java encontrado
echo.

echo ==================================================
echo   MENU DE TESTES
echo ==================================================
echo 1. Compilar codigo
echo 2. Executar Main (menu interativo)
echo 3. Teste completo (servidor + cliente)
echo 4. Verificar portas em uso
echo 5. Matar processos Java
echo 0. Sair
echo ==================================================
echo.

set /p choice="Escolha uma opcao: "

if "%choice%"=="1" goto COMPILE
if "%choice%"=="2" goto RUN_MAIN
if "%choice%"=="3" goto QUICK_TEST
if "%choice%"=="4" goto CHECK_PORTS
if "%choice%"=="5" goto KILL_JAVA
if "%choice%"=="0" goto EXIT
goto MENU

:COMPILE
echo.
echo [*] Compilando codigo Java...
cd src\main\java
javac *.java
if %ERRORLEVEL% NEQ 0 (
    echo [ERRO] Falha na compilacao!
    cd ..\..\..
    pause
    goto MENU
)
echo [OK] Compilacao bem-sucedida!
cd ..\..\..
pause
goto MENU

:RUN_MAIN
echo.
echo [*] Executando Main...
cd src\main\java
java Main
cd ..\..\..
pause
goto MENU

:QUICK_TEST
echo.
echo [*] Executando teste completo...
echo.
echo Este teste ira:
echo   1. Iniciar o servidor
echo   2. Aguardar 3 segundos
echo   3. Iniciar o cliente
echo.
echo IMPORTANTE: Feche as janelas manualmente quando terminar os testes
echo.
pause

cd src\main\java

REM Inicia servidor em nova janela
start "Servidor" java ProcessingServer

REM Aguarda 3 segundos
timeout /t 3 /nobreak >nul

REM Inicia cliente em nova janela
start "Cliente" java ProcessingClient

cd ..\..\..
echo.
echo [OK] Servidor e Cliente iniciados em janelas separadas
pause
goto MENU

:CHECK_PORTS
echo.
echo [*] Verificando portas...
echo.
echo Porta 5000 (TCP):
netstat -an | findstr :5000
echo.
echo Porta 5001 (UDP):
netstat -an | findstr :5001
echo.
pause
goto MENU

:KILL_JAVA
echo.
echo [*] Matando todos os processos Java...
taskkill /F /IM java.exe >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [INFO] Nenhum processo Java encontrado
) else (
    echo [OK] Processos Java finalizados
)
pause
goto MENU

:EXIT
echo.
echo Ate logo!
timeout /t 2 /nobreak >nul
exit /b 0
