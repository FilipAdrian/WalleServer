package com.walle.project.UI.interfaces;

import com.walle.project.UI.client.Mapper;
import com.walle.project.UI.tables.UserTable;
import com.walle.project.UI.client.RoleController;
import com.walle.project.UI.client.UserController;
import com.walle.project.server.entity.Role;
import com.walle.project.server.entity.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.converter.DefaultStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class UserViewController implements Initializable {
    @FXML
    public Button addButton;
    @FXML
    public Button deleteButton;
    @FXML
    public TextField search;
    @FXML
    public Button refreshButton;
    @FXML
    TableView <UserTable> tableID;
    @FXML
    TableColumn <Object, String> iID;
    @FXML
    TableColumn <Object, String> iName;
    @FXML
    TableColumn <Object, String> iPhone;
    @FXML
    TableColumn <Object, String> iEmail;
    @FXML
    TableColumn <Object, String> iAddress;
    @FXML
    TableColumn <Object, String> iRole;
    @FXML
    public Pane pnUser;

    public Integer flag = 0;
    private UserAddViewController userAddViewController = new UserAddViewController ( );
    private UserController userController;
    private List <Users> users;
    private ObservableList <UserTable> data;
    private ObservableList <String> roles;
    private RoleController roleController = new RoleController ( );
    private List <Role> roleList = roleController.fetchList ( );

    public UserViewController() {
    }

    private ObservableList <UserTable> insertData() {
        ObservableList <UserTable> data = FXCollections.observableArrayList ( );

        for (int i = 0; i < users.size ( ); i++) {
            String nameSurname = new String (users.get (i).getName ( ) + "   " + users.get (i).getSurname ( ));

            data.add (new UserTable (users.get (i).getId ( ), nameSurname, users.get (i).getPhone ( ),
                    users.get (i).getEmail ( ), users.get (i).getAddress ( ), users.get (i).getRole ( ).getName ( )));
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
            users = userController.fetchList ( );
        } catch (Exception e) {
            System.out.println (e.getMessage ( ));
        }

        data = insertData ( );
        tableID.setItems (data);
        editRow ();

    }

    public Pane loadUser(AnchorPane home, Pane pnlUser) throws IOException {
        AnchorPane pane = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("user.fxml"));
        pane.prefHeightProperty ( ).bind (home.heightProperty ( ));
        pane.prefWidthProperty ( ).bind (home.widthProperty ( ));
        pnlUser.getChildren ( ).setAll (pane);
        return pnlUser;
    }

    public void addButton(ActionEvent actionEvent) {
        try {
            userAddViewController.startStage ( );
        } catch (IOException e) {
            e.printStackTrace ( );
            e.getMessage ( );
        }
    }

    public void accessdelete(ActionEvent actionEvent) {
        Long id = tableID.getSelectionModel ( ).getSelectedItem ( ).getrID ( );
        Integer status = userController.deleteUser (id);
        AlertViewController.delete (status, tableID, "client");

    }

    public void accessRefresh(ActionEvent actionEvent) {
        tableID.getItems ( ).clear ( );
        data.clear ( );
        userController = new UserController ( );
        try {
            users = userController.fetchList ( );
        } catch (Exception e) {
            System.out.println (e.getMessage ( ));
        }
        data = insertData ( );
        tableID.setItems (data);
    }

    public void searchProduct(KeyEvent keyEvent) {
        FilteredList <UserTable> filteredList = new FilteredList <> (data, e -> true);
        search.textProperty ( ).addListener (((observable, oldValue, newValue) -> {
            filteredList.setPredicate ((Predicate <? super UserTable>) user -> {
                if (newValue == null || newValue.isEmpty ( )) {
                    return true;
                }
                String lowerCase = newValue.toLowerCase ( );
                if (user.getrName ( ).toLowerCase ( ).contains (lowerCase)) {
                    return true;
                } else if (user.getrPhone ( ).toLowerCase ( ).contains (lowerCase)) {
                    return true;
                } else if (user.getrEmail ( ).toLowerCase ( ).contains (lowerCase)) {
                    return true;
                } else if (user.getrAddress ( ).toLowerCase ( ).contains (lowerCase)) {
                    return true;
                } else if (user.getrRole ( ).toLowerCase ( ).contains (lowerCase)) {
                    return true;
                }

                return false;
            });
        }));
        SortedList <UserTable> sortedList = new SortedList <> (filteredList);
        sortedList.comparatorProperty ( ).bind (tableID.comparatorProperty ( ));
        tableID.setItems (sortedList);
    }

    private void editRow() {
        roles = FXCollections.observableArrayList ( );
        for (int i = 0; i < roleList.size ( ); i++) {
            roles.add (roleList.get (i).getName ( ));
        }
        iName.setCellFactory (TextFieldTableCell.forTableColumn ( ));
        iPhone.setCellFactory (TextFieldTableCell.forTableColumn ( ));
        iAddress.setCellFactory (TextFieldTableCell.forTableColumn ( ));
        iEmail.setCellFactory (TextFieldTableCell.forTableColumn ( ));
        iRole.setCellFactory (ComboBoxTableCell.forTableColumn (new DefaultStringConverter ( ), roles));
        iRole.setOnEditCommit (event -> {
            UserTable userTable = tableID.getSelectionModel ( ).getSelectedItem ( );
            userTable.setrRole (event.getNewValue ( ));
            userController.addOrUpdate (Mapper.transformtIntoUserObject (userTable));
            tableID.refresh ( );
        });
    }

    public void editColumn(TableColumn.CellEditEvent <Object, Object> objectObjectCellEditEvent) {
        UserTable userTable = tableID.getSelectionModel ( ).getSelectedItem ( );
        String columnName = objectObjectCellEditEvent.getTableColumn ( ).getId ( );
        if (columnName.equals ("iName")){
            userTable.setrName (objectObjectCellEditEvent.getNewValue ( ).toString ( ));
        }else if (columnName.equals ("iPhone")){
            userTable.setrPhone (objectObjectCellEditEvent.getNewValue ( ).toString ( ));
        }else if (columnName.equals ("iAddress")){
            userTable.setrAddress (objectObjectCellEditEvent.getNewValue ( ).toString ( ));
        }else if (columnName.equals ("iEmail")){
            userTable.setrEmail (objectObjectCellEditEvent.getNewValue ( ).toString ( ));
        }

        Users user = Mapper.transformtIntoUserObject (userTable);
        userController.addOrUpdate (user);
        tableID.refresh ();
    }
}
