# 📡 Guia de Uso via LAN / Radmin

## ⚙️ Configuração Inicial

### No PC do Servidor (seu amigo):

1. **Abrir o programa** e escolher opção "Servidor"
2. **Porta padrão**: 5000 (ou configure outra no `config.properties`)
3. **Liberar a porta no Firewall**:
   - Windows: Firewall do Windows → Regras de Entrada → Nova Regra → Porta TCP 5000
   - Ou adicionar o programa Java/aplicação nas exceções
4. **Descobrir o IP**:
   - Abrir CMD: `ipconfig`
   - Anotar o **IPv4** (ex: `192.168.1.100`)
   - Se usar Radmin VPN: usar o IP da rede Radmin
5. **Iniciar o Servidor**: Clicar em "▶ Iniciar Servidor"

---

### No seu PC (Cliente):

1. **Abrir o programa** e escolher opção "Cliente"
2. **Configurar conexão**:
   - **IP/Host do Servidor**: Colocar o IPv4 do seu amigo
     - Exemplo LAN local: `192.168.1.100`
     - Exemplo Radmin VPN: `26.123.45.67` (IP da rede Radmin)
   - **Porta**: `5000` (mesma do servidor)
3. **Escolher o arquivo**: Clicar em "Escolher arquivo..."
4. **Enviar**: Clicar em "Enviar"

---

## 🔐 Autenticação

O sistema usa **autenticação** para segurança:

- **Chave de Autenticação**: `ViniShow` (padrão)
- **Senha**: `SuperViniD` (padrão)

**Ambos os PCs precisam ter as MESMAS credenciais** configuradas no arquivo `config.properties`:

```properties
auth.key=ViniShow
password=SuperViniD
```

Para alterar, edite o arquivo `config.properties` ou use o menu "Configurações" no programa.

---

## 🌐 Testando a Conexão

### 1. Teste Local (mesmo PC):

- Servidor: `localhost` ou `127.0.0.1`
- Porta: `5000`

### 2. Teste na Rede Local (mesma Wi-Fi/Ethernet):

- Servidor: IP local do amigo (ex: `192.168.1.100`)
- Ping no CMD: `ping 192.168.1.100`

### 3. Teste via Radmin VPN:

- Servidor: IP da rede Radmin (ex: `26.x.x.x`)
- Ambos precisam estar conectados na mesma rede Radmin
- Ping no CMD: `ping [IP_RADMIN]`

---

## 🚨 Resolução de Problemas

### ❌ "Connection refused" ou "Timeout"

- ✅ Verificar se o servidor está rodando
- ✅ Confirmar que a porta está correta (mesma nos dois PCs)
- ✅ Liberar porta no firewall do servidor
- ✅ Testar com `ping` se os PCs se "veem" na rede
- ✅ Se usar Radmin VPN: ambos estão na mesma rede?

### ❌ "Autenticação falhou"

- ✅ Confirmar que `auth.key` e `password` são IDÊNTICOS nos dois PCs
- ✅ Verificar no arquivo `config.properties` de ambos

### ❌ Servidor não inicia

- ✅ Porta 5000 já está em uso? Trocar para outra (ex: 5555)
- ✅ Fechar outros programas que usem a mesma porta

---

## 📂 Onde os arquivos são salvos?

Por padrão, na pasta que você escolheu no botão "📁 Pasta".

Os arquivos recebidos são salvos com **timestamp** no nome:

- Original: `relatorio.pdf`
- Salvo como: `20241202_143052_relatorio.pdf`

---

## 💡 Exemplo Prático

**Cenário**: Você quer enviar `trabalho.pdf` para seu amigo

1. **Amigo** (IP: `192.168.1.50`):

   - Abre o servidor
   - Inicia na porta 5000
   - Libera porta no firewall
   - Aguarda conexão

2. **Você**:
   - Abre o cliente
   - Host: `192.168.1.50`
   - Porta: `5000`
   - Escolhe `trabalho.pdf`
   - Clica em "Enviar"
   - ✅ Arquivo transferido com segurança!

---

## 🔧 Alterando a Porta (se necessário)

Edite o arquivo `config.properties`:

```properties
tcpPort=5555
```

Ou use o botão "Configurações" no menu principal do programa.

**Lembre-se**: Servidor e cliente devem usar a MESMA porta!

---

## 📝 Notas Importantes

- ✅ **Funciona em redes locais** (mesma Wi-Fi/Ethernet)
- ✅ **Funciona com VPNs** como Radmin, Hamachi, ZeroTier
- ✅ **Autenticação obrigatória** (segurança)
- ✅ **Barra de progresso** em tempo real
- ✅ **Suporta qualquer tipo de arquivo**
- ⚠️ Para uso pela **Internet pública**, é necessário **port forwarding** no roteador

---

## 🎯 Resumo Ultra-Rápido

**Servidor:**

```
1. Iniciar servidor
2. Anotar IP (ipconfig)
3. Liberar porta no firewall
```

**Cliente:**

```
1. Colocar IP do servidor
2. Colocar porta (mesma do servidor)
3. Escolher arquivo
4. Enviar!
```

🚀 **Pronto!** Agora vocês podem transferir arquivos via LAN/Radmin com segurança!
