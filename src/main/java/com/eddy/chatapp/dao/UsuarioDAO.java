package com.eddy.chatapp.dao;

import com.eddy.chatapp.core.MacID;
import com.eddy.chatapp.model.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface UsuarioDAO {
    public boolean registro(Users user);
    public boolean Registrado();
    public String obtenerContra();
    public static Users yo(){
        String sql = "SELECT * FROM users WHERE id = 0";
        try(Connection conn = new MySQLConnector().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
            {
                return new Users(MacID.obtenerId(), rs.getString("nickname"),rs.getBytes("foto") );
            }
            return null;
        }catch (SQLException E)
        {
            E.printStackTrace();
            return null;
        }
    }
}
