package com.walle.project.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class LunchUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("Login.fxml"));
           primaryStage.getIcons ().add (new Image ("/images/2Bicon.png"));

            primaryStage.setTitle ("Login");
            primaryStage.setScene (new Scene (root, 800, 387));
            primaryStage.show ( );
//            primaryStage.setResizable (false);
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