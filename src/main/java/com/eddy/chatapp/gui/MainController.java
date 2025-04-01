package com.eddy.chatapp.gui;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Esta clase administra los eventos de la clase principal
 * @author Jacob Jahir Vera del Carmen
 * @author Saul David Peña Martínez
 * */
public class MainController {
    @FXML
    private StackPane contentArea;

    private Node nuevoView;
    private Node chatView;

    @FXML private VBox vboxDefault; // VBox del mensaje por defecto
    @FXML private VBox vboxChat;    // VBox de los mensajes

    @FXML
    public void initialize() throws IOException {
        // Cargar las diferentes interfaces una sola vez
        chatView = FXMLLoader.load(getClass().getResource("chatsR.fxml"));
        nuevoView = FXMLLoader.load(getClass().getResource("nuevoC.fxml"));

        // Mostrar la vista de chats por defecto
        contentArea.getChildren().setAll(chatView);
    }

    @FXML
    private void showChats() {
        Platform.runLater(() -> contentArea.getChildren().setAll(chatView));

    }

    @FXML
    private void showNew() {
        Platform.runLater(() -> contentArea.getChildren().setAll(nuevoView));
    }


}
