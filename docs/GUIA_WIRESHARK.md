# 🔍 GUIA RÁPIDO WIRESHARK

## Para Iniciantes - Passo a Passo Simples

### 📥 INSTALAÇÃO

**Windows/Mac/Linux:**

1. Acesse: https://www.wireshark.org/download.html
2. Baixe a versão para seu sistema
3. Instale (deixe todas as opções padrão)
4. Reinicie o computador se solicitado

---

## 🚀 CAPTURA BÁSICA

### Passo 1: Iniciar Wireshark

1. Abra o Wireshark
2. Você verá uma lista de interfaces de rede

**Interfaces Comuns:**

- **Windows:**

  - `Ethernet` (cabo de rede)
  - `Wi-Fi` (rede sem fio)
  - `Adapter for loopback traffic` (localhost)

- **Mac:**

  - `en0` (rede sem fio)
  - `en1` (ethernet)
  - `lo0` (loopback/localhost)

- **Linux:**
  - `eth0` (ethernet)
  - `wlan0` (wi-fi)
  - `lo` (loopback)

### Passo 2: Escolher Interface

**Para testar LOCALHOST (mesmo computador):**

- Windows: `Adapter for loopback traffic`
- Mac: `Loopback: lo0`
- Linux: `lo`

**Para testar REDE (computadores diferentes):**

- Escolha a interface que você está usando (Wi-Fi ou Ethernet)

### Passo 3: Iniciar Captura

1. **Clique duplo** na interface escolhida
2. A captura começa IMEDIATAMENTE
3. Você verá pacotes aparecendo em tempo real

### Passo 4: Executar sua Aplicação

1. **DURANTE a captura**, execute:
   - Inicie o servidor
   - Inicie o cliente
   - Execute UMA tarefa
2. **Dica:** Não deixe capturando muito tempo (máx. 30 segundos)

### Passo 5: Parar Captura

1. Clique no botão **vermelho quadrado** (Stop)
2. A captura para
3. Agora você pode analisar os pacotes

---

## 🔎 FILTROS ESSENCIAIS

### Barra de Filtros

A barra de filtros fica no topo da janela (texto verde quando válido).

### Filtros para Este Projeto:

#### Ver APENAS TCP (porta 5000):

```
tcp.port == 5000
```

#### Ver APENAS UDP (porta 5001):

```
udp.port == 5001
```

#### Ver AMBOS (TCP E UDP):

```
tcp.port == 5000 || udp.port == 5001
```

#### Ver apenas HANDSHAKE TCP:

```
tcp.port == 5000 && tcp.flags.syn == 1
```

#### Ver apenas DADOS TCP:

```
tcp.port == 5000 && tcp.len > 0
```

#### Ver IPs específicos:

```
ip.addr == 192.168.1.10 && ip.addr == 10.0.0.100
```

### Como Aplicar Filtro:

1. Digite o filtro na barra
2. Pressione ENTER
3. A lista de pacotes é filtrada
4. Para limpar: clique no **X** ao lado da barra

---

## 📊 ANÁLISE DE PACOTES

### Entendendo a Interface:

**Janela dividida em 3 partes:**

1. **TOPO:** Lista de pacotes

   - Colunas: No., Time, Source, Destination, Protocol, Info

2. **MEIO:** Detalhes do pacote selecionado

   - Clique nos ▶ para expandir
   - Mostra cabeçalhos (Ethernet, IP, TCP/UDP, Dados)

3. **FUNDO:** Dados brutos (hexadecimal e ASCII)

### Analisando TCP:

1. Aplique filtro: `tcp.port == 5000`

2. Procure pelo **Handshake** (3 primeiros pacotes):

   ```
   1. [SYN]          Cliente → Servidor
   2. [SYN, ACK]     Servidor → Cliente
   3. [ACK]          Cliente → Servidor
   ```

3. Procure por **Dados**:

   - Info: `[PSH, ACK]` ou `Len > 0`
   - Clique no pacote
   - Expanda: `Transmission Control Protocol`
   - Veja: Sequence Number, ACK Number, Window Size

4. Procure por **Finalização**:
   ```
   [FIN, ACK]    Cliente → Servidor
   [FIN, ACK]    Servidor → Cliente
   [ACK]         Cliente → Servidor
   ```

### Analisando UDP:

1. Aplique filtro: `udp.port == 5001`

2. Observe:

   - Pacotes pequenos (requisição ~50 bytes)
   - Pacotes maiores (resposta com JSON ~200-500 bytes)

3. Ver conteúdo:
   - Clique em pacote de resposta (maior)
   - Expanda: `User Datagram Protocol`
   - Expanda: `Data`
   - Clique direito → `Copy` → `...as Printable Text`
   - Cole em editor de texto para ver JSON

---

## 📸 CAPTURAS PARA O PROJETO

### Screenshot 1: TCP Geral

**Filtro:** `tcp.port == 5000`

**Capturar:**

- Lista completa de pacotes TCP
- Destacar handshake (3 primeiros)

**Como:**

1. Aplique filtro
2. Redimensione colunas para ficarem visíveis
3. Print Screen ou Cmd+Shift+4 (Mac)

---

### Screenshot 2: Handshake Detalhado

