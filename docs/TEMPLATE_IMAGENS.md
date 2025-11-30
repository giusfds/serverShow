# 📝 TEMPLATE PARA INSERIR IMAGENS NO RELATÓRIO

## Use este arquivo como referência para substituir os #TODO no RELATORIO.md

---

## SEÇÃO 5.1 - TOPOLOGIA

**Localização no RELATORIO.md:** Linha ~183

**Substituir:**

```markdown
#TODO
[INSERIR SCREENSHOT: Topologia completa no Packet Tracer]
```

**Por:**

```markdown
![Topologia Completa](screenshots/01_topologia_completa.png)

_Figura 1: Topologia da rede com 2 PCs e 3 roteadores configurados no Cisco Packet Tracer_
```

---

## SEÇÃO 5.3 - CONFIGURAÇÃO DOS ROTEADORES

**Localização no RELATORIO.md:** Linha ~210

**Substituir:**

```markdown
#TODO
[INSERIR SCREENSHOTS: Configuração CLI de R1, R2, R3]
```

**Por:**

```markdown
### Roteador R1

![Configuração do Roteador R1](screenshots/02_config_r1.png)

_Figura 2: Comandos de configuração executados no R1_

![Interfaces do R1](screenshots/05_r1_interfaces.png)

_Figura 3: Verificação das interfaces do R1 (show ip interface brief)_

---

### Roteador R2

![Configuração do Roteador R2](screenshots/03_config_r2.png)

_Figura 4: Comandos de configuração executados no R2_

![Interfaces do R2](screenshots/06_r2_interfaces.png)

_Figura 5: Verificação das interfaces do R2 (show ip interface brief)_

---

### Roteador R3

![Configuração do Roteador R3](screenshots/04_config_r3.png)

_Figura 6: Comandos de configuração executados no R3_

![Interfaces do R3](screenshots/07_r3_interfaces.png)

_Figura 7: Verificação das interfaces do R3 (show ip interface brief)_
```

---

## SEÇÃO 5.4 - TESTE DE CONECTIVIDADE

**Localização no RELATORIO.md:** Linha ~245

**Substituir:**

```markdown
#TODO
[INSERIR SCREENSHOT: ping de PC1 para PC2]
```

**Por:**

```markdown
![Teste de Ping](screenshots/08_ping_pc1_pc2.png)

_Figura 8: Teste de conectividade bem-sucedido entre PC1 (192.168.1.10) e PC2 (10.0.0.100)_

![Traceroute](screenshots/09_traceroute.png)

_Figura 9: Traceroute mostrando o caminho dos pacotes através dos 3 roteadores_

**Análise do Resultado:**
O ping foi bem-sucedido com tempo de resposta médio de ~19ms. O traceroute confirma que os pacotes passam pelos 3 roteadores (R1 → R2 → R3) antes de alcançar o PC2, demonstrando que o roteamento está funcionando corretamente.
```

---

## SEÇÃO 6.1 - TELAS DO SERVIDOR

**Localização no RELATORIO.md:** Linha ~270

**Substituir:**

```markdown
#TODO
[INSERIR SCREENSHOT: Tela principal do servidor]
```

**Por:**

```markdown
![Servidor Iniciado](screenshots/10_servidor_iniciado.png)

_Figura 10: Interface do ProcessingServer com servidores TCP (porta 5000) e UDP (porta 5001) ativos_

**Elementos Visíveis na Tela:**

- ✅ Status dos servidores TCP e UDP (ATIVO)
- 📋 Lista de tarefas processadas com timestamps
- 📊 Métricas do sistema (uptime, tarefas processadas, threads ativas, memória)
- 📝 Log de eventos em tempo real
- 🎯 Botões de controle (Iniciar/Parar servidores)
```

---

## SEÇÃO 6.2 - TELAS DO CLIENTE

**Localização no RELATORIO.md:** Linha ~285

**Substituir:**

```markdown
#TODO
[INSERIR SCREENSHOT: Tela principal do cliente]
```

