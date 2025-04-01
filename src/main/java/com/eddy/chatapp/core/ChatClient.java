package com.eddy.chatapp.core;

import com.eddy.chatapp.dao.MessageDAOImpl;
import com.eddy.chatapp.dao.SQLiteConnector;
import com.eddy.chatapp.model.Message;
import com.eddy.chatapp.dao.MessageDAO;

import java.io.*;
import java.net.*;

/**
 * Esta clase implementa el cliente de chat que se conecta al servidor, envía mensajes
 * y escucha respuestas.
 *
 * @author Eduardo
 */
public class ChatClient {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private MessageDAO messageDAO;

    /**
     * Envía un mensaje al servidor de chat.
     *
     * Este método recibe como parámetros:
     * 1) La IP del destinatario (para envío en tiempo real, no se envía al servidor),
     * 2) La MAC del destinatario (para identificarlo en el historial),
     * 3) El texto del mensaje.
     *
     * Internamente, se obtiene la MAC del remitente usando MacID.obtenerId() y se envían al servidor:
     * - La MAC del remitente.
     * - La MAC del destinatario.
     * - El texto del mensaje
     * @param destinatarioIP la dirección MAC del destinatario.
     * @param mensaje el contenido del mensaje.
     */
    public void sendMessage(String destinatarioIP, String mensaje) {
        try (Socket socket = new Socket(destinatarioIP, 7500)) {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // Obtener la MAC del remitente
            String remitenteMAC = MacID.obtenerId(); // Usando tu clase implementada

            // Enviar datos al destinatario
            out.writeUTF(remitenteMAC); // Envía la MAC del remitente
            out.writeUTF(mensaje);      // Envía el mensaje

            System.out.println("Mensaje enviado correctamente a " + destinatarioIP);
        } catch (IOException e) {
            System.err.println("Error al enviar el mensaje: " + e.getMessage());
        }
    }
}
