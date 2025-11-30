# 📋 TUTORIAL COMPLETO - O QUE FALTA FAZER

## 🎯 Visão Geral

Este tutorial mostra **exatamente o que falta** no projeto e **como fazer de forma simples**.

### ✅ O que JÁ está pronto:

- ✅ Código Java completo (Cliente, Servidor, Main)
- ✅ Interface gráfica Swing
- ✅ Documentação dos comandos Cisco
- ✅ Documentação dos filtros Wireshark
- ✅ README.md estruturado
- ✅ Scripts de execução (run.bat, run.sh)

### ❌ O que FALTA fazer:

#### 1. **Screenshots e imagens** (MAIS IMPORTANTE)

- Topologia no Cisco Packet Tracer
- Configuração dos roteadores
- Telas da aplicação rodando
- Capturas do Wireshark
- Testes de ping/conectividade

#### 2. **Arquivo .pkt do Cisco Packet Tracer**

- Rede completa configurada

#### 3. **Testes práticos**

- Executar servidor e cliente
- Capturar tráfego no Wireshark
- Fazer testes de conectividade

---

## 📸 PARTE 1: CAPTURAS DE TELA (OBRIGATÓRIO)

### 1.1 Cisco Packet Tracer - Topologia Completa

**O QUE FAZER:**

1. Abra o Cisco Packet Tracer
2. Monte a rede seguindo o diagrama abaixo
3. Tire um screenshot da topologia completa

**TOPOLOGIA A CRIAR:**

```
     PC1                    PC2
  (Cliente)              (Servidor)
192.168.1.10            10.0.0.100
     |                      |
     |                      |
   [R1]------------------[R2]------------------[R3]
Fa0/0 | Fa0/1      Fa0/0 | Fa0/1      Fa0/0 | Fa0/1
192.168.0.1          172.16.0.1           10.0.0.1
        172.16.0.2          10.0.0.2
```

**COMPONENTES NECESSÁRIOS:**

- 2 PCs (Generic PC)
- 3 Roteadores (2911 ou 2901)
- 4 Switches (2960) - opcional, mas recomendado
- Cabos straight-through

**ONDE SALVAR:**

- Screenshot: `docs/screenshots/01_topologia_completa.png`

---

### 1.2 Cisco - Configuração dos Roteadores (3 screenshots)

**O QUE FAZER:**
Para cada roteador (R1, R2, R3):

1. Clique no roteador
2. Vá em CLI
3. Copie e cole os comandos do arquivo `docs/CISCO_CONFIG.md`
4. Tire screenshot mostrando os comandos executados
5. Execute: `show ip interface brief`
6. Tire screenshot do resultado

**ONDE SALVAR:**

- `docs/screenshots/02_config_r1.png`
- `docs/screenshots/03_config_r2.png`
- `docs/screenshots/04_config_r3.png`
- `docs/screenshots/05_r1_interfaces.png`
- `docs/screenshots/06_r2_interfaces.png`
- `docs/screenshots/07_r3_interfaces.png`

**COMANDOS DE VERIFICAÇÃO:**

```cisco
show ip interface brief
show ip route
show ip nat translations
```

---

### 1.3 Teste de Conectividade (ping)

**O QUE FAZER:**

1. No PC1, abra Command Prompt
2. Execute: `ping 10.0.1.100`
3. Tire screenshot mostrando sucesso (Reply from...)
4. Execute: `tracert 10.0.1.100`
5. Tire screenshot mostrando o caminho pelos 3 roteadores

**ONDE SALVAR:**

- `docs/screenshots/08_ping_pc1_pc2.png`
- `docs/screenshots/09_traceroute.png`

---

### 1.4 Aplicação Java - Servidor

**O QUE FAZER:**

1. Compile e execute o servidor:
   ```bash
   cd src/main/java
   javac *.java
   java Main
   ```
