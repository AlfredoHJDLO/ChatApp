package com.eddy.chatapp.core;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Esta clase obtendrá el Mac de la computadora
 * para utilizarlo como id
 *
 * @author AlfredoHJDLO
 * @author Ricardo Daniel Lopez Jimenez
 *
 * @version 0.9
 * */
public class MacID {

    /**
     * Esta función es estatica. Sirve para regresar
     * la mac en una cadena
     *
     * @return Esta función regresa la cadena con la dirección
     * mac
     * */
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
