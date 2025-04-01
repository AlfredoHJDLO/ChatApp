package com.eddy.chatapp.core;

import com.eddy.chatapp.dao.MessageDAO;
import com.eddy.chatapp.dao.MessageDAOImpl;
import com.eddy.chatapp.dao.SQLiteConnector;
import com.eddy.chatapp.model.Message;

import java.io.*;
import java.net.*;
import java.util.function.Consumer;

public class ChatServer {
    private static final int PORT = 7500;
    private static MessageDAO messageDAO;
    private static Consumer<Message> messageListener; // Listener para recibir mensajes en la UI

    public static void start() {
        messageDAO = new MessageDAOImpl(new SQLiteConnector());

        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("📡 Servidor de chat activo en el puerto " + PORT);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(() -> handleClient(clientSocket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void handleClient(Socket clientSocket) {
        try (DataInputStream in = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {

            String remitenteMAC = MacID.obtenerId();
            String mensaje = in.readUTF();

            // Crear el objeto mensaje
            Message message = new Message("0", remitenteMAC, mensaje);
            messageDAO.saveMessage(message); // Guardar en la BD

            // Notificar a la UI
            if (messageListener != null) {
                messageListener.accept(message);
            }

            out.writeUTF("✅ Mensaje recibido y guardado en la base de datos");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ignored) {}
        }
    }

    public static void setMessageListener(Consumer<Message> listener) {
        messageListener = listener;
    }
}
