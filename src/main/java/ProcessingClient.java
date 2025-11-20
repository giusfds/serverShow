import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * Cliente de Processamento Distribuído
 * Envia tarefas computacionais via TCP e monitora servidor via UDP
 */
public class ProcessingClient extends JFrame {
    // UI Components - Connection
    private JTextField hostField, tcpPortField, udpPortField;
    private JButton connectBtn, disconnectBtn, monitorBtn;

    // UI Components - Tasks
    private JComboBox<String> taskTypeCombo;
    private JPanel taskConfigPanel;
    private JButton executeBtn, clearBtn;
    private JTextArea resultsArea, metricsArea;
    private JProgressBar progressBar;
    private JLabel statusLabel;

    // Task-specific components
    private JTextField arraySizeField, targetField, matrixSizeField, primeLimitField;
    private JComboBox<String> sortAlgoCombo, searchAlgoCombo;
    private JButton chooseFileBtn;
    private JTextField filePathField;
    private JFileChooser fileChooser;

    // Network
    private String serverHost = "localhost";
    private int tcpPort = 5000;
    private int udpPort = 5001;
    private boolean connected = false;
    private ScheduledExecutorService monitorExecutor;

    // Configuration
    private static final String AUTH_KEY = Config.get("auth.key");
    private static final String PASSWORD = Config.get("password");

    public ProcessingClient() {
        super("Processing Client - Cliente de Processamento Distribuído");
        initUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 750);
        setLocationRelativeTo(null);
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Top Panel - Connection
        JPanel connectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        connectionPanel.setBorder(new TitledBorder("Conexão com Servidor"));

        hostField = new JTextField("localhost", 12);
        tcpPortField = new JTextField("5000", 5);
        udpPortField = new JTextField("5001", 5);
        connectBtn = new JButton("Conectar");
        disconnectBtn = new JButton("Desconectar");
        disconnectBtn.setEnabled(false);
        monitorBtn = new JButton("Monitorar (UDP)");
        monitorBtn.setEnabled(false);

        connectionPanel.add(new JLabel("Host:"));
        connectionPanel.add(hostField);
        connectionPanel.add(new JLabel("TCP:"));
        connectionPanel.add(tcpPortField);
        connectionPanel.add(new JLabel("UDP:"));
        connectionPanel.add(udpPortField);
        connectionPanel.add(connectBtn);
        connectionPanel.add(disconnectBtn);
        connectionPanel.add(monitorBtn);

        root.add(connectionPanel, BorderLayout.NORTH);

        // Center Panel - Tasks and Results
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        // Left - Task Configuration
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setBorder(new TitledBorder("Configuração de Tarefas"));

        JPanel taskTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        taskTypePanel.add(new JLabel("Tipo de Tarefa:"));
        taskTypeCombo = new JComboBox<>(new String[] {
                "Ordenação (SORT)",
                "Busca (SEARCH)",
                "Multiplicação de Matrizes (MATRIX)",
                "Números Primos (PRIME)",
                "Transferência de Arquivo (FILE)"
        });
        taskTypePanel.add(taskTypeCombo);
        leftPanel.add(taskTypePanel, BorderLayout.NORTH);

        // Dynamic task configuration panel
        taskConfigPanel = new JPanel(new CardLayout());
        taskConfigPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        taskConfigPanel.add(createSortPanel(), "Ordenação (SORT)");
        taskConfigPanel.add(createSearchPanel(), "Busca (SEARCH)");
        taskConfigPanel.add(createMatrixPanel(), "Multiplicação de Matrizes (MATRIX)");
        taskConfigPanel.add(createPrimePanel(), "Números Primos (PRIME)");
        taskConfigPanel.add(createFilePanel(), "Transferência de Arquivo (FILE)");

        leftPanel.add(taskConfigPanel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout());
        executeBtn = new JButton("Executar Tarefa");
        executeBtn.setEnabled(false);
        clearBtn = new JButton("Limpar Resultados");
        actionPanel.add(executeBtn);
        actionPanel.add(clearBtn);
        leftPanel.add(actionPanel, BorderLayout.SOUTH);

        // Right - Results and Metrics
        JPanel rightPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBorder(new TitledBorder("Resultados"));
        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        resultsArea.setBackground(Color.WHITE);
        resultsArea.setForeground(Color.BLACK);
        JScrollPane resultsScroll = new JScrollPane(resultsArea);
        resultsPanel.add(resultsScroll, BorderLayout.CENTER);

