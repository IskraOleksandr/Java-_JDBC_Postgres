package brainacad_org;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Hello world!
 *
 */
public class AppConfig
{
    private static PropertiesConfiguration config;

    static {
        try {
            config = new PropertiesConfiguration("database.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static String getDbUrl() {
        return config.getString("db.url");
    }

    public static String getDbUsername() {
        return config.getString("db.username");
    }

    public static String getDbPassword() {
        return config.getString("db.password");
    }
}
