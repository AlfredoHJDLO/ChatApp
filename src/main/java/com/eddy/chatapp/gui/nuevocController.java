package com.eddy.chatapp.gui;

import com.eddy.chatapp.core.RedClient;
import com.eddy.chatapp.core.RedServer;
import com.eddy.chatapp.dao.MessageDAO;
import com.eddy.chatapp.dao.MessageDAOImpl;
import com.eddy.chatapp.dao.SQLiteConnector;
import com.eddy.chatapp.model.Message;
import com.eddy.chatapp.model.Users;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;
import java.util.List;

public class nuevocController {
    private RedServer redServer;
    private RedClient redClient;

    @FXML private ListView<Users> listViewDevices;
    @FXML private VBox vboxDefault; // VBox del mensaje por defecto
    @FXML private VBox vboxChat;    // VBox de los mensajes

    @FXML
    private Label chatUserLabel;  // Etiqueta para mostrar el nombre del usuario con el que estamos chateando
    @FXML
    private TextArea chatTextArea;  // Área de texto donde se muestra el historial del chat
    @FXML
    private TextField messageTextField;

    private String chatUser;

    @FXML
    public void initialize(){
        redServer = new RedServer();
        redClient = new RedClient();
        redServer.start();

        startUserDiscovery();

        // Configurar celdas personalizadas con imágenes de los contactos
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

        // Agregar listener para cambiar entre vboxDefault y vboxChat
        listViewDevices.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // Ocultar el VBox de "Selecciona un contacto" y mostrar el chat
                vboxDefault.setVisible(false);
                vboxDefault.setManaged(false);
                vboxChat.setVisible(true);
                vboxChat.setManaged(true);

                System.out.println("Mostrando chat con: " + newVal.getNickname());
            } else {
                // Si no hay selección, mostrar el mensaje por defecto
                vboxDefault.setVisible(true);
                vboxDefault.setManaged(true);
                vboxChat.setVisible(false);
                vboxChat.setManaged(false);
            }
        });
    }

    public void initChat(String usuario) {
        this.chatUser = usuario;
        chatUserLabel.setText("Chat con: " + usuario);  // Mostrar el nombre del usuario en el encabezado
    }

    /**
     * Envía un mensaje. Este método se invoca al pulsar el botón "Enviar".
     * El mensaje se muestra en el área de chat y se guarda en la base de datos.
     */
    @FXML
    private void sendMessage() {
        String message = messageTextField.getText();
        if (!message.isEmpty()) {
            chatTextArea.appendText("Tú: " + message + "\n");
            messageTextField.clear();

            // Crear el objeto Message y guardarlo en la base de datos
            // Supongamos que chatUser es el remitente y defines un destinatario fijo para la prueba
            String remitente = "prueba"; // chatUser se establece en initChat()
            String destinatario = "DestinatarioFijo"; // o puedes obtenerlo de otro control
            Message newMessage = new Message(destinatario, remitente, message);

            // Crea la conexión con la base de datos (por ejemplo, usando MySQLConnector)
            MessageDAO messageDAO = new MessageDAOImpl(new SQLiteConnector());
            messageDAO.saveMessage(newMessage);
        }
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
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                }
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
