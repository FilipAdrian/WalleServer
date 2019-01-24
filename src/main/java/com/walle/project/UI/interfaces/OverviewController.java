package com.walle.project.UI.interfaces;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.Label;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class OverviewController implements Initializable {

    @FXML
    public Pane pnlOverview;
    @FXML
    public Label user;
    @FXML
    public Label access;
    @FXML
    public Text month;
    @FXML
    public Text data;
    @FXML
    public Text day;
    public Pane loadPurchase(AnchorPane home, Pane pnlOverview) throws IOException {
        AnchorPane pane = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("overview.fxml"));
        pane.prefHeightProperty ( ).bind (home.heightProperty ( ));
        pane.prefWidthProperty ( ).bind (home.widthProperty ( ));
        pnlOverview.getChildren ( ).setAll (pane);
        return pnlOverview;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user.setText (String.valueOf (LoginViewController.nameSurname));
        access.setText (LoginViewController.access+":");

        month.setText (LocalDate.now ().getMonth ().toString ());
        day.setText (LocalDate.now ().getDayOfWeek ().toString ());
        Integer  nowData= LocalDate.now ().getDayOfMonth ();
        data.setText (nowData.toString ());

    }
}
