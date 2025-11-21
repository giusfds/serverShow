import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static Properties props = new Properties();
    private static final String FILE_NAME = "config.properties"; 

    static {
        load();
    }

    public static void load() {
        try (FileInputStream is = new FileInputStream(FILE_NAME)) {
            props.load(is);
        } catch (IOException e) {
            System.out.println("Criando configurações padrão...");
            createDefaults();
        }
    }

    public static void save() {
        try (FileOutputStream out = new FileOutputStream(FILE_NAME)) {
            props.store(out, "Configuracoes ServerShow");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDefaults() {
        props.setProperty("serverIp", "127.0.0.1");
        props.setProperty("tcpPort", "5000");
        props.setProperty("udpPort", "5001");
        props.setProperty("latency", "0");
        props.setProperty("packetLoss", "0");
    }

    // Getters e Setters Tipados
    public static String getIp() { return props.getProperty("serverIp", "127.0.0.1"); }
    public static void setIp(String ip) { props.setProperty("serverIp", ip); }

    public static int getTcpPort() { return Integer.parseInt(props.getProperty("tcpPort", "5000")); }
    public static void setTcpPort(int port) { props.setProperty("tcpPort", String.valueOf(port)); }

    public static int getUdpPort() { return Integer.parseInt(props.getProperty("udpPort", "5001")); }
    public static void setUdpPort(int port) { props.setProperty("udpPort", String.valueOf(port)); }

    public static int getLatency() { return Integer.parseInt(props.getProperty("latency", "0")); }
    public static void setLatency(int ms) { props.setProperty("latency", String.valueOf(ms)); }

    public static int getPacketLoss() { return Integer.parseInt(props.getProperty("packetLoss", "0")); }
    public static void setPacketLoss(int pct) { props.setProperty("packetLoss", String.valueOf(pct)); }
    
    // Genérico (para compatibilidade com códigos antigos se houver)
    public static String get(String key) { return props.getProperty(key, ""); }
}