package com.walle.project.UI.interfaces;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.walle.project.UI.client.Mapper;
import com.walle.project.UI.client.ClientController;
import com.walle.project.UI.client.ProductController;
import com.walle.project.UI.client.SalesController;
import com.walle.project.server.entity.Client;
import com.walle.project.server.entity.Product;
import com.walle.project.server.entity.Sales;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SalesAddViewController  implements Initializable, ShowButtonsController {
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
    public JFXTextField price;
    @FXML
    public Button addButtonClick;
    private ClientController clientController = new ClientController ( );
    private List <String> clients = new ArrayList <> ( );
    private List <String> products = new ArrayList <> ( );
    private List <Client> clientList = clientController.fetchList ( );
    private ProductController productController = new ProductController ( );
    private List <Product> productList = productController.fetchList ( );
    private SalesController salesController = new SalesController ( );
    private Product productSelected = new Product ( );
    private Client clientSelected = new Client ( );
    private ClientAddViewController clientAddViewController = new ClientAddViewController ();


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
        TextFields.bindAutoCompletion (product, products).setOnAutoCompleted (event -> {
            for (int i = 0; i < productList.size ( ); i++) {
                if (productList.get (i).getId ( ).contains (product.getText ( ))) {
                    productSelected = productList.get (i);
                }
            }
            price.setText (productSelected.getPrice ( ).toString ( ));
        });

        setAmount (quantity,price );

    }

    public Stage startStage() throws IOException {
        Parent root = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("salesADD.fxml"));
        Stage stage = new Stage ( );
        stage.setTitle ("Add Sale");
        stage.setScene (new Scene (root, 1060, 670));

        stage.show ( );
        stage.setResizable (false);
        return stage;

    }

    public void onClick(ActionEvent actionEvent) {
        System.out.println (data.getValue ( ));

        Integer status = null;
        Boolean fieldComplet = true;
        JFXTextField[] textFields = {client, product, price, amount, quantity};
        for (JFXTextField field : textFields) {
            if (field.getText ( ).isEmpty ( ) || field.getText ( ) == null) {
                fieldComplet = false;
                AlertViewController.error ( );
                break;
            }
        }
        if (fieldComplet) {
            if (decreaseProductQuantity (productSelected)) {
                for (int i = 0; i < clientList.size ( ); i++) {
                    if (clientList.get (i).getName ( ).contains (Mapper.splitBySpace (client.getText ( )).get (0)) ||
                            clientList.get (i).getName ( ).contains (Mapper.splitBySpace (client.getText ( )).get (1))) {
                        clientSelected = clientList.get (i);
                    }
                }
                Sales sales = new Sales (data.getValue ( ).toString ( ), Double.parseDouble (price.getText ()), Integer.parseInt (quantity.getText ()),
                        LoginViewController.currentUser, productSelected, clientSelected, Double.parseDouble (amount.getText ()));
                status = salesController.addOrUpdate (sales);
                AlertViewController.add (status, "sale");
                cleanField (textFields);
            }

        }


    }


    public void setAmount(JFXTextField quantity , JFXTextField price) {
        try {
            quantity.setOnMouseExited (event -> {
                Double totalPrice = Double.parseDouble (quantity.getText ( )) * Double.parseDouble (price.getText ( ));
                DecimalFormat df = new DecimalFormat ("#.##");
                df.setRoundingMode (RoundingMode.CEILING);
                amount.setText (df.format (totalPrice));
            });
        } catch (NumberFormatException e) {
            e.getMessage ( );
        }
    }

    public Boolean decreaseProductQuantity(Product product) {
        Boolean response = true;
        Integer currentQuantity = product.getQuantiy ( );
        Integer selectedQuantity = Integer.parseInt (quantity.getText ( ));
        if (currentQuantity >= selectedQuantity && currentQuantity > 0 ) {
            Integer finalQuantity = currentQuantity - selectedQuantity;
            product.setQuantiy (finalQuantity);
            productController.addOrUpdate (product);
        } else {
            response = false;
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle ("Error");
            alert.setContentText ("NOT enough Quantity!");
            alert.setHeaderText (null);
            alert.showAndWait ( );
        }
        return response;
    }

    public void addButtonClick(ActionEvent actionEvent) {
        try {
            clientAddViewController.startStage ();
        } catch (IOException e) {
            e.printStackTrace ( );
        }
    }
}
