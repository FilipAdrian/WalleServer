package com.walle.project.UI.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("Login.fxml"));
            primaryStage.setTitle ("Login");
            primaryStage.setScene (new Scene (root, 800, 387));
            primaryStage.show ( );
        }
        catch (Exception e){
            e.getMessage ();
            e.printStackTrace ();
        }
    }

    public static void main(String[] args) {
        launch (args);
    }
}