2. Escolha opção **1** (Executar Servidor)
3. Clique em "Iniciar TCP Server"
4. Clique em "Iniciar UDP Server"
5. Tire screenshot da tela completa do servidor ativo

**ONDE SALVAR:**

- `docs/screenshots/10_servidor_iniciado.png`

---

### 1.5 Aplicação Java - Cliente

**O QUE FAZER:**

1. Execute o cliente (escolha opção **2** no Main)
2. Configure:
   - Host: `127.0.0.1` (ou IP do servidor)
   - TCP Port: `5000`
   - UDP Port: `5001`
3. Selecione uma tarefa (ex: SORT - Ordenação)
4. Configure parâmetros (ex: 1000 elementos, Quick Sort)
5. Clique em "Executar Tarefa"
6. Tire screenshot mostrando o resultado

**ONDE SALVAR:**

- `docs/screenshots/11_cliente_conectado.png`
- `docs/screenshots/12_tarefa_sort.png`
- `docs/screenshots/13_resultado_sort.png`

**REPETIR PARA OUTRAS TAREFAS:**

- Screenshot de SEARCH (busca)
- Screenshot de MATRIX (multiplicação de matrizes)
- Screenshot de PRIME (números primos)
- Screenshot de FILE (transferência de arquivo)

**ONDE SALVAR:**

- `docs/screenshots/14_tarefa_search.png`
- `docs/screenshots/15_tarefa_matrix.png`
- `docs/screenshots/16_tarefa_prime.png`
- `docs/screenshots/17_tarefa_file.png`

---

### 1.6 Wireshark - Captura TCP (PORTA 5000)

**O QUE FAZER:**

1. Abra Wireshark
2. Selecione interface de rede (Ethernet ou Wi-Fi)
3. Inicie captura
4. No cliente, execute UMA tarefa
5. Pare a captura
6. Aplique filtro: `tcp.port == 5000`
7. Tire screenshot mostrando:
   - Three-way handshake (SYN, SYN-ACK, ACK)
   - Pacotes de dados (PSH, ACK)
   - Finalização (FIN, ACK)

**ONDE SALVAR:**

- `docs/screenshots/18_wireshark_tcp_geral.png`
- `docs/screenshots/19_wireshark_handshake.png`
- `docs/screenshots/20_wireshark_tcp_dados.png`

**ANÁLISE DETALHADA:** 8. Clique em um pacote SYN 9. Expanda "Transmission Control Protocol" 10. Tire screenshot mostrando detalhes do TCP header

**ONDE SALVAR:**

- `docs/screenshots/21_tcp_header_detalhes.png`

---

### 1.7 Wireshark - Captura UDP (PORTA 5001)

**O QUE FAZER:**

1. No Wireshark, aplique filtro: `udp.port == 5001`
2. No cliente, observe as métricas sendo atualizadas
3. Tire screenshot mostrando:
   - Pacotes UDP periódicos (a cada 2 segundos)
   - Pacote de requisição
   - Pacote de resposta

**ONDE SALVAR:**

- `docs/screenshots/22_wireshark_udp_geral.png`

**ANÁLISE DO PAYLOAD:** 4. Clique em um pacote de resposta (maior) 5. Expanda "User Datagram Protocol" 6. Expanda "Data" 7. Tire screenshot mostrando o JSON com métricas

**ONDE SALVAR:**

- `docs/screenshots/23_udp_payload_json.png`

---

### 1.8 Comparação TCP vs UDP

**O QUE FAZER:**

1. No Wireshark, use filtro: `(tcp.port == 5000 || udp.port == 5001)`
2. Tire screenshot mostrando ambos os protocolos lado a lado
3. Observe:
   - TCP: Mais pacotes (ACKs, handshake)
   - UDP: Menos pacotes (direto)

**ONDE SALVAR:**

- `docs/screenshots/24_comparacao_tcp_udp.png`

---

## 🏗️ PARTE 2: CRIAR ARQUIVO DO PACKET TRACER

