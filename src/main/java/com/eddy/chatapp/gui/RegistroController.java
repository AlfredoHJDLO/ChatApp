package com.eddy.chatapp.gui;

import com.eddy.chatapp.core.Login;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import net.coobird.thumbnailator.Thumbnails;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


/**
 * Esta clase es la controladora del FXML del
 * registro, ademas sube a base de datos la
 * información del usuario
 *
 *
 * @author Eduardo Rojas Rodriguez
 * @author Saul David Peña Martínez
 * */
public class RegistroController {

    public byte[] archivo = null;

    @FXML
    private TextField nickname;

    @FXML
    private PasswordField password;

    @FXML
    private Label filenameLael;

    @FXML
    private Button registro;

    @FXML
    private Button cargarArchivo;

    @FXML
    public void cargarImagen()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione una imagen");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif", "*.jpeg", "*.bmp")
        );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            filenameLael.setText(file.getName());
            archivo = convertToPng(file.getAbsoluteFile());
        }
    }

    @FXML
    public void Registrar()
    {
        String nicknameStr = nickname.getText();
        String passwordStr = password.getText();
        Login login = new Login();
        login.registro(nicknameStr, passwordStr, archivo);
        CambioVentana.cambioVentana(registro.getScene().getWindow(), "mainGui.fxml","ChatApp");

    }

    private byte[] convertToPng(File input)
    {
        try(ByteArrayOutputStream arch = new ByteArrayOutputStream();){
            Thumbnails.of(input).size(512,512).outputFormat("png").toOutputStream(arch);
            return arch.toByteArray();
        }catch (IOException E)
        {
            E.printStackTrace();
        }
        return null;
    }
}
