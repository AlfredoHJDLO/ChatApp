package com.eddy.chatapp.gui;

import com.eddy.chatapp.core.Contactos;
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

/**
 * Esta clase es el controlador de la interfaz de chat.
 * Se encarga de gestionar la interacción con el usuario y el envío de mensajes a la base de datos.
 *
 * @author Eduardo
 */
public class ChatController {

    @FXML
    private Label chatUserLabel;  // Etiqueta para mostrar el nombre del usuario con el que estamos chateando
    @FXML
    private TextArea chatTextArea;  // Área de texto donde se muestra el historial del chat
    @FXML
    private TextField messageTextField;  // Campo de texto para escribir nuevos mensajes

    private String chatUser;
    @FXML private ListView<Users> listViewDevices;
    @FXML private VBox vboxDefault; // VBox del mensaje por defecto
    @FXML private VBox vboxChat;    // VBox de los mensajes


    @FXML
    public void initialize (){
        startUsers();
        //System.out.println(listViewDevices);
        if (listViewDevices != null){
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
    }

    private void startUsers (){
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call () throws Exception {
                while (true) {
                    Contactos contactosDAO = new Contactos(); // Crear instancia de Contactos
                    List<Users> contactos = contactosDAO.listContacts(); // Obtener contactos de la BD
                    System.out.println("Entrando");

                    Platform.runLater(() -> {
                        listViewDevices.getItems().setAll(contactos);
                    });

                    try {
                        Thread.sleep(15000); // Actualiza la lista cada segundo
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
    /**
     * Inicializa el chat estableciendo el usuario con el que se chatea.
     *
     * @param usuario el nombre del usuario con el que se inicia el chat
     */

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

    @FXML
    private void Bloquear() {

    }

}

