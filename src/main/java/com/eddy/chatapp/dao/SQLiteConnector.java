package com.eddy.chatapp.dao;

import org.sqlite.SQLiteDataSource;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLiteConnector implements DatabaseConnector{
    private static final String DB_URL = "jdbc:sqlite:chatapp.db";
    //private static final String USER = "root";
    //private static final String PASSWORD = "";

    @Override
    public Connection getConnection() throws SQLException
    {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(DB_URL);
        return ds.getConnection();
        //return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

}
