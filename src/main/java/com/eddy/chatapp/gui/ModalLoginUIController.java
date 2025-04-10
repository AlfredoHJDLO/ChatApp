package com.eddy.chatapp.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


/**
 * Esta clase es la controladora del FXML
 * @author Eduardo Rojas Rodriguez
 * @author Saul David Peña Martínez
 * */
public class ModalLoginUIController {

    @FXML
    private Button closeButton;
    @FXML
    protected void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}