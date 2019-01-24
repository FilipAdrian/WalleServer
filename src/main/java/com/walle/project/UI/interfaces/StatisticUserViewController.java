package com.walle.project.UI.interfaces;

import com.walle.project.UI.client.SalesController;
import com.walle.project.UI.client.UserController;
import com.walle.project.server.entity.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticUserViewController implements Initializable {
    @FXML
    public Button next;
    @FXML
    public Button previous;
    @FXML
    public NumberAxis activity;
    @FXML
    public CategoryAxis user;
    @FXML
    public BarChart chartUser;
    @FXML
    public AnchorPane stUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        activity = new NumberAxis (0, 1500, 20);
        completChart ( );
    }


    private void completChart() {
        SalesController salesController = new SalesController ( );
        UserController userController = new UserController ( );
        XYChart.Series amount = new XYChart.Series ( );
        amount.setName ("Amount");
        XYChart.Series quantity = new XYChart.Series ( );
        quantity.setName ("Sold Units");

        List <Users> users = userController.fetchList ( );
        List <String> userName = new ArrayList <> ( );
        for (int i = 0; i < users.size ( ); i++) {
            String user = users.get (i).getName ( ) + "  " + users.get (i).getSurname ( );
            List <Double> list = salesController.getByUserID (users.get (i).getId ( ));
            amount.getData ( ).add (new XYChart.Data (user, list.get (1)));
            quantity.getData ( ).add (new XYChart.Data (user, list.get (0)));
        }

        chartUser.getData ( ).addAll (amount, quantity);
        next.setOnAction (event -> {
            stUser.getChildren ().clear ();
            AnchorPane pane = null;
            try {
                pane = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("stTopProduct.fxml"));
            } catch (IOException e) {
                e.printStackTrace ( );
            }
            stUser.getChildren ().addAll (pane);
        });
    }

    public void getPrevious(ActionEvent actionEvent) throws IOException {
        stUser.getChildren ().clear ();
        AnchorPane pane = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("statistics.fxml"));
        stUser.getChildren ().addAll (pane);
    }

}
