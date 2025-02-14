package com.varkovich.lesson_33.service;

import com.varkovich.lesson_33.config.DatabaseConfig;
import lombok.extern.log4j.Log4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Log4j
public class DatabaseService {

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            log.info("PostgreSQL JDBC Driver loaded");
        } catch (ClassNotFoundException e) {
            log.error("PostgreSQL JDBC Driver not found." + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            DatabaseConfig databaseConfig = DatabaseConfig.getInstance();
            try {
                connection = DriverManager.getConnection(databaseConfig.getDatabaseUrl(), databaseConfig.getDatabaseLogin(), databaseConfig.getDatabasePassword());
            } catch (SQLException e) {
                log.error("Error while connecting to PostgreSQL Database. " + e.getMessage());
                throw new RuntimeException(e);
            }
        }

        log.info("Connected to PostgreSQL Database.");
        return connection;
    }
}
