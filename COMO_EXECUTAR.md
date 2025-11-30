# 🚀 Como Executar a Aplicação

## Método 1: Script Automático (Recomendado)

### Linux/Mac:

```bash
./run.sh
```

### Windows:

```cmd
run.bat
```

**Observação:** Se aparecer erro de permissão no Mac/Linux, execute:

```bash
chmod +x run.sh
```

---

## Método 2: Execução Manual

### Passo 1: Compilar

```bash
mkdir -p bin
javac -d bin src/main/java/*.java
cp src/main/java/config.properties bin/
```

### Passo 2: Executar

```bash
cd bin
java Main
```

---

## 📋 Menu Principal

Ao executar, você verá 5 opções:

1. **Executar Servidor (ProcessingServer)** - Inicia apenas o servidor
2. **Executar Cliente (ProcessingClient)** - Inicia apenas o cliente
3. **Executar Ambos** - Inicia servidor e depois cliente
4. **Configurações de Rede / Roteador** - Alterar IP, portas, latência, etc.
5. **Sair** - Fecha a aplicação

---

## 🔧 Configurando Portas

### Opção A: Pela Interface de Configuração

1. No menu principal, escolha **"4. Configurações de Rede / Roteador"**
2. Na aba **"Conexão"**, altere:
   - IP do Servidor
   - Porta TCP (padrão: 5000)
   - Porta UDP (padrão: 5001)
3. Clique em **"Salvar Configurações"**
4. Reinicie a aplicação para aplicar as mudanças

### Opção B: Diretamente nas Janelas (Mais Prático)

#### No Servidor (ProcessingServer):

1. Abra o servidor
2. Nos campos "Porta TCP" e "Porta UDP" no topo da janela, digite as portas desejadas
3. Clique em "Iniciar TCP" ou "Iniciar UDP"
4. O servidor usará as portas especificadas

#### No Cliente (ProcessingClient):

1. Abra o cliente
2. Nos campos "Host", "TCP" e "UDP" no topo da janela, digite:
   - Host: IP do servidor (ex: `localhost` ou `192.168.1.100`)
   - TCP: Porta TCP do servidor (ex: `5000`)
   - UDP: Porta UDP do servidor (ex: `5001`)
3. Clique em "Conectar"

---

## 🧪 Testando a Aplicação

### Teste Básico (Servidor + Cliente na mesma máquina):

1. Execute o script: `./run.sh`
2. Escolha opção **3** (Executar Ambos)
3. Aguarde o servidor abrir
4. Aguarde o cliente abrir (2 segundos depois)
5. No cliente:
   - Clique em **"Conectar"**
   - Escolha uma tarefa (ex: "Ordenação")
   - Clique em **"Executar Tarefa"**
6. Veja o resultado no cliente e a tarefa processada no servidor

### Teste em Máquinas Diferentes:

#### Na Máquina Servidor:

1. Execute `./run.sh`
2. Escolha opção **1** (Executar Servidor)
3. Anote o IP da máquina (use `ifconfig` ou `ip addr`)
4. Clique em "Iniciar TCP" e "Iniciar UDP"

#### Na Máquina Cliente:

1. Execute `./run.sh`
2. Escolha opção **2** (Executar Cliente)
3. No campo "Host", digite o IP do servidor
4. Clique em "Conectar"
5. Execute tarefas normalmente

---

## 🔍 Tipos de Tarefas Disponíveis

1. **Ordenação (SORT)** - Ordena um array de números

   - Algoritmos: BUBBLE, QUICK, MERGE

2. **Busca (SEARCH)** - Busca um elemento em um array

   - Algoritmos: LINEAR, BINARY

3. **Multiplicação de Matrizes (MATRIX)** - Multiplica duas matrizes NxN

4. **Números Primos (PRIME)** - Encontra todos os primos até um limite

5. **Transferência de Arquivo (FILE)** - Envia um arquivo para o servidor

---

## ⚙️ Simulação de Rede

Use "Configurações de Rede / Roteador" para simular:

- **Latência (Ping):** Atraso de 0 a 5000ms
- **Perda de Pacotes:** 0% a 100%

Útil para testar o comportamento da aplicação em redes ruins!

---

## ❓ Resolução de Problemas

### Erro: "Permissão negada" ao executar ./run.sh

```bash
chmod +x run.sh
./run.sh
```

### Erro: "javac: command not found"

Instale o JDK:

```bash
# Ubuntu/Debian
sudo apt install default-jdk

# macOS
brew install openjdk
```

### Erro: "Falha ao conectar" no cliente

1. Verifique se o servidor está rodando
2. Verifique se as portas estão corretas
3. Verifique se o IP está correto
4. Teste com `localhost` se estiver na mesma máquina

### Erro: "Autenticação falhou"

As credenciais padrão são:

- **Auth Key:** ViniShow
- **Password:** SuperViniD

(Definidas em `config.properties`)

---

## 📁 Estrutura de Arquivos

```
serverShow/
├── run.sh              # Script de execução (Linux/Mac)
├── run.bat             # Script de execução (Windows)
├── bin/                # Arquivos compilados (criado automaticamente)
├── received_files/     # Arquivos recebidos pelo servidor
├── config.properties   # Configurações (criado automaticamente)
└── src/main/java/      # Código fonte
    ├── Main.java
    ├── Config.java
    ├── ConfigDialog.java
    ├── ProcessingServer.java
    ├── ProcessingClient.java
    ├── FileServer.java
    └── FileClient.java
```

---

## ✅ Pronto!

A aplicação está configurada e pronta para uso. Boa sorte com seu projeto! 🚀
