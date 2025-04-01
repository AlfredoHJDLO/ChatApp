package com.eddy.chatapp.dao;

import com.eddy.chatapp.model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDAOImpl implements MessageDAO {
    private DatabaseConnector connector;
    public MessageDAOImpl(DatabaseConnector connector) {
        this.connector = connector;
    }

    @Override
    public void saveMessage(Message message) {
        String sql = "INSERT INTO messages (remitente, destinatario, texto, timestamp) VALUES (?, ?, ?, ?)";
        try(Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, message.getRemitente());
            stmt.setString(2, message.getDestinatario());
            stmt.setString(3, message.getTexto());
            stmt.setString(4, message.getTimestamp().toString());
            stmt.executeUpdate();
        }catch (SQLException E)
        {
            E.printStackTrace();
        }
    }

    @Override
    public List<Message> getMessages(String remitente, String destinatario) {
        List<Message> messages = new ArrayList<Message>();
        String sql = "SELECT * FROM messages WHERE remitente = ? AND destinatario = ? OR remitente = ? AND destinatario = ? ORDER BY timestamp ASC";
        try(Connection conn = connector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, remitente);
            stmt.setString(2, destinatario);
            stmt.setString(3, destinatario);
            stmt.setString(4, remitente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                messages.add(new Message(rs.getString("destinatario"), rs.getString("remitente"), rs.getString("texto")));
            }
        }catch (SQLException E)
        {
            E.printStackTrace();
        }
        return messages;
    }
}
