package com.walle.project.UI.sample;


import com.walle.project.controller.UserController;
import com.walle.project.entity.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginViewController extends Window implements Initializable {


    private void showNextScene(String sceneName) {

        try {
            Parent root = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource (sceneName));
            Scene scene = new Scene (root);
            Stage stage = new Stage ( );
            stage.setScene (scene);
            stage.setTitle ("ToBe");
            stage.show ( );
        } catch (Exception e) {

            Logger.getLogger (Main.class.getName ( )).log (Level.SEVERE, null, e);
        }

    }

    public Stage startStage() throws IOException {
        Parent root = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("Login.fxml"));
        Stage stage = new Stage ( );
        stage.setTitle ("Login");
        stage.setScene (new Scene (root, 800, 387));
        stage.show ( );
        return stage;

    }


    @FXML
    protected Button btnLogIn;
    @FXML
    private PasswordField password;
    @FXML
    private TextField login;
    @FXML
    private TextField error;
    public static Long roleUser;
    public static StringBuilder nameSurname = new StringBuilder (" ");
    public static String access = new String ( );
    @Autowired
    UserController userController = new UserController ( );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        error.setOpacity (0);
        login.setOnKeyPressed (
                event -> {
                    if (event.getCode ( ) == KeyCode.ENTER) {
                        getUserAndPassword ();                    }
                });
        password.setOnKeyPressed (
                event -> {
                    if (event.getCode ( ) == KeyCode.ENTER) {
                        getUserAndPassword ();                    }
                });


    }

    private void getUserAndPassword() {
        Users user = userController.readCheckedUser (login.getText ( ), password.getText ( ));
        if (user != null) {
            roleUser = user.getRole ().getId ();
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
//            UserRepository userRepository= null;
//            String encryption = Encryption.stringToHash (password.getText ( ));
            getUserAndPassword ( );

        }
    }

}
