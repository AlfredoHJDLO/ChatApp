package com.eddy.chatapp.dao;

import com.eddy.chatapp.model.BlockedUsers;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BlockedUserDAOImplTest {
    private BlockedUserDAOImpl blockedUserDAO;
    private Connection connection;

    @BeforeAll
    void setupDatabase() throws SQLException {
        // Crear conexión a H2 en memoria
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");

        // Crear la tabla con la estructura correcta
        connection.createStatement().execute("CREATE TABLE blocked_users (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, mac_address TEXT)");

        // Pasar un DatabaseConnector para que el DAO obtenga la conexión
        DatabaseConnector connector = () -> connection;

        // Inicializar BlockedUserDAOImpl con el conector
        blockedUserDAO = new BlockedUserDAOImpl(connector);
    }

    @BeforeEach
    void cleanTable() throws SQLException {
        connection.createStatement().execute("DELETE FROM blocked_users");
    }

    @AfterAll
    void closeDatabase() throws SQLException {
        connection.close();
    }

    @Test
    void testAddBlockedUser() {
        BlockedUsers user = new BlockedUsers(1, "AA:BB:CC:DD:EE:FF");
        assertTrue(blockedUserDAO.addBlockedUser(user));
    }

    @Test
    void testIsBlocked() {
        BlockedUsers user = new BlockedUsers(1, "AA:BB:CC:DD:EE:FF");
        blockedUserDAO.addBlockedUser(user);
        assertTrue(blockedUserDAO.isBlocked("AA:BB:CC:DD:EE:FF"));
        assertFalse(blockedUserDAO.isBlocked("00:11:22:33:44:55"));
    }

    @Test
    void testRemoveBlockedUser() {
        BlockedUsers user = new BlockedUsers(1, "AA:BB:CC:DD:EE:FF");
        blockedUserDAO.addBlockedUser(user);
        assertTrue(blockedUserDAO.removeBlockedUser("AA:BB:CC:DD:EE:FF"));
        assertFalse(blockedUserDAO.isBlocked("AA:BB:CC:DD:EE:FF"));
    }
}
