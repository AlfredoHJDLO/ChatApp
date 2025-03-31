package com.eddy.chatapp.dao;

import com.eddy.chatapp.model.Users;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioDAOImplTest {
    private UsuarioDAO usuarioDAO;
    private Connection connection;

    @BeforeAll
    void setupDatabase() throws SQLException {
        // Crear conexión a H2 en memoria
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");

        // Crear la tabla con la estructura correcta
        connection.createStatement().execute("CREATE TABLE users (id TEXT PRIMARY KEY, nickname TEXT, password TEXT NULL, foto MEDIUMBLOB)");

        // Pasar un DatabaseConnector para que el DAO obtenga la conexión
        DatabaseConnector connector = () -> connection;

        // Inicializar UsuarioDAO con el conector
        usuarioDAO = new UsuarioDAOImpl(new MySQLConnector());
    }

    @BeforeEach
    void cleanTable() throws SQLException {
        connection.createStatement().execute("DELETE FROM users");
        connection.createStatement().execute("INSERT INTO users (id, nickname, password, foto) VALUES ('0', 'Admin', 'x', NULL)");
    }

    @AfterAll
    void closeDatabase() throws SQLException {
        connection.close();
    }

    @Test
    void testAddContact() {
        Users user = new Users("FF:EE:DD:CC:BB:AA", "Ricardo", new byte[]{1, 2, 3});
        assertTrue(usuarioDAO.addContact(user));

        List<Users> contacts = usuarioDAO.listContacts();
        assertEquals(1, contacts.size());
        assertEquals("Ricardo", contacts.getFirst().getNickname());
    }

    @Test
    void testDeleteContact() {
        Users user = new Users("AA:BB:CC:DD:EE:FF", "Juan", new byte[]{1, 2, 3});
        usuarioDAO.addContact(user);

        assertTrue(usuarioDAO.deleteContact("AA:BB:CC:DD:EE:FF"));
        List<Users> contacts = usuarioDAO.listContacts();
        assertTrue(contacts.isEmpty());
    }

    @Test
    void testDeleteContact_IdZero() {
        assertFalse(usuarioDAO.deleteContact("0"));
    }

    @Test
    void testListContacts() {
        Users user1 = new Users("AA:BB:CC:DD:EE:FF", "Juan", new byte[]{1, 2, 3});
        Users user2 = new Users("11:22:33:44:55:66", "Maria", new byte[]{4, 5, 6});

        usuarioDAO.addContact(user1);
        usuarioDAO.addContact(user2);

        List<Users> contacts = usuarioDAO.listContacts();
        assertEquals(2, contacts.size());
    }
}