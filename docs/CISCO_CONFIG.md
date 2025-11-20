# CONFIGURAÇÃO CISCO PACKET TRACER

## Comandos Completos para Configuração dos Roteadores

## ROTEADOR R1

### Configuração Básica
```cisco
Router> enable
Router# configure terminal
Router(config)# hostname R1
R1(config)# 
```

### Configuração de Interfaces
```cisco
R1(config)# interface FastEthernet0/0
R1(config-if)# description LAN1 - Rede PC1
R1(config-if)# ip address 192.168.0.1 255.255.0.0
R1(config-if)# no shutdown
R1(config-if)# exit

R1(config)# interface FastEthernet0/1
R1(config-if)# description WAN - Conexao com R2
R1(config-if)# ip address 172.16.0.2 255.240.0.0
R1(config-if)# no shutdown
R1(config-if)# exit
```

### Configuração NAT
```cisco
R1(config)# ip nat inside source static tcp 172.16.0.1 5000 interface FastEthernet0/0 5000
R1(config)# ip nat inside source static tcp 172.16.0.1 5001 interface FastEthernet0/0 5001
R1(config)# ip nat inside source static udp 172.16.0.1 5001 interface FastEthernet0/0 5001

R1(config)# interface FastEthernet0/0
R1(config-if)# ip nat inside
R1(config-if)# exit

R1(config)# interface FastEthernet0/1
R1(config-if)# ip nat outside
R1(config-if)# exit
```

### Rotas Estáticas
```cisco
R1(config)# ip route 10.0.0.0 255.0.0.0 172.16.0.1
R1(config)# ip route 172.16.0.0 255.240.0.0 172.16.0.1
```

### Salvar Configuração
```cisco
R1(config)# exit
R1# write memory
R1# copy running-config startup-config
```

### Verificação
```cisco
R1# show ip interface brief
R1# show ip nat translations
R1# show ip route
```

## ROTEADOR R2

### Configuração Básica
```cisco
Router> enable
Router# configure terminal
Router(config)# hostname R2
R2(config)# 
```

### Configuração de Interfaces
```cisco
R2(config)# interface FastEthernet0/0
R2(config-if)# description WAN - Conexao com R1
R2(config-if)# ip address 172.16.0.1 255.240.0.0
R2(config-if)# no shutdown
R2(config-if)# exit

R2(config)# interface FastEthernet0/1
R2(config-if)# description LAN1 - Conexao com R3
R2(config-if)# ip address 10.0.0.2 255.0.0.0
R2(config-if)# no shutdown
R2(config-if)# exit
```

### Configuração NAT
```cisco
R2(config)# ip nat inside source static tcp 10.0.0.100 5000 172.16.0.1 5000
R2(config)# ip nat inside source static tcp 10.0.0.100 5001 172.16.0.1 5001
R2(config)# ip nat inside source static udp 10.0.0.100 5001 172.16.0.1 5001

R2(config)# interface FastEthernet0/0
R2(config-if)# ip nat outside
R2(config-if)# exit

R2(config)# interface FastEthernet0/1
R2(config-if)# ip nat inside
R2(config-if)# exit
```

### Rotas Estáticas
```cisco
R2(config)# ip route 192.168.0.0 255.255.0.0 172.16.0.2
R2(config)# ip route 10.0.0.0 255.0.0.0 10.0.0.1
```

### Salvar Configuração
```cisco
R2(config)# exit
R2# write memory
R2# copy running-config startup-config
```

### Verificação
```cisco
R2# show ip interface brief
R2# show ip nat translations
R2# show ip route
```

## ROTEADOR R3

### Configuração Básica
```cisco
Router> enable
Router# configure terminal
Router(config)# hostname R3
R3(config)# 
```

### Configuração de Interfaces
```cisco
R3(config)# interface FastEthernet0/0
R3(config-if)# description WAN - Conexao com R2
R3(config-if)# ip address 10.0.0.1 255.0.0.0
R3(config-if)# no shutdown
R3(config-if)# exit

R3(config)# interface FastEthernet0/1
R3(config-if)# description LAN1 - Rede PC2
R3(config-if)# ip address 10.0.0.1 255.0.0.0
R3(config-if)# no shutdown
R3(config-if)# exit
```

### Configuração NAT
```cisco
R3(config)# ip nat inside source static tcp 10.0.0.100 5000 10.0.0.1 5000
R3(config)# ip nat inside source static tcp 10.0.0.100 5001 10.0.0.1 5001
R3(config)# ip nat inside source static udp 10.0.0.100 5001 10.0.0.1 5001

R3(config)# interface FastEthernet0/0
R3(config-if)# ip nat outside
R3(config-if)# exit

R3(config)# interface FastEthernet0/1
R3(config-if)# ip nat inside
R3(config-if)# exit
```

### Rotas Estáticas
```cisco
R3(config)# ip route 0.0.0.0 0.0.0.0 10.0.0.2
R3(config)# ip route 192.168.0.0 255.255.0.0 10.0.0.2
R3(config)# ip route 172.16.0.0 255.240.0.0 10.0.0.2
```

### Salvar Configuração
```cisco
R3(config)# exit
R3# write memory
R3# copy running-config startup-config
```

### Verificação
```cisco
R3# show ip interface brief
R3# show ip nat translations
R3# show ip route
```

## CONFIGURAÇÃO DOS PCs

### PC1 (Cliente)

**Via Interface Gráfica:**
1. Desktop → IP Configuration
2. IP Address: `192.168.1.10`
3. Subnet Mask: `255.255.0.0`
4. Default Gateway: `192.168.0.1`
5. DNS Server: `8.8.8.8` (opcional)

**Via Command Prompt:**
```
PC> ip 192.168.1.10 255.255.0.0 192.168.0.1
```

### PC2 (Servidor)

**Via Interface Gráfica:**
1. Desktop → IP Configuration
2. IP Address: `10.0.0.100`
3. Subnet Mask: `255.0.0.0`
4. Default Gateway: `10.0.0.1`
5. DNS Server: `8.8.8.8` (opcional)

**Via Command Prompt:**
```
PC> ip 10.0.0.100 255.0.0.0 10.0.0.1
```

## TESTES DE CONECTIVIDADE

### No PC1 (Cliente)
```
PC1> ping 192.168.0.1
PC1> ping 172.16.0.2
PC1> ping 172.16.0.1
PC1> ping 10.0.0.2
PC1> ping 10.0.0.1
PC1> ping 10.0.0.100
PC1> tracert 10.0.0.100
```

### No PC2 (Servidor)
```
PC2> ping 10.0.0.1
PC2> ping 10.0.0.2
PC2> ping 172.16.0.1
PC2> ping 172.16.0.2
PC2> ping 192.168.0.1
PC2> ping 192.168.1.10
PC2> tracert 192.168.1.10
```

### Nos Roteadores

**R1:**
```cisco
R1# ping 172.16.0.1
R1# ping 192.168.1.10
R1# show ip nat translations
R1# debug ip nat
```

**R2:**
```cisco
R2# ping 172.16.0.2
R2# ping 10.0.0.1
R2# show ip nat translations
R2# debug ip nat
```

**R3:**
```cisco
R3# ping 10.0.0.2
R3# ping 10.0.0.100
R3# show ip nat translations
R3# debug ip nat
```

## COMANDOS DE DIAGNÓSTICO

### Verificar Interfaces
```cisco
show ip interface brief
show interfaces
show ip interface FastEthernet0/0
```

### Verificar NAT
```cisco
show ip nat translations
show ip nat statistics
clear ip nat translation *
```

### Verificar Rotas
```cisco
show ip route
show ip route static
show ip protocols
```

### Verificar Conectividade
```cisco
ping <IP>
traceroute <IP>
show arp
```

### Debug (usar com cuidado)
```cisco
debug ip nat
debug ip packet
debug ip icmp
```

**Para desativar debug:**
```cisco
no debug all
undebug all
```

## TABELA DE ENDEREÇAMENTO RESUMIDA

| Dispositivo | Interface | IP           | Máscara     | Gateway     |
| ----------- | --------- | ------------ | ----------- | ----------- |
| **PC1**     | Eth0      | 192.168.1.10 | 255.255.0.0 | 192.168.0.1 |
| **R1**      | Fa0/0     | 192.168.0.1  | 255.255.0.0 | -           |
| **R1**      | Fa0/1     | 172.16.0.2   | 255.240.0.0 | -           |
| **R2**      | Fa0/0     | 172.16.0.1   | 255.240.0.0 | -           |
| **R2**      | Fa0/1     | 10.0.0.2     | 255.0.0.0   | -           |
| **R3**      | Fa0/0     | 10.0.0.1     | 255.0.0.0   | -           |
| **R3**      | Fa0/1     | 10.0.0.1     | 255.0.0.0   | -           |
| **PC2**     | Eth0      | 10.0.0.100   | 255.0.0.0   | 10.0.0.1    |

## PORTAS REDIRECIONADAS

| Porta | Protocolo | Origem | Destino | Finalidade       |
| ----- |---------- | ------ | ------- | ---------------- |
| 5000  | TCP       | PC1    | PC2     | Envio de tarefas |
| 5001  | TCP       | PC1    | PC2     | Controle         |
| 5001  | UDP       | PC1    | PC2     | Monitoramento    |

## TROUBLESHOOTING COMUM

### Problema 1: Ping não funciona entre PCs

**Verificar:**
```cisco
# Em cada roteador
show ip route
show ip interface brief
ping <próximo_hop>
```

**Solução:** Verificar rotas estáticas e interfaces ativas.

### Problema 2: NAT não traduz

**Verificar:**
```cisco
show ip nat translations
show ip nat statistics
```

**Solução:** 
- Confirmar `ip nat inside/outside` nas interfaces corretas
- Verificar regras NAT

### Problema 3: Aplicação não conecta

**Verificar:**
1. Servidor está rodando?
2. Firewall bloqueando portas?
3. Port forwarding configurado?

**Testar:**
```
PC1> telnet 10.0.0.100 5000
```

## BACKUP DE CONFIGURAÇÃO

### Salvar para TFTP (se disponível)
```cisco
copy running-config tftp:
# Digite IP do servidor TFTP
# Digite nome do arquivo
```

### Salvar para Flash
```cisco
copy running-config flash:
# Digite nome do arquivo: backup-config
```

### Restaurar de Flash
```cisco
copy flash:backup-config running-config
```
