import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Properties props = new Properties();

    static {
        try {
            // Tenta carregar do classpath primeiro
            InputStream is = Config.class.getClassLoader().getResourceAsStream("gui/config.properties");
            if (is == null) {
                // Se não encontrar, tenta do diretório atual
                is = new FileInputStream("src/gui/config.properties");
            }
            props.load(is);
            is.close();
        } catch (IOException e) {
            System.err.println("Erro ao carregar config.properties: " + e.getMessage());
            // Define valores padrão
            props.setProperty("auth.key", "ViniShow");
            props.setProperty("password", "SuperVini");
        }
    }

    public static String get(String key) {
        return props.getProperty(key, "");
    }
}