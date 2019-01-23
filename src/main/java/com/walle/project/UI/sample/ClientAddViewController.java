package com.walle.project.UI.sample;

import com.jfoenix.controls.JFXTextField;
import com.walle.project.UI.client.CountryController;
import com.walle.project.UI.client.EmailValidator;
import com.walle.project.UI.client.ClientController;
import com.walle.project.UI.client.TypeController;
import com.walle.project.server.entity.Client;
import com.walle.project.server.entity.Country;
import com.walle.project.server.entity.Type;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientAddViewController implements Initializable, ShowButtonsController {
    @FXML
    public JFXTextField name;
    @FXML
    public JFXTextField surname;
    @FXML
    public JFXTextField phone;
    @FXML
    public JFXTextField email;
    @FXML
    public JFXTextField country;
    @FXML
    public JFXTextField address;
    @FXML
    public JFXTextField type;
    @FXML
    public Label countryLabel;
    @FXML
    public Label addressLabel;
    @FXML
    public Label typeLabel;
    private List nameOfCountry = new ArrayList ( );
    private List nameType = new ArrayList ( );
    private CountryController countryController = new CountryController ( );
    private TypeController typeController = new TypeController ( );
    private List <Country> countryList = countryController.fetchList ( );
    private List <Type> typeList = typeController.fetchList ( );
    private ClientController clientController = new ClientController ( );
    private List <Client> clientList = clientController.fetchList ( );
    public List <String> clients = new ArrayList <> ( );


    @Override

    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < countryList.size ( ); i++) {
            nameOfCountry.add (countryList.get (i).getName ( ));
        }
        for (int i = 0; i < typeList.size ( ); i++) {
            nameType.add (typeList.get (i).getName ( ));

        }

        EmailValidator validator = new EmailValidator ( );
        validator.setMessage ("E-mail must be of format : toBe@email.com");
        email.getValidators ( ).add (validator);
        email.focusedProperty ( ).addListener ((observable, oldValue, newValue) -> {
            if (!newValue) {
                email.validate ( );
                translateValidatorOnY (20d);
            }

        });
        TextFields.bindAutoCompletion (country, nameOfCountry);
        TextFields.bindAutoCompletion (type, nameType);

    }

    private void translateValidatorOnY(Double y) {
        country.setTranslateY (y);
        countryLabel.setTranslateY (y);
        address.setTranslateY (y);
        addressLabel.setTranslateY (y);
        type.setTranslateY (y);
        typeLabel.setTranslateY (y);
    }

    public Stage startStage() throws IOException {
        Parent root = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("clientADD.fxml"));
        Stage stage = new Stage ( );
        stage.setTitle ("Add Client");
        stage.setScene (new Scene (root, 500, 672));
        stage.show ( );
        stage.setResizable (false);
        return stage;

    }

    public void onClick(ActionEvent actionEvent) throws IOException {
        Type typeSelected = new Type ( );
        Country countrySelected = new Country ( );
        for (int i = 0; i < countryList.size ( ); i++) {
            if (countryList.get (i).getName ( ).contains (country.getText ( ))) {
                countrySelected = countryList.get (i);
            }
        }

        for (int i = 0; i < typeList.size ( ); i++) {
            if (typeList.get (i).getName ( ).contains (type.getText ( ))) {
                typeSelected = typeList.get (i);
            }
        }


        JFXTextField[] textFields = {name, surname, phone, email, address, country, type};
        Integer status = null;
        Boolean fieldComplet = true;
        for (JFXTextField field : textFields) {
            if (field.getText ( ).isEmpty ( ) || field.getText ( ) == null) {
                fieldComplet = false;
                AlertViewController.error ( );
                break;
            }
        }
        if (fieldComplet) {
            Client client = new Client (name.getText ( ), surname.getText ( ), phone.getText ( ),
                    email.getText ( ), countrySelected, address.getText ( ), typeSelected);
            status = clientController.addOrUpdate (client);
            AlertViewController.add (status, "client");
            cleanField (textFields);

        }
    }
}
