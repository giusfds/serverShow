import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Servidor de Processamento Distribuído
 * Processa tarefas computacionais usando TCP e envia métricas via UDP
 */
public class ProcessingServer extends JFrame {
    // UI Components
    private JButton startTcpBtn, stopTcpBtn, startUdpBtn, stopUdpBtn;
    private JList<String> taskList;
    private DefaultListModel<String> taskListModel;
    private JTextArea metricsArea, logArea;
    private JLabel statusLabel, cpuLabel, memLabel, tasksLabel;
    private JTextField tcpPortField, udpPortField;

    // Network Components
    private ServerSocket tcpServerSocket;
    private DatagramSocket udpSocket;
    private ExecutorService threadPool = Executors.newFixedThreadPool(4); // Thread pool para tarefas
    private ExecutorService networkPool = Executors.newCachedThreadPool(); // Thread pool para rede
    private volatile boolean tcpRunning = false;
    private volatile boolean udpRunning = false;

    // Metrics
    private AtomicInteger tasksProcessed = new AtomicInteger(0);
    private AtomicInteger activeThreads = new AtomicInteger(0);
    private long startTime;

    // Configuration
    private int tcpPort = 5000;
    private int udpPort = 5001;
    private static final String AUTH_KEY = Config.getAuthKey();
    private static final String PASSWORD_HASH = sha256(Config.getPassword());

