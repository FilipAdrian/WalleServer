package com.walle.project.UI.sample;

import com.walle.project.UI.model.ProductTable;
import com.walle.project.controller.CountryController;
import com.walle.project.controller.ManufactureController;
import com.walle.project.controller.ProductController;
import com.walle.project.controller.WarehouseController;
import com.walle.project.entity.Country;
import com.walle.project.entity.Manufacture;
import com.walle.project.entity.Product;
import com.walle.project.entity.Warehouse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ProductAddViewController implements Initializable {
    @FXML
    private TextField quantity;
    @FXML
    private TextField price;
    @FXML
    private TextField warehouse;
    @FXML
    private TextField manufacturer;
    @FXML
    private TextField name;
    @FXML
    private TextField id;
    @FXML
    private Button saveButton;

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
        stage.setTitle ("Login");
        stage.setScene (new Scene (root, 800, 487));
        stage.show ( );
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
        Integer qt = Integer.parseInt (quantity.getText ( ));
        Double pr = Double.parseDouble (price.getText ( ));
        Product newProduct = new Product (id.getText ( ), name.getText ( ), qt, pr, mt, wh);
        ProductTable productTable = new ProductTable (newProduct.getId ( ), newProduct.getName ( ), newProduct.getQuantiy ( ), newProduct.getPrice ( ).doubleValue ( ), newProduct.getManufacture ( ).getName ( ), newProduct.getWarehouse ( ).getName ( ));
        Integer status = productController.addOrUpdate (newProduct);

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