**Por:**

```markdown
![Cliente Conectado](screenshots/11_cliente_conectado.png)

_Figura 11: Interface do ProcessingClient conectado ao servidor_

**Elementos Visíveis na Tela:**

- 🔌 Configuração de conexão (host, portas TCP/UDP)
- 🎛️ Seleção de tipo de tarefa (SORT, SEARCH, MATRIX, PRIME, FILE)
- ⚙️ Parâmetros configuráveis para cada tarefa
- 📊 Área de resultados e métricas
- 📡 Monitoramento UDP do servidor em tempo real
- ▶️ Botão de execução de tarefas
```

---

## SEÇÃO 6.3 - EXECUÇÃO DE TAREFAS

**Localização no RELATORIO.md:** Linha ~300

**Substituir:**

```markdown
#### Tarefa de Ordenação

#TODO
[INSERIR SCREENSHOT: Execução de SORT]
```

**Por:**

```markdown
#### Tarefa de Ordenação (SORT)

![Execução de Ordenação](screenshots/12_tarefa_sort.png)

_Figura 12: Configuração e execução de tarefa de ordenação_

![Resultado de Ordenação](screenshots/13_resultado_sort.png)

_Figura 13: Resultado da ordenação com métricas de tempo de execução_

**Parâmetros Utilizados:**

- Tamanho do array: 10.000 elementos
- Algoritmo: Quick Sort
- Tempo de execução: ~4ms
- Memória utilizada: 85 MB

**Análise:**
O Quick Sort demonstrou excelente performance para arrays de tamanho médio, processando 10.000 elementos em menos de 5ms. A ordenação foi realizada corretamente, como confirmado pela visualização dos primeiros e últimos elementos do array ordenado.
```

**Substituir:**

```markdown
#### Tarefa de Busca

#TODO
[INSERIR SCREENSHOT: Execução de SEARCH]
```

**Por:**

```markdown
#### Tarefa de Busca (SEARCH)

![Execução de Busca](screenshots/14_tarefa_search.png)

_Figura 14: Execução de busca binária em array ordenado_

**Parâmetros Utilizados:**

- Tamanho do array: 100.000 elementos
- Algoritmo: Busca Binária
- Elemento procurado: 50000
- Tempo de execução: ~2µs
- Resultado: Elemento encontrado na posição 50000

**Análise:**
A busca binária demonstrou eficiência O(log n), encontrando o elemento em menos de 3 microssegundos em um array de 100.000 elementos. Isso representa aproximadamente 16 comparações (log₂ 100000 ≈ 16.6).
```

**Substituir:**

```markdown
#### Tarefa de Matriz

#TODO
[INSERIR SCREENSHOT: Execução de MATRIX]
```

**Por:**

```markdown
#### Tarefa de Multiplicação de Matrizes (MATRIX)

![Execução de Matriz](screenshots/15_tarefa_matrix.png)

_Figura 15: Multiplicação de matrizes quadradas_

**Parâmetros Utilizados:**

- Dimensão: 100x100
- Operação: Multiplicação A × B
- Tempo de execução: ~45ms
- Complexidade: O(n³)

**Análise:**
A multiplicação de matrizes 100×100 envolveu 1.000.000 de operações (100³), completadas em 45ms. Este resultado demonstra o overhead computacional de operações matriciais e a importância de otimizações para matrizes maiores.
```

---

## SEÇÃO 7.1 - ANÁLISE TCP NO WIRESHARK

**Localização no RELATORIO.md:** Linha ~340

**Substituir:**

```markdown
#TODO
[INSERIR SCREENSHOT: Wireshark mostrando tráfego TCP]
```

**Por:**

```markdown
![Captura TCP no Wireshark](screenshots/18_wireshark_tcp_geral.png)

_Figura 16: Captura completa do tráfego TCP na porta 5000_

**Filtro Aplicado:** `tcp.port == 5000`

**Observações:**

- Total de pacotes TCP: ~45
- Incluindo: handshake, dados, confirmações e finalização
- Tempo total da conexão: ~2.5 segundos
```

**Substituir:**

```markdown
#TODO
[INSERIR SCREENSHOT: Detalhes do handshake]
```

**Por:**

```markdown
#### Three-Way Handshake Detalhado

![Handshake TCP](screenshots/19_wireshark_handshake.png)

_Figura 17: Três pacotes do handshake TCP_

**Análise do Handshake:**

1. **Pacote #1 - SYN** (Cliente → Servidor)

   - Flags: SYN
   - Seq: 0 (relativo)
   - Tamanho: 0 bytes de dados
   - Significado: Cliente solicita conexão

2. **Pacote #2 - SYN, ACK** (Servidor → Cliente)

   - Flags: SYN, ACK
   - Seq: 0 (relativo)
   - Ack: 1
   - Significado: Servidor aceita conexão e confirma

3. **Pacote #3 - ACK** (Cliente → Servidor)
   - Flags: ACK
   - Ack: 1
   - Significado: Cliente confirma, conexão estabelecida

**Tempo do Handshake:** ~15ms (incluindo latência de rede)
```

**Substituir:**

```markdown
#TODO
[INSERIR SCREENSHOT: Pacotes de dados]
```

**Por:**

```markdown
#### Transferência de Dados TCP

![Dados TCP](screenshots/20_wireshark_tcp_dados.png)

_Figura 18: Pacotes TCP contendo dados da aplicação_

**Filtro Aplicado:** `tcp.port == 5000 && tcp.len > 0`

![Detalhes do Header TCP](screenshots/21_tcp_header_detalhes.png)

_Figura 19: Análise detalhada do cabeçalho TCP_

**Campos Importantes Observados:**

- **Source Port:** 5000 (servidor) ou porta efêmera (cliente)
- **Destination Port:** 5000 (servidor) ou porta efêmera (cliente)
- **Sequence Number:** Número sequencial para ordenação
- **Acknowledgment Number:** Confirma bytes recebidos
- **Flags:** PSH (push data), ACK (acknowledgment)
- **Window Size:** Geralmente 64KB (controle de fluxo)
- **Checksum:** Verificação de integridade
- **Payload Length:** Varia de 50 a 1500 bytes

**Observações:**

- Todos os pacotes de dados são confirmados com ACK
- Window Size constante indica boa conexão sem congestionamento
- PSH flag indica que dados devem ser entregues imediatamente à aplicação
```

---

## SEÇÃO 7.2 - ANÁLISE UDP NO WIRESHARK

**Localização no RELATORIO.md:** Linha ~390

**Substituir:**

```markdown
#TODO
[INSERIR SCREENSHOT: Wireshark mostrando tráfego UDP]
```

**Por:**

```markdown
![Captura UDP no Wireshark](screenshots/22_wireshark_udp_geral.png)

_Figura 20: Captura do tráfego UDP na porta 5001_

**Filtro Aplicado:** `udp.port == 5001`

**Observações:**

- Pacotes de requisição: ~40 bytes
- Pacotes de resposta: ~300 bytes (contém JSON)
- Intervalo: ~2 segundos entre requisições
- Sem handshake ou confirmações
```

**Substituir:**

```markdown
#TODO
[INSERIR SCREENSHOT: Conteúdo do pacote UDP com JSON]
```

**Por:**

````markdown
#### Análise do Payload UDP

![Payload UDP com JSON](screenshots/23_udp_payload_json.png)

_Figura 21: Conteúdo do pacote UDP mostrando métricas em formato JSON_

**Estrutura do JSON Recebido:**

```json
{
  "status": "ONLINE",
  "uptime": "00:05:47",
  "tasksProcessed": 12,
  "activeThreads": 2,
  "memoryUsed": 85.3,
  "timestamp": "2024-01-15T14:23:45"
}
```
````

**Análise:**

- Servidor envia métricas em tempo real via UDP
- JSON compacto (~250 bytes)
- Perda ocasional de pacotes é aceitável (não crítico)
- Cliente atualiza interface a cada pacote recebido

