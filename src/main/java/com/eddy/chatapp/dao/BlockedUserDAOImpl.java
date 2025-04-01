package com.eddy.chatapp.dao;

import com.eddy.chatapp.model.BlockedUsers;
import com.eddy.chatapp.dao.MySQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Esta clase es la controladora de los usuarios bloqueados
 * Contiene tres metodos: addBlockedUser, isBlocked y removeBlockedUser
 *
 * @author Jacob Jahir Vera Del Carmen
 * @author Saul David Peña Martinez
 * @version 0.9
 */

public class BlockedUserDAOImpl implements BlockedUserDAO {
    private DatabaseConnector connector;

    /**
     * Este es el contructor, establece la conexión con la base de datos
     * @param connector
     */
    public BlockedUserDAOImpl(DatabaseConnector connector) {
        this.connector = connector;
    }

    /**
     * Este es el método addBlockedUser, sirve para bloquear a un usuario.
     * @param blockedUser El modelo del usuario bloqueado.
     * @return Regresa {@code true} si se agregó correctamente a la tabla de usuarios
     * bloqueados y {@code false} en caso contrario.
     */
    @Override
    public boolean addBlockedUser(BlockedUsers blockedUser){
        String sql = "INSERT INTO blocked_users (mac_address) VALUES (?)";
        try(Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, blockedUser.getMac_address());
            return stmt.executeUpdate() > 0;
        }catch (SQLException E)
        {
            E.printStackTrace();
            return false;
        }
    }

    /**
     * Este es el método isBlocked, sirve para comprobar si un usuario se encuentra bloqueado.
     * @param mac_address String que contiene la mac address del usuario a comprobar si está
     *                    bloqueado.
     * @return Regresa {@code true} si el usuario se encuentra bloqueado y {@code false} en caso contrario.
     */
    @Override
    public boolean isBlocked(String mac_address){
        String sql = "SELECT * FROM blocked_users WHERE mac_address = ?";
        try(Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            if (conn.isClosed()) {
                System.err.println("ERROR: La conexión está cerrada antes de ejecutar la consulta.");
                return false;
            }
            stmt.setString(1,mac_address);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }catch (SQLException E)
        {
            E.printStackTrace();
            return false;
        }
    }

    /**
     * Este es el método removeBlockedUser, sirve para desbloquear a un usuario bloqueado.
     * @param mac_address String que contiene la mac address del usuario a comprobar si está
     *      *             bloqueado.
     * @return Regresa {@code true} si el usuario se pudo desbloquear y {@code false} en caso
     * de que no se haya podido desbloquear.
     */
    @Override
    public boolean removeBlockedUser(String mac_address){
        String sql = "DELETE from blocked_users WHERE mac_address = ?";
        try(Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, mac_address);
            return stmt.executeUpdate() > 0;
        }catch (SQLException E)
        {
            E.printStackTrace();
            return false;
        }
    }
}
