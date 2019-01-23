package com.walle.project.UI.sample;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public interface ShowButtonsController {
    default void showButton(Button deleteButton,Button addButton ,Button refreshButton,Long user){
        deleteButton.setOpacity (0);
        addButton.setOpacity (0);
        refreshButton.setOpacity (0);
        deleteButton.setDisable (true);
        refreshButton.setDisable (true);
        addButton.setDisable (true);
        if (user != 91001) {
            deleteButton.setOpacity (1);
            refreshButton.setOpacity (1);
            addButton.setOpacity (1);
            deleteButton.setDisable (false);
            refreshButton.setDisable (false);
            addButton.setDisable (false);

        }
    }

    default void cleanField(JFXTextField [] textFields ){
        for (JFXTextField field: textFields) {
            field.clear ();
        }
    }
}
