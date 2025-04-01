package com.eddy.chatapp;

import com.eddy.chatapp.dao.Inicializador;
import com.eddy.chatapp.gui.LoginUI;

//import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Inicializador.initializeDatabase();
        LoginUI.main(args);
    }
}