**Filtro:** `tcp.port == 5000 && tcp.flags.syn == 1`

**Capturar:**

- SYN
- SYN-ACK
- ACK (adicione manualmente ao filtro depois)

**Como:**

1. Clique no primeiro pacote [SYN]
2. Shift+Click no terceiro pacote [ACK]
3. Expanda detalhes TCP de um deles
4. Screenshot

---

### Screenshot 3: Dados TCP

**Filtro:** `tcp.port == 5000 && tcp.len > 0`

**Capturar:**

- Pacotes com dados [PSH, ACK]
- Detalhes do cabeçalho TCP

**Como:**

1. Aplique filtro
2. Clique em um pacote com dados
3. Expanda: `Transmission Control Protocol`
4. Screenshot mostrando lista + detalhes

---

### Screenshot 4: UDP Geral

**Filtro:** `udp.port == 5001`

**Capturar:**

- Lista de pacotes UDP
- Par requisição/resposta

**Como:**

1. Aplique filtro
2. Ajuste colunas
3. Screenshot da lista completa

---

### Screenshot 5: Payload UDP

**Filtro:** `udp.port == 5001`

**Capturar:**

- Dados do pacote (JSON)

**Como:**

1. Clique em pacote de resposta (maior)
2. Expanda: `Data`
3. Se aparecer texto, screenshot
4. Se não, clique direito → Copy → as Printable Text

---

### Screenshot 6: Comparação TCP vs UDP

**Filtro:** `(tcp.port == 5000 || udp.port == 5001)`

**Capturar:**

- Ambos os protocolos visíveis
- Diferenças de quantidade de pacotes

**Como:**

1. Aplique filtro
2. Ordene por protocolo (clique em coluna Protocol)
3. Screenshot

---

## 💡 DICAS IMPORTANTES

### Antes de Capturar:

✅ Feche outros programas que usam rede (navegador, Spotify, etc.)
✅ Limpe capturas antigas (Ctrl+E ou Cmd+E)
✅ Escolha a interface correta

### Durante a Captura:

✅ Execute APENAS a tarefa que quer analisar
✅ Não deixe capturando muito tempo (máx. 30-60 segundos)
✅ Faça UMA ação por captura (mais fácil de analisar)

### Depois da Captura:

✅ Salve a captura (.pcap) antes de fechar
✅ Use filtros para reduzir ruído
✅ Analise um protocolo de cada vez
✅ Tire screenshots com boa resolução

---

## 🆘 PROBLEMAS COMUNS

### "Não vejo nenhum pacote"

**Soluções:**

- Verifique se escolheu a interface correta
- Para localhost, use interface Loopback
- Execute a aplicação DURANTE a captura
- Verifique se servidor está rodando

### "Muitos pacotes, não acho os da aplicação"

**Soluções:**

- Use filtros: `tcp.port == 5000`
- Feche outros programas de rede
- Capture por menos tempo
- Execute só uma tarefa por vez

### "Não vejo conteúdo dos pacotes UDP"

**Soluções:**

- Clique direito no campo Data
- Copy → as Printable Text
- Cole em editor de texto
- O JSON deve aparecer

### "Wireshark não abre / Permission denied"

**Soluções:**

- **Mac/Linux:** Execute com sudo
  ```bash
  sudo wireshark
  ```
- **Windows:** Execute como Administrador

### "Interface não aparece na lista"

**Soluções:**

- Reinicie o Wireshark
- Reinstale com privilégios de administrador
- Verifique se drivers foram instalados

---

## 📚 COMANDOS ÚTEIS

### Salvar Captura:

```
File → Save As → meucaptura.pcap
```

### Exportar Pacotes:

```
File → Export Packet Dissections → As Plain Text
```

### Limpar Captura:

```
Ctrl+E (Windows/Linux)
Cmd+E (Mac)
```

### Procurar:

```
Ctrl+F (Windows/Linux)
Cmd+F (Mac)
```

---

## 📖 GLOSSÁRIO

- **SYN:** Synchronize - Inicia conexão TCP
- **ACK:** Acknowledgment - Confirma recebimento
- **PSH:** Push - Envia dados imediatamente
- **FIN:** Finish - Finaliza conexão
- **Seq:** Sequence Number - Número de sequência
- **Len:** Length - Tamanho dos dados
- **TTL:** Time To Live - Tempo de vida do pacote

---

## 🎓 RECURSOS ADICIONAIS

### Tutoriais Oficiais:

- https://www.wireshark.org/docs/wsug_html_chunked/

### Vídeos (YouTube):

- "Wireshark Tutorial for Beginners" - NetworkChuck
- "How to Use Wireshark" - David Bombal

### Prática:

- Capture tráfego de sites: `http`
- Capture DNS: `dns`
- Capture HTTPS: `tls`

---

## ✅ CHECKLIST FINAL

Antes de tirar os screenshots finais:

- [ ] Apliquei os filtros corretos
- [ ] Redimensionei as colunas para ficarem visíveis
- [ ] Selecionei os pacotes relevantes
- [ ] Expandi os detalhes necessários
- [ ] Tirei screenshot em boa resolução
- [ ] Salvei com o nome correto
- [ ] Salvei na pasta `docs/screenshots/`

---

**Boa sorte com as capturas! 🚀**
