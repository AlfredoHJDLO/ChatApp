package com.eddy.chatapp.core;

import com.eddy.chatapp.dao.UsuarioDAO;

import java.io.*;
import java.net.*;

/**
 * Esta administra el servidor. Es la encargada de
 * enviar los datos como nickname y foto de perfil
 * en la red
 *
 * @author Jacob Jahir Vera del Carmen
 * @author Saul David Peña Martínez
 * */
public class RedServer extends Thread {
    private String userId;
    private String nickname;
    private String profilePicturePath;
    private int port = 7400;

    public RedServer() {
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName("0.0.0.0"))) {
            System.out.println("🔍 Servidor de descubrimiento activo en el puerto " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> handleClient(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket socket) {
        try (DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            // Enviar ID y nickname
            out.writeUTF(UsuarioDAO.yo().getId());
            out.writeUTF(UsuarioDAO.yo().getNickname());
            out.writeInt(UsuarioDAO.yo().getFoto().length);
            out.write(UsuarioDAO.yo().getFoto());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}
