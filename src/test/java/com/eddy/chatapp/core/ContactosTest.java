package com.eddy.chatapp.core;

import com.eddy.chatapp.dao.DatabaseConnector;
import com.eddy.chatapp.dao.SQLiteConnector;
import com.eddy.chatapp.dao.UsuarioDAO;
import com.eddy.chatapp.dao.UsuarioDAOImpl;
import com.eddy.chatapp.model.Users;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContactosTest {
   /* private UsuarioDAOImpl contactos;
    private Connection connection;

    @Test
    void testIsUserActive() {
        Users user1 = new Users("AA:BB:CC:DD:EE:FF", "Juan", new byte[]{1, 2, 3});
        Users user2 = new Users("11:22:33:44:55:66", "Maria", new byte[]{4, 5, 6});

        contactos.addContact(user1);
        contactos.addContact(user2);

        List<Users> activeUsers = contactos.isUserActive();
        assertNotNull(activeUsers);
        assertEquals(2, activeUsers.size());

        // Verificar que los usuarios activos sean los esperados
        assertTrue(activeUsers.stream().anyMatch(user -> "AA:BB:CC:DD:EE:FF".equals(user.getId())));
        assertTrue(activeUsers.stream().anyMatch(user -> "11:22:33:44:55:66".equals(user.getId())));

        // Eliminar los contactos después del test
        contactos.deleteContact(user1.getId());
        contactos.deleteContact(user2.getId());
    }*/
}