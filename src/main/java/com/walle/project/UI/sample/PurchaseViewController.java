package com.walle.project.UI.sample;

import com.walle.project.UI.model.PurchaseTable;
import com.walle.project.UI.client.PurchaseController;
import com.walle.project.server.entity.Purchase;
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

public class PurchaseViewController implements Initializable {

    public Button addButton;
    public Button deleteButton;
    public TextField search;
    public Button refreshButton;
    @FXML
    TableView<PurchaseTable> tableID;
    @FXML
    TableColumn <Object, Object> iID;
    @FXML
    TableColumn <Object, Object> iData;
    @FXML
    TableColumn <Object, Object> iCost;
    @FXML
    TableColumn <Object, Object> iQuantity;
    @FXML
    TableColumn <Object, Object> iClient;
    @FXML
    TableColumn <Object, Object> iProduct;
    @FXML
    TableColumn <Object, Object> iUser;
    @FXML
    TableColumn <Object, Object> iAmount;

    private PurchaseController purchaseController;
    private List<Purchase> purchase;
    private ObservableList<PurchaseTable> data;
    private Integer userRole = LoginViewController.roleUser.intValue ();
    private PurchaseAddViewController purchaseAddViewController = new PurchaseAddViewController ();


    private ObservableList <PurchaseTable> insertData() {
        ObservableList <PurchaseTable> data = FXCollections.observableArrayList ( );

        for (int i = 0; i < purchase.size ( ); i++) {
            String client = new String (purchase.get (i).getClient ( ).getName () + "  " + purchase.get (i).getClient ( ).getSurname ());
            String user = new String (purchase.get (i).getUsers ( ).getName () + "  " + purchase.get (i).getUsers ( ).getSurname ());
            Date date = purchase.get (i).getData ( );
            SimpleDateFormat sdfDate = new SimpleDateFormat ("yyyy-MM-dd");
            String strDate = sdfDate.format (date);
            data.add (new PurchaseTable (purchase.get (i).getId ( ),strDate,purchase.get (i).getCost ().doubleValue (),
                    purchase.get (i).getQuantity (),client,purchase.get (i).getProduct ().getId (),user ,purchase.get (i).getAmount ().doubleValue ()));

        }
        return data;
    }

    public void initialize(URL location, ResourceBundle resources) {
        iID.setCellValueFactory (new PropertyValueFactory <> ("rID"));
        iData.setCellValueFactory (new PropertyValueFactory <> ("rData"));
        iCost.setCellValueFactory (new PropertyValueFactory <> ("rCost"));
        iQuantity.setCellValueFactory (new PropertyValueFactory <> ("rProductQuantity"));
        iClient.setCellValueFactory (new PropertyValueFactory <> ("rClient"));
        iProduct.setCellValueFactory (new PropertyValueFactory <> ("rIdProduct"));
        iUser.setCellValueFactory (new PropertyValueFactory <> ("rUser"));
        iAmount.setCellValueFactory (new PropertyValueFactory <> ("rAmount"));


        purchaseController = new PurchaseController ( );
        try {
            purchase = purchaseController.fetchList ( );
        } catch (Exception e) {
            System.out.println (e.getMessage ( ));
        }
        if (userRole != 91003){
            deleteButton.setDisable (true);
            deleteButton.setOpacity (0);
            refreshButton.setTranslateX (76);

        }
        data = insertData ( );
        tableID.setItems (data);

    }

    public Pane loadPurchase(AnchorPane home, Pane pnlPurchase) throws IOException {
        AnchorPane pane = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("purchase.fxml"));
        pane.prefHeightProperty ( ).bind (home.heightProperty ( ));
        pane.prefWidthProperty ( ).bind (home.widthProperty ( ));
        pnlPurchase.getChildren ( ).setAll (pane);
        return pnlPurchase;
    }

    public void addButton(ActionEvent actionEvent) {
        try {
            purchaseAddViewController.startStage ();
        } catch (IOException e) {
            e.printStackTrace ( );
        }
    }

    public void accessdelete(ActionEvent actionEvent) {
        Long id = tableID.getSelectionModel ( ).getSelectedItem ( ).getrID ( );
        Integer status = purchaseController.deletePurchase (id);
        AlertViewController.delete (status, tableID, "purchase");
    }

    public void accessRefresh(ActionEvent actionEvent) {
        tableID.getItems ( ).clear ( );
        data.clear ( );
        purchaseController = new PurchaseController ( );
        try {
            purchase = purchaseController.fetchList ( );
        } catch (Exception e) {
            System.out.println (e.getMessage ( ));
        }
        data = insertData ( );
        tableID.setItems (data);
    }

    public void searchProduct(KeyEvent keyEvent) {
        FilteredList<PurchaseTable> filteredList = new FilteredList <> (data, e -> true);
        search.textProperty ( ).addListener (((observable, oldValue, newValue) -> {
            filteredList.setPredicate ((Predicate<? super PurchaseTable>) purchase -> {
                if (newValue == null || newValue.isEmpty ( )) {
                    return true;
                }
                String lowerCase = newValue.toLowerCase ( );
                if (purchase.getrClient ().toLowerCase ( ).contains (lowerCase)) {
                    return true;
                } else if (purchase.getrData ().toLowerCase ( ).contains (lowerCase)) {
                    return true;
                } else if (purchase.getrProduct ().toLowerCase ( ).contains (lowerCase)) {
                    return true;
                } else if (purchase.getrUser ().toLowerCase ( ).contains (lowerCase)) {
                    return true;
                }

                return false;
            });
        }));
        SortedList<PurchaseTable> sortedList = new SortedList <> (filteredList);
        sortedList.comparatorProperty ( ).bind (tableID.comparatorProperty ( ));
        tableID.setItems (sortedList);
    }
}