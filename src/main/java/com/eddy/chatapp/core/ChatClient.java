package com.eddy.chatapp.core;

import java.io.*;
import java.net.*;

public class ChatClient {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private MessageListener messageListener;

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

    // Enviar mensaje: remitente, destinatario y texto
    public void sendMessage(String remitente, String destinatario, String message) {
        try {
            out.writeUTF(remitente);
            out.writeUTF(destinatario);
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Configurar listener para mensajes entrantes
    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }

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

    public interface MessageListener {
        void onMessageReceived(String message);
    }
}
