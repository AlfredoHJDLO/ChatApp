//package com.eddy.chatapp.core;
//
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Test;
//import java.io.IOException;
//import java.util.List;
//public class RedServerTest {
//
//    @Test
//    public void testGetLocalIPAddress() {
//        try {
//            // Verifica que la dirección IP local obtenida no sea null
//            String localIP = RedServer.getLocalIPAddress();
//            assertNotNull(localIP, "La dirección IP local no debería ser null.");
//            System.out.println("IP obtenida: " + localIP);
//
//            // Verifica que sea una dirección IPv4 válida
//            assertTrue(localIP.matches("\\d+\\.\\d+\\.\\d+\\.\\d+"), "La dirección IP debería ser válida y en formato IPv4.");
//        } catch (IOException e) {
//            fail("No se pudo obtener la IP local debido a una excepción: " + e.getMessage());
//        }
//    }
//
//    @Test
//    public void testIsHostActive() {
//        // Mock para verificar un host activo
//        String activeIP = "8.8.8.8"; // Usamos una IP pública conocida (Google DNS)
//        boolean isActive = RedServer.isHostActive(activeIP);
//        assertTrue(isActive, "La IP debería estar activa: " + activeIP);
//
//        // Prueba con una IP que probablemente no esté activa
//        String inactiveIP = "192.0.2.123"; // IP reservada para pruebas
//        boolean isInactive = RedServer.isHostActive(inactiveIP);
//        assertFalse(isInactive, "La IP debería estar inactiva: " + inactiveIP);
//    }
//
//    @Test
//    public void testScanNetwork() {
//        List<String> activeDevices = RedServer.scanNetwork();
//        assertNotNull(activeDevices, "La lista de dispositivos activos no debería ser null.");
//        assertFalse(activeDevices.isEmpty(), "La lista de dispositivos activos no debería estar vacía.");
//
//        // Verifica que las IPs tengan formato válido
//        for (String device : activeDevices) {
//            assertTrue(device.matches("\\d+\\.\\d+\\.\\d+\\.\\d+"), "Formato de IP inválido: " + device);
//        }
//    }
//}