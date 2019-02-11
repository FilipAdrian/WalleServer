package com.walle.project.UI.interfaces;

import com.jfoenix.controls.JFXTextField;
import com.walle.project.UI.client.EmailSender;
import com.walle.project.UI.client.EmailValidator;
import com.walle.project.UI.client.Encryption;
import com.walle.project.UI.client.CountryController;
import com.walle.project.UI.client.RoleController;
import com.walle.project.UI.client.UserController;
import com.walle.project.server.entity.Country;
import com.walle.project.server.entity.Role;
import com.walle.project.server.entity.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.commons.lang3.RandomStringUtils;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserAddViewController implements Initializable, ShowButtonsController {
    @FXML
    public JFXTextField name;
    @FXML
    public JFXTextField surname;
    @FXML
    public JFXTextField country;
    @FXML
    public JFXTextField address;
    @FXML
    public JFXTextField phone;
    @FXML
    public JFXTextField email;
    @FXML
    public JFXTextField login;
    @FXML
    public Label passwordLabel;
    @FXML
    public JFXTextField password;
    @FXML
    public Label roleLabel;
    @FXML
    public JFXTextField role;
    @FXML
    public Label loginLabel;
    private List nameOfCountry = new ArrayList ( );
    private List nameRole = new ArrayList ( );
    private CountryController countryController = new CountryController ( );
    private RoleController roleController = new RoleController ( );
    private List <Country> countryList = countryController.fetchList ( );
    private List <Role> roleList = roleController.fetchList ( );
    private UserController userController = new UserController ( );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < countryList.size ( ); i++) {
            nameOfCountry.add (countryList.get (i).getName ( ));
        }
        for (int i = 0; i < roleList.size ( ); i++) {
            nameRole.add (roleList.get (i).getName ( ));
        }

        EmailValidator validator = new EmailValidator ( );
        validator.setMessage ("E-mail must be of format : toBe@email.com");
        email.getValidators ( ).add (validator);
        email.focusedProperty ( ).addListener ((observable, oldValue, newValue) -> {
            if (!newValue) {
                email.validate ( );
                translateValidatorOnY ( );
            }

        });
        TextFields.bindAutoCompletion (country, nameOfCountry);
        TextFields.bindAutoCompletion (role, nameRole);
        rndText (password);
        rndText (login);


    }

    private void translateValidatorOnY() {
        loginLabel.setTranslateY (20);
        login.setTranslateY (20);
        password.setTranslateY (20);
        passwordLabel.setTranslateY (20);
        role.setTranslateY (20);
        roleLabel.setTranslateY (20);
    }

    public Stage startStage() throws IOException {
        Parent root = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("userADD.fxml"));
        Stage stage = new Stage ( );
        stage.setTitle ("Add User");
        stage.setScene (new Scene (root, 500, 750));
        stage.show ( );
        stage.setResizable (false);
        return stage;

    }

    public void onClick(ActionEvent actionEvent) {
        Role roleSelected = new Role ( );
        Country countrySelected = new Country ( );
        for (Country ct : countryList) {
            if (ct.getName ( ).contains (country.getText ( ))) {
                countrySelected = ct;
            }
        }

        for (Role rl : roleList) {
            if (rl.getName ( ).contains (role.getText ( ))) {
                roleSelected = rl;
            }

        }

        Users user = new Users (name.getText ( ), surname.getText ( ), phone.getText ( ), email.getText ( ),
                countrySelected, address.getText ( ), login.getText ( ), password.getText ( ), roleSelected);
        Integer status = null;
        Boolean fieldComplet = true;
        JFXTextField[] textFields = {name, surname, phone, address, email, country, login, password, role};
        for (JFXTextField field : textFields) {
            if (field.getText ( ).isEmpty ( ) || field.getText ( ) == null) {
                fieldComplet = false;
                AlertViewController.error ( );
                break;
            }
        }
        if (fieldComplet) {
            String pss = user.getPassword ( );
            String encryption = Encryption.stringToHash (pss);
            user.setPassword (encryption);
            status = userController.addOrUpdate (user);
            AlertViewController.add (status, "user");
            user.setPassword (pss);
            EmailSender.sendEmailToNewUser (user);
            cleanField (textFields);
            rndText (password);
            rndText (login);

        }


    }

    private static String rndText(JFXTextField textField) {
        String text = RandomStringUtils.random (8, true, true);
        textField.setText (text);
        textField.setDisable (true);
        return text;
    }
}