### 2.1 Configurar a Rede Completa

**PASSO A PASSO SIMPLIFICADO:**

1. **Adicionar Dispositivos:**

   - 2 PCs (arraste da aba End Devices → Generic PC)
   - 3 Roteadores (aba Network Devices → Routers → 2911)

2. **Conectar com Cabos:**

   - PC1 → R1 (Fa0/0)
   - R1 (Fa0/1) → R2 (Fa0/0)
   - R2 (Fa0/1) → R3 (Fa0/0)
   - R3 (Fa0/1) → PC2

3. **Configurar IPs dos PCs:**

   **PC1:**

   - Clique em PC1 → Desktop → IP Configuration
   - IP: `192.168.1.10`
   - Mask: `255.255.0.0`
   - Gateway: `192.168.0.1`

   **PC2:**

   - Clique em PC2 → Desktop → IP Configuration
   - IP: `10.0.0.100`
   - Mask: `255.0.0.0`
   - Gateway: `10.0.0.1`

4. **Configurar Roteadores:**

   - Use os comandos completos em `docs/CISCO_CONFIG.md`
   - Copie e cole bloco por bloco no CLI

5. **Testar:**

   - No PC1: `ping 10.0.0.100`
   - Deve ter sucesso!

6. **Salvar:**
   - File → Save As
   - Nome: `serverShow_network.pkt`
   - Local: pasta raiz do projeto

**ONDE SALVAR:**

- `serverShow_network.pkt` (na raiz)

---

## 🧪 PARTE 3: EXECUTAR E TESTAR

### 3.1 Teste Básico - Localhost

**OBJETIVO:** Testar se o código funciona sem rede

**PASSOS:**

1. Compile:

   ```bash
   cd src/main/java
   javac *.java
   ```

2. Execute servidor e cliente juntos:

   ```bash
   java Main
   # Escolha opção 3
   ```

3. Configure cliente:

   - Host: `127.0.0.1`
   - TCP: `5000`
   - UDP: `5001`

4. Teste TODAS as tarefas:
   - ✅ SORT (Ordenação)
   - ✅ SEARCH (Busca)
   - ✅ MATRIX (Multiplicação)
   - ✅ PRIME (Números primos)
   - ✅ FILE (Transferência)

**TIRE SCREENSHOTS DE CADA TESTE!**

---

### 3.2 Teste com Wireshark - Localhost

**PASSOS:**

1. Abra Wireshark
2. Inicie captura em "Loopback: lo0" (macOS) ou "Adapter for loopback" (Windows)
3. Execute uma tarefa no cliente
4. Pare captura
5. Aplique filtros:
   - `tcp.port == 5000`
   - `udp.port == 5001`

**TIRE SCREENSHOTS!**

---

### 3.3 Teste em Rede Real (Opcional, mas recomendado)

**OBJETIVO:** Testar em 2 computadores diferentes

**REQUISITOS:**

- 2 computadores na mesma rede
- Java instalado em ambos

**PASSOS:**

**No Computador 1 (Servidor):**

1. Descubra seu IP:

   ```bash
   # macOS/Linux:
   ifconfig | grep inet

   # Windows:
   ipconfig
   ```

2. Execute o servidor:
   ```bash
   java Main
   # Opção 1
   ```

**No Computador 2 (Cliente):**

1. Execute o cliente:

   ```bash
   java Main
   # Opção 2
   ```

2. Configure host com o IP do servidor (ex: `192.168.0.105`)

3. Teste as tarefas

**TIRE SCREENSHOTS!**

---

## 📝 PARTE 4: COMPLETAR O RELATÓRIO

### 4.1 Inserir Screenshots no RELATORIO.md

**O QUE FAZER:**

1. Abra `docs/RELATORIO.md`
2. Procure por `#TODO`
3. Substitua por links para as imagens:

**EXEMPLO:**

