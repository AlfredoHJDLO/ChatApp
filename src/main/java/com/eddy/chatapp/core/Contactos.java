package com.eddy.chatapp.core;

import com.eddy.chatapp.dao.DatabaseConnector;
import com.eddy.chatapp.model.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Contactos {
    private DatabaseConnector connector;
    public Contactos(DatabaseConnector connector) {
        this.connector = connector;
    }

    /**
     * Este es el método addContact, sirve para agregar contactos a la base de datos.
     * @param user Es el modelo que va a insertar en la base de datos.
     * @return Regresa {@code true} si se agregó correctamente a la tabla de usuarios
     * y {@code false} en caso contrario.
     */
    public boolean addContact(Users user) {
        String sql = "INSERT INTO users (id, nickname, password, foto) VALUES (?, ?, ?, ?)";
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getId());
            stmt.setString(2, user.getNickname());
            stmt.setString(3, user.getPassword() == null ? "" : user.getPassword()); // Manejar NULL
            stmt.setBytes(4, user.getFoto());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Este es el método deleteContact, sirve para eliminar un contacto.
     * @param id String que contiene la mac address del contacto a eliminar
     * @return Regresa {@code true} si el contacto se pudo eliminar y {@code false} en caso
     * de que no se haya podido desbloquear.
     */
    public boolean deleteContact(String id) {
        // Evita borrar el usuario con id "0"
        if ("0".equals(id)) {
            System.err.println("No se puede eliminar el contacto con ID = 0.");
            return false;
        }

        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Este es el método listContacts, sirve para listar a todos los contactos de un usuario
     * @return Regresa una lista de objetos {@code Users} que contienen el ID, el nickname y la
     * foto de cada contacto. Si no hay contactos registrados (excepto el ID "0"), la lista
     * estará vacía.
     */
    public List<Users> listContacts() {
        List<Users> contacts = new ArrayList<>();
        String sql = "SELECT id, nickname, foto FROM users WHERE id <> '0'";

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Users user = new Users(
                        rs.getString("id"),
                        rs.getString("nickname"),
                        rs.getBytes("foto") // foto es un array de bytes (byte[])
                );
                contacts.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }
//--------------------------------------------------------------------------------------------------------
    //private RedClient redClient;

    /*public Contactos() {
        this.redClient = new RedClient();
    }¨/


    /**
     * Este es el método isUserActive, sirve para verificar si un usuario está activo en la red.
     * además de verificar si el usuario está en la lista de usuarios conectados.
     * @param macAddress Es la dirección MAC del usuario a verificar.
     * @return Regresa {@code true} si el usuario está activo y {@code false} en caso contrario.
     */
    public List<Users> isUserActive() {

        List <Users> connectedUsers = listContacts();

        for (Users user : connectedUsers){
            if (user.getId() != null && !user.getId().isEmpty()) {
                return connectedUsers;
            }
        }
        return null;
    }
}
