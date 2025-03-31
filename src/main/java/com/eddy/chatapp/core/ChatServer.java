package com.eddy.chatapp.core;

import com.eddy.chatapp.dao.MySQLConnector;
import com.eddy.chatapp.dao.MessageDAO;
import com.eddy.chatapp.dao.MessageDAOImpl;
import com.eddy.chatapp.model.Message;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;

public class ChatServer {
    private static final int PORT = 7500;
    private static MessageDAO messageDAO;

    public static void main(String[] args) {
        // Usamos MySQLConnector como la conexión a la BD
        messageDAO = new MessageDAOImpl(new MySQLConnector());

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("📡 Servidor de chat activo en el puerto " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (DataInputStream in = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {

            String remitente = in.readUTF();
            String destinatario = in.readUTF();
            String mensaje = in.readUTF();

            // Crear objeto Message y guardarlo en BD
            Message message = new Message(0, remitente, destinatario, mensaje, LocalDateTime.now());
            messageDAO.saveMessage(message);

            out.writeUTF("✅ Mensaje recibido y guardado en la base de datos");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ignored) {}
        }
    }
}
