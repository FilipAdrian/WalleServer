package com.walle.project.UI.interfaces;

import com.jfoenix.controls.JFXTextField;
import com.walle.project.UI.client.CountryController;
import com.walle.project.UI.client.ManufactureController;
import com.walle.project.server.entity.Country;
import com.walle.project.server.entity.Manufacture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManufactureAddViewController implements Initializable,ShowButtonsController {
    @FXML
    public JFXTextField address;
    @FXML
    public JFXTextField country;
    @FXML
    public JFXTextField name;

    public List <String> nameOfCountry = new ArrayList <> ( );
    public CountryController countryController = new CountryController ( );
    private ManufactureController manufactureController = new ManufactureController ( );
    public List <Country> countryList = countryController.fetchList ( );


    public Stage startStage(String stageName) throws IOException {
        Parent root = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource (stageName));
        Stage stage = new Stage ( );
        stage.setTitle ("Add Manufacture");
        stage.setScene (new Scene (root, 500, 487));
        stage.show ( );
        stage.setResizable (false);
        return stage;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < countryList.size ( ); i++) {
            nameOfCountry.add (countryList.get (i).getName ( ));
        }
        TextFields.bindAutoCompletion (country, nameOfCountry);
    }


    public void onClick(ActionEvent actionEvent) throws IOException {

        Country countrySelected = new Country ( );
        for (int i = 0; i < countryList.size ( ); i++) {
            if (countryList.get (i).getName ( ).contains (country.getText ( ))) {
                countrySelected = countryList.get (i);
            }
        }
        JFXTextField[] textFields = {name, address, country};
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
            Manufacture manufacture = new Manufacture (name.getText ( ), countrySelected, address.getText ( ));
            status = manufactureController.addOrUpdate (manufacture);
            AlertViewController.add (status, "manufacture");
            cleanField (textFields);


        }
        cleanField (textFields);
    }
}
