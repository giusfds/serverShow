import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class FileClient extends JFrame {
    private JTextField hostField;
    private JTextField portField;
    private JTextField fileField;
    private JButton browseBtn, sendBtn;
    private JProgressBar progressBar;
    private JFileChooser fileChooser;

    private static final String AUTH_KEY = Config.get("auth.key");
    private static final String PASSWORD = Config.get("password");

    public FileClient() {
        super("File Client");
        initUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(560, 220);
        setLocationRelativeTo(null);
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(12, 12, 12, 12));

        JPanel fields = new JPanel(new GridLayout(2, 1, 6, 6));

        JPanel hp = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        hostField = new JTextField("localhost", 15);
        portField = new JTextField("5000", 5);
        hp.add(new JLabel("Host:"));
        hp.add(hostField);
        hp.add(new JLabel("Port:"));
        hp.add(portField);

        JPanel filePanel = new JPanel(new BorderLayout(6, 6));
        fileField = new JTextField();
        fileField.setEditable(false);
        browseBtn = new JButton("Escolher arquivo...");
        filePanel.add(new JLabel("Arquivo:"), BorderLayout.WEST);
        filePanel.add(fileField, BorderLayout.CENTER);
        filePanel.add(browseBtn, BorderLayout.EAST);

        fields.add(hp);
        fields.add(filePanel);

        root.add(fields, BorderLayout.CENTER);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        sendBtn = new JButton("Enviar");
        sendBtn.setEnabled(false);

        JPanel bottom = new JPanel(new BorderLayout(8, 8));
        bottom.add(progressBar, BorderLayout.CENTER);
        bottom.add(sendBtn, BorderLayout.EAST);
        root.add(bottom, BorderLayout.SOUTH);

        add(root);

        fileChooser = new JFileChooser();
        browseBtn.addActionListener(e -> chooseFile());
        sendBtn.addActionListener(e -> sendFile());
    }

    private void chooseFile() {
        int res = fileChooser.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            fileField.setText(f.getAbsolutePath());
            sendBtn.setEnabled(true);
        }
    }

    private void sendFile() {
        String host = hostField.getText().trim();
        int port = Integer.parseInt(portField.getText().trim());
        File f = new File(fileField.getText());

        if (!f.exists() || !f.isFile()) {
            JOptionPane.showMessageDialog(this, "Escolha um arquivo válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        sendBtn.setEnabled(false);
        new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() {
                try (Socket socket = new Socket(host, port);
                     DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                     InputStream fileIn = new BufferedInputStream(new FileInputStream(f))) {

                    // 🔐 Envia credenciais
                    out.writeUTF(AUTH_KEY);
                    out.writeUTF(PASSWORD);

                    // 🧾 Envia nome e tamanho
                    byte[] nameBytes = f.getName().getBytes("UTF-8");
                    out.writeInt(nameBytes.length);
                    out.write(nameBytes);
                    out.writeLong(f.length());

                    byte[] buffer = new byte[8192];
                    long total = 0;
                    int read;
                    while ((read = fileIn.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                        total += read;
                        publish((int) ((total * 100) / f.length()));
                    }
                    out.flush();

                } catch (IOException ex) {
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(FileClient.this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE)
                    );
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                int last = chunks.get(chunks.size() - 1);
                progressBar.setValue(last);
            }

            @Override
            protected void done() {
                progressBar.setValue(100);
                JOptionPane.showMessageDialog(FileClient.this, "Envio concluído!");
                sendBtn.setEnabled(true);
            }
        }.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FileClient().setVisible(true));
    }
}