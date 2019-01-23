package com.walle.project.UI.sample;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.walle.project.UI.client.Mapper;
import com.walle.project.UI.client.ClientController;
import com.walle.project.UI.client.ProductController;
import com.walle.project.UI.client.PurchaseController;
import com.walle.project.server.entity.Client;
import com.walle.project.server.entity.Product;
import com.walle.project.server.entity.Purchase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PurchaseAddViewController extends SalesAddViewController implements Initializable, ShowButtonsController {
    @FXML
    public JFXTextField client;
    @FXML
    public JFXDatePicker data;
    @FXML
    public JFXTextField product;
    @FXML
    public JFXTextField quantity;
    @FXML
    public JFXTextField amount;
    @FXML
    public JFXTextField cost;
    @FXML
    public Button addButtonClick;
    private ClientController clientController = new ClientController ( );
    private List <String> clients = new ArrayList <> ( );
    private List <String> products = new ArrayList <> ( );
    private List <Client> clientList = clientController.fetchList ( );
    private ProductController productController = new ProductController ( );
    private List <Product> productList = productController.fetchList ( );
    private Product productSelected = new Product ( );
    private Client clientSelected = new Client ( );
    private ClientAddViewController clientAddViewController = new ClientAddViewController ( );
    private PurchaseController purchaseController = new PurchaseController ( );


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < productList.size ( ); i++) {
            products.add (productList.get (i).getId ( ));

        }
        for (int i = 0; i < clientList.size ( ); i++) {
            String nameSurname = new String (clientList.get (i).getName ( ) + "   " + clientList.get (i).getSurname ( ));
            clients.add (nameSurname);
        }
        TextFields.bindAutoCompletion (client, clients);
        TextFields.bindAutoCompletion (product, products);
        setAmount ( );

    }

    public Stage startStage() throws IOException {
        Parent root = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("purchaseADD.fxml"));
        Stage stage = new Stage ( );
        stage.setTitle ("Add Purchase");
        stage.setScene (new Scene (root, 1060, 670));

        stage.show ( );
        stage.setResizable (false);
        return stage;

    }

    public void onClick(ActionEvent actionEvent) {

        Integer status = null;
        Boolean fieldComplet = true;
        JFXTextField[] textFields = {client, product, cost, amount, quantity};
        for (JFXTextField field : textFields) {
            if (field.getText ( ).isEmpty ( ) || field.getText ( ) == null) {
                fieldComplet = false;
                AlertViewController.error ( );
                break;
            }
        }
        if (fieldComplet) {
            for (int i = 0; i < clientList.size ( ); i++) {
                if (clientList.get (i).getName ( ).contains (Mapper.splitBySpace (client.getText ( )).get (0)) ||
                        clientList.get (i).getName ( ).contains (Mapper.splitBySpace (client.getText ( )).get (1))) {
                    clientSelected = clientList.get (i);
                }
            }
            for (int i = 0; i < productList.size ( ); i++) {
                if (productList.get (i).getId ( ).contains (product.getText ( ))) {
                    productSelected = productList.get (i);
                }
            }
            Purchase purchase = new Purchase (data.getValue ( ).toString ( ), Double.parseDouble (cost.getText ( )), Integer.parseInt (quantity.getText ( )),
                    LoginViewController.currentUser, productSelected, clientSelected, Double.parseDouble (amount.getText ( )));
            System.out.println (purchase.toString () );
            status = purchaseController.addOrUpdate (purchase);
            AlertViewController.add (status, "purchase");
            increaseProductQuantity (productSelected);
            cleanField (textFields);


        }


    }


    public void setAmount() {
        super.setAmount (quantity,cost);
    }

    public void increaseProductQuantity(Product product) {
        Integer currentQuantity = product.getQuantiy ( );
        Integer selectedQuantity = Integer.parseInt (quantity.getText ( ));
        Integer finalQuantity = currentQuantity + selectedQuantity;
        product.setQuantiy (finalQuantity);
        productController.addOrUpdate (product);

    }

    public void addButtonClick(ActionEvent actionEvent) {
        try {
            clientAddViewController.startStage ( );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
    }
}
