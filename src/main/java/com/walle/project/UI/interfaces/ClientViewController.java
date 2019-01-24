package com.walle.project.UI.interfaces;

import com.walle.project.UI.client.Mapper;
import com.walle.project.UI.tables.ClientTable;
import com.walle.project.UI.client.ClientController;
import com.walle.project.UI.client.TypeController;
import com.walle.project.server.entity.Client;
import com.walle.project.server.entity.Type;
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

public class ClientViewController implements Initializable, ShowButtonsController {
    @FXML
    public Button addButton;
    @FXML
    public Button deleteButton;
    @FXML
    public TextField search;
    @FXML
    public Button refreshButton;
    @FXML
    TableView <ClientTable> tableID;
    @FXML
    TableColumn <Object, Object> iID;
    @FXML
    TableColumn <Object, String> iClient;
    @FXML
    TableColumn <Object, String> iPhone;
    @FXML
    TableColumn <Object, String> iEmail;
    @FXML
    TableColumn <Object, String> iAddress;
    @FXML
    TableColumn <Object, String> iType;
    private ObservableList <String> types;
    private TypeController typeController = new TypeController ( );
    private List <Type> typeList = typeController.fetchList ( );
    private ClientController clientController;
    private List <Client> clients;
    private ObservableList <ClientTable> data;
    private ClientAddViewController clientAddViewController = new ClientAddViewController ( );
    private Integer userRole = LoginViewController.roleUser.intValue ( );

    public ClientViewController() {
    }

    private ObservableList <ClientTable> insertData() {
        ObservableList <ClientTable> data = FXCollections.observableArrayList ( );

        for (int i = 0; i < clients.size ( ); i++) {
            String nameSurname = new String (clients.get (i).getName ( ) + "   " + clients.get (i).getSurname ( ));
            data.add (new ClientTable (clients.get (i).getId ( ), nameSurname, clients.get (i).getPhone ( ),
                    clients.get (i).getEmail ( ), clients.get (i).getAddress ( ), clients.get (i).getType ( ).getName ( )));
        }
        return data;
    }

    @Override

    public void initialize(URL location, ResourceBundle resources) {
        iID.setCellValueFactory (new PropertyValueFactory <> ("rID"));
        iClient.setCellValueFactory (new PropertyValueFactory <> ("rClient"));
        iPhone.setCellValueFactory (new PropertyValueFactory <> ("rPhone"));
        iEmail.setCellValueFactory (new PropertyValueFactory <> ("rEmail"));
        iAddress.setCellValueFactory (new PropertyValueFactory <> ("rAddress"));
        iType.setCellValueFactory (new PropertyValueFactory <> ("rType"));

        clientController = new ClientController ( );
        try {
            clients = clientController.fetchList ( );
        } catch (Exception e) {
            System.out.println (e.getMessage ( ));
        }
        if (userRole == 91001) {
            deleteButton.setDisable (true);
            deleteButton.setOpacity (0);
            refreshButton.setTranslateX (76);

        }
        if (userRole == 91001) {
            deleteButton.setDisable (true);
            deleteButton.setOpacity (0);
            refreshButton.setTranslateX (76);

        }
        data = insertData ( );
        tableID.setItems (data);
        editRow ( );

    }

    public Pane loadClient(AnchorPane home, Pane pnlClient) throws IOException {
        AnchorPane pane = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("client.fxml"));
        pane.prefHeightProperty ( ).bind (home.heightProperty ( ));
        pane.prefWidthProperty ( ).bind (home.widthProperty ( ));
        pnlClient.getChildren ( ).setAll (pane);
        return pnlClient;
    }

    public void accessRefresh(ActionEvent actionEvent) {
        tableID.getItems ( ).clear ( );
        data.clear ( );
        clientController = new ClientController ( );
        try {
            clients = clientController.fetchList ( );
        } catch (Exception e) {
            System.out.println (e.getMessage ( ));
        }
        data = insertData ( );
        tableID.setItems (data);
    }


    public void searchProduct(KeyEvent keyEvent) {
        FilteredList <ClientTable> filteredList = new FilteredList <> (data, e -> true);
        search.textProperty ( ).addListener (((observable, oldValue, newValue) -> {
            filteredList.setPredicate ((Predicate <? super ClientTable>) client -> {
                if (newValue == null || newValue.isEmpty ( )) {
                    return true;
                }
                String lowerCase = newValue.toLowerCase ( );
                if (client.getrClient ( ).toLowerCase ( ).contains (lowerCase)) {
                    return true;
                } else if (client.getrPhone ( ).toLowerCase ( ).contains (lowerCase)) {
                    return true;
                } else if (client.getrEmail ( ).toLowerCase ( ).contains (lowerCase)) {
                    return true;
                } else if (client.getrAddress ( ).toLowerCase ( ).contains (lowerCase)) {
                    return true;
                } else if (client.getrType ( ).toLowerCase ( ).contains (lowerCase)) {
                    return true;
                }

                return false;
            });
        }));
        SortedList <ClientTable> sortedList = new SortedList <> (filteredList);
        sortedList.comparatorProperty ( ).bind (tableID.comparatorProperty ( ));
        tableID.setItems (sortedList);
    }

    public void addButton(ActionEvent actionEvent) {
        try {
            clientAddViewController.startStage ( );
        } catch (IOException e) {
            e.printStackTrace ( );
            e.getMessage ( );
        }
    }

    public void accessdelete(ActionEvent actionEvent) {
        Long id = tableID.getSelectionModel ( ).getSelectedItem ( ).getrID ( );
        Integer status = clientController.deleteClient (id);
        AlertViewController.delete (status, tableID, "client");

    }

    private void editRow() {
        types = FXCollections.observableArrayList ( );
        for (int i = 0; i < typeList.size ( ); i++) {
            types.add (typeList.get (i).getName ( ));
        }
        iClient.setCellFactory (TextFieldTableCell.forTableColumn ( ));
        iPhone.setCellFactory (TextFieldTableCell.forTableColumn ( ));
        iAddress.setCellFactory (TextFieldTableCell.forTableColumn ( ));
        iEmail.setCellFactory (TextFieldTableCell.forTableColumn ( ));
        iType.setCellFactory (ComboBoxTableCell.forTableColumn (new DefaultStringConverter ( ), types));
        iType.setOnEditCommit (event -> {
            ClientTable clientTable = tableID.getSelectionModel ( ).getSelectedItem ( );
            clientTable.setrType (event.getNewValue ( ));
            clientController.addOrUpdate (Mapper.transformIntoClientObject (clientTable));
            tableID.refresh ( );
        });
    }


    public void editColumn(TableColumn.CellEditEvent <Object, Object> objectObjectCellEditEvent) {
        ClientTable clientTable = tableID.getSelectionModel ( ).getSelectedItem ( );
        String columnName = objectObjectCellEditEvent.getTableColumn ( ).getId ( );
        if (columnName.equals ("iClient")) {
            clientTable.setrClient (objectObjectCellEditEvent.getNewValue ( ).toString ( ));
        } else if (columnName.equals ("iPhone")) {
            clientTable.setrPhone (objectObjectCellEditEvent.getNewValue ( ).toString ( ));
        } else if (columnName.equals ("iAddress")) {
            clientTable.setrAddress (objectObjectCellEditEvent.getNewValue ( ).toString ( ));
        } else if (columnName.equals ("iEmail")) {
            clientTable.setrEmail (objectObjectCellEditEvent.getNewValue ( ).toString ( ));
        }

        Client client = Mapper.transformIntoClientObject (clientTable);
        clientController.addOrUpdate (client);
        tableID.refresh ( );
    }
}