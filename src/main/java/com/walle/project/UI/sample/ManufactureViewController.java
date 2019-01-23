package com.walle.project.UI.sample;


import com.walle.project.UI.client.Mapper;
import com.walle.project.UI.model.ManufactureTable;
import com.walle.project.UI.client.ManufactureController;
import com.walle.project.server.entity.Manufacture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

public class ManufactureViewController implements Initializable, ShowButtonsController {
    @FXML
    public Button refreshButton;
    @FXML
    TableView <ManufactureTable> tableID;
    @FXML
    TableColumn <Object, Object> iID;
    @FXML
    TableColumn <Object, String> iName;
    @FXML
    TableColumn <Object, String> iAddress;
    @FXML
    private TextField search;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addButton;

    private ManufactureController manufactureController;
    private List <Manufacture> manufacture;
    private ObservableList <ManufactureTable> data;
    private ManufactureAddViewController manufactureAddViewController = new ManufactureAddViewController ( );
    private Long user = LoginViewController.roleUser;

    private ObservableList <ManufactureTable> insertData() {
        ObservableList <ManufactureTable> data = FXCollections.observableArrayList ( );

        for (int i = 0; i < manufacture.size ( ); i++) {

            data.add (new ManufactureTable (manufacture.get (i).getId ( ), manufacture.get (i).getName ( ), manufacture.get (i).getAddress ( )));
        }
        return data;
    }

    public void initialize(URL location, ResourceBundle resources) {
        iID.setCellValueFactory (new PropertyValueFactory <> ("rID"));
        iName.setCellValueFactory (new PropertyValueFactory <> ("rName"));
        iAddress.setCellValueFactory (new PropertyValueFactory <> ("rAddress"));

        manufactureController = new ManufactureController ( );
        try {
            manufacture = manufactureController.fetchList ( );
        } catch (Exception e) {
            System.out.println (e.getMessage ( ));
        }

        data = insertData ( );
        tableID.setItems (data);
        showButton (deleteButton, addButton, refreshButton, user);
        if (user != 91001) {
            iName.setCellFactory (TextFieldTableCell.forTableColumn ( ));
            iAddress.setCellFactory (TextFieldTableCell.forTableColumn ( ));

        }
    }

    public Pane loadManufacture(AnchorPane home, Pane pnlManufacture) throws IOException {
        AnchorPane pane = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("manufacture.fxml"));
        pane.prefHeightProperty ( ).bind (home.heightProperty ( ));
        pane.prefWidthProperty ( ).bind (home.widthProperty ( ));
        pnlManufacture.getChildren ( ).setAll (pane);
        return pnlManufacture;
    }


    public void oneSelect(TableColumn.CellEditEvent <Object, Object> objectObjectCellEditEvent) {
        ManufactureTable manufactureTable = tableID.getSelectionModel ( ).getSelectedItem ( );
        String columnName = objectObjectCellEditEvent.getTableColumn ( ).getId ( );
        if (columnName.equals ("iName")) {
            manufactureTable.setrName (objectObjectCellEditEvent.getNewValue ( ).toString ( ));
        } else {
            manufactureTable.setrAddress (objectObjectCellEditEvent.getNewValue ( ).toString ( ));
        }
        Manufacture manufacture = Mapper.transformIntoManufactureObject (manufactureTable);

        manufactureController.addOrUpdate (manufacture);
        tableID.refresh ( );

    }


    public void accessdelete(ActionEvent actionEvent) {
        if (user == 91002 || user == 91003) {
            Long id = tableID.getSelectionModel ( ).getSelectedItem ( ).getrID ( );
            Integer status = manufactureController.deleteManufacture (id);
            AlertViewController.delete (status, tableID, "manufacture");
        }
    }

    public void addButton(ActionEvent actionEvent) throws IOException {
        System.out.println ("Access");
        try {
            manufactureAddViewController.startStage ("manufactureADD.fxml");
        } catch (Exception e) {
            e.printStackTrace ( );
        }

    }


    public void searchProduct(KeyEvent keyEvent) {
        FilteredList <ManufactureTable> filteredList = new FilteredList <> (data, e -> true);
        search.textProperty ( ).addListener (((observable, oldValue, newValue) -> {
            filteredList.setPredicate ((Predicate <? super ManufactureTable>) manufacture -> {
                if (newValue == null || newValue.isEmpty ( )) {
                    return true;
                }
                String lowerCase = newValue.toLowerCase ( );
                if (manufacture.getrName ( ).toLowerCase ( ).contains (lowerCase)) {
                    return true;
                } else if (manufacture.getrAddress ( ).toLowerCase ( ).contains (lowerCase)) {
                    return true;
                }

                return false;
            });
        }));
        SortedList <ManufactureTable> sortedList = new SortedList <> (filteredList);
        sortedList.comparatorProperty ( ).bind (tableID.comparatorProperty ( ));
        tableID.setItems (sortedList);
    }

    public void accessRefresh(ActionEvent actionEvent) {
        tableID.getItems ( ).clear ( );
        data.clear ( );
        manufactureController = new ManufactureController ( );
        try {
            manufacture = manufactureController.fetchList ( );
        } catch (Exception e) {
            System.out.println (e.getMessage ( ));
        }
        data = insertData ( );
        tableID.setItems (data);

    }
}