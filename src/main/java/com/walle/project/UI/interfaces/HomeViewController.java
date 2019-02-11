package com.walle.project.UI.interfaces;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {

    @FXML
    public Button nextScene;
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
    public Pane pnlStatistics = new Pane ( );
    @FXML
    private Pane pnlSettings;
    @FXML
    private Label nameSurname;
    @FXML
    public AnchorPane home;

    public Long user = LoginViewController.roleUser;
    private SaettingViewController saettingViewController = new SaettingViewController ( );
    private ProductViewController productViewController = new ProductViewController ( );
    private UserViewController userViewController = new UserViewController ( );
    private ManufactureViewController manufactureViewController = new ManufactureViewController ( );
    private SalesViewController salesViewController = new SalesViewController ( );
    private PurchaseViewController purchaseViewController = new PurchaseViewController ( );
    private ClientViewController clientViewController = new ClientViewController ( );
    private WarehouseViewController warehouseViewController = new WarehouseViewController ( );
    private OverviewController overviewController = new OverviewController ( );
    private StatisticViewController statisticViewController = new StatisticViewController ( );

    public void handleClicks(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource ( ) == btnOverview) {
            overviewController.pnlOverview = overviewController.loadPurchase (home, pnlOverview);
            pnlOverview.toFront ( );
        }

        if (actionEvent.getSource ( ) == btnUser) {
            userViewController.loadUser (home, pnlUser);
            pnlUser.toFront ( );
        }

        if (actionEvent.getSource ( ) == btnClient) {
            clientViewController.loadClient (home, pnlClient);
            pnlClient.toFront ( );
        }
        if (actionEvent.getSource ( ) == btnProducts) {
            productViewController.loadProduct (home, pnlProducts);
            pnlProducts.toFront ( );
        }
        if (actionEvent.getSource ( ) == btnManufactures) {
            manufactureViewController.loadManufacture (home, pnlManufactures);
            pnlManufactures.toFront ( );
        }
        if (actionEvent.getSource ( ) == btnWarehouses) {
            warehouseViewController.loadWarehouse (home, pnlWarehouses);
            pnlWarehouses.toFront ( );
        }
        if (actionEvent.getSource ( ) == btnSales) {
            salesViewController.loadSales (home, pnlSales);
            pnlSales.toFront ( );
        }
        if (actionEvent.getSource ( ) == btnPurchases) {
            purchaseViewController.loadPurchase (home, pnlPurchases);
            pnlPurchases.toFront ( );
        }
        if (actionEvent.getSource ( ) == btnStatistics) {

            statisticViewController.loadStatistic (home, pnlStatistics, "statistics.fxml");
            pnlStatistics.toFront ( );
        }
        if (actionEvent.getSource ( ) == btnSettings) {
            saettingViewController.loadStetting (home,pnlSettings,"settings.fxml");
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
        user = LoginViewController.roleUser;
        if (user == 91003) {
            btnUser.setOpacity (1);
            btnUser.setDisable (false);
            btnStatistics.setOpacity (1);
            btnStatistics.setDisable (false);
        } else if (user == 91002) {
            btnUser.setOpacity (0);
            btnUser.setDisable (true);
            btnStatistics.setOpacity (0);
            btnStatistics.setDisable (true);
            btnSettings.setTranslateY (-2 * btnSettings.prefHeightProperty ( ).getValue ( ));
            btnLogOut.setTranslateY (-2 * btnLogOut.prefHeightProperty ( ).getValue ( ));
        } else if (user == 91001) {
            btnUser.setOpacity (0);
            btnUser.setDisable (true);
            btnStatistics.setOpacity (0);
            btnStatistics.setDisable (true);
            btnPurchases.setOpacity (0);
            btnPurchases.setDisable (true);
            btnSettings.setTranslateY (-3 * btnSettings.prefHeightProperty ( ).getValue ( ));
            btnPurchases.setTranslateY (-3 * btnPurchases.prefHeightProperty ( ).getValue ( ));
            btnLogOut.setTranslateY (-3 * btnLogOut.prefHeightProperty ( ).getValue ( ));
        }

        try {
            System.out.println (user);
            nameSurname.setText (String.valueOf (LoginViewController.nameSurname));
            overviewController.pnlOverview = overviewController.loadPurchase (home, pnlOverview);
            pnlOverview.toFront ( );

        } catch (Exception e) {
            e.getLocalizedMessage ( );
        }


    }
}

