package com.eddy.chatapp.gui;

import com.eddy.chatapp.dao.MessageDAO;
import com.eddy.chatapp.dao.MessageDAOImpl;
import com.eddy.chatapp.dao.MySQLConnector;
import com.eddy.chatapp.model.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
            MessageDAO messageDAO = new MessageDAOImpl(new MySQLConnector());
            messageDAO.saveMessage(newMessage);
        }
    }

}

