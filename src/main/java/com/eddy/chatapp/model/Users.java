package com.eddy.chatapp.model;

public class Users {
    private String id;
    private String nickname;
    private String password;
    private byte[] foto;
    private String ip;

    public Users(String id, String nickname, byte[] foto) {
        this.id = id;
        this.nickname = nickname;
        this.foto = foto;
    }

    public Users(String id, String nickname, byte[] foto, String ip) {
        this.id = id;
        this.nickname = nickname;
        this.foto = foto;
        this.ip = ip;
    }

    public Users(String nickname, String password, byte[] foto, int dif) {
        this.id = "0";
        this.nickname = nickname;
        this.password = password;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public byte[] getFoto() {
        return foto;
    }

    public String getPassword() {
        return password;
    }

    public String getIp() {return ip;}
}


