package org.tsvil.bugtracker;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

    private static Properties properties;

    public static Properties getConfig() throws IOException {
        if (properties == null) {
            InputStream inputStream = AppConfig.class.getClassLoader().getResourceAsStream("config.properties");
            properties = new Properties();
            properties.load(inputStream);
        }
        return properties;
    }

    public static String getDbResourceName() throws IOException {
        return getConfig().getProperty("db.resource.name");
    }

    public static String getDbName() throws IOException {
        return getConfig().getProperty("db.name");
    }
}
