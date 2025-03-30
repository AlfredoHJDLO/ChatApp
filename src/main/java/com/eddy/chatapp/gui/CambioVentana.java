package com.eddy.chatapp.gui;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;


/**
 * Esta administra el cambio entre el loguin y la ventana principal
 * @author Jacob Jahir Vera del Carmen
 * @author Saul David Peña Martínez
 * */
public class CambioVentana{

    public static void cambioVentana(Window currentWindow, String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(CambioVentana.class.getResource(fxmlFile));
            Parent root = loader.load();

            Object controller = loader.getController();
            if(controller instanceof Initializable)
            {
                System.out.println("Inicializando FXML");
            }

            Stage newStage = new Stage();
            newStage.setTitle(title);
            newStage.setScene(new Scene(root));
            newStage.show();

            // Cerrar la ventana actual
            currentWindow.hide();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}