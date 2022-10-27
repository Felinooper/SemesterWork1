package ru.kpfu.itis.felinooper.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnectionHelper {

    public static final Logger LOGGER = LoggerFactory.getLogger(PostgresConnectionHelper.class);

    private static Connection connection;
    private static final String DRIVER = "org.postgresql.Driver";
    public static final String URL = "jdbc:postgresql://localhost:5432/semester";
    public static final String USER = "postgres";
    public static final String PASSWORD = "1234";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(
                        URL,
                        USER,
                        PASSWORD
                );
            } catch (ClassNotFoundException | SQLException e) {
                LOGGER.error("Failed connect to db", e);
            }
        }
        return connection;
    }
}
