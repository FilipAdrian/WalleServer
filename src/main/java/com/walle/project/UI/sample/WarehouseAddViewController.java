package com.walle.project.UI.sample;

import com.jfoenix.controls.JFXTextField;
import com.walle.project.UI.client.WarehouseController;
import com.walle.project.server.entity.Country;
import com.walle.project.server.entity.Warehouse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WarehouseAddViewController extends ManufactureAddViewController implements ShowButtonsController{
    @FXML
    public JFXTextField address;
    @FXML
    public JFXTextField country;
    @FXML
    public JFXTextField name;
    @FXML
    public JFXTextField phone;
    private WarehouseController warehouseController = new WarehouseController ( );

    public Stage startStage(String stageName) throws IOException {
        return super.startStage (stageName);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize (location, resources);
    }

    public void onClick(ActionEvent actionEvent) {
        Country countrySelected = new Country ( );
        for (int i = 0; i < countryList.size ( ); i++) {
            if (countryList.get (i).getName ( ).contains (country.getText ( ))) {
                countrySelected = countryList.get (i);
            }
        }
        Warehouse warehouse = new Warehouse (name.getText ( ), countrySelected, address.getText ( ), phone.getText ( ));
        JFXTextField[] textFields = {name, phone, address, country};
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
            status = warehouseController.addOrUpdate (warehouse);
            AlertViewController.add (status, "warehouse");
            cleanField (textFields);

        }
    }
}
