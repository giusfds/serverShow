# Sistema de Processamento Distribuído

<div style="text-align: center">

  [![Java](https://img.shields.io/badge/Java-8+-orange.svg)](https://www.java.com)
  [![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
  [![Network](https://img.shields.io/badge/Network-TCP%2FUDP-green.svg)]()
</div>

## Sobre o Projeto

Sistema cliente-servidor completo para **processamento distribuído de algoritmos computacionais**, desenvolvido para demonstrar conceitos de:

- **Redes de Computadores** (TCP/UDP, NAT, Roteamento)
- **Arquitetura de Computadores** (Processamento paralelo)
- **Sistemas Operacionais** (Multithreading)
- **Análise de Algoritmos** (Comparação de complexidade)

## Funcionalidades

### Servidor

- Servidor TCP multithread (porta 5000)
- Servidor UDP para monitoramento (porta 5001)
- Autenticação SHA-256
- Processamento de 5 tipos de tarefas:
  - Ordenação (Bubble, Quick, Merge Sort)
  - Busca (Linear, Binária)
  - Multiplicação de Matrizes
  - Números Primos (Crivo de Eratóstenes)
  - Transferência de Arquivos

### Cliente

- Interface gráfica Java Swing
- Conexão TCP configurável
- Monitoramento UDP em tempo real
- Execução assíncrona de tarefas
- Visualização de resultados e métricas

## Início Rápido

### Pré-requisitos
- Java JDK 8 ou superior
- Cisco Packet Tracer (para testes de rede)
- Wireshark (para análise de pacotes)

### Compilação e Execução

```bash
git clone https://github.com/giusfds/serverShow.git
cd serverShow
```

**Windows:**
```bash
run.bat
```

**Linux/Mac:**
```bash
chmod +x run.sh
./run.sh
```

Escolha a opção **3** para executar servidor e cliente simultaneamente.

## Tecnologias

- **Linguagem:** Java 8+
- **Interface:** Java Swing
- **Rede:** Java Sockets (TCP/UDP)
- **Concorrência:** ExecutorService, SwingWorker
- **Segurança:** SHA-256
- **Ferramentas:** Cisco Packet Tracer, Wireshark

## Autores

- André Luis Silva de Paula
- Breno Pires Santos
- Caio Faria Diniz
- Giuseppe Sena Cordeiro
- Matheus Fagundes Araujo
- Vinícius Miranda de Araújo

## Links Úteis

- [Java Documentation](https://docs.oracle.com/javase/8/docs/)
- [Cisco Packet Tracer](https://www.netacad.com/courses/packet-tracer)
- [Wireshark](https://www.wireshark.org/)
- [TCP/IP Guide](http://www.tcpipguide.com/)
