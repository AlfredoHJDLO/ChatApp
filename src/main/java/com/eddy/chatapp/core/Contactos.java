package com.eddy.chatapp.core;

import com.eddy.chatapp.dao.DatabaseConnector;
import com.eddy.chatapp.dao.SQLiteConnector;
import com.eddy.chatapp.dao.UsuarioDAOImpl;
import com.eddy.chatapp.model.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Contactos {
    //private DatabaseConnector connector;
    public Contactos(/*DatabaseConnector connector*/) {
        //this.connector = connector;
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

        List <Users> connectedUsers = new UsuarioDAOImpl(new SQLiteConnector()).listContacts();

        for (Users user : connectedUsers){
            if (user.getId() != null && !user.getId().isEmpty()) {
                return connectedUsers;
            }
        }
        return null;
    }

    public List<Users> listContacts() {
        List <Users> connectedUsers = new UsuarioDAOImpl(new SQLiteConnector()).listContacts();
        return connectedUsers;
    }


}
