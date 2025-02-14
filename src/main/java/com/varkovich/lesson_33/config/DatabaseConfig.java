package com.varkovich.lesson_33.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConfig {

    private static DatabaseConfig databaseConfig;

    private Properties properties;

    private DatabaseConfig() {
        loudProperties();
    }

    public static DatabaseConfig getInstance() {
        if (databaseConfig == null) {
            databaseConfig = new DatabaseConfig();
        }
        return databaseConfig;
    }

    private void loudProperties() {
        properties = new Properties();

        try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            //TODO log exception
            e.printStackTrace();
        }
    }

    public String getDatabaseUrl() {
        return properties.getProperty("db.url");
    }

    public String getDatabaseLogin() {
        return properties.getProperty("db.login");
    }

    public String getDatabasePassword() {
        return properties.getProperty("db.pass");
    }
}
