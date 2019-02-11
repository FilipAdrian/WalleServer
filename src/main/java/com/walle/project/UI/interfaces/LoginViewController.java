package com.walle.project.UI.interfaces;


import com.walle.project.UI.LunchUI;
import com.walle.project.UI.client.Encryption;
import com.walle.project.UI.client.UserController;
import com.walle.project.server.entity.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginViewController extends Window implements Initializable {


    @FXML
    protected Button btnLogIn;
    @FXML
    private PasswordField password;
    @FXML
    private TextField login;
    @FXML
    private TextField error;
    public static Users currentUser;
    public static Long roleUser;
    public static StringBuilder nameSurname = new StringBuilder (" ");
    public static String access = new String ( );
    @Autowired
    UserController userController = new UserController ( );

    private void showNextScene(String sceneName) {

        try {
            Parent root = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource (sceneName));
            Scene scene = new Scene (root);
            Stage stage = new Stage ( );
            stage.getIcons ().add (new Image ("/images/2Bicon.png"));
            stage.setScene (scene);
            stage.setTitle ("ToBe");
            stage.show ( );
        } catch (Exception e) {

            Logger.getLogger (LunchUI.class.getName ( )).log (Level.SEVERE, null, e);
        }

    }

    public Stage startStage() {
        Parent root = null;
        try {
            root = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace ( );
        }
        Stage stage = new Stage ( );
        stage.setTitle ("Login");
        stage.setScene (new Scene (root, 800, 387));
        stage.getIcons ().add (new Image ("/images/2Bicon.png"));
        stage.show ( );
        stage.setResizable (false);
        return stage;

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        error.setOpacity (0);
        login.setOnKeyPressed (
                event -> {
                    if (event.getCode ( ) == KeyCode.ENTER) {
                        getUserAndPassword ( );
                    }
                });
        password.setOnKeyPressed (
                event -> {
                    if (event.getCode ( ) == KeyCode.ENTER) {
                        getUserAndPassword ( );
                    }
                });


    }

    private void getUserAndPassword() {
        String encryption = Encryption.stringToHash (password.getText ( ));
        Users user = userController.readCheckedUser (login.getText ( ), encryption);
        if (user != null) {
            currentUser = user;
            roleUser = user.getRole ( ).getId ( );
            nameSurname.append (user.getSurname ( )).append (" ").append (user.getName ( ));
            access = user.getRole ( ).getName ( );
            error.setOpacity (0);
            showNextScene ("Home.fxml");

            Stage stage = (Stage) btnLogIn.getScene ( ).getWindow ( );
            stage.close ( );
        } else {
            error.setOpacity (1);

        }
    }

    public void handleClicks(ActionEvent actionEvent) throws Exception {
        if (actionEvent.getSource ( ) == btnLogIn) {
            getUserAndPassword ( );

        }
    }

}
