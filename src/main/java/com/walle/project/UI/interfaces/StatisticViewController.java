package com.walle.project.UI.interfaces;

import com.jfoenix.controls.JFXTextField;
import com.walle.project.UI.client.PurchaseController;
import com.walle.project.UI.client.SalesController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticViewController implements Initializable {
    @FXML
    public AreaChart perYear;
    @FXML
    public CategoryAxis month;
    @FXML
    public NumberAxis money;
    @FXML
    public JFXTextField year;
    @FXML
    public Button show;
    @FXML
    public Text income;
    @FXML
    public Button next;
    @FXML
    public Text textYear;
    @FXML
    public Pane statistic;
    public Integer click = 0;

    public Pane loadStatistic(AnchorPane home, Pane pnlClient, String name) throws IOException {
        AnchorPane pane = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource (name));
        pane.prefHeightProperty ( ).bind (home.heightProperty ( ));
        pane.prefWidthProperty ( ).bind (home.widthProperty ( ));
        pnlClient.getChildren ( ).setAll (pane);
        return pnlClient;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        money = new NumberAxis (0, 1000, 20);
        Integer curentYear = LocalDate.now ( ).getYear ( );
        completAreaPerYear (curentYear.toString ( ));
        year.setOnKeyPressed (event -> {
            if (event.getCode ( ) == KeyCode.ENTER) {
                completAreaPerYear (year.getText ( ));
            }
        });
        show.setOnAction (event -> {
            completAreaPerYear (year.getText ( ));
        });

    }

    private void completAreaPerYear(String year) {
        perYear.getData ( ).clear ( );
        textYear.setText (year);
        Integer yr = Integer.parseInt (year);
        SalesController salesController = new SalesController ( );
        PurchaseController purchaseController = new PurchaseController ( );
        List <Double> sales = salesController.getAmountPerMonthOnYear (yr);
        List <Double> purchase = purchaseController.getAmountPerYear (yr);
        XYChart.Series seriesSales = new XYChart.Series ( );
        seriesSales.setName ("Sales  " + year);
        XYChart.Series seriesPurchase = new XYChart.Series ( );
        seriesPurchase.setName ("Purchases  " + year);
        String[] months = {"January", "February", "March", "April", "May", "April",
                "June", "July", "August", "September", "October", "November", "December"};
        Double amountSales = 0.0;
        Double amountPurchase = 0.0;
        for (int i = 0; i < 12; i++) {
            seriesPurchase.getData ( ).add (new XYChart.Data (months[i], purchase.get (i)));
            seriesSales.getData ( ).add (new XYChart.Data (months[i], sales.get (i)));
            amountSales += sales.get (i);
            amountPurchase += purchase.get (i);
        }


        perYear.getData ( ).addAll (seriesPurchase, seriesSales);

        Double totalProfit = amountSales - amountPurchase;
        DecimalFormat df = new DecimalFormat ("#.####");
        df.setRoundingMode (RoundingMode.CEILING);
        income.setText (df.format (totalProfit));

    }

    public void nextPage(ActionEvent actionEvent) throws IOException {

        AnchorPane pane = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("stOnUser.fxml"));
        statistic.getChildren ().addAll (pane);
    }
}
