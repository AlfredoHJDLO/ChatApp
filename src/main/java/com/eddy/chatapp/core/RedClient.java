package com.eddy.chatapp.core;

import com.eddy.chatapp.model.Users;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class RedClient {
    private static final int DISCOVERY_PORT = 7400;
    private List<Users> connectedUsers = Collections.synchronizedList(new ArrayList<>());
    private static final int MAX_THREADS = 50;  // Número de hilos concurrentes
    private String selfIP; // IP de tu propio dispositivo

    public RedClient() {
        try {
            selfIP = InetAddress.getLocalHost().getHostAddress(); // Obtener IP del propio dispositivo
        } catch (UnknownHostException e) {
            e.printStackTrace();
            selfIP = ""; // En caso de error, evita que cause problemas
        }
    }

    public List<Users> discoverUsers() {
        connectedUsers.clear();
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
        String localSubnet;

        try {
            localSubnet = getLocalSubnet();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return connectedUsers;
        }

        List<Future<?>> futures = new ArrayList<>();
        for (int i = 1; i < 255; i++) {
            String host = localSubnet + i;

            // Excluir la IP propia
            if (host.equals(selfIP)) {
                System.out.println("🛑 Saltando mi propio dispositivo: " + selfIP);
                continue;
            }

            futures.add(executor.submit(() -> scanHost(host)));
        }

        // Esperar a que todos los hilos terminen
        for (Future<?> future : futures) {
            try {
                future.get(); // Espera la finalización de cada tarea
            } catch (InterruptedException | ExecutionException ignored) {}
        }

        executor.shutdown();
        return connectedUsers;
    }

    private void scanHost(String host) {
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

            connectedUsers.add(new Users(userId, nickname, imageBytes));

            System.out.println("✅ Usuario encontrado en " + host);
        } catch (IOException ignored) {}
    }

    private String getLocalSubnet() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        byte[] ip = localHost.getAddress();
        return (ip[0] & 0xFF) + "." + (ip[1] & 0xFF) + "." + (ip[2] & 0xFF) + ".";
    }
}
