import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ConfigDialog extends JDialog {

    private JTextField txtIp, txtTcp, txtUdp;
    private JSlider sliderLatency, sliderLoss;
    private JLabel lblLatencyVal, lblLossVal;

    public ConfigDialog(Frame parent) {
        super(parent, "Configurações de Rede e Simulação", true); // true = Modal
        setSize(500, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // --- ABA 1: APLICAÇÃO (IP e Portas) ---
        JPanel pnlApp = new JPanel(new GridBagLayout());
        pnlApp.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Carrega valores atuais
        txtIp = new JTextField(Config.getIp(), 15);
        txtTcp = new JTextField(String.valueOf(Config.getTcpPort()), 5);
        txtUdp = new JTextField(String.valueOf(Config.getUdpPort()), 5);

        addLabelAndField(pnlApp, "IP do Servidor:", txtIp, gbc, 0);
        addLabelAndField(pnlApp, "Porta TCP (Tarefas):", txtTcp, gbc, 1);
        addLabelAndField(pnlApp, "Porta UDP (Monitor):", txtUdp, gbc, 2);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JLabel lblNote = new JLabel("Nota: Reinicie a aplicação para aplicar mudanças de porta.");
        lblNote.setFont(new Font("Arial", Font.ITALIC, 11));
        lblNote.setForeground(Color.GRAY);
        pnlApp.add(lblNote, gbc);

        tabbedPane.addTab("Conexão", pnlApp);

        // --- ABA 2: ROTEADOR (Simulação de Rede) ---
        JPanel pnlRouter = new JPanel(new GridLayout(6, 1, 5, 5));
        pnlRouter.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Latência (Atraso)
        int currentLatency = Config.getLatency();
        lblLatencyVal = new JLabel("Latência (Ping): " + currentLatency + " ms");
        sliderLatency = new JSlider(0, 5000, currentLatency);
        sliderLatency.setMajorTickSpacing(1000);
        sliderLatency.setPaintTicks(true);
        sliderLatency.addChangeListener(e -> lblLatencyVal.setText("Latência (Ping): " + sliderLatency.getValue() + " ms"));

        // Perda de Pacotes
        int currentLoss = Config.getPacketLoss();
        lblLossVal = new JLabel("Perda de Pacotes: " + currentLoss + "%");
        sliderLoss = new JSlider(0, 100, currentLoss);
        sliderLoss.setMajorTickSpacing(20);
        sliderLoss.setPaintTicks(true);
        sliderLoss.setPaintLabels(true);
        sliderLoss.addChangeListener(e -> lblLossVal.setText("Perda de Pacotes: " + sliderLoss.getValue() + "%"));

        pnlRouter.add(new JLabel("Simulação de Atraso (Lag):"));
        pnlRouter.add(lblLatencyVal);
        pnlRouter.add(sliderLatency);
        pnlRouter.add(new JSeparator());
        pnlRouter.add(lblLossVal);
        pnlRouter.add(sliderLoss);

        tabbedPane.addTab("Simulação de Roteador", pnlRouter);

        // --- BOTÕES ---
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Salvar Configurações");
        JButton btnCancel = new JButton("Cancelar");

        btnSave.setBackground(new Color(76, 175, 80));
        btnSave.setForeground(Color.BLACK);

        btnSave.addActionListener(e -> saveAndClose());
        btnCancel.addActionListener(e -> dispose());

        pnlButtons.add(btnSave);
        pnlButtons.add(btnCancel);

        add(tabbedPane, BorderLayout.CENTER);
        add(pnlButtons, BorderLayout.SOUTH);
    }

    private void addLabelAndField(JPanel p, String label, Component c, GridBagConstraints gbc, int y) {
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 1;
        p.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        p.add(c, gbc);
    }

    private void saveAndClose() {
        try {
            // Validação e Salvamento
            Config.setIp(txtIp.getText());
            Config.setTcpPort(Integer.parseInt(txtTcp.getText()));
            Config.setUdpPort(Integer.parseInt(txtUdp.getText()));
            
            Config.setLatency(sliderLatency.getValue());
            Config.setPacketLoss(sliderLoss.getValue());
            
            Config.save(); // Salva no arquivo
            
            JOptionPane.showMessageDialog(this, "Configurações salvas com sucesso!");
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "As portas devem ser números inteiros.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        }
    }
}