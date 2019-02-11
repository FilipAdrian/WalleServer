package com.walle.project.UI.interfaces;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

public class AlertViewController {
    public static void delete(Integer status, TableView tableID,String entity){
        if (status == 500) {
            Alert alert = new Alert (Alert.AlertType.WARNING);
            alert.setTitle ("Warning");
            alert.setContentText ("This "+entity+" can be deleted !" +
                    "\nPlease check if it does not appear somewhere.");
            alert.setHeaderText (null);
            alert.showAndWait ( );
        } else if (status == 200) {
            Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
            alert.setTitle ("Confirmation");
            alert.setContentText ("This "+entity+" has been deleted successfully");
            alert.setHeaderText (null);
            alert.showAndWait ( );
            ObservableList<?> allManufacturer, SingleManufacturer;
            allManufacturer = tableID.getItems ( );
            SingleManufacturer = tableID.getSelectionModel ( ).getSelectedItems ( );
            SingleManufacturer.forEach (allManufacturer::remove);
            tableID.refresh ( );

        }
        tableID.refresh ( );
    }

    public static  void add(Integer status,String entity){
        if (status == 500) {
            Alert alert = new Alert (Alert.AlertType.WARNING);

            alert.setTitle ("Warning");
            alert.setContentText ("This "+entity+" can not be saved.Maybe it already exists");
            alert.setHeaderText (null);
            alert.showAndWait ( );
        } else if (status == 200) {
            Alert alert = new Alert (Alert.AlertType.INFORMATION);
            alert.setTitle ("Confirmation");
            alert.setContentText ("This "+entity+" has been saved successfully");
            alert.setHeaderText (null);
            alert.showAndWait ( );
        }
    }

    public static void error(){
        Alert alert = new Alert (Alert.AlertType.ERROR);
        alert.setTitle ("Error");
        alert.setContentText ("All fields must be completed");
        alert.setHeaderText (null);
        alert.showAndWait ( );
    }
}