        JPanel metricsPanel = new JPanel(new BorderLayout());
        metricsPanel.setBorder(new TitledBorder("Métricas do Servidor (UDP)"));
        metricsArea = new JTextArea();
        metricsArea.setEditable(false);
        metricsArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        metricsArea.setBackground(Color.WHITE);
        metricsArea.setForeground(Color.BLACK);
        JScrollPane metricsScroll = new JScrollPane(metricsArea);
        metricsPanel.add(metricsScroll, BorderLayout.CENTER);

        rightPanel.add(resultsPanel);
        rightPanel.add(metricsPanel);

        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);
        root.add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel - Status
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        statusLabel = new JLabel("Desconectado. Insira o endereço do servidor e clique em Conectar.");
        statusLabel.setBorder(new EmptyBorder(5, 5, 5, 5));

        bottomPanel.add(progressBar, BorderLayout.NORTH);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);
        root.add(bottomPanel, BorderLayout.SOUTH);

        add(root);

        fileChooser = new JFileChooser();

        // Event Listeners
        connectBtn.addActionListener(e -> connect());
        disconnectBtn.addActionListener(e -> disconnect());
        monitorBtn.addActionListener(e -> toggleMonitoring());
        executeBtn.addActionListener(e -> executeTask());
        clearBtn.addActionListener(e -> resultsArea.setText(""));
        taskTypeCombo.addActionListener(e -> switchTaskPanel());
    }

    private JPanel createSortPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Tamanho do Array:"));
        arraySizeField = new JTextField("1000");
        panel.add(arraySizeField);

        panel.add(new JLabel("Algoritmo:"));
        sortAlgoCombo = new JComboBox<>(new String[] { "BUBBLE", "QUICK", "MERGE" });
        panel.add(sortAlgoCombo);

        panel.add(new JLabel("Descrição:"));
        panel.add(new JLabel("Ordena um array de números aleatórios"));

        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(new JLabel("Tamanho do Array:"));
        JTextField searchSizeField = new JTextField("1000");
        panel.add(searchSizeField);

        panel.add(new JLabel("Elemento a Buscar:"));
        targetField = new JTextField("500");
        panel.add(targetField);

        panel.add(new JLabel("Algoritmo:"));
        searchAlgoCombo = new JComboBox<>(new String[] { "LINEAR", "BINARY" });
        panel.add(searchAlgoCombo);

        panel.add(new JLabel("Descrição:"));
        panel.add(new JLabel("Busca um elemento em um array"));

        return panel;
    }

    private JPanel createMatrixPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.add(new JLabel("Tamanho da Matriz (NxN):"));
        matrixSizeField = new JTextField("50");
        panel.add(matrixSizeField);

        panel.add(new JLabel("Descrição:"));
        panel.add(new JLabel("Multiplica duas matrizes aleatórias"));

        return panel;
    }

    private JPanel createPrimePanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.add(new JLabel("Limite:"));
        primeLimitField = new JTextField("10000");
        panel.add(primeLimitField);

        panel.add(new JLabel("Descrição:"));
        panel.add(new JLabel("Encontra números primos até o limite"));

        return panel;
    }

    private JPanel createFilePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel fileSelectPanel = new JPanel(new BorderLayout(5, 5));
        filePathField = new JTextField();
        filePathField.setEditable(false);
        chooseFileBtn = new JButton("Escolher Arquivo");
        chooseFileBtn.addActionListener(e -> chooseFile());

        fileSelectPanel.add(new JLabel("Arquivo:"), BorderLayout.WEST);
        fileSelectPanel.add(filePathField, BorderLayout.CENTER);
        fileSelectPanel.add(chooseFileBtn, BorderLayout.EAST);

        panel.add(fileSelectPanel, BorderLayout.NORTH);
        panel.add(new JLabel("Envia um arquivo para o servidor processar/armazenar"), BorderLayout.CENTER);

        return panel;
    }

    private void switchTaskPanel() {
        CardLayout cl = (CardLayout) taskConfigPanel.getLayout();
        cl.show(taskConfigPanel, (String) taskTypeCombo.getSelectedItem());
    }

    private void chooseFile() {
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            filePathField.setText(file.getAbsolutePath());
        }
    }

    private void connect() {
        serverHost = hostField.getText().trim();
        tcpPort = Integer.parseInt(tcpPortField.getText().trim());
        udpPort = Integer.parseInt(udpPortField.getText().trim());

        // Test connection
        new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                try (Socket testSocket = new Socket()) {
                    testSocket.connect(new InetSocketAddress(serverHost, tcpPort), 3000);
                    return true;
                } catch (IOException e) {
                    return false;
                }
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        connected = true;
                        connectBtn.setEnabled(false);
                        disconnectBtn.setEnabled(true);
                        monitorBtn.setEnabled(true);
                        executeBtn.setEnabled(true);
                        hostField.setEnabled(false);
                        tcpPortField.setEnabled(false);
                        udpPortField.setEnabled(false);
                        statusLabel.setText("Conectado ao servidor em " + serverHost + ":" + tcpPort);
                        logResult("✓ Conexão estabelecida com sucesso!");
                    } else {
                        statusLabel.setText("Falha ao conectar. Verifique o endereço e porta.");
                        JOptionPane.showMessageDialog(ProcessingClient.this,
                                "Não foi possível conectar ao servidor.\nVerifique se o servidor está ativo.",
                                "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    statusLabel.setText("Erro ao conectar: " + e.getMessage());
                }
            }
        }.execute();
    }

    private void disconnect() {
        connected = false;
        connectBtn.setEnabled(true);
        disconnectBtn.setEnabled(false);
        monitorBtn.setEnabled(false);
        executeBtn.setEnabled(false);
        hostField.setEnabled(true);
        tcpPortField.setEnabled(true);
        udpPortField.setEnabled(true);
        statusLabel.setText("Desconectado.");

        if (monitorExecutor != null && !monitorExecutor.isShutdown()) {
            monitorExecutor.shutdown();
            monitorBtn.setText("Monitorar (UDP)");
        }
    }

    private void toggleMonitoring() {
        if (monitorExecutor == null || monitorExecutor.isShutdown()) {
            startMonitoring();
            monitorBtn.setText("Parar Monitoramento");
        } else {
            stopMonitoring();
            monitorBtn.setText("Monitorar (UDP)");
        }
    }

    private void startMonitoring() {
        monitorExecutor = Executors.newSingleThreadScheduledExecutor();
        monitorExecutor.scheduleAtFixedRate(() -> {
            try {
                DatagramSocket socket = new DatagramSocket();
                socket.setSoTimeout(2000);

                String request = "STATUS";
                byte[] sendData = request.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(
                        sendData, sendData.length,
                        InetAddress.getByName(serverHost), udpPort);
                socket.send(sendPacket);

                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                updateMetricsDisplay(response);

                socket.close();
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> metricsArea.setText("Erro ao obter métricas: " + e.getMessage()));
            }
        }, 0, 2, TimeUnit.SECONDS);

        logResult("✓ Monitoramento UDP iniciado");
    }

    private void stopMonitoring() {
        if (monitorExecutor != null) {
            monitorExecutor.shutdown();
            logResult("Monitoramento UDP parado");
        }
    }

    private void updateMetricsDisplay(String jsonData) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Parse simple JSON manually
                String json = jsonData.replace("{", "").replace("}", "");
                String[] pairs = json.split(",");
                Map<String, String> metrics = new HashMap<>();

                for (String pair : pairs) {
                    String[] kv = pair.split(":");
                    if (kv.length == 2) {
                        metrics.put(kv[0].replace("\"", "").trim(), kv[1].replace("\"", "").trim());
                    }
                }

                long uptime = Long.parseLong(metrics.getOrDefault("uptime", "0"));

                StringBuilder sb = new StringBuilder();
                sb.append("═══════════════════════════════════\n");
                sb.append("  MÉTRICAS DO SERVIDOR\n");
                sb.append("═══════════════════════════════════\n");
                sb.append(String.format("Uptime:      %02d:%02d:%02d\n",
                        uptime / 3600, (uptime % 3600) / 60, uptime % 60));
                sb.append(String.format("Tarefas:     %s\n", metrics.getOrDefault("tasks", "0")));
                sb.append(String.format("Threads:     %s\n", metrics.getOrDefault("threads", "0")));
                sb.append(String.format("Memória:     %s MB\n", metrics.getOrDefault("memory", "0")));
                sb.append(String.format("TCP:         %s\n",
                        Boolean.parseBoolean(metrics.getOrDefault("tcpActive", "false")) ? "ATIVO" : "INATIVO"));
                sb.append(String.format("UDP:         %s\n",
                        Boolean.parseBoolean(metrics.getOrDefault("udpActive", "false")) ? "ATIVO" : "INATIVO"));
                sb.append("═══════════════════════════════════\n");

                metricsArea.setText(sb.toString());
            } catch (Exception e) {
                metricsArea.setText("Erro ao processar métricas");
            }
        });
    }

    private void executeTask() {
        String taskType = (String) taskTypeCombo.getSelectedItem();
        executeBtn.setEnabled(false);
        progressBar.setValue(0);

        new SwingWorker<String, Integer>() {
            @Override
            protected String doInBackground() throws Exception {
                publish(10);

                try (Socket socket = new Socket(serverHost, tcpPort);
                        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                        DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()))) {

                    publish(20);

                    // Autenticação
                    out.writeUTF(AUTH_KEY);
                    out.writeUTF(PASSWORD);
                    out.flush();

                    publish(30);

                    // Envia tipo de tarefa e dados
                    if (taskType.startsWith("Ordenação")) {
                        sendSortTask(out);
                    } else if (taskType.startsWith("Busca")) {
                        sendSearchTask(out);
                    } else if (taskType.startsWith("Multiplicação")) {
                        sendMatrixTask(out);
                    } else if (taskType.startsWith("Números Primos")) {
                        sendPrimeTask(out);
                    } else if (taskType.startsWith("Transferência")) {
                        sendFileTask(out);
                    }

                    publish(70);

                    // Recebe resposta
                    String status = in.readUTF();
                    String result = in.readUTF();

                    publish(100);

                    if ("SUCCESS".equals(status)) {
                        return "✓ " + result;
                    } else {
                        return "✗ Erro: " + result;
                    }

                } catch (Exception e) {
                    throw e;
                }
            }

            @Override
            protected void process(List<Integer> chunks) {
                int last = chunks.get(chunks.size() - 1);
                progressBar.setValue(last);
            }

            @Override
            protected void done() {
                try {
                    String result = get();
                    logResult(result);
                    statusLabel.setText("Tarefa concluída com sucesso");
                } catch (Exception e) {
                    logResult("✗ Erro ao executar tarefa: " + e.getMessage());
                    statusLabel.setText("Erro ao executar tarefa");
                    e.printStackTrace();
                }
                executeBtn.setEnabled(true);
                progressBar.setValue(0);
            }
        }.execute();
    }

    private void sendSortTask(DataOutputStream out) throws IOException {
        out.writeUTF("SORT");

        int size = Integer.parseInt(arraySizeField.getText().trim());
        String algorithm = (String) sortAlgoCombo.getSelectedItem();

        out.writeInt(size);
        out.writeUTF(algorithm);

        Random random = new Random();
        for (int i = 0; i < size; i++) {
            out.writeInt(random.nextInt(10000));
        }

        out.flush();
        logResult(String.format("→ Enviando tarefa SORT: %d elementos, algoritmo %s", size, algorithm));
    }

    private void sendSearchTask(DataOutputStream out) throws IOException {
        out.writeUTF("SEARCH");

        int size = Integer.parseInt(arraySizeField.getText().trim());
        int target = Integer.parseInt(targetField.getText().trim());
        String algorithm = (String) searchAlgoCombo.getSelectedItem();

        out.writeInt(size);
        out.writeInt(target);
        out.writeUTF(algorithm);

        Random random = new Random();
        for (int i = 0; i < size; i++) {
            out.writeInt(random.nextInt(1000));
        }

        out.flush();
        logResult(String.format("→ Enviando tarefa SEARCH: buscar %d em array de %d elementos, algoritmo %s",
                target, size, algorithm));
    }

    private void sendMatrixTask(DataOutputStream out) throws IOException {
        out.writeUTF("MATRIX");

        int size = Integer.parseInt(matrixSizeField.getText().trim());
        out.writeInt(size);

        Random random = new Random();

        // Matrix A
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                out.writeInt(random.nextInt(100));
            }
        }

        // Matrix B
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                out.writeInt(random.nextInt(100));
            }
        }

        out.flush();
        logResult(String.format("→ Enviando tarefa MATRIX: multiplicação de matrizes %dx%d", size, size));
    }

    private void sendPrimeTask(DataOutputStream out) throws IOException {
        out.writeUTF("PRIME");

        int limit = Integer.parseInt(primeLimitField.getText().trim());
        out.writeInt(limit);

        out.flush();
        logResult(String.format("→ Enviando tarefa PRIME: encontrar primos até %d", limit));
    }

    private void sendFileTask(DataOutputStream out) throws IOException {
        out.writeUTF("FILE");

        File file = new File(filePathField.getText());
        if (!file.exists() || !file.isFile()) {
            throw new IOException("Arquivo inválido");
        }

        byte[] nameBytes = file.getName().getBytes("UTF-8");
        out.writeInt(nameBytes.length);
        out.write(nameBytes);
        out.writeLong(file.length());

        try (InputStream fileIn = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = fileIn.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        }

        out.flush();
        logResult(String.format("→ Enviando arquivo: %s (%d bytes)", file.getName(), file.length()));
    }

    private void logResult(String message) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String logMessage = String.format("[%s] %s\n", timestamp, message);
        SwingUtilities.invokeLater(() -> {
            resultsArea.append(logMessage);
            resultsArea.setCaretPosition(resultsArea.getDocument().getLength());
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProcessingClient().setVisible(true));
    }
}
