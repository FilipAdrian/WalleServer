package com.walle.project.UI.sample;

import com.jfoenix.controls.JFXTextField;
import com.walle.project.UI.client.ManufactureController;
import com.walle.project.UI.client.ProductController;
import com.walle.project.UI.client.WarehouseController;
import com.walle.project.server.entity.Manufacture;
import com.walle.project.server.entity.Product;
import com.walle.project.server.entity.Warehouse;
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

public class ProductAddViewController implements Initializable,ShowButtonsController{
    @FXML
    private JFXTextField quantity;
    @FXML
    private JFXTextField price;
    @FXML
    private JFXTextField warehouse;
    @FXML
    private JFXTextField manufacturer;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField id;

    private List <String> manufactures = new ArrayList <> ( );
    private List <String> warehouses = new ArrayList <> ( );
    private ManufactureController manufactureController = new ManufactureController ( );
    private WarehouseController warehouseController = new WarehouseController ( );
    private ProductController productController = new ProductController ( );
    private List <Manufacture> manufactureList = manufactureController.fetchList ( );
    private List <Warehouse> warehouseList = warehouseController.fetchList ( );

    public Stage startStage() throws IOException {
        Parent root = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("productADD.fxml"));
        Stage stage = new Stage ( );
        stage.setTitle ("Add Product");
        stage.setScene (new Scene (root, 500, 672));
        stage.show ( );
        stage.setResizable (false);
        return stage;

    }

    public void onClick(ActionEvent actionEvent) throws IOException {
        Warehouse wh = new Warehouse ( );
        Manufacture mt = new Manufacture ( );

        for (int i = 0; i < warehouseList.size ( ); i++) {
            if (warehouseList.get (i).getName ( ).contains (warehouse.getText ( ))) {
                wh = warehouseList.get (i);
            }
        }
        for (int i = 0; i < manufactureList.size ( ); i++) {
            if (manufactureList.get (i).getName ( ).contains (manufacturer.getText ( ))) {
                mt = manufactureList.get (i);
            }
        }

        Integer status = null;
        Boolean fieldComplet = true;
        JFXTextField[] textFields = {name, price, manufacturer, warehouse, quantity};

        for (JFXTextField field : textFields) {
            if (field.getText ( ).isEmpty ( ) || field.getText ( ) == null) {
                fieldComplet = false;
                AlertViewController.error ( );
                break;
            }
        }
        if (fieldComplet) {
            Integer qt = Integer.parseInt (quantity.getText ( ));
            Double pr = Double.parseDouble (price.getText ( ));
            Product newProduct = new Product (id.getText ( ), name.getText ( ), qt, pr, mt, wh);
            status = productController.addOrUpdate (newProduct);
            AlertViewController.add (status,"product");
            cleanField (textFields);
        }


    }

    private void getLists() {

        for (int i = 0; i < manufactureList.size ( ); i++) {
            manufactures.add (manufactureList.get (i).getName ( ));
        }
        for (int i = 0; i < warehouseList.size ( ); i++) {
            warehouses.add (warehouseList.get (i).getName ( ));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getLists ( );
        TextFields.bindAutoCompletion (warehouse, warehouses);
        TextFields.bindAutoCompletion (manufacturer, manufactures);


    }
}
