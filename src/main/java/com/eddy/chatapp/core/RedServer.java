package com.eddy.chatapp.core;

import com.eddy.chatapp.dao.UsuarioDAO;

import java.io.*;
import java.net.*;

public class RedServer extends Thread {
    private String userId;
    private String nickname;
    private String profilePicturePath;
    private int port = 7400;

    public RedServer() {
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("🔍 Servidor de descubrimiento activo en el puerto " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                // Enviar ID y nickname
                out.writeUTF(UsuarioDAO.yo().getId());
                out.writeUTF(UsuarioDAO.yo().getNickname());
                out.writeInt(UsuarioDAO.yo().getfoto().length);
                out.write(UsuarioDAO.yo().getfoto());

                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
