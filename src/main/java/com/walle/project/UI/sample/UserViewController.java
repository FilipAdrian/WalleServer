package com.walle.project.UI.sample;

import com.walle.project.UI.model.UserTable;
import com.walle.project.controller.UserController;
import com.walle.project.entity.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {
    @FXML
    TableView <UserTable> tableID;
    @FXML
    TableColumn <Object, Object> iID;
    @FXML
    TableColumn <Object, Object> iName;
    @FXML
    TableColumn <Object, Object> iPhone;
    @FXML
    TableColumn <Object, Object> iEmail;
    @FXML
    TableColumn <Object, Object> iAddress;
    @FXML
    TableColumn <Object, Object> iRole;
    @FXML
    public Pane pnUser;

    public Integer flag = 0;

    private UserController userController;
    private List <Users> users;
    private ObservableList <UserTable> data;

    public UserViewController() {
    }

    private ObservableList <UserTable> insertData() {
        ObservableList <UserTable> data = FXCollections.observableArrayList ( );

        for (int i = 0; i < users.size ( ); i++) {
            String nameSurname = new String (users.get (i).getName () + "  " + users.get (i).getSurname ());

            data.add (new UserTable (users.get (i).getId ( ),nameSurname, users.get (i).getPhone ( ),
                    users.get (i).getEmail ( ),users.get (i).getAddress ( ), users.get (i).getRole ( ).getName ()));
        }
        return data;
    }

    @Override

    public void initialize(URL location, ResourceBundle resources) {
        iID.setCellValueFactory (new PropertyValueFactory <> ("rID"));
        iName.setCellValueFactory (new PropertyValueFactory <> ("rName"));
        iPhone.setCellValueFactory (new PropertyValueFactory <> ("rPhone"));
        iEmail.setCellValueFactory (new PropertyValueFactory <> ("rEmail"));
        iAddress.setCellValueFactory (new PropertyValueFactory <> ("rAddress"));
        iRole.setCellValueFactory (new PropertyValueFactory <> ("rRole"));

        userController = new UserController ( );
        try {
            users = userController.fetchList ();
        } catch (Exception e) {
            System.out.println (e.getMessage ( ));
        }

        data = insertData ( );

        tableID.setItems (data);

    }

    public Pane loadUser(AnchorPane home, Pane pnlUser) throws IOException {
        AnchorPane pane = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("user.fxml"));
        pane.prefHeightProperty ( ).bind (home.heightProperty ( ));
        pane.prefWidthProperty ( ).bind (home.widthProperty ( ));
        pnlUser.getChildren ( ).setAll (pane);
        return pnlUser;
    }

}