```markdown
# Antes:

#TODO
[INSERIR SCREENSHOT: Topologia completa no Packet Tracer]

# Depois:

![Topologia Completa](screenshots/01_topologia_completa.png)
```

**LISTA DE TODAS AS SUBSTITUIÇÕES:**

```markdown
### Seção 5.1 - Topologia

![Topologia Completa](screenshots/01_topologia_completa.png)

### Seção 5.3 - Configuração dos Roteadores

![Configuração R1](screenshots/02_config_r1.png)
![Configuração R2](screenshots/03_config_r2.png)
![Configuração R3](screenshots/04_config_r3.png)

### Seção 5.4 - Teste de Conectividade

![Ping PC1 para PC2](screenshots/08_ping_pc1_pc2.png)
![Traceroute](screenshots/09_traceroute.png)

### Seção 6.1 - Servidor

![Servidor Iniciado](screenshots/10_servidor_iniciado.png)

### Seção 6.2 - Cliente

![Cliente Conectado](screenshots/11_cliente_conectado.png)

### Seção 6.3 - Execução de Tarefas

![Tarefa SORT](screenshots/12_tarefa_sort.png)
![Tarefa SEARCH](screenshots/14_tarefa_search.png)
![Tarefa MATRIX](screenshots/15_tarefa_matrix.png)

### Seção 7.1 - Captura TCP

![Wireshark TCP Geral](screenshots/18_wireshark_tcp_geral.png)
![Handshake TCP](screenshots/19_wireshark_handshake.png)
![Dados TCP](screenshots/20_wireshark_tcp_dados.png)

### Seção 7.2 - Captura UDP

![Wireshark UDP Geral](screenshots/22_wireshark_udp_geral.png)
![Payload UDP JSON](screenshots/23_udp_payload_json.png)

### Seção 7.4 - Análise de Roteamento

![Traceroute](screenshots/09_traceroute.png)
```

---

## ✅ CHECKLIST FINAL

Use este checklist para garantir que tudo está completo:

### Screenshots Cisco Packet Tracer:

- [ ] Topologia completa
- [ ] Configuração R1 (CLI)
- [ ] Configuração R2 (CLI)
- [ ] Configuração R3 (CLI)
- [ ] Interfaces R1 (show ip interface brief)
- [ ] Interfaces R2 (show ip interface brief)
- [ ] Interfaces R3 (show ip interface brief)
- [ ] Ping PC1 → PC2 (sucesso)
- [ ] Traceroute PC1 → PC2

### Screenshots Aplicação:

- [ ] Servidor iniciado
- [ ] Cliente conectado
- [ ] Tarefa SORT executada
- [ ] Resultado SORT
- [ ] Tarefa SEARCH executada
- [ ] Tarefa MATRIX executada
- [ ] Tarefa PRIME executada
- [ ] Tarefa FILE executada

### Screenshots Wireshark:

- [ ] Tráfego TCP geral (porta 5000)
- [ ] Three-way handshake detalhado
- [ ] Pacotes de dados TCP
- [ ] Detalhes do TCP header
- [ ] Tráfego UDP geral (porta 5001)
- [ ] Payload UDP com JSON
- [ ] Comparação TCP vs UDP

### Arquivos:

- [ ] `serverShow_network.pkt` (arquivo Packet Tracer)
- [ ] Todas as screenshots na pasta `docs/screenshots/`
- [ ] `RELATORIO.md` com todos os `#TODO` substituídos

### Testes:

- [ ] Compilação sem erros
- [ ] Servidor inicia corretamente
- [ ] Cliente conecta ao servidor
- [ ] Todas as 5 tarefas funcionam
- [ ] Wireshark captura TCP e UDP
- [ ] Ping funciona no Packet Tracer

---

## 🚀 ORDEM RECOMENDADA DE EXECUÇÃO

Para fazer tudo de forma eficiente, siga esta ordem:

### DIA 1: Configuração de Rede (2-3 horas)

