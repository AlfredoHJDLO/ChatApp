package com.eddy.chatapp.dao;

import com.eddy.chatapp.core.MacID;
import com.eddy.chatapp.model.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAOImpl implements UsuarioDAO {

    private DatabaseConnector connector;
    public UsuarioDAOImpl(DatabaseConnector connector) {
        this.connector = connector;
    }

    @Override
    public boolean registro(Users user) {
        String sql = "INSERT into users (id, nickname, password, foto) values (?,?,?,?)";
        try(Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, user.getId());
            stmt.setString(2, user.getNickname());
            stmt.setString(3, user.getPassword());
            stmt.setBytes(4, user.getfoto());
            return stmt.executeUpdate() > 0;
        }catch (SQLException E)
        {
            E.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean Registrado() {
        String sql = "SELECT * FROM users WHERE id = ?";
        try(Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1,"0");
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }catch (SQLException E)
        {
            E.printStackTrace();
            return false;
        }
    }

    @Override
    public String obtenerContra() {
        String sql = "SELECT * FROM users WHERE id = 0";
        try(Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
                return rs.getString("password");
            }
            return null;
        }catch (SQLException E)
        {
            E.printStackTrace();
            return null;
        }
    }
}
