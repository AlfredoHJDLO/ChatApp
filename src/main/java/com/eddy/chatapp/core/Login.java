package com.eddy.chatapp.core;

import org.mindrot.jbcrypt.BCrypt;

import java.io.*;


/**
 * Esta clase es la controladora del logueo con
 * contraseña del usuario, contiene 2 metodos llamados
 * login y registro
 *
 * @author AlfredoHJDLO
 * @version 0.9
 * */
public class Login {
    /**
     * Esta es el constructor, no sirve para nada ;v
     * */
    public Login() {}


    /**
     * Esta es el método registro, sive para que el usuario genere
     * su contraseña
     *
     * @param contrasenha El string con la contraseña
     * @return Regresa {@code true} si se guardó correctamente
     * la contraseña y {@code false} si no se pudo guardar
     * */
    public boolean registro(String contrasenha)
    {
        String hash = BCrypt.hashpw(contrasenha, BCrypt.gensalt(4));

        try(FileOutputStream archivo = new FileOutputStream("contra.bat");
            ObjectOutputStream out = new ObjectOutputStream( archivo))
        {
            out.writeObject(hash);
            return true;
        }catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Esta función compara la contraseña ingresada
     * con la que está almacenada en el archivo.
     *
     * @param contrasenha Es la contraseña ingresada por el usuario
     * @return Regresa {@code true} si la contraseña es correcta
     * y {@code false} si es incorrecta
     * */
    public boolean login(String contrasenha)
    {
        try(FileInputStream archivo = new FileInputStream("contra.bat");
        ObjectInputStream in = new ObjectInputStream(archivo))
        {
            String hashG = (String) in.readObject();
            return BCrypt.checkpw(contrasenha, hashG);
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Este método regresa si el usuario ya ha configurado su contraseña
     * @return {@code false} si no se ha generado una contraseña y
     * {@code true} si si existe la contraseña
     * */
    public boolean existeContra()
    {
        File archivo = new File("contra.bat");
        return archivo.exists();
    }
}
