package com.eddy.chatapp.gui;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
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

    @FXML
    public void initialize() throws IOException {
        // Cargar las diferentes interfaces una sola vez
        chatView = FXMLLoader.load(getClass().getResource("chatsR.fxml"));
        nuevoView = FXMLLoader.load(getClass().getResource("nuevoC.fxml"));

        // Mostrar la vista de chats por defecto
        contentArea.getChildren().setAll(chatView);
    }

                    if(user.getFoto().length > 0){
                        Image image = new Image(new ByteArrayInputStream(user.getFoto()));
                        imageView.setImage(image);
                        imageView.setFitWidth(40);
                        imageView.setFitHeight(40);
                    }
                    setGraphic(imageView);
                }
            }
        });



}
