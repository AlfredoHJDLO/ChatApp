package com.eddy.chatapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector implements DatabaseConnector{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/chatapp";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    @Override
    public Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}
