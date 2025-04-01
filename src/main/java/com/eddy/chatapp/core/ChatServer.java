package com.eddy.chatapp.core;

import com.eddy.chatapp.dao.SQLiteConnector;
import com.eddy.chatapp.dao.MessageDAO;
import com.eddy.chatapp.dao.MessageDAOImpl;
import com.eddy.chatapp.model.Message;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
/**
 * Esta clase implementa el servidor de chat que escucha conexiones de clientes,
 * recibe mensajes y los guarda en la base de datos.
 *
 * @author Eduardo
 */
public class ChatServer {
    private static final int PORT = 7500;
    private static MessageDAO messageDAO;

    /**
     * Método principal para iniciar el servidor de chat.
     * Inicializa la conexión a la base de datos y entra en un ciclo de espera de clientes.
     *
     *
     */
    public static void start() {
        // Usamos MySQLConnector como la conexión a la BD
        messageDAO = new MessageDAOImpl(new SQLiteConnector());

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

    /**
     * Maneja la conexión de un cliente, recibe el mensaje y lo guarda en la base de datos.
     *
     * @param clientSocket el socket del cliente conectado
     */
    private static void handleClient(Socket clientSocket) {
        try (DataInputStream in = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {

            String remitenteMAC = MacID.obtenerId();
            String mensaje = in.readUTF();



            Message message = new Message("0", remitenteMAC, mensaje);
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
