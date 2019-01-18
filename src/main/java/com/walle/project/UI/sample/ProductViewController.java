package com.walle.project.UI.sample;


import com.walle.project.UI.model.ProductTable;
import com.walle.project.controller.ManufactureController;
import com.walle.project.entity.Manufacture;
import com.walle.project.entity.Product;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ProductViewController implements Initializable {
    @FXML
    public TableView <ProductTable> tableID;
    @FXML
    private TableColumn <Object, String> iID;
    @FXML
    private TableColumn <Object, String> iName;
    @FXML
    private TableColumn <Object, String> iQuantity;
    @FXML
    private TableColumn <Object, String> iPrice;
    @FXML
    private TableColumn <Object, String> iManufacture;
    @FXML
    private TableColumn <Object, String> iWarehouse;
    @FXML
    public Pane pnStaff;
    @FXML
    private TextField search;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addButton;

    public Integer flag = 0;
    private ProductAddViewController productAddViewController = new ProductAddViewController ( );
    private Long user = LoginViewController.roleUser;
    @Autowired
    private com.walle.project.controller.ProductController productController;
    private List <Product> products;
    private ObservableList <ProductTable> data;
    private ManufactureController manufactureController = new ManufactureController ( );
    private List <Manufacture> manufactureList = manufactureController.fetchList ( );
    private ObservableList <String> manufactures;

    private ObservableList <ProductTable> insertData() {
        ObservableList <ProductTable> data = FXCollections.observableArrayList ( );

        for (int i = 0; i < products.size ( ); i++) {

            data.add (new ProductTable (products.get (i).getId ( ), products.get (i).getName ( ), products.get (i).getQuantiy ( ).toString (), products.get (i).getPrice ( ).toString (),
                    products.get (i).getManufacture ( ).getName ( ), products.get (i).getWarehouse ( ).getName ( )));
        }
        return data;
    }

    public void initialize(URL location, ResourceBundle resources) {
        iID.setCellValueFactory (new PropertyValueFactory <> ("rID"));
        iName.setCellValueFactory (new PropertyValueFactory <> ("rName"));
        iQuantity.setCellValueFactory (new PropertyValueFactory <> ("rQuantity"));
        iPrice.setCellValueFactory (new PropertyValueFactory <> ("rPrice"));
        iManufacture.setCellValueFactory (new PropertyValueFactory <> ("rManufacture"));
        iWarehouse.setCellValueFactory (new PropertyValueFactory <> ("rWarehouse"));
        deleteButton.setOpacity (0);
        addButton.setOpacity (0);
        deleteButton.setDisable (true);
        addButton.setDisable (true);
        productController = new com.walle.project.controller.ProductController ( );
        try {
            products = productController.fetchList ( );
        } catch (Exception e) {
            System.out.println (e.getMessage ( ));
        }
        System.out.println (user);
        data = insertData ( );
        tableID.setItems (data);
        if (user != 91001) {
            manufactures = FXCollections.observableArrayList ( );
            deleteButton.setOpacity (1);
            addButton.setOpacity (1);
            deleteButton.setDisable (false);
            addButton.setDisable (false);
            iName.setCellFactory (TextFieldTableCell.forTableColumn ( ));
            iQuantity.setCellFactory (TextFieldTableCell.forTableColumn ( ));
            iPrice.setCellFactory (TextFieldTableCell.forTableColumn ( ));
            iWarehouse.setCellFactory (TextFieldTableCell.forTableColumn ( ));
            for (int i = 0; i < manufactureList.size ( ); i++) {
                manufactures.add (manufactureList.get (i).getName ( ));
            }
            iManufacture.setCellFactory (ComboBoxTableCell.forTableColumn ( new DefaultStringConverter (),manufactures));
iManufacture.setOnEditCommit (new EventHandler <TableColumn.CellEditEvent <Object, String>> ( ) {
    @Override
    public void handle(TableColumn.CellEditEvent <Object, String> event) {
        System.out.println (event.getNewValue () );
    }
});
        }

    }

    public Pane loadProduct(AnchorPane home, Pane pnlProducts) throws IOException {
        AnchorPane pane = FXMLLoader.load (getClass ( ).getClassLoader ( ).getResource ("product.fxml"));
        pane.prefHeightProperty ( ).bind (home.heightProperty ( ));
        pane.prefWidthProperty ( ).bind (home.widthProperty ( ));
        pnlProducts.getChildren ( ).setAll (pane);
        return pnlProducts;
    }

    public void searchProduct(KeyEvent keyEvent) {
        FilteredList <ProductTable> filteredList = new FilteredList <> (data, e -> true);
        search.textProperty ( ).addListener (((observable, oldValue, newValue) -> {
            filteredList.setPredicate ((Predicate <? super ProductTable>) product -> {
                if (newValue == null || newValue.isEmpty ( )) {
                    return true;
                }
                String lowerCase = newValue.toLowerCase ( );
                if (product.getrID ( ).contains (lowerCase)) {
                    return true;
                } else if (product.getrName ( ).toLowerCase ( ).contains (lowerCase)) {
                    return true;
                } else if (product.getrManufacture ( ).toLowerCase ( ).contains (lowerCase)) {
                    return true;
                } else if (product.getrWarehouse ( ).toLowerCase ( ).contains (lowerCase)) {
                    return true;
                }

                return false;
            });
        }));
        SortedList <ProductTable> sortedList = new SortedList <> (filteredList);
        sortedList.comparatorProperty ( ).bind (tableID.comparatorProperty ( ));
        tableID.setItems (sortedList);
    }

    public void accessdelete(ActionEvent actionEvent) {
        if (user == 91002 || user == 51003) {
            String id = tableID.getSelectionModel ( ).getSelectedItem ( ).getrID ( );
            Integer status = productController.deleteProduct (id);
            AlertViewController.delete (status, tableID, "product");
        }

    }

    public void onSelect(TableColumn.CellEditEvent <Object, Object> objectObjectCellEditEvent) {

        ProductTable client = tableID.getSelectionModel ( ).getSelectedItem ( );
        System.out.println (client.getrID ( ));

    }

    public void addButton(ActionEvent actionEvent) {
        try {
            productAddViewController.startStage ( );
        } catch (Exception e) {
            e.getMessage ( );
            e.getLocalizedMessage ( );
        }

    }
}

