# Alterações Realizadas na Aplicação

## 🔧 Problemas Corrigidos

### 1. **Config.java - Configurações Faltantes**

- ✅ Adicionados métodos `getAuthKey()` e `getPassword()` com valores padrão
- ✅ Adicionados valores padrão para `auth.key` e `password` em `createDefaults()`
- ✅ Corrigido fallback para todos os getters

**Antes:**

```java
private static final String AUTH_KEY = Config.get("auth.key"); // Retornava string vazia
```

**Depois:**

```java
private static final String AUTH_KEY = Config.getAuthKey(); // Retorna "ViniShow" por padrão
```

### 2. **FileServer.java - Porta Fixa**

- ✅ Alterada porta de `final int` para `int` configurável
- ✅ Porta agora é lida do `Config.getTcpPort()` no construtor
- ✅ Corrigido uso de autenticação para usar os novos getters

### 3. **FileClient.java - Autenticação**

- ✅ Corrigido para usar `Config.getAuthKey()` e `Config.getPassword()`
- ✅ Agora busca credenciais corretas do arquivo de configuração

### 4. **ProcessingServer.java - Portas Editáveis**

- ✅ Campos de porta TCP e UDP agora inicializam com valores do Config
- ✅ Servidor lê as portas dos campos de texto da UI ao iniciar
- ✅ Usuário pode alterar as portas diretamente na interface
- ✅ Corrigido uso de autenticação

**Melhorias:**

- Porta TCP configurável via interface antes de iniciar servidor
- Porta UDP configurável via interface antes de iniciar servidor
- Valores padrão carregados do `Config`

### 5. **ProcessingClient.java - Conexão Configurável**

- ✅ Campos de host e portas agora inicializam com valores do Config
- ✅ Cliente lê host e portas dos campos de texto ao conectar
- ✅ Validação de portas numéricas ao conectar
- ✅ Usuário pode alterar host, TCP e UDP diretamente na interface

**Antes:**

```java
serverHost = Config.getIp();
tcpPort = Config.getTcpPort();
```

**Depois:**

```java
serverHost = hostField.getText().trim();
tcpPort = Integer.parseInt(tcpPortField.getText().trim());
// Com validação de erro
```

## 🎯 Funcionalidades Agora Disponíveis

### ✅ Alteração de Portas pela Interface

**ProcessingServer:**

1. Abra o servidor
2. Altere os valores nos campos "Porta TCP" e "Porta UDP"
3. Clique em "Iniciar TCP" ou "Iniciar UDP"
4. O servidor usa as portas especificadas

**ProcessingClient:**

1. Abra o cliente
2. Altere os valores nos campos "Host", "TCP" e "UDP"
3. Clique em "Conectar"
4. O cliente conecta nas portas especificadas

### ✅ Configuração Persistente

Use o menu principal → "Configurações de Rede / Roteador" para:

- Alterar IP padrão do servidor
- Alterar portas TCP e UDP padrão
- Configurar simulação de latência
- Configurar simulação de perda de pacotes

**As configurações são salvas em `config.properties`**

## 🚀 Como Executar

### Linux/Mac:

```bash
./run.sh
```

**Nota:** Se der erro de permissão, execute primeiro: `chmod +x run.sh`

### Windows:

```cmd
run.bat
```

### Alternativa (Execução Manual):

```bash
# Compilar
mkdir -p bin
javac -d bin src/main/java/*.java
cp src/main/java/config.properties bin/

# Executar
cd bin
java Main
```

## 📝 Valores Padrão

- **IP do Servidor:** 127.0.0.1
- **Porta TCP:** 5000
- **Porta UDP:** 5001
- **Auth Key:** ViniShow
- **Password:** SuperViniD
- **Latência:** 0 ms
- **Perda de Pacotes:** 0%

## ✅ Status dos Arquivos

| Arquivo               | Status       | Descrição                               |
| --------------------- | ------------ | --------------------------------------- |
| Config.java           | ✅ Corrigido | Getters com fallback, auth configurável |
| FileServer.java       | ✅ Corrigido | Porta configurável via Config           |
| FileClient.java       | ✅ Corrigido | Auth corrigido                          |
| ProcessingServer.java | ✅ Corrigido | Portas editáveis na UI                  |
| ProcessingClient.java | ✅ Corrigido | Host e portas editáveis na UI           |
| Main.java             | ✅ OK        | Sem alterações necessárias              |
| ConfigDialog.java     | ✅ OK        | Sem alterações necessárias              |

## 🎉 Resultado Final

A aplicação agora está **totalmente funcional** com:

- ✅ Configurações carregadas corretamente
- ✅ Portas alteráveis pela interface do usuário
- ✅ Autenticação funcionando
- ✅ Servidor e cliente podem ser executados normalmente
- ✅ Todas as tarefas (SORT, SEARCH, MATRIX, PRIME, FILE) funcionando
- ✅ Monitoramento UDP funcionando
