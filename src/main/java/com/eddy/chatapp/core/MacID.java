package com.eddy.chatapp.core;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MacID {
    public static String obtenerId()
    {
        try {
            // Obtener todas las interfaces de red disponibles
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();

                // Obtener la dirección MAC de la interfaz
                byte[] macAddress = networkInterface.getHardwareAddress();

                if (macAddress != null) {
                    // Convertir los bytes de la dirección MAC a formato legible
                    StringBuilder macString = new StringBuilder();
                    for (int i = 0; i < macAddress.length; i++) {
                        macString.append(String.format("%02X", macAddress[i]));
                        if (i < macAddress.length - 1) {
                            macString.append("-");
                        }
                    }
                    return macString.toString(); // Devuelve la dirección MAC
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("Error al obtener la dirección MAC.");
        }
        return null; // Devuelve null si no se encuentra la dirección MAC

    }

}
