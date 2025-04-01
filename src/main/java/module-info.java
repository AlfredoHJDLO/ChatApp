module com.eddy.chatapp.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires jbcrypt;
    requires java.desktop;
    requires thumbnailator;
    requires org.xerial.sqlitejdbc;


    opens com.eddy.chatapp.gui to javafx.fxml;
    exports com.eddy.chatapp.gui;
}