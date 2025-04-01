package com.eddy.chatapp.core;

import com.eddy.chatapp.dao.DatabaseConnector;
import com.eddy.chatapp.dao.MySQLConnector;
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
    private Contactos contactos;
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
        contactos = new Contactos(new MySQLConnector());
    }

    @BeforeEach
    void cleanTable() throws SQLException {
        connection.createStatement().execute("DELETE FROM users");
        //connection.createStatement().execute("INSERT INTO users (id, nickname, password, foto) VALUES ('0', 'Admin', 'x', NULL)");
    }

    @AfterAll
    void closeDatabase() throws SQLException {
        connection.close();
    }

    @Test
    void testAddContact() {
        Users user = new Users("FF:EE:DD:CC:BB:AA", "Ricardo", new byte[]{1, 2, 3});
        assertTrue(contactos.addContact(user));

        List<Users> contacts = contactos.listContacts();
        // Imprimir todos los contactos
        System.out.println("Contactos en la tabla:");
        for (Users contact : contacts) {
            System.out.println("ID: " + contact.getId() + ", Nickname: " + contact.getNickname());
        }

        assertEquals(1, contacts.size());
        assertEquals("Ricardo", contacts.get(0).getNickname());

        // Eliminar el contacto después del test
        contactos.deleteContact(user.getId());
    }

    @Test
    void testDeleteContact() {
        Users user = new Users("AA:BB:CC:DD:EE:FF", "Juan", new byte[]{1, 2, 3});
        contactos.addContact(user);

        assertTrue(contactos.deleteContact("AA:BB:CC:DD:EE:FF"));
        List<Users> contacts = contactos.listContacts();
        assertTrue(contacts.isEmpty());

        // Eliminar el contacto después del test (si queda algún contacto por eliminar)
        contactos.deleteContact(user.getId());
    }

    @Test
    void testDeleteContact_IdZero() {
        assertFalse(contactos.deleteContact("0"));

        // No es necesario eliminar nada en este caso, ya que no se ha agregado ningún contacto.
    }

    @Test
    void testListContacts() {
        Users user1 = new Users("AA:BB:CC:DD:EE:FF", "Juan", new byte[]{1, 2, 3});
        Users user2 = new Users("11:22:33:44:55:66", "Maria", new byte[]{4, 5, 6});

        contactos.addContact(user1);
        contactos.addContact(user2);

        List<Users> contacts = contactos.listContacts();
        assertEquals(2, contacts.size());

        // Eliminar los contactos después del test
        contactos.deleteContact(user1.getId());
        contactos.deleteContact(user2.getId());
    }

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
    }
}