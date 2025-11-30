import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * Servidor de Processamento Distribuído
 * Processa tarefas computacionais usando TCP e envia métricas via UDP
 */
public class FileServer extends JFrame {
    // UI Components - Old FileServer variables
    private JButton startBtn, stopBtn, chooseDirBtn;
    private JList<String> fileList;
    private DefaultListModel<String> listModel;
    private JProgressBar progressBar;
    private JLabel statusLabel;
    private JFileChooser dirChooser;
    private File saveDir = new File(".");

    // Network Components
    private ServerSocket serverSocket;
    private ExecutorService pool = Executors.newCachedThreadPool();
    private volatile boolean running = false;
    private int port;

    // Configuration
    private static final String AUTH_KEY = Config.getAuthKey();
    private static final String PASSWORD_HASH = sha256(Config.getPassword());

    public FileServer() {
        super("File Server");
        this.port = Config.getTcpPort();
        initUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 420);
        setLocationRelativeTo(null);
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        startBtn = new JButton("Start Server (port " + port + ")");
        stopBtn = new JButton("Stop Server");
        stopBtn.setEnabled(false);
        chooseDirBtn = new JButton("Save Dir: " + saveDir.getAbsolutePath());
        top.add(startBtn);
        top.add(stopBtn);
        top.add(chooseDirBtn);
        root.add(top, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        JScrollPane scroll = new JScrollPane(fileList);
        scroll.setBorder(BorderFactory.createTitledBorder("Arquivos recebidos"));
        root.add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout(8, 8));
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        statusLabel = new JLabel("Servidor parado.");
        bottom.add(progressBar, BorderLayout.NORTH);
        bottom.add(statusLabel, BorderLayout.SOUTH);
        root.add(bottom, BorderLayout.SOUTH);

        add(root);

        dirChooser = new JFileChooser();
        dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        startBtn.addActionListener(e -> startServer());
        stopBtn.addActionListener(e -> stopServer());
        chooseDirBtn.addActionListener(e -> chooseDirectory());
    }

    private void chooseDirectory() {
        int res = dirChooser.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            saveDir = dirChooser.getSelectedFile();
            chooseDirBtn.setText("Save Dir: " + saveDir.getAbsolutePath());
        }
    }

    private void startServer() {
        running = true;
        startBtn.setEnabled(false);
        stopBtn.setEnabled(true);
        statusLabel.setText("Iniciando servidor...");

        pool.submit(() -> {
            try {
                serverSocket = new ServerSocket(port);
                SwingUtilities.invokeLater(() -> statusLabel.setText("Servidor ouvindo na porta " + port));
                while (running) {
                    Socket client = serverSocket.accept();
                    pool.submit(() -> handleClient(client));
                }
            } catch (IOException ex) {
                if (running) {
                    SwingUtilities.invokeLater(() -> statusLabel.setText("Erro no servidor: " + ex.getMessage()));
                }
            } finally {
                stopServerCleanup();
            }
        });
    }

    private void stopServer() {
        running = false;
        startBtn.setEnabled(true);
        stopBtn.setEnabled(false);
        statusLabel.setText("Parando servidor...");
        try {
            if (serverSocket != null && !serverSocket.isClosed())
                serverSocket.close();
        } catch (IOException ignored) {
        }
    }

    private void stopServerCleanup() {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("Servidor parado.");
            progressBar.setValue(0);
        });
    }

    private void handleClient(Socket socket) {
        String peer = socket.getRemoteSocketAddress().toString();
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()))) {

            // 🔐 1) Autenticação
            String key = in.readUTF();
            String password = in.readUTF();
            if (!AUTH_KEY.equals(key) || !PASSWORD_HASH.equals(sha256(password))) {
                SwingUtilities.invokeLater(() -> statusLabel.setText("Autenticação falhou para " + peer));
                socket.close();
                return;
            }

            // ✅ 2) Receber arquivo
            int nameLen = in.readInt();
            byte[] nameBytes = new byte[nameLen];
            in.readFully(nameBytes);
            String filename = new String(nameBytes, "UTF-8");
            long fileSize = in.readLong();

            SwingUtilities.invokeLater(() -> {
                statusLabel.setText("Recebendo '" + filename + "' de " + peer);
                progressBar.setValue(0);
            });

            File target = new File(saveDir, makeSafeFilename(filename));
            try (OutputStream fileOut = new BufferedOutputStream(new FileOutputStream(target))) {
                byte[] buffer = new byte[8192];
                long totalRead = 0;
                int read;
                while ((read = in.read(buffer)) != -1) {
                    fileOut.write(buffer, 0, read);
                    totalRead += read;
                    int percent = (int) ((totalRead * 100) / fileSize);
                    SwingUtilities.invokeLater(() -> progressBar.setValue(percent));
                    if (totalRead >= fileSize)
                        break;
                }
            }

            String entry = String.format("%s  |  %s  |  %s bytes",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                    filename, fileSize);
            SwingUtilities.invokeLater(() -> {
                listModel.addElement(entry);
                statusLabel.setText("Recebido: " + filename + " de " + peer);
                progressBar.setValue(100);
            });

        } catch (IOException ex) {
            SwingUtilities.invokeLater(() -> statusLabel.setText("Erro recebendo arquivo: " + ex.getMessage()));
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
            ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
            ses.schedule(() -> SwingUtilities.invokeLater(() -> progressBar.setValue(0)), 800, TimeUnit.MILLISECONDS);
            ses.shutdown();
        }
    }

    private String makeSafeFilename(String original) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return timestamp + "_" + original.replaceAll("[^a-zA-Z0-9._-]", "_");
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
        SwingUtilities.invokeLater(() -> new FileServer().setVisible(true));
    }
}