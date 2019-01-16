package com.walle.project.UI.sample;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {


    @FXML
    private Button btnOverview;
    @FXML
    private Button btnUser;
    @FXML
    private Button btnClient;
    @FXML
    private Button btnProducts;
    @FXML
    private Button btnManufactures;
    @FXML
    private Button btnWarehouses;
    @FXML
    private Button btnSales;
    @FXML
    private Button btnPurchases;
    @FXML
    private Button btnStatistics;
    @FXML
    private Button btnSettings;
    @FXML
    protected Button btnLogOut;

    @FXML
    private Pane pnlOverview;
    @FXML
    private Pane pnlUser;
    @FXML
    private Pane pnlClient;
    @FXML
    private Pane pnlProducts;
    @FXML
    private Pane pnlManufactures;
    @FXML
    private Pane pnlWarehouses;
    @FXML
    private Pane pnlSales;
    @FXML
    private Pane pnlPurchases;
    @FXML
    private Pane pnlStatistics;
    @FXML
    private Pane pnlSettings;
    @FXML
    private Label nameSurname;
    @FXML
    private AnchorPane home;
    @FXML
    private VBox vBox;
    private Long user;

    private ProductViewController productViewController = new ProductViewController ( );
    private UserViewController userViewController = new UserViewController ( );
    private ManufactureViewController manufactureViewController = new ManufactureViewController ( );
    private SalesViewController salesViewController = new SalesViewController ( );
    private PurchaseViewController purchaseViewController = new PurchaseViewController ( );
    private ClientViewController clientViewController = new ClientViewController ( );
    private WarehouseViewController warehouseViewController = new WarehouseViewController ( );
    private OverviewController overviewController = new OverviewController ( );

    public void handleClicks(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource ( ) == btnOverview) {

            overviewController.pnlOverview = overviewController.loadPurchase (home, pnlOverview);
            pnlOverview.toFront ( );
        }

        if (actionEvent.getSource ( ) == btnUser) {

            if (userViewController.flag == 0) {
                try {
                    userViewController.pnUser = userViewController.loadUser (home, pnlUser);
                    userViewController.flag = 1;
                } catch (Exception e) {
                    System.out.println (e.getMessage ( ));
                }
            } else pnlUser = userViewController.pnUser;
            pnlUser.toFront ( );
        }

        if (actionEvent.getSource ( ) == btnClient) {
            if (clientViewController.flag == 0) {
                clientViewController.pnlClient = clientViewController.loadClient (home, pnlClient);
                clientViewController.flag = 1;
            } else pnlClient = clientViewController.pnlClient;
            pnlClient.toFront ( );
        }
        if (actionEvent.getSource ( ) == btnProducts) {


            if (productViewController.flag == 0) {
                try {
                    productViewController.pnStaff = productViewController.loadProduct (home, pnlProducts);
                    productViewController.flag = 1;
                } catch (Exception e) {
                    System.out.println (e.getMessage ( ));
                }
            } else pnlProducts = productViewController.pnStaff;
            pnlProducts.toFront ( );
        }
        if (actionEvent.getSource ( ) == btnManufactures) {
            if (manufactureViewController.flag == 0) {
                manufactureViewController.pnManufacture = manufactureViewController.loadManufacture (home, pnlManufactures);
                manufactureViewController.flag = 1;
            } else pnlManufactures = manufactureViewController.pnManufacture;
            pnlManufactures.toFront ( );
        }
        if (actionEvent.getSource ( ) == btnWarehouses) {
            if (warehouseViewController.flag == 0) {
                warehouseViewController.pnlWarehouse = warehouseViewController.loadWarehouse (home, pnlWarehouses);
                warehouseViewController.flag = 1;
            } else pnlWarehouses = warehouseViewController.pnlWarehouse;
            pnlWarehouses.toFront ( );
        }
        if (actionEvent.getSource ( ) == btnSales) {
            if (salesViewController.flag == 0) {
                salesViewController.pnSales = salesViewController.loadSales (home, pnlSales);
                salesViewController.flag = 1;
            } else pnlSales = salesViewController.pnSales;
            pnlSales.toFront ( );
        }
        if (actionEvent.getSource ( ) == btnPurchases) {
            if (purchaseViewController.flag == 0) {
                purchaseViewController.pnlPurchase = purchaseViewController.loadPurchase (home, pnlPurchases);
                purchaseViewController.flag = 1;
            } else pnlPurchases = purchaseViewController.pnlPurchase;
            pnlPurchases.toFront ( );
        }
        if (actionEvent.getSource ( ) == btnStatistics) {
            pnlStatistics.toFront ( );
        }
        if (actionEvent.getSource ( ) == btnSettings) {

            pnlSettings.toFront ( );
        }
        if (actionEvent.getSource ( ) == btnLogOut) {

            Stage stage = (Stage) btnManufactures.getScene ( ).getWindow ( );
            stage.close ( );
            System.out.println (nameSurname.getText ( ));
            LoginViewController.nameSurname = new StringBuilder ( );
            System.out.println (nameSurname.getText ( ));

            LoginViewController controllerLoginView = new LoginViewController ( );
            controllerLoginView.startStage ( );

        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        try {
            user = LoginViewController.roleUser;
            System.out.println (user );
            nameSurname.setText (String.valueOf (LoginViewController.nameSurname));
            overviewController.pnlOverview = overviewController.loadPurchase (home, pnlOverview);
            pnlOverview.toFront ( );
            System.out.println (overviewController.access.getText ( ));

        } catch (Exception e) {
            e.getLocalizedMessage ( );
        }

    }
}

