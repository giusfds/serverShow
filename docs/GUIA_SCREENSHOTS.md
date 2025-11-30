# 📸 GUIA: COMO TIRAR BONS SCREENSHOTS

## Ferramentas por Sistema Operacional

### 🍎 macOS

#### Opção 1: Captura de Tela Inteira

```
Cmd + Shift + 3
```

- Captura tela inteira
- Salva na Área de Trabalho

#### Opção 2: Captura de Área Selecionada (RECOMENDADO)

```
Cmd + Shift + 4
```

- Cursor vira cruz (+)
- Arraste para selecionar área
- Solte para capturar
- Salva na Área de Trabalho

#### Opção 3: Captura de Janela Específica

```
Cmd + Shift + 4, depois Space
```

- Cursor vira câmera
- Clique na janela desejada
- Captura janela com sombra

#### Opção 4: Screenshot para Clipboard

Adicione `Ctrl` a qualquer comando acima para copiar para clipboard ao invés de salvar.

#### Ferramenta Avançada:

```
Cmd + Shift + 5
```

- Abre barra de ferramentas de captura
- Opções: tela inteira, janela, área selecionada
- Pode gravar vídeo também

---

### 🪟 Windows

#### Opção 1: Tela Inteira

```
Print Screen (PrtScn)
```

- Captura tela inteira
- Copia para clipboard
- Cole no Paint/Word

#### Opção 2: Janela Ativa

```
Alt + Print Screen
```

- Captura apenas janela ativa
- Copia para clipboard

#### Opção 3: Snipping Tool (RECOMENDADO)

```
Win + Shift + S
```

- Tela escurece
- Selecione área
- Copia para clipboard
- Notificação aparece → clique para editar/salvar

#### Opção 4: Ferramenta de Captura Completa

```
Procure: "Ferramenta de Captura" ou "Snip & Sketch"
```

- Modo retangular, forma livre, janela, tela inteira
- Pode anotar após captura

---

### 🐧 Linux

#### Ubuntu/GNOME:

```
Print Screen         → Tela inteira
Alt + Print Screen   → Janela ativa
Shift + Print Screen → Área selecionada
```

#### KDE:

```
Spectacle (ferramenta padrão)
```

#### Linha de Comando:

```bash
gnome-screenshot              # Tela inteira
gnome-screenshot -w           # Janela
gnome-screenshot -a           # Área
```

---

## 📋 CHECKLIST: ANTES DE TIRAR O SCREENSHOT

### ✅ Preparação Geral:

- [ ] Feche abas/programas desnecessários
- [ ] Limpe área de trabalho (minimize janelas não relacionadas)
- [ ] Verifique se não tem informações sensíveis visíveis
- [ ] Ajuste resolução da janela (maximize ou redimensione)
- [ ] Verifique iluminação da tela (não muito escuro/claro)

### ✅ Para Packet Tracer:

- [ ] Organize dispositivos na topologia (não deixe sobreposto)
- [ ] Use zoom adequado (100% ou 125%)
- [ ] Oculte painéis laterais se não forem necessários
- [ ] Destaque dispositivos importantes (se houver opção)

### ✅ Para Aplicação Java:

- [ ] Maximize janela ou use tamanho padrão
- [ ] Fonte legível (não muito pequena)
- [ ] Dados visíveis completos (não cortados)
- [ ] Aguarde carregamento completo antes de capturar

### ✅ Para Wireshark:

- [ ] Ajuste largura das colunas para ficarem visíveis
- [ ] Aplique filtro antes de capturar
- [ ] Selecione pacotes relevantes
- [ ] Expanda seções necessárias
- [ ] Role para mostrar informações importantes

### ✅ Para Terminal/CLI:

- [ ] Fonte legível
- [ ] Comandos e saídas completos
- [ ] Prompt visível (mostrando usuário/diretório)
- [ ] Sem erros de digitação

---

## 🎨 DICAS DE QUALIDADE

### Resolução:

✅ **Mínimo:** 1280x720 (HD)
✅ **Recomendado:** 1920x1080 (Full HD)
❌ **Evite:** Menos de 1024x768

### Formato:

✅ **PNG** (sem perda, melhor para texto e interfaces)
✅ **JPG** (aceitável, menor tamanho)
❌ **BMP** (muito grande)

### Tamanho do Arquivo:

✅ **Ideal:** 200KB - 2MB
⚠️ **Aceitável:** 100KB - 5MB
❌ **Evite:** Mais de 10MB

