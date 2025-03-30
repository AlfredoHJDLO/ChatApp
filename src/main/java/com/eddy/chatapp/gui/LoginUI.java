package com.eddy.chatapp.gui;

import com.eddy.chatapp.core.Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Esta clase es la interfaz gráfica principal del logueo
 * @author AlfredoHJDLO
 */

public class LoginUI extends Application {

    /**
     * Esta función start es la implementación del método del mismo nombre
     *  de la clase Application
     *
     *
     * */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader;
        if(new Login().existeContra())
            fxmlLoader = new FXMLLoader(LoginUI.class.getResource("login.fxml"));
        else
            fxmlLoader = new FXMLLoader(LoginUI.class.getResource("registro.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1366, 768);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}