    public ProcessingServer() {
        super("Processing Server - Sistema de Processamento Distribuído");
        initUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        startTime = System.currentTimeMillis();
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Top Panel - Controls
        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        // TCP Controls
        JPanel tcpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        tcpPanel.setBorder(new TitledBorder("TCP Server (Processamento de Tarefas)"));
        tcpPortField = new JTextField(String.valueOf(Config.getTcpPort()), 5);
        startTcpBtn = new JButton("Iniciar TCP");
        stopTcpBtn = new JButton("Parar TCP");
        stopTcpBtn.setEnabled(false);
        tcpPanel.add(new JLabel("Porta:"));
        tcpPanel.add(tcpPortField);
        tcpPanel.add(startTcpBtn);
        tcpPanel.add(stopTcpBtn);

        // UDP Controls
        JPanel udpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        udpPanel.setBorder(new TitledBorder("UDP Server (Monitoramento)"));
        udpPortField = new JTextField(String.valueOf(Config.getUdpPort()), 5);
        startUdpBtn = new JButton("Iniciar UDP");
        stopUdpBtn = new JButton("Parar UDP");
        stopUdpBtn.setEnabled(false);
        udpPanel.add(new JLabel("Porta:"));
        udpPanel.add(udpPortField);
        udpPanel.add(startUdpBtn);
        udpPanel.add(stopUdpBtn);

        topPanel.add(tcpPanel);
        topPanel.add(udpPanel);
        root.add(topPanel, BorderLayout.NORTH);

        // Center Panel - Lists and Info
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        // Task List
        JPanel taskPanel = new JPanel(new BorderLayout());
        taskPanel.setBorder(new TitledBorder("Tarefas Processadas"));
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setFont(new Font("Monospaced", Font.PLAIN, 11));
        taskList.setBackground(Color.WHITE);
        taskList.setForeground(Color.BLACK);
        JScrollPane taskScroll = new JScrollPane(taskList);
        taskPanel.add(taskScroll, BorderLayout.CENTER);

        // Metrics and Logs
        JPanel rightPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        // Metrics
        JPanel metricsPanel = new JPanel(new BorderLayout());
        metricsPanel.setBorder(new TitledBorder("Métricas do Sistema"));
        metricsArea = new JTextArea();
        metricsArea.setEditable(false);
        metricsArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        metricsArea.setBackground(Color.WHITE);
        metricsArea.setForeground(Color.BLACK);
        JScrollPane metricsScroll = new JScrollPane(metricsArea);
        metricsPanel.add(metricsScroll, BorderLayout.CENTER);

        // Logs
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(new TitledBorder("Log de Eventos"));
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 10));
        logArea.setBackground(Color.WHITE);
        logArea.setForeground(Color.BLACK);
        JScrollPane logScroll = new JScrollPane(logArea);
        logPanel.add(logScroll, BorderLayout.CENTER);

        rightPanel.add(metricsPanel);
        rightPanel.add(logPanel);

        centerPanel.add(taskPanel);
        centerPanel.add(rightPanel);
        root.add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel - Status
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        tasksLabel = new JLabel("Tarefas: 0");
        cpuLabel = new JLabel("Threads Ativas: 0");
        memLabel = new JLabel("Memória: 0 MB");
        statsPanel.add(tasksLabel);
        statsPanel.add(cpuLabel);
        statsPanel.add(memLabel);

        statusLabel = new JLabel("Servidor parado. Aguardando inicialização...");
        statusLabel.setBorder(new EmptyBorder(5, 5, 5, 5));

        bottomPanel.add(statsPanel);
        bottomPanel.add(statusLabel);
        root.add(bottomPanel, BorderLayout.SOUTH);

        add(root);

        // Event Listeners
        startTcpBtn.addActionListener(e -> startTcpServer());
        stopTcpBtn.addActionListener(e -> stopTcpServer());
        startUdpBtn.addActionListener(e -> startUdpServer());
        stopUdpBtn.addActionListener(e -> stopUdpServer());

        // Update metrics every second
        javax.swing.Timer metricsTimer = new javax.swing.Timer(1000, e -> updateMetrics());
        metricsTimer.start();
    }

    private void startTcpServer() {
        tcpPort = Integer.parseInt(tcpPortField.getText().trim());
        tcpRunning = true;
        startTcpBtn.setEnabled(false);
        stopTcpBtn.setEnabled(true);
        tcpPortField.setEnabled(false);

        log("Iniciando servidor TCP na porta " + tcpPort + "...");

        networkPool.submit(() -> {
            try {
                tcpServerSocket = new ServerSocket(tcpPort);
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("Servidor TCP ativo na porta " + tcpPort);
                    log("Servidor TCP iniciado com sucesso");
                });

                while (tcpRunning) {
                    Socket client = tcpServerSocket.accept();
                    log("Nova conexão TCP: " + client.getRemoteSocketAddress());
                    threadPool.submit(() -> handleTcpClient(client));
                }
            } catch (IOException ex) {
                if (tcpRunning) {
                    SwingUtilities.invokeLater(() -> {
                        statusLabel.setText("Erro no servidor TCP: " + ex.getMessage());
                        log("✗ Erro no servidor TCP: " + ex.getMessage());
                    });
                }
            } finally {
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("Servidor TCP parado.");
                    log("Servidor TCP encerrado");
                });
            }
        });
    }

    private void stopTcpServer() {
        tcpRunning = false;
        startTcpBtn.setEnabled(true);
        stopTcpBtn.setEnabled(false);
        tcpPortField.setEnabled(true);

        try {
            if (tcpServerSocket != null && !tcpServerSocket.isClosed()) {
                tcpServerSocket.close();
            }
        } catch (IOException ignored) {
        }
    }

    private void startUdpServer() {
        // Pega a porta do campo de texto (caso o usuário tenha mudado manualmente)
        try {
            udpPort = Integer.parseInt(udpPortField.getText().trim());
        } catch (NumberFormatException e) {
            udpPort = Config.getUdpPort(); // Fallback para o Config se o texto for inválido
        }

        udpRunning = true;
        startUdpBtn.setEnabled(false);
        stopUdpBtn.setEnabled(true);
        udpPortField.setEnabled(false);

        log("Iniciando servidor UDP na porta " + udpPort + "...");

        networkPool.submit(() -> {
            try {
                udpSocket = new DatagramSocket(udpPort);
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("Servidor UDP ativo na porta " + udpPort);
                    log("Servidor UDP iniciado com sucesso");
                });

                byte[] buffer = new byte[1024];
                while (udpRunning) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    udpSocket.receive(packet);

                    int lossPercentage = Config.getPacketLoss();
                    if (lossPercentage > 0) {
                        if (new java.util.Random().nextInt(100) < lossPercentage) {

                            log("⚠️ Pacote UDP descartado (Simulação de Roteador Ruim)");
                            continue;
                        }
                    }

                    int latency = Config.getLatency();
                    if (latency > 0) {
                        try {
                            Thread.sleep(latency);
                        } catch (InterruptedException e) {
                        }
                    }

                    String request = new String(packet.getData(), 0, packet.getLength());
                    log("Requisição UDP recebida: " + request);

                    // Envia métricas de volta
                    String metrics = getMetricsJson();
                    byte[] response = metrics.getBytes();
                    DatagramPacket responsePacket = new DatagramPacket(
                            response, response.length, packet.getAddress(), packet.getPort());
                    udpSocket.send(responsePacket);
                }
            } catch (IOException ex) {
                if (udpRunning) {
                    SwingUtilities.invokeLater(() -> {
                        statusLabel.setText("Erro no servidor UDP: " + ex.getMessage());
                        log("✗ Erro no servidor UDP: " + ex.getMessage());
                    });
                }
            } finally {
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("Servidor UDP parado.");
                    log("Servidor UDP encerrado");
                });
            }
        });
    }

    private void stopUdpServer() {
        udpRunning = false;
        startUdpBtn.setEnabled(true);
        stopUdpBtn.setEnabled(false);
        udpPortField.setEnabled(true);

        if (udpSocket != null && !udpSocket.isClosed()) {
            udpSocket.close();
        }
    }

    private void handleTcpClient(Socket socket) {

        int latency = Config.getLatency(); // Pega o valor do slider
        if (latency > 0) {
            try {
                // Simula o atraso da rede (Ping alto)
                Thread.sleep(latency);
            } catch (InterruptedException e) {
                log("Simulação de latência interrompida.");
            }
        }

        activeThreads.incrementAndGet();
        String peer = socket.getRemoteSocketAddress().toString();

        try (DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {

            // Autenticação
            String key = "";
            String password = "";

            try {
                key = in.readUTF();
                password = in.readUTF();
            } catch (EOFException e) {
                log("Conexão fechada antes da autenticação: " + peer);
                return;
            }

            if (!AUTH_KEY.equals(key) || !PASSWORD_HASH.equals(sha256(password))) {
                log("Autenticação falhou para " + peer);
                out.writeUTF("ERROR");
                out.writeUTF("Autenticação falhou");
                out.flush();
                return;
            }

            log("Cliente autenticado: " + peer);

            // Recebe tipo de tarefa
            String taskType = in.readUTF();
            log("Tarefa recebida: " + taskType + " de " + peer);

            long startTask = System.currentTimeMillis();
            String result = "";

            switch (taskType) {
                case "SORT":
                    result = processSortTask(in, out);
                    break;
                case "SEARCH":
                    result = processSearchTask(in, out);
                    break;
                case "MATRIX":
                    result = processMatrixTask(in, out);
                    break;
                case "PRIME":
                    result = processPrimeTask(in, out);
                    break;
                case "FILE":
                    log("→ Processando tarefa FILE...");
                    result = processFileTask(in, out);
                    break;
                default:
                    out.writeUTF("ERROR");
                    out.writeUTF("Tipo de tarefa desconhecido");
                    out.flush();
                    return;
            }

            long duration = System.currentTimeMillis() - startTask;

            out.writeUTF("SUCCESS");
            out.writeUTF(result);
            out.flush();

            tasksProcessed.incrementAndGet();

            String taskInfo = String.format("[%s] %s | %dms | %s",
                    new SimpleDateFormat("HH:mm:ss").format(new Date()),
                    taskType, duration, peer);

            SwingUtilities.invokeLater(() -> taskListModel.addElement(taskInfo));
            log("Tarefa concluída: " + taskType + " em " + duration + "ms");

        } catch (Exception ex) {
            log("Erro processando tarefa: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            activeThreads.decrementAndGet();
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }

    private String processSortTask(DataInputStream in, DataOutputStream out) throws IOException {
        int size = in.readInt();
        String algorithm = in.readUTF();

        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = in.readInt();
        }

        long start = System.nanoTime();

        switch (algorithm) {
            case "BUBBLE":
                bubbleSort(array);
                break;
            case "QUICK":
                quickSort(array, 0, array.length - 1);
                break;
            case "MERGE":
                array = mergeSort(array);
                break;
            default:
                Arrays.sort(array);
        }

        long duration = System.nanoTime() - start;

        return String.format("Ordenação %s de %d elementos concluída em %.2f ms",
                algorithm, size, duration / 1_000_000.0);
    }

    private String processSearchTask(DataInputStream in, DataOutputStream out) throws IOException {
        int size = in.readInt();
        int target = in.readInt();
        String algorithm = in.readUTF();

        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = in.readInt();
        }

        long start = System.nanoTime();
        int index = -1;

        switch (algorithm) {
            case "LINEAR":
                index = linearSearch(array, target);
                break;
            case "BINARY":
                Arrays.sort(array);
                index = binarySearch(array, target);
                break;
        }

        long duration = System.nanoTime() - start;

        return String.format("Busca %s: elemento %d %s em %.2f µs",
                algorithm, target,
                index >= 0 ? "encontrado no índice " + index : "não encontrado",
                duration / 1_000.0);
    }

    private String processMatrixTask(DataInputStream in, DataOutputStream out) throws IOException {
        int size = in.readInt();

        int[][] matrixA = new int[size][size];
        int[][] matrixB = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixA[i][j] = in.readInt();
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixB[i][j] = in.readInt();
            }
        }

        long start = System.nanoTime();
        int[][] result = multiplyMatrices(matrixA, matrixB);
        long duration = System.nanoTime() - start;

        return String.format("Multiplicação de matrizes %dx%d concluída em %.2f ms",
                size, size, duration / 1_000_000.0);
    }

    private String processPrimeTask(DataInputStream in, DataOutputStream out) throws IOException {
        int limit = in.readInt();

        long start = System.nanoTime();
        List<Integer> primes = findPrimes(limit);
        long duration = System.nanoTime() - start;

        return String.format("Encontrados %d números primos até %d em %.2f ms",
                primes.size(), limit, duration / 1_000_000.0);
    }

    private String processFileTask(DataInputStream in, DataOutputStream out) throws IOException {
        try {
            log("→ Iniciando recebimento de arquivo...");
            
            int nameLen = in.readInt();
            if (nameLen <= 0 || nameLen > 1024) {
                throw new IOException("Tamanho do nome do arquivo inválido: " + nameLen);
            }
            
            byte[] nameBytes = new byte[nameLen];
            in.readFully(nameBytes);
            String filename = new String(nameBytes, "UTF-8");
            log("→ Nome do arquivo: " + filename);
            
            long fileSize = in.readLong();
            if (fileSize <= 0) {
                throw new IOException("Tamanho do arquivo inválido: " + fileSize);
            }
            log("→ Tamanho esperado: " + fileSize + " bytes");

            File tempDir = new File("received_files");
            if (!tempDir.exists()) {
                if (!tempDir.mkdirs()) {
                    throw new IOException("Não foi possível criar diretório: " + tempDir.getAbsolutePath());
                }
            }

            File target = new File(tempDir, makeSafeFilename(filename));
            log("→ Salvando em: " + target.getAbsolutePath());

            try (OutputStream fileOut = new BufferedOutputStream(new FileOutputStream(target))) {
                byte[] buffer = new byte[8192];
                long totalRead = 0;
                int read;
                
                while (totalRead < fileSize && (read = in.read(buffer, 0, (int) Math.min(buffer.length, fileSize - totalRead))) != -1) {
                    fileOut.write(buffer, 0, read);
                    totalRead += read;
                }
                
                fileOut.flush();
                
                if (totalRead != fileSize) {
                    log("⚠ Aviso: Bytes recebidos (" + totalRead + ") diferem do esperado (" + fileSize + ")");
                }
                
                log("✓ Arquivo recebido com sucesso: " + totalRead + " bytes");
            }

            return String.format("Arquivo '%s' (%.2f KB) recebido e salvo com sucesso", 
                                 filename, fileSize / 1024.0);
                                 
        } catch (IOException e) {
            log("✗ Erro ao processar arquivo: " + e.getMessage());
            throw new IOException("Falha no processamento do arquivo: " + e.getMessage(), e);
        }
    }

    // Algoritmos de Ordenação
    private void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    private int[] mergeSort(int[] arr) {
        if (arr.length <= 1)
            return arr;

        int mid = arr.length / 2;
        int[] left = mergeSort(Arrays.copyOfRange(arr, 0, mid));
        int[] right = mergeSort(Arrays.copyOfRange(arr, mid, arr.length));

        return merge(left, right);
    }

    private int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        while (i < left.length)
            result[k++] = left[i++];
        while (j < right.length)
            result[k++] = right[j++];

        return result;
    }

    // Algoritmos de Busca
    private int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target)
                return i;
        }
        return -1;
    }

    private int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target)
                return mid;
            if (arr[mid] < target)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return -1;
    }

    // Multiplicação de Matrizes
    private int[][] multiplyMatrices(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        return result;
    }

    // Números Primos
    private List<Integer> findPrimes(int limit) {
        List<Integer> primes = new ArrayList<>();
        boolean[] isPrime = new boolean[limit + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;

        for (int i = 2; i * i <= limit; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= limit; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        for (int i = 2; i <= limit; i++) {
            if (isPrime[i])
                primes.add(i);
        }

        return primes;
    }

    private String makeSafeFilename(String original) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return timestamp + "_" + original.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    private void updateMetrics() {
        long uptime = (System.currentTimeMillis() - startTime) / 1000;
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);

        tasksLabel.setText("Tarefas: " + tasksProcessed.get());
        cpuLabel.setText("Threads Ativas: " + activeThreads.get());
        memLabel.setText("Memória: " + usedMemory + " MB");

        StringBuilder sb = new StringBuilder();
        sb.append("=======================================\n");
        sb.append("  MÉTRICAS DO SISTEMA\n");
        sb.append("=======================================\n");
        sb.append(String.format("Uptime:          %02d:%02d:%02d\n", uptime / 3600, (uptime % 3600) / 60, uptime % 60));
        sb.append(String.format("Tarefas:         %d\n", tasksProcessed.get()));
        sb.append(String.format("Threads Ativas:  %d\n", activeThreads.get()));
        sb.append(String.format("Memória Usada:   %d MB\n", usedMemory));
        sb.append(String.format("TCP Status:      %s\n", tcpRunning ? "ATIVO" : "INATIVO"));
        sb.append(String.format("UDP Status:      %s\n", udpRunning ? "ATIVO" : "INATIVO"));
        sb.append("=======================================\n");

        metricsArea.setText(sb.toString());
    }

    private String getMetricsJson() {
        long uptime = (System.currentTimeMillis() - startTime) / 1000;
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);

        return String.format(
                "{\"uptime\":%d,\"tasks\":%d,\"threads\":%d,\"memory\":%d,\"tcpActive\":%b,\"udpActive\":%b}",
                uptime, tasksProcessed.get(), activeThreads.get(), usedMemory, tcpRunning, udpRunning);
    }

    private void log(String message) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String logMessage = String.format("[%s] %s\n", timestamp, message);
        SwingUtilities.invokeLater(() -> {
            logArea.append(logMessage);
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    private static String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash)
                sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProcessingServer().setVisible(true));
    }
}
