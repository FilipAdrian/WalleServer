package com.walle.project.UI.sample;

import com.walle.project.UI.model.PurchaseTable;
import com.walle.project.controller.PurchaseController;
import com.walle.project.entity.Purchase;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PurchaseViewController implements Initializable {

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

    @FXML
    public Pane pnlPurchase;

    public Integer flag = 0;
    private PurchaseController purchaseController;
    private List<Purchase> purchase;
    private ObservableList<PurchaseTable> data;


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
}