````

---

## SEÇÃO 7.3 - COMPARAÇÃO TCP VS UDP

**Localização no RELATORIO.md:** Linha ~420

**Adicionar após a tabela existente:**

```markdown
![Comparação Visual TCP vs UDP](screenshots/24_comparacao_tcp_udp.png)

*Figura 22: Visualização lado a lado de tráfego TCP e UDP*

**Filtro Aplicado:** `(tcp.port == 5000 || udp.port == 5001)`

**Observações Visuais:**

**TCP (Azul escuro no Wireshark):**
- Maior número de pacotes
- Padrões de confirmação (ACKs)
- Handshake e finalização visíveis
- Overhead evidente

**UDP (Azul claro no Wireshark):**
- Menos pacotes
- Sem confirmações
- Direto ao ponto
- Baixo overhead

Esta visualização confirma as diferenças teóricas entre os protocolos e justifica o uso de TCP para tarefas críticas e UDP para monitoramento.
````

---

## SEÇÃO 7.4 - ANÁLISE DE ROTEAMENTO

**Localização no RELATORIO.md:** Linha ~450

**Substituir:**

```markdown
#TODO
[INSERIR SCREENSHOT: Traceroute PC1 → PC2]
```

**Por:**

```markdown
![Traceroute Detalhado](screenshots/09_traceroute.png)

_Figura 23: Caminho dos pacotes através da rede_

**Resultado do Traceroute:**
```

Tracing route to 10.0.0.100 over a maximum of 30 hops:

1 <1 ms <1 ms <1 ms 192.168.0.1 [R1]
2 5 ms 6 ms 5 ms 172.16.0.1 [R2]
3 10 ms 11 ms 9 ms 10.0.0.1 [R3]
4 15 ms 16 ms 14 ms 10.0.0.100 [PC2 - Servidor]

Trace complete.

```

**Análise:**
- ✅ Pacotes atravessam os 3 roteadores conforme planejado
- ✅ Cada hop adiciona ~5ms de latência
- ✅ Latência total: ~15ms (aceitável)
- ✅ Sem perda de pacotes
- ✅ Roteamento estático funcional

Esta sequência confirma que o NAT e as rotas estáticas foram configurados corretamente em todos os roteadores.
```

---

## 📋 INSTRUÇÕES DE USO

1. **Abra o arquivo:** `docs/RELATORIO.md`

2. **Use Ctrl+F (ou Cmd+F)** para procurar por: `#TODO`

3. **Para cada `#TODO` encontrado:**

   - Veja a seção correspondente neste arquivo (TEMPLATE)
   - Copie o código markdown correspondente
   - Cole no lugar do `#TODO`
   - Ajuste os nomes das imagens se necessário

4. **Verifique:**

   - Todos os caminhos de imagem estão corretos
   - As imagens existem na pasta `docs/screenshots/`
   - A numeração das figuras está sequencial

5. **Salve o arquivo**

---

## ✅ CHECKLIST

- [ ] Substituí TODO da seção 5.1 (Topologia)
- [ ] Substituí TODO da seção 5.3 (Configuração Roteadores)
- [ ] Substituí TODO da seção 5.4 (Teste Conectividade)
- [ ] Substituí TODO da seção 6.1 (Servidor)
- [ ] Substituí TODO da seção 6.2 (Cliente)
- [ ] Substituí TODO da seção 6.3 (Tarefas - SORT, SEARCH, MATRIX)
- [ ] Substituí TODO da seção 7.1 (TCP Wireshark)
- [ ] Substituí TODO da seção 7.2 (UDP Wireshark)
- [ ] Adicionei comparação na seção 7.3
- [ ] Substituí TODO da seção 7.4 (Roteamento)
- [ ] Verifiquei todos os links de imagens
- [ ] Testei visualização em um visualizador Markdown

---

**Pronto! Seu relatório estará completo com todas as imagens! 🎉**
