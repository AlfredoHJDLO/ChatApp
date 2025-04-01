package com.eddy.chatapp.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.DriverManager.getConnection;

public class Inicializador {
    public static void initializeDatabase() throws SQLException {
        String createMessagesTableSQL = "CREATE TABLE IF NOT EXISTS messages ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "destinatario TEXT NOT NULL, "
                + "remitente TEXT NOT NULL, "
                + "texto TEXT NOT NULL, "
                + "timestamp DATE NOT NULL)";

        String createUsersTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                + "id TEXT PRIMARY KEY, "
                + "nickname TEXT NOT NULL, "
                + "password TEXT NOT NULL, "
                + "foto BLOB NOT NULL)";

        String createBlockedUsersTableSQL = "CREATE TABLE IF NOT EXISTS blocked_users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "mac_address TEXT NOT NULL)";

        try (Connection connection = new SQLiteConnector().getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(createMessagesTableSQL);
            statement.execute(createUsersTableSQL);
            statement.execute(createBlockedUsersTableSQL);
        }
    }
}
