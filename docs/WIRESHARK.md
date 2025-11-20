# ANÁLISE NO WIRESHARK

## Filtros Essenciais para o Projeto

## FILTROS BÁSICOS

### Filtrar por Protocolo
```wireshark
tcp                    # Apenas pacotes TCP
udp                    # Apenas pacotes UDP
ip                     # Apenas pacotes IP
icmp                   # Apenas ICMP (ping)
```

### Filtrar por Porta
```wireshark
tcp.port == 5000       # TCP na porta 5000
udp.port == 5001       # UDP na porta 5001
tcp.port == 5000 || udp.port == 5001    # Ambas
```

### Filtrar por IP
```wireshark
ip.addr == 192.168.1.10                 # Qualquer tráfego com este IP
ip.src == 192.168.1.10                  # Origem
ip.dst == 10.0.0.100                    # Destino
ip.addr == 192.168.1.10 && ip.addr == 10.0.0.100   # Entre PC1 e PC2
```


## FILTROS ESPECÍFICOS DO PROJETO

### Tráfego da Aplicação
```wireshark
# Todo tráfego TCP/UDP da aplicação
tcp.port == 5000 || udp.port == 5001

# Apenas dados (sem handshake)
tcp.port == 5000 && tcp.len > 0

# Apenas handshake TCP
tcp.flags.syn == 1

# Apenas ACKs
tcp.flags.ack == 1

# Pacotes com dados significativos
tcp.len > 100
```

### Análise TCP Detalhada
```wireshark
# Three-way handshake
tcp.flags.syn == 1 && tcp.flags.ack == 0    # SYN
tcp.flags.syn == 1 && tcp.flags.ack == 1    # SYN-ACK
tcp.flags.syn == 0 && tcp.flags.ack == 1    # ACK

# Finalização de conexão
tcp.flags.fin == 1

# Retransmissões
tcp.analysis.retransmission

# Pacotes duplicados
tcp.analysis.duplicate_ack

# Window size (controle de fluxo)
tcp.window_size > 0
```

### Análise UDP
```wireshark
# Pacotes UDP da aplicação
udp.port == 5001

# UDP com payload
udp.length > 8

# Requisições de status (contém "STATUS")
udp contains "STATUS"

# Respostas com JSON (contém "{")
udp contains "{"
```

## ANÁLISE PASSO A PASSO

### 1. Capturar Handshake TCP

**Filtro:**
```wireshark
tcp.port == 5000 && (tcp.flags.syn == 1 || tcp.flags.ack == 1)
```

**O que procurar:**
1. **SYN** (Cliente → Servidor)
   - Seq = 0
   - Flags: SYN
   
2. **SYN-ACK** (Servidor → Cliente)
   - Seq = 0
   - Ack = 1
   - Flags: SYN, ACK
   
3. **ACK** (Cliente → Servidor)
   - Ack = 1
   - Flags: ACK

**Screenshot para o relatório:**
- Selecione os 3 pacotes
- File → Export Packet Dissections → As Plain Text
- Ou tire screenshot da lista de pacotes

### 2. Analisar Transferência de Dados TCP

**Filtro:**
```wireshark
tcp.port == 5000 && tcp.len > 0
```

**O que procurar:**
- **PSH, ACK**: Pacotes com dados
- **Sequence numbers**: Verificar ordenação
- **ACK numbers**: Confirmações
- **Window Size**: Controle de fluxo
- **Payload**: Dados da tarefa/resultado

**Análise detalhada de um pacote:**
1. Clique no pacote
2. Expand: Internet Protocol Version 4
3. Expand: Transmission Control Protocol
4. Expand: Data (payload)

**Para o relatório:**
- Screenshot mostrando:
  - Lista de pacotes com dados
  - Detalhes de um pacote (TCP header)
  - Payload em hexadecimal

### 3. Analisar Tráfego UDP

**Filtro:**
```wireshark
udp.port == 5001
```

**O que procurar:**
- **Sem handshake**: Pacotes diretos
- **Requisições**: Pequenos pacotes (< 50 bytes)
- **Respostas**: Pacotes maiores com JSON
- **Timestamps**: Intervalo de ~2 segundos

**Análise de payload:**
1. Clique em pacote de resposta
2. Expand: User Datagram Protocol
3. Expand: Data
4. Clique direito → Copy → ...as a Hex Stream
5. Converta para texto para ver JSON

**Para o relatório:**
- Screenshot mostrando:
  - Par de pacotes (requisição + resposta)
  - Detalhes UDP header
  - Payload JSON legível

### 4. Comparar TCP vs UDP

**Filtro:**
```wireshark
(tcp.port == 5000 || udp.port == 5001) && ip.addr == 192.168.1.10 && ip.addr == 10.0.0.100
```

**Criar tabela comparativa:**

| Característica      | TCP (5000) | UDP (5001) |
| ------------------- | ---------- | ---------- |
| Handshake           | 3 pacotes  | 0 pacotes  |
| Overhead por pacote | ~40 bytes  | ~8 bytes   |
| ACKs                | Sim        | Não        |
| Ordenação garantida | Sim        | Não        |
| Retransmissão       | Sim        | Não        |

**Calcular overhead:**
```
TCP Overhead = (Total bytes TCP) - (Payload bytes)
UDP Overhead = (Total bytes UDP) - (Payload bytes)
```
