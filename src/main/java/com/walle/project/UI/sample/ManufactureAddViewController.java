package com.walle.project.UI.sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ManufactureAddViewController {

    public Stage startStage() throws IOException {
        Parent root = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("manufactureADD.fxml"));
        Stage stage = new Stage ( );
        stage.setTitle ("Add Manufacture");
        stage.setScene (new Scene (root, 450, 487));
        stage.show ( );
        return stage;

    }

}
