package com.eddy.chatapp.core;

import com.eddy.chatapp.model.Users;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class RedClient {
    private static final int DISCOVERY_PORT = 7400;
    private final List<Users> connectedUsers = Collections.synchronizedList(new ArrayList<>());
    private static final int MAX_THREADS = 50; // Número de hilos concurrentes
    private final String selfIP; // IP del propio dispositivo

    public RedClient() {
        this.selfIP = getSelfIP();
    }

    public List<Users> discoverUsers() {
        connectedUsers.clear();
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
        String localSubnet = getLocalSubnet();

        if (localSubnet == null) {
            System.err.println("❌ No se pudo obtener la subred local.");
            return connectedUsers;
        }

        List<Callable<Void>> tasks = new ArrayList<>();
        for (int i = 1; i < 255; i++) {
            String host = localSubnet + i;

            // Excluir la IP propia
            if (host.equals(selfIP)) {
                System.out.println("🛑 Saltando mi propio dispositivo: " + selfIP);
                continue;
            }

            tasks.add(() -> {
                scanHost(host);
                return null;
            });
        }

        try {
            executor.invokeAll(tasks); // Ejecuta las tareas en paralelo y espera su finalización
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();
        return connectedUsers;
    }

    private void scanHost(String host) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, DISCOVERY_PORT), 100);

            try (DataInputStream in = new DataInputStream(socket.getInputStream())) {
                // Recibir ID y nickname
                String userId = in.readUTF();
                String nickname = in.readUTF();

                // Recibir imagen de perfil
                int imageSize = in.readInt();
                byte[] imageBytes = new byte[imageSize];
                in.readFully(imageBytes);

                connectedUsers.add(new Users(userId, nickname, imageBytes));
                System.out.println("✅ Usuario encontrado en " + host);
            }
        } catch (IOException ignored) {
        }
    }

    private String getLocalSubnet() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                        byte[] ip = addr.getAddress();
                        return (ip[0] & 0xFF) + "." + (ip[1] & 0xFF) + "." + (ip[2] & 0xFF) + ".";
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null; // Devuelve null si no se encuentra una IP válida
    }

    private String getSelfIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "";
        }
    }
}
