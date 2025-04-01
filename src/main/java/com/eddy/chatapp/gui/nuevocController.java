package com.eddy.chatapp.gui;

import com.eddy.chatapp.core.RedClient;
import com.eddy.chatapp.core.RedServer;
import com.eddy.chatapp.model.Users;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.util.List;

public class nuevocController {
    private RedServer redServer;
    private RedClient redClient;

    @FXML
    private ListView<Users> listViewDevices;

    @FXML
    public void initialize(){
        redServer = new RedServer();
        redClient = new RedClient();
        redServer.start();

        startUserDiscovery();
        listViewDevices.setCellFactory(param -> new ListCell<Users>() {
            private ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Users user, boolean empty) {
                super.updateItem(user, empty);
                if(empty || user == null){
                    setText(null);
                    setGraphic(null);
                }else{
                    setText(user.getNickname());

                    if(user.getfoto().length > 0){
                        Image image = new Image(new ByteArrayInputStream(user.getfoto()));
                        imageView.setImage(image);
                        imageView.setFitWidth(40);
                        imageView.setFitHeight(40);
                    }
                    setGraphic(imageView);
                }
            }
        });
    }

    private void startUserDiscovery() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    List<Users> conectados = redClient.discoverUsers();
                    Platform.runLater(()->
                    {
                        listViewDevices.getItems().setAll(conectados);
                    });
                    try
                    {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){}
                }
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
