package com.walle.project.UI.sample;

import com.walle.project.controller.ClientController;
import com.walle.project.controller.CountryController;
import com.walle.project.controller.TypeController;
import com.walle.project.entity.Client;
import com.walle.project.entity.Country;
import com.walle.project.entity.Type;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientAddViewController implements Initializable {
    @FXML
    public TextField name;
    @FXML
    public TextField surname;
    @FXML
    public TextField phone;
    @FXML
    public TextField email;
    @FXML
    public TextField country;
    @FXML
    public TextField address;
    @FXML
    public TextField type;
    @FXML
    public ImageView saveButton;
    private List nameOfCountry = new ArrayList ( );
    private List nameType = new ArrayList ( );
    private CountryController countryController = new CountryController ( );
    private TypeController typeController = new TypeController ( );
    private List <Country> countryList = countryController.fetchList ( );
    private List <Type> typeList = typeController.fetchList ( );
    private ClientController clientController = new ClientController ( );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < countryList.size ( ); i++) {
            nameOfCountry.add (countryList.get (i).getName ( ));
        }
        for (int i = 0; i < typeList.size ( ); i++) {
            nameType.add (typeList.get (i).getName ( ));

        }
        TextFields.bindAutoCompletion (country, nameOfCountry);
        TextFields.bindAutoCompletion (type, nameType);
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

    public void onClick(ActionEvent actionEvent) {
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

        Client client = new Client (name.getText ( ), surname.getText ( ), phone.getText ( ),
                email.getText ( ), countrySelected, address.getText ( ), typeSelected);
        Integer status = clientController.addOrUpdate (client);
        System.out.println (client.toString () );
        AlertViewController.add (status,"client");

    }
}
