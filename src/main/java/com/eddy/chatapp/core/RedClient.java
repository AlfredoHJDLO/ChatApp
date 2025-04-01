package com.eddy.chatapp.core;

import com.eddy.chatapp.model.Users;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Esta clase es la controladora del cliente
 * es la encargada de buscar los dispositivos
 * conectados.
 *
 * @author AlfredoHJDLO
 * @author Ricardo Daniel Lopez Jimenez
 * @version 0.9
 * */
public class RedClient {
    private static final int DISCOVERY_PORT = 7400;
    private final List<Users> connectedUsers = Collections.synchronizedList(new ArrayList<>());
    private static final int MAX_THREADS = 50; // Número de hilos concurrentes
    private final String selfIP; // IP del propio dispositivo

    public RedClient() {
        this.selfIP = getSelfIP();
    }

    private final Map<String, Long> userLastSeen = Collections.synchronizedMap(new HashMap<>());
    private static final int USER_TIMEOUT = 5000; // Tiempo límite para usuarios inactivos en milisegundos

    public List<Users> discoverUsers() {
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
        String localSubnet = getLocalSubnet();

        System.out.println("📡 Escaneando la subred: " + localSubnet);
        System.out.println("🚫 Mi dirección IP: " + selfIP);

        List<Future<?>> futures = new ArrayList<>();
        for (int i = 1; i < 255; i++) {
            String host = localSubnet + i;

            if (host.equals(selfIP)) {
                System.out.println("🛑 Saltando mi propio dispositivo: " + host);
                continue;
            }

            futures.add(executor.submit(() -> scanHost(host)));
        }

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException ignored) {}
        }

        executor.shutdown();

        // Limpieza de usuarios inactivos
        long currentTime = System.currentTimeMillis();
        userLastSeen.entrySet().removeIf(entry -> (currentTime - entry.getValue()) > USER_TIMEOUT);
        connectedUsers.removeIf(user -> !userLastSeen.containsKey(user.getId()));

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

                synchronized (connectedUsers) {
                    boolean exists = connectedUsers.stream().anyMatch(user -> user.getId().equals(userId));
                    if (!exists) {
                        connectedUsers.add(new Users(userId, nickname, imageBytes, host));
                        System.out.println("✅ Usuario encontrado en " + host);
                    }
                }

                // Actualizar la marca de tiempo del usuario
                userLastSeen.put(userId, System.currentTimeMillis());
            }
        } catch (IOException ignored) {
        }
    }

    private String getLocalSubnet() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();

                // Ignorar interfaces inactivas o virtuales
                if (!iface.isUp() || iface.isLoopback() || iface.isVirtual()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();

                    // Solo considerar direcciones IPv4 válidas (descartando localhost y redes virtuales)
                    if (addr instanceof Inet4Address && !addr.isLoopbackAddress() && !addr.isAnyLocalAddress()) {
                        byte[] ip = addr.getAddress();
                        return (ip[0] & 0xFF) + "." + (ip[1] & 0xFF) + "." + (ip[2] & 0xFF) + ".";
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null; // Devuelve null si no encuentra una IP válida
    }


    private String getSelfIP() {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            System.out.println("🌐 IP detectada: " + ip);
            return ip;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "";
        }
    }

}
