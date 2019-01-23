package com.walle.project.UI.sample;


import com.walle.project.UI.client.Mapper;
import com.walle.project.UI.model.WarehouseTable;
import com.walle.project.UI.client.WarehouseController;
import com.walle.project.server.entity.Warehouse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class WarehouseViewController implements Initializable,ShowButtonsController {

    @FXML
    public TextField search;
    @FXML
    public Button deleteButton;
    @FXML
    public Button addButton;
    @FXML
    public Button refreshButton;
    @FXML
    TableView <WarehouseTable> tableID;
    @FXML
    TableColumn <Object, Object> iID;
    @FXML
    TableColumn <Object, String> iName;
    @FXML
    TableColumn <Object, String> iAddress;
    @FXML
    TableColumn <Object, String> iPhone;

    private WarehouseController warehouseController;
    private List <Warehouse> warehouse;
    private ObservableList <WarehouseTable> data;
    private WarehouseAddViewController warehouseAddViewController = new WarehouseAddViewController ( );
    private Long user = LoginViewController.roleUser;

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
            warehouse = warehouseController.fetchList ( );
        } catch (Exception e) {
            System.out.println (e.getMessage ( ));
        }

        data = insertData ( );
        tableID.setItems (data);
        showButton (deleteButton, addButton, refreshButton, user);
        if (user != 91001) {
            iName.setCellFactory (TextFieldTableCell.forTableColumn ( ));
            iAddress.setCellFactory (TextFieldTableCell.forTableColumn ( ));
            iPhone.setCellFactory (TextFieldTableCell.forTableColumn ( ));

        }
    }

    public Pane loadWarehouse(AnchorPane home, Pane pnlWarehouse) throws IOException {
        AnchorPane pane = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("warehouse.fxml"));
        pane.prefHeightProperty ( ).bind (home.heightProperty ( ));
        pane.prefWidthProperty ( ).bind (home.widthProperty ( ));
        pnlWarehouse.getChildren ( ).setAll (pane);
        return pnlWarehouse;
    }



    public void addButton(ActionEvent actionEvent) {
        System.out.println ("Access");
        try {
            warehouseAddViewController.startStage ("warehouseADD.fxml");
        } catch (Exception e) {
            e.printStackTrace ( );
        }
    }

    public void accessdelete(ActionEvent actionEvent) {
        if (user == 91002 || user == 91003) {
            Long id = tableID.getSelectionModel ( ).getSelectedItem ( ).getrID ( );
            Integer status = warehouseController.deleteWarehouse (id);
            AlertViewController.delete (status, tableID, "manufacture");
        }
    }

    public void accessRefresh(ActionEvent actionEvent) {
        tableID.getItems ( ).clear ( );
        data.clear ( );
        warehouseController = new WarehouseController ( );
        try {
            warehouse = warehouseController.fetchList ( );
        } catch (Exception e) {
            System.out.println (e.getMessage ( ));
        }
        data = insertData ( );
        tableID.setItems (data);

    }

    public void searchProduct(KeyEvent keyEvent) {
        FilteredList <WarehouseTable> filteredList = new FilteredList <> (data, e -> true);
        search.textProperty ( ).addListener (((observable, oldValue, newValue) -> {
            filteredList.setPredicate ((Predicate <? super WarehouseTable>) warehouse -> {
                if (newValue == null || newValue.isEmpty ( )) {
                    return true;
                }
                String lowerCase = newValue.toLowerCase ( );
                if (warehouse.getrName ( ).toLowerCase ( ).contains (lowerCase)) {
                    return true;
                } else if (warehouse.getrAddress ( ).toLowerCase ( ).contains (lowerCase)) {
                    return true;
                } else if (warehouse.getrPhone ( ).toLowerCase ( ).contains (lowerCase)) {
                    return true;
                }

                return false;
            });
        }));
        SortedList <WarehouseTable> sortedList = new SortedList <> (filteredList);
        sortedList.comparatorProperty ( ).bind (tableID.comparatorProperty ( ));
        tableID.setItems (sortedList);
    }


    public void oneSelect(TableColumn.CellEditEvent <Object, Object> objectObjectCellEditEvent) {
        WarehouseTable warehouseTable = tableID.getSelectionModel ( ).getSelectedItem ( );
        String columnName = objectObjectCellEditEvent.getTableColumn ( ).getId ( );
        if (columnName.equals ("iName")) {
            warehouseTable.setrName (objectObjectCellEditEvent.getNewValue ( ).toString ( ));
        } else if (columnName.equals ("iAddress")) {
            warehouseTable.setrAddress (objectObjectCellEditEvent.getNewValue ( ).toString ( ));
        } else if (columnName.equals ("iPhone")) {
            warehouseTable.setrPhone (objectObjectCellEditEvent.getNewValue ( ).toString ( ));
        }
        Warehouse warehouse = Mapper.transformIntoWarehouseObject (warehouseTable);

        warehouseController.addOrUpdate (warehouse);
        tableID.refresh ( );
    }
}