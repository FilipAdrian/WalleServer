package com.walle.project.UI.sample;


import com.walle.project.UI.model.WarehouseTable;
import com.walle.project.controller.WarehouseController;
import com.walle.project.entity.Warehouse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WarehouseViewController implements Initializable {

    @FXML
    TableView<WarehouseTable> tableID;
    @FXML
    TableColumn <Object, Object> iID;
    @FXML
    TableColumn <Object, Object> iName;
    @FXML
    TableColumn <Object, Object> iAddress;
    @FXML
    TableColumn <Object, Object> iPhone;

    @FXML
    public Pane pnlWarehouse;

    public Integer flag = 0;
    private WarehouseController warehouseController;
    private List<Warehouse> warehouse;
    private ObservableList<WarehouseTable> data;


    private ObservableList <WarehouseTable> insertData() {
        ObservableList <WarehouseTable> data = FXCollections.observableArrayList ( );

        for (int i = 0; i < warehouse.size ( ); i++) {

            data.add (new WarehouseTable (warehouse.get (i).getId ( ), warehouse.get (i).getName ( ), warehouse.get (i).getAddress ( ), warehouse.get (i).getPhone ( )));
        }
        return data;
    }

    public void initialize(URL location, ResourceBundle resources) {
        iID.setCellValueFactory (new PropertyValueFactory <> ("rID"));
        iName.setCellValueFactory (new PropertyValueFactory <> ("rName"));
        iAddress.setCellValueFactory (new PropertyValueFactory <> ("rAddress"));
        iPhone.setCellValueFactory (new PropertyValueFactory <> ("rPhone"));

        warehouseController = new WarehouseController ( );
        try {
            warehouse = warehouseController.fetchList ();
        } catch (Exception e) {
            System.out.println (e.getMessage ( ));
        }

        data = insertData ( );
        tableID.setItems (data);
    }

    public Pane loadWarehouse(AnchorPane home, Pane pnlWarehouse) throws IOException {
        AnchorPane pane = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("warehouse.fxml"));
        pane.prefHeightProperty ( ).bind (home.heightProperty ( ));
        pane.prefWidthProperty ( ).bind (home.widthProperty ( ));
        pnlWarehouse.getChildren ( ).setAll (pane);
        return pnlWarehouse;
    }
}