package com.walle.project.UI.interfaces;

import com.walle.project.UI.client.Encryption;
import com.walle.project.UI.client.UserController;
import com.walle.project.server.entity.Users;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SaettingViewController extends StatisticViewController implements Initializable {
    @FXML
    public TextField user;
    @FXML
    public TextField pasword;
    @FXML
    public TextField login;
    @FXML
    public Button btnLogin;
    @FXML
    public Button btnPassword;
    private UserController userController = new UserController ( );
    private Users users = LoginViewController.currentUser;

    public void loadStetting(AnchorPane home, Pane pnlClient, String name) throws IOException {
        super.loadStatistic (home, pnlClient, name);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String nameUser = users.getName ( ) + "   " + users.getSurname ( );
        user.setText (nameUser);

        btnPassword.setOnAction (event -> {
            String encryption = Encryption.stringToHash (pasword.getText ( ));
            users.setPassword (encryption);
            userController.addOrUpdate (users);

        });

        btnLogin.setOnAction (event -> {
            String newLogin = login.getText ();
            users.setLogin (newLogin);
            userController.addOrUpdate (users);
        });


    }
}
