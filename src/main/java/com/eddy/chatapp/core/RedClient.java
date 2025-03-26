package com.eddy.chatapp.core;

import com.eddy.chatapp.model.Users;

import java.io.*;
import java.net.*;
import java.util.*;

public class RedClient {
    private static final int DISCOVERY_PORT = 7400;
    private List<Users> connectedUsers = new ArrayList<>();

    public List<Users> discoverUsers() {
        connectedUsers.clear();

        try {
            // Obtener la subred local
            String localSubnet = getLocalSubnet();
            for (int i = 1; i < 255; i++) {
                String host = localSubnet + i;

                try (Socket socket = new Socket()) {
                    socket.connect(new InetSocketAddress(host, DISCOVERY_PORT), 100);

                    DataInputStream in = new DataInputStream(socket.getInputStream());

                    // Recibir ID y nickname
                    String userId = in.readUTF();
                    String nickname = in.readUTF();

                    // Recibir imagen de perfil
                    int imageSize = in.readInt();
                    byte[] imageBytes = new byte[imageSize];
                    in.readFully(imageBytes);

                    // Guardar usuario en la lista
                    connectedUsers.add(new Users(userId, nickname, imageBytes));
                    socket.close();

                } catch (IOException ignored) {}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connectedUsers;
    }

    private String getLocalSubnet() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        byte[] ip = localHost.getAddress();
        return (ip[0] & 0xFF) + "." + (ip[1] & 0xFF) + "." + (ip[2] & 0xFF) + ".";
    }
}
