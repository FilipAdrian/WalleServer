package com.walle.project.UI.sample;


import com.walle.project.UI.model.SalesTable;
import com.walle.project.UI.client.SalesController;
import com.walle.project.server.entity.Sales;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class SalesViewController implements Initializable {

    @FXML
    public TextField search;
    @FXML
    public Button deleteButton;
    @FXML
    public Button addButton;
    @FXML
    public Button refreshButton;
    @FXML
    TableView <SalesTable> tableID;
    @FXML
    TableColumn <Object, Object> iID;
    @FXML
    TableColumn <Object, Object> iData;
    @FXML
    TableColumn <Object, Object> iProductQuantity;
    @FXML
    TableColumn <Object, Object> iClient;
    @FXML
    TableColumn <Object, Object> iProduct;
    @FXML
    TableColumn <Object, Object> iUser;
    @FXML
    TableColumn <Object, Object> iAmount;
    @FXML
    TableColumn <Object, Object> iPrice;
    @FXML
    public Pane pnSales;

    public Integer flag = 0;
    private SalesController salesController;
    private List <Sales> sales;
    private ObservableList <SalesTable> data;
    private  SalesAddViewController salesAddViewController = new SalesAddViewController ();
    private Integer userRole = LoginViewController.roleUser.intValue ();


    private ObservableList <SalesTable> insertData() {
        ObservableList <SalesTable> data = FXCollections.observableArrayList ( );

        for (int i = 0; i < sales.size ( ); i++) {
            String client = new String (sales.get (i).getClient ( ).getName ( ) + "  " + sales.get (i).getClient ( ).getSurname ( ));
            String user = new String (sales.get (i).getUsers ( ).getName ( ) + "  " + sales.get (i).getUsers ( ).getSurname ( ));
            Date date = sales.get (i).getData ( );
            SimpleDateFormat sdfDate = new SimpleDateFormat ("yyyy-MM-dd");
            String strDate = sdfDate.format (date);
            data.add (new SalesTable (sales.get (i).getId ( ), strDate, sales.get (i).getQuantity ( ),
                    client, sales.get (i).getProduct ( ).getId ( ), user, sales.get (i).getAmount ( ).doubleValue ( ), sales.get (i).getPrice ( ).doubleValue ( )));
        }
        return data;
    }

    public void initialize(URL location, ResourceBundle resources) {
        iID.setCellValueFactory (new PropertyValueFactory <> ("rID"));
        iData.setCellValueFactory (new PropertyValueFactory <> ("rData"));
        iProductQuantity.setCellValueFactory (new PropertyValueFactory <> ("rProductQuantity"));
        iClient.setCellValueFactory (new PropertyValueFactory <> ("rClient"));
        iProduct.setCellValueFactory (new PropertyValueFactory <> ("rProduct"));
        iUser.setCellValueFactory (new PropertyValueFactory <> ("rUser"));
        iAmount.setCellValueFactory (new PropertyValueFactory <> ("rAmount"));
        iPrice.setCellValueFactory (new PropertyValueFactory <> ("rPrice"));

        salesController = new SalesController ( );
        try {
            sales = salesController.fetchList ( );
        } catch (Exception e) {
            e.printStackTrace ();
            System.out.println (e.getMessage ( ));
        }
        if (userRole != 91003) {
            deleteButton.setDisable (true);
            deleteButton.setOpacity (0);
            refreshButton.setTranslateX (76);

        }

        data = insertData ( );
        tableID.setItems (data);
    }

    public Pane loadSales(AnchorPane home, Pane pnlSales) throws IOException {
        AnchorPane pane = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("sales.fxml"));
        pane.prefHeightProperty ( ).bind (home.heightProperty ( ));
        pane.prefWidthProperty ( ).bind (home.widthProperty ( ));
        pnlSales.getChildren ( ).setAll (pane);
        return pnlSales;
    }

    public void addButton(ActionEvent actionEvent) {
        try {
            salesAddViewController.startStage ( );
        } catch (IOException e) {
            e.printStackTrace ( );
            e.getMessage ( );
        }
    }

    public void accessRefresh(ActionEvent actionEvent) {
        tableID.getItems ( ).clear ( );
        data.clear ( );
        salesController = new SalesController ( );
        try {
            sales = salesController.fetchList ( );
        } catch (Exception e) {
            System.out.println (e.getMessage ( ));
        }
        data = insertData ( );
        tableID.setItems (data);
    }

    public void searchProduct(KeyEvent keyEvent) {
        FilteredList<SalesTable> filteredList = new FilteredList <> (data, e -> true);
        search.textProperty ( ).addListener (((observable, oldValue, newValue) -> {
            filteredList.setPredicate ((Predicate<? super SalesTable>) sale -> {
                if (newValue == null || newValue.isEmpty ( )) {
                    return true;
                }
                String lowerCase = newValue.toLowerCase ( );
                if (sale.getrClient ().toLowerCase ( ).contains (lowerCase)) {
                    return true;
                } else if (sale.getrData ().toLowerCase ( ).contains (lowerCase)) {
                    return true;
                } else if (sale.getrProduct ().toLowerCase ( ).contains (lowerCase)) {
                    return true;
                } else if (sale.getrUser ().toLowerCase ( ).contains (lowerCase)) {
                    return true;
                }

                return false;
            });
        }));
        SortedList<SalesTable> sortedList = new SortedList <> (filteredList);
        sortedList.comparatorProperty ( ).bind (tableID.comparatorProperty ( ));
        tableID.setItems (sortedList);
    }

    public void accessdelete(ActionEvent actionEvent) {
        Long id = tableID.getSelectionModel ( ).getSelectedItem ( ).getrID ( );
        Integer status = salesController.deleteSales (id);
        AlertViewController.delete (status, tableID, "sale");
    }
}