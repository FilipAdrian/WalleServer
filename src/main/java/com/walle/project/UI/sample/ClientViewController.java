package com.walle.project.UI.sample;

import com.walle.project.UI.model.ClientTable;
import com.walle.project.controller.ClientController;
import com.walle.project.entity.Client;
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

public class ClientViewController implements Initializable {

    @FXML
    TableView <ClientTable> tableID;
    @FXML
    TableColumn <Object, Object> iID;
    @FXML
    TableColumn <Object, String> iClient;
    @FXML
    TableColumn <Object, Object> iPhone;
    @FXML
    TableColumn <Object, Object> iEmail;
    @FXML
    TableColumn <Object, Object> iAddress;
    @FXML
    TableColumn <Object, Object> iType;
    @FXML
    public Pane pnlClient;

    public Integer flag = 0;

    private ClientController clientController;
    private List <Client> clients;
    private ObservableList <ClientTable> data;

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

        data = insertData ( );
        tableID.setItems (data);
        tableID.setEditable (true);
    }

    public Pane loadClient(AnchorPane home, Pane pnlClient) throws IOException {
        AnchorPane pane = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("client.fxml"));
        pane.prefHeightProperty ( ).bind (home.heightProperty ( ));
        pane.prefWidthProperty ( ).bind (home.widthProperty ( ));
        pnlClient.getChildren ( ).setAll (pane);
        return pnlClient;
    }

    public void onSelect(TableColumn.CellEditEvent<Object, Object> objectObjectCellEditEvent) {

        ClientTable client =  tableID.getSelectionModel ().getSelectedItem ();
        System.out.println (client.getrID () );
    }
//
//    public Client onEditChange(TableColumn.CellEditEvent<Client,String> clientStringCellEditEvent){
//        ClientTable client =  tableID.getSelectionModel ().getSelectedItem ();
//        System.out.println (client );
//        client.setrName ();
//        System.out.println (client.getName () );
////        ClientRepository clientRepository = new ClientRepository ();
//// try {
////     clientRepository.update (client);
//// }
//// catch (Exception e){
////     e.getLocalizedMessage ();
//// }
//
//        return client;
//    }

}