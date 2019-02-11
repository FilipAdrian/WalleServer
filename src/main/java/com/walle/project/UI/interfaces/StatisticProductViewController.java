package com.walle.project.UI.interfaces;

import com.walle.project.UI.client.Mapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticProductViewController implements Initializable {
    @FXML
    public PieChart chart;
    @FXML
    public AnchorPane stProduct;

    public void getPrevious(ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("stOnUser.fxml"));
        stProduct.getChildren ().addAll (pane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadChart ( );
    }

    private void loadChart() {
        List <String> products = Mapper.productQuantity ( );
        ObservableList <PieChart.Data> pieChart = FXCollections.observableArrayList ( );
        for (int i = 0; i < products.size ( ) - 1; i++) {
            Integer quantity = Integer.parseInt (products.get (i));
            pieChart.add (new PieChart.Data (products.get (i + 1), quantity));
            i++;
        }
        chart.setData (pieChart);
        chart.setStartAngle (0);
    }


}
