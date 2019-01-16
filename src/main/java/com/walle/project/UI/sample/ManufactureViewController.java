package com.walle.project.UI.sample;


import com.walle.project.UI.model.ManufactureTable;
import com.walle.project.controller.ManufactureController;
import com.walle.project.entity.Manufacture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

public class ManufactureViewController implements Initializable {

    @FXML
    TableView<ManufactureTable> tableID;
    @FXML
    TableColumn <Object, Object> iID;
    @FXML
    TableColumn <Object, String> iName;
    @FXML
    TableColumn <Object, String> iAddress;

    @FXML
    public Pane pnManufacture;

    public Integer flag = 0;
    private ManufactureController manufactureController;
    private List<Manufacture> manufacture;
    private ObservableList<ManufactureTable> data;


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
        iName.setCellFactory (TextFieldTableCell.forTableColumn ( ));
        iAddress.setCellFactory (TextFieldTableCell.forTableColumn ());
    }

    public Pane loadManufacture(AnchorPane home, Pane pnlManufacture) throws IOException {
        AnchorPane pane = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("manufacture.fxml"));
        pane.prefHeightProperty ( ).bind (home.heightProperty ( ));
        pane.prefWidthProperty ( ).bind (home.widthProperty ( ));
        pnlManufacture.getChildren ( ).setAll (pane);
        return pnlManufacture;
    }


    public void oneSelect(TableColumn.CellEditEvent<Object, Object> objectObjectCellEditEvent) {
        ManufactureTable manufactureTable = tableID.getSelectionModel ().getSelectedItem ();
        String columnName = objectObjectCellEditEvent.getTableColumn ().getId ();
        if (columnName.equals ("iName")) {
            System.out.println ("Name" );
            manufactureTable.setrName (objectObjectCellEditEvent.getNewValue ().toString ());
        }
        else {
            System.out.println ("Address" );
            manufactureTable.setrAddress (objectObjectCellEditEvent.getNewValue ().toString ());
        }
        Mapper mapper =new Mapper ();
        Manufacture manufacture = mapper.transformIntoManufactureObject (manufactureTable);

        manufactureController.addOrUpdate (manufacture);
        tableID.refresh ();

    }



}