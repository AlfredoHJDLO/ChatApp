package com.eddy.chatapp.core;

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

    /**
     * Constructor que establece la conexión con el servidor de chat.
     *
     * @param serverIP la dirección IP del servidor de chat
     */
    public ChatClient(String serverIP) {
        try {
            socket = new Socket(serverIP, 7500);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            new Thread(this::listenForMessages).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Envía un mensaje al servidor de chat.
     *
     * @param remitente   el nombre del remitente
     * @param destinatario el nombre del destinatario
     * @param message     el contenido del mensaje
     */
    public void sendMessage(String remitente, String destinatario, String message) {
        try {
            out.writeUTF(remitente);
            out.writeUTF(destinatario);
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Interfaz para manejar eventos de mensajes recibidos.
     */
    public interface MessageListener {
        void onMessageReceived(String message);
    }
}
