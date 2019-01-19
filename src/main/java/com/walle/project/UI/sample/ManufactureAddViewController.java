package com.walle.project.UI.sample;

import com.walle.project.UI.model.ManufactureTable;
import com.walle.project.controller.CountryController;
import com.walle.project.controller.ManufactureController;
import com.walle.project.entity.Country;
import com.walle.project.entity.Manufacture;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManufactureAddViewController implements Initializable {
    @FXML
    public TextField address;
    @FXML
    public TextField country;
    @FXML
    public TextField name;

    private List <String> nameOfCountry = new ArrayList <> ( );
    private CountryController countryController = new CountryController ( );
    private ManufactureController manufactureController = new ManufactureController ( );
    private List <Country> countryList = countryController.fetchList ( );


    public Stage startStage() throws IOException {
        Parent root = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("manufactureADD.fxml"));
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
        Manufacture manufacture = new Manufacture (name.getText ( ), countrySelected, address.getText ( ));

        Integer status = manufactureController.addOrUpdate (manufacture);
        AlertViewController.add (status,"manufacture");
    }
}
