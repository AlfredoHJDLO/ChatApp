package com.eddy.chatapp.model;

public class BlockedUsers {
    private int id;
    private String mac_address;

    public BlockedUsers(int id, String mac_address) {
        this.id = id;
        this.mac_address = mac_address;
    }

    public int getId() {
        return id;
    }

    public String getMac_address(){
        return mac_address;
    }
}
