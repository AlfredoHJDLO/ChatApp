package com.eddy.chatapp.dao;

import com.eddy.chatapp.model.BlockedUsers;
import com.eddy.chatapp.dao.MySQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlockedUserDAOImpl implements BlockedUserDAO {
    private DatabaseConnector connector;
    public BlockedUserDAOImpl(DatabaseConnector connector) {
        this.connector = connector;
    }

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
