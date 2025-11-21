import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Classe principal para executar o sistema
 * Oferece um menu para escolher entre Servidor, Cliente ou ambos
 */
public class Main extends JFrame {

    public Main() {
        super("Sistema de Processamento Distribuído - Menu Principal");
        initUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titleLabel = new JLabel("Sistema de Processamento Distribuído", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));

        JButton serverBtn = new JButton("1. Executar Servidor (ProcessingServer)");
        JButton clientBtn = new JButton("2. Executar Cliente (ProcessingClient)");
        JButton bothBtn = new JButton("3. Executar Ambos (Servidor + Cliente)");
        
        JButton configBtn = new JButton("4. Configurações de Rede / Roteador");
        
        JButton exitBtn = new JButton("5. Sair");

        // Estilizar botões
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);
        serverBtn.setFont(buttonFont);
        clientBtn.setFont(buttonFont);
        bothBtn.setFont(buttonFont);
        configBtn.setFont(buttonFont);
        exitBtn.setFont(buttonFont);

        serverBtn.setFocusPainted(false);
        clientBtn.setFocusPainted(false);
        bothBtn.setFocusPainted(false);
        configBtn.setFocusPainted(false);
        exitBtn.setFocusPainted(false);

        // Cores
        serverBtn.setBackground(new Color(76, 175, 80));
        serverBtn.setForeground(Color.BLACK);
        clientBtn.setBackground(new Color(33, 150, 243));
        clientBtn.setForeground(Color.BLACK);
        bothBtn.setBackground(new Color(156, 39, 176));
        bothBtn.setForeground(Color.BLACK);
        
        configBtn.setBackground(new Color(255, 152, 0));
        configBtn.setForeground(Color.BLACK);
        
        exitBtn.setBackground(new Color(244, 67, 54));
        exitBtn.setForeground(Color.BLACK);

        // Eventos
        serverBtn.addActionListener(e -> executeServer());
        clientBtn.addActionListener(e -> executeClient());
        bothBtn.addActionListener(e -> executeBoth());
        
        configBtn.addActionListener(e -> openConfig());
        
        exitBtn.addActionListener(e -> System.exit(0));

        // Teclas de atalho
        serverBtn.setMnemonic(KeyEvent.VK_1);
        clientBtn.setMnemonic(KeyEvent.VK_2);
        bothBtn.setMnemonic(KeyEvent.VK_3);
        exitBtn.setMnemonic(KeyEvent.VK_5);

        buttonPanel.add(serverBtn);
        buttonPanel.add(clientBtn);
        buttonPanel.add(bothBtn);
        buttonPanel.add(configBtn);
        buttonPanel.add(exitBtn);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Rodapé
        JLabel footerLabel = new JLabel("Desenvolvido para Redes de Computadores - PUC Minas", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        footerLabel.setForeground(Color.GRAY);
        mainPanel.add(footerLabel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void executeServer() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Deseja iniciar o ProcessingServer?\n\nO servidor irá abrir em uma nova janela.",
                "Confirmar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            SwingUtilities.invokeLater(() -> {
                new ProcessingServer().setVisible(true);
                this.dispose();
            });
        }
    }

    private void executeClient() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Deseja iniciar o ProcessingClient?\n\nO cliente irá abrir em uma nova janela.",
                "Confirmar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            SwingUtilities.invokeLater(() -> {
                new ProcessingClient().setVisible(true);
                this.dispose();
            });
        }
    }

    private void executeBoth() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Deseja iniciar Servidor E Cliente?\n\n" +
                        "O servidor será iniciado primeiro,\n" +
                        "seguido pelo cliente após 2 segundos.",
                "Confirmar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            // Inicia servidor
            SwingUtilities.invokeLater(() -> {
                new ProcessingServer().setVisible(true);
            });

            // Aguarda 2 segundos e inicia cliente
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    SwingUtilities.invokeLater(() -> {
                        new ProcessingClient().setVisible(true);
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            this.dispose();
        }
    }

    private void openConfig() {
        SwingUtilities.invokeLater(() -> {
            ConfigDialog dialog = new ConfigDialog(this);
            dialog.setVisible(true);
        });
    }

    public static void main(String[] args) {
        // Define look and feel do sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Usa o look and feel padrão se falhar
        }

        // Inicia o menu principalw
        SwingUtilities.invokeLater(() -> {
            Main mainMenu = new Main();
            mainMenu.setVisible(true);
        });
    }
}