**Dica:** Se arquivo PNG estiver muito grande, converta para JPG com qualidade 85-90%.

### Clareza:

✅ Texto legível sem zoom
✅ Cores naturais
✅ Sem desfoque
✅ Sem moiré (padrões ondulados)
❌ Foto da tela com celular (NUNCA faça isso!)

---

## 📸 PROCESSO RECOMENDADO

### PASSO 1: Preparar a Tela

1. Abra apenas o programa necessário
2. Configure visualização (maximize, ajuste colunas, etc.)
3. Execute a ação que deseja documentar
4. Aguarde carregamento completo

### PASSO 2: Capturar

**macOS:** `Cmd + Shift + 4` → selecione área
**Windows:** `Win + Shift + S` → selecione área
**Linux:** `Shift + Print Screen` → selecione área

### PASSO 3: Revisar

1. Abra a imagem
2. Verifique se está legível
3. Confirme que capturou tudo necessário
4. Se não estiver bom, tire novamente

### PASSO 4: Salvar/Renomear

1. Use nome EXATO da lista (ex: `01_topologia_completa.png`)
2. Salve em `docs/screenshots/`
3. Marque no checklist

### PASSO 5: Verificar

Abra a imagem em tamanho real e confirme:

- [ ] Texto é legível?
- [ ] Informações importantes estão visíveis?
- [ ] Não há informações sensíveis?
- [ ] Qualidade está boa?

---

## 🎯 DICAS ESPECÍFICAS POR TIPO

### 📡 Cisco Packet Tracer:

**Topologia:**

- Organize dispositivos em linha/grid
- Use zoom 100-125%
- Capture área da topologia + barra de ferramentas

**CLI (Comandos):**

- Maximize janela CLI
- Execute 1-3 comandos por screenshot
- Inclua prompt no topo (mostra hostname)
- Mostre resultado completo

**Testes (ping/traceroute):**

- Execute comando
- Aguarde resultado completo
- Capture comando + resultado
- Certifique-se que "Reply from..." está visível

### 💻 Aplicação Java:

**Servidor:**

- Janela completa (título + conteúdo)
- Botões visíveis
- Log mostrando atividade
- Métricas atualizadas

**Cliente:**

- Janela completa
- Formulário de configuração visível
- Área de resultado com dados
- Status de conexão

**Execução de Tarefa:**

- ANTES: Configuração preenchida
- DEPOIS: Resultado mostrado
- Métricas de tempo/performance visíveis

### 🔍 Wireshark:

**Lista de Pacotes:**

- Ajuste largura das colunas:
  - No.: 50px
  - Time: 80px
  - Source: 120px
  - Destination: 120px
  - Protocol: 80px
  - Info: restante
- Filtro visível no topo
- 10-20 pacotes visíveis

**Detalhes do Pacote:**

- Capture lista + detalhes + dados brutos
- Expanda seção relevante (TCP/UDP)
- Destaque campos importantes

**Payload:**

- Expanda "Data"
- Se for texto, capture legível
- Se for hex, capture hex + ASCII

---

## 🚫 ERROS COMUNS A EVITAR

❌ **Fotografar a tela com celular**
✅ Use as ferramentas de captura do sistema

❌ **Captura com texto ilegível**
✅ Aumente fonte ou capture em resolução maior

❌ **Informações cortadas**
✅ Role/redimensione antes de capturar

❌ **Muita informação irrelevante**
✅ Capture apenas área necessária

❌ **Capturas borradas/tremidas**
✅ Use ferramentas do sistema, não fotos

❌ **Fundo com informações pessoais**
✅ Minimize janelas não relacionadas

❌ **Nome de arquivo genérico (Screenshot1.png)**
✅ Use nomes descritivos da lista

❌ **Formato BMP ou TIFF**
✅ Use PNG ou JPG

❌ **Arquivo muito grande (>10MB)**
✅ Comprima ou converta para JPG

❌ **Baixa resolução**
✅ Capture em pelo menos 720p

---

## 🔧 PÓS-PROCESSAMENTO (Opcional)

Se quiser melhorar os screenshots (não obrigatório):

### Ferramentas Gratuitas:

**macOS:**

- Preview (nativo) - cortar, anotar
- Skitch - anotar, setas, destaques

**Windows:**

- Paint (nativo) - cortar, redimensionar
- Paint.NET - gratuito, mais recursos
- Greenshot - gratuito, captura + edição

**Linux:**

- GIMP - edição completa
- Shutter - captura + edição
- Flameshot - captura + anotação

**Multiplataforma:**

- GIMP - edição avançada
- Inkscape - vetores, anotações
- Draw.io - diagramas, anotações

### Edições Úteis:

✅ **Cortar:** Remover bordas desnecessárias
✅ **Redimensionar:** Reduzir tamanho mantendo legibilidade
✅ **Anotar:** Adicionar setas/caixas para destacar
✅ **Comprimir:** Reduzir tamanho do arquivo
⚠️ **Evite filtros/efeitos:** Mantenha aparência natural

---

## 📊 EXEMPLOS DE BOAS PRÁTICAS

### ✅ BOM:

```
✓ Texto legível
✓ Informações completas
✓ Fundo limpo
✓ Resolução adequada
✓ Nome descritivo
✓ Formato PNG
✓ Tamanho razoável (~500KB)
```

### ❌ RUIM:

```
✗ Texto borrado
✗ Informações cortadas
✗ Fundo poluído com outras janelas
✗ Baixa resolução
✗ Nome genérico (Screenshot1.png)
✗ Formato BMP
✗ Tamanho excessivo (15MB)
```

---

## 🎓 WORKFLOW RECOMENDADO

Para este projeto, siga este workflow eficiente:

### 1. Prepare Ambiente (5 min)

- Feche programas não relacionados
- Limpe área de trabalho
- Tenha checklist aberto

### 2. Para Cada Screenshot (2-5 min cada):

```
a) Prepare tela (ajuste janela, execute ação)
b) Capture (Cmd+Shift+4 ou Win+Shift+S)
c) Verifique qualidade
d) Salve com nome correto
e) Marque no checklist
f) Próximo
```

### 3. Revisão Final (10 min)

- Abra cada screenshot
- Confirme legibilidade
- Verifique nomes
- Confirme pasta correta
- Marque checklist final

**Tempo total estimado:** ~2-3 horas para 24 screenshots

---

## 📱 BONUS: CONVERSÃO E OTIMIZAÇÃO

### Converter PNG para JPG (se necessário):

**macOS (Terminal):**

```bash
sips -s format jpeg imagem.png --out imagem.jpg
```

**Windows (Paint):**

```
1. Abrir imagem no Paint
2. File → Save As → JPEG
3. Qualidade: 90%
```

**Linux (Terminal):**

```bash
convert imagem.png -quality 90 imagem.jpg
```

### Redimensionar Imagem:

**macOS (Terminal):**

```bash
sips -Z 1920 imagem.png
```

**Windows (Paint):**

```
1. Abrir imagem
2. Resize
3. Manter proporção
4. Largura: 1920px
```

**Linux (Terminal):**

```bash
convert imagem.png -resize 1920x imagem_nova.png
```

### Comprimir PNG:

**Online (grátis):**

- TinyPNG.com (recomendado)
- Compressor.io

**Terminal (ImageMagick):**

```bash
convert input.png -quality 90 output.png
```

---

## ✅ CHECKLIST FINAL POR SCREENSHOT

Antes de considerar um screenshot "pronto":

- [ ] Capturei a área correta
- [ ] Texto está legível
- [ ] Informações importantes estão visíveis
- [ ] Não há informações sensíveis
- [ ] Qualidade está boa
- [ ] Nome do arquivo está correto
- [ ] Salvei em docs/screenshots/
- [ ] Marquei no checklist
- [ ] Arquivo tem tamanho razoável (<5MB)
- [ ] Formato é PNG ou JPG

---

## 🆘 PROBLEMAS COMUNS

### "Screenshot ficou muito grande (>10MB)"

**Solução:**

- Converta PNG → JPG (qualidade 85-90%)
- Ou use ferramenta de compressão (TinyPNG.com)

### "Texto está borrado/ilegível"

**Solução:**

- Aumente tamanho da fonte no programa
- Capture em resolução maior
- Use ferramenta de captura ao invés de redimensionar

### "Esqueci de capturar algo importante"

**Solução:**

- Reproduza o cenário
- Capture novamente
- Substitua arquivo anterior

### "Capturei janela errada"

**Solução:**

- Delete o arquivo
- Tire novamente
- Verifique antes de salvar

---

**BOA SORTE COM OS SCREENSHOTS! 📸**

Lembre-se: qualidade > quantidade. É melhor tirar menos screenshots com qualidade do que muitos ruins!
