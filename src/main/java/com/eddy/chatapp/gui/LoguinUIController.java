package com.eddy.chatapp.gui;

import com.eddy.chatapp.core.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Esta clase es la controladora de la interfaz gráfica de
 * la ventana LogIn, controla si la contraseña es correcta o no
 *
 * @author AlfredoHJDLO
 * @version 0.9
 * */
public class LoguinUIController {

   @FXML
    private PasswordField contrasenha;
   @FXML
    private Button loguin;

   @FXML
    private void handleButtonAction(ActionEvent event) {
       String contraseha = contrasenha.getText();
       Login login = new Login();
       if(!login.login(contraseha)) {
           errorContrasenha();
       }
       else {
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Login");
           alert.setHeaderText("Contraseña correcta");
           alert.setContentText("La contraseña es correcta, todo ha salido bién :3");
           alert.showAndWait();
       }
   }

   private void errorContrasenha() {
        try
        {
            FXMLLoader loader = new FXMLLoader(LoginUI.class.getResource("login_no.fxml"));
            Scene modalScene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle("Error de contraseña!");
            stage.setScene(modalScene);

            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
   }


}
