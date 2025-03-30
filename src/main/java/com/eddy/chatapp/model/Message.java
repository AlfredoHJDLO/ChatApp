package com.eddy.chatapp.model;

import java.time.LocalDateTime;

public class Message {
    private int id;
    private String destinatario;
    private String remitente;
    private String texto;
    private LocalDateTime timestamp;

    public Message(int id, String destinatario, String remitente, String texto, LocalDateTime timestamp) {
        this.id = id;
        this.destinatario = destinatario;
        this.remitente = remitente;
        this.texto = texto;
        this.timestamp = timestamp;
    }
    public Message(String destinatario, String remitente, String texto) {
        this.destinatario = destinatario;
        this.remitente = remitente;
        this.texto = texto;
        this.timestamp = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getRemitente() {
        return remitente;
    }

    public String getTexto() {
        return texto;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