1. ✅ Montar topologia no Packet Tracer
2. ✅ Configurar os 3 roteadores
3. ✅ Testar conectividade (ping)
4. ✅ Tirar todos os screenshots do Packet Tracer
5. ✅ Salvar arquivo .pkt

### DIA 2: Testes da Aplicação (1-2 horas)

1. ✅ Compilar o código
2. ✅ Executar servidor
3. ✅ Executar cliente
4. ✅ Testar todas as 5 tarefas
5. ✅ Tirar todos os screenshots da aplicação

### DIA 3: Análise Wireshark (1-2 horas)

1. ✅ Capturar tráfego TCP
2. ✅ Capturar tráfego UDP
3. ✅ Analisar pacotes
4. ✅ Tirar todos os screenshots do Wireshark

### DIA 4: Documentação (1 hora)

1. ✅ Inserir screenshots no RELATORIO.md
2. ✅ Revisar texto do relatório
3. ✅ Verificar checklist final
4. ✅ Testar se tudo funciona

---

## 💡 DICAS IMPORTANTES

### Para Screenshots:

- Use resolução alta (não tire fotos da tela com celular!)
- Deixe as janelas maximizadas
- No Wireshark, ajuste as colunas para ficarem visíveis
- No Packet Tracer, use zoom adequado

### Para Packet Tracer:

- Salve frequentemente (Ctrl+S)
- Teste cada roteador individualmente
- Use `write memory` para salvar config
- Se der erro, use `no debug all`

### Para Wireshark:

- Feche outros aplicativos que usam rede
- Use filtros para reduzir ruído
- Capture por no máximo 30 segundos
- Salve as capturas (.pcap) para referência

### Para o Código Java:

- Se der erro de compilação, verifique o Java JDK
- Use `javac -version` para verificar versão
- Mantenha servidor rodando enquanto testa cliente
- Aguarde 2-3 segundos entre testes

---

## 🆘 RESOLUÇÃO DE PROBLEMAS

### "Ping não funciona no Packet Tracer"

- Verifique se interfaces estão UP (show ip interface brief)
- Verifique rotas (show ip route)
- Aguarde alguns segundos (ARP precisa resolver)
- Teste ping entre roteadores vizinhos primeiro

### "Cliente não conecta ao servidor"

- Verifique se servidor está rodando
- Confirme IP e portas
- Teste com localhost (127.0.0.1) primeiro
- Verifique firewall do sistema

### "Wireshark não captura nada"

- Selecione a interface correta
- Para localhost, use "Loopback"
- Execute a tarefa DURANTE a captura
- Use filtros corretos

### "Erro ao compilar Java"

- Verifique se tem JDK instalado (não apenas JRE)
- Use Java 8 ou superior
- Compile na pasta `src/main/java`
- Verifique se todos os .java estão na mesma pasta

---

## 📚 RECURSOS ADICIONAIS

### Tutoriais em Vídeo:

- Cisco Packet Tracer: https://www.youtube.com/netacad
- Wireshark: https://www.youtube.com/wireshark
- Java Sockets: https://www.youtube.com/watch?v=BULb0SAlXfI

### Documentação:

- Packet Tracer: https://www.netacad.com/
- Wireshark: https://www.wireshark.org/docs/
- Java Network: https://docs.oracle.com/javase/tutorial/networking/

---

## 🎓 RESUMO EXECUTIVO

**O que você PRECISA fazer:**

1. 📸 Tirar ~25 screenshots (rede + aplicação + wireshark)
2. 💾 Criar arquivo .pkt do Packet Tracer
3. 📝 Inserir screenshots no RELATORIO.md
4. ✅ Verificar checklist final

**Tempo estimado:** 6-8 horas total (distribuído em 4 dias)

**Prioridade:**

- 🔥 ALTA: Screenshots e arquivo .pkt
- ⚠️ MÉDIA: Testes práticos extensivos
- ℹ️ BAIXA: Melhorias no código (já está pronto)

**Boa sorte! 🚀**
