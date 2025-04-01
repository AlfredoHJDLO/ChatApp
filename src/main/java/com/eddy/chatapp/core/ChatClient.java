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
    private MessageListener messageListener;
    private MessageDAO messageDAO;

    public static void main(String[] args) {
        // Ejemplo de uso: la IP del servidor es "192.168.2.120".
        // Los parámetros para sendMessage son:
        // 1) IP del destinatario (para envío en tiempo real; no se envía al servidor),
        // 2) La MAC del destinatario,
        // 3) El mensaje.
        String serverIP = "192.168.2.138"; // Cambia a la IP del servidor
        //ChatClient client = new ChatClient(serverIP);

        // Ejemplo: se envía un mensaje al destinatario con MAC "AA-BB-CC-DD-EE-FF"
        // y la IP del destinatario (por ejemplo, "192.168.2.121") para el envío en tiempo real.
        //client.sendMessage("192.168.2.121", "AA-BB-CC-DD-EE-FF", "Hola desde otra PC");
    }
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


    /**
     * Configura el listener para recibir mensajes del servidor.
     *
     * @param listener una implementación de MessageListener para manejar mensajes entrantes
     */
    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }

    /**
     * Método que escucha continuamente los mensajes enviados por el servidor.
     */
    private void listenForMessages() {
        try {
            while (true) {
                String response = in.readUTF();
                if (messageListener != null) {
                    messageListener.onMessageReceived(response);
                }
            }
        } catch (EOFException eof) {
            System.out.println("La conexión se cerró (EOFException).");
        } catch (IOException e) {e.printStackTrace();       }
    }

            /**
             * Interfaz para manejar eventos de mensajes recibidos.
             */
    public interface MessageListener {
        void onMessageReceived(String message);
    }
}
