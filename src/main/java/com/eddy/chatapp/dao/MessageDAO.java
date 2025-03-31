package com.eddy.chatapp.dao;

import com.eddy.chatapp.model.Message;

import java.util.List;

public interface MessageDAO {
    void saveMessage(Message message);
    List<Message> getMessages(String remitente, String destinatario);
}
