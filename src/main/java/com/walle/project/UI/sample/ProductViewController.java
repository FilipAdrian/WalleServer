package com.walle.project.UI.sample;


import com.walle.project.UI.client.Mapper;
import com.walle.project.UI.model.ProductTable;
import com.walle.project.UI.client.ManufactureController;
import com.walle.project.UI.client.ProductController;
import com.walle.project.UI.client.WarehouseController;
import com.walle.project.server.entity.Manufacture;
import com.walle.project.server.entity.Product;
import com.walle.project.server.entity.Warehouse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
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
import javafx.util.converter.DefaultStringConverter;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ProductViewController implements Initializable, ShowButtonsController {
    @FXML
    public TableView <ProductTable> tableID;
    @FXML
    public Button refreshButton;
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
    private TextField search;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addButton;

    private ProductAddViewController productAddViewController = new ProductAddViewController ( );
    private Long user = LoginViewController.roleUser;
    @Autowired
    private ProductController productController;
    private List <Product> products;
    private ObservableList <ProductTable> data;
    private ManufactureController manufactureController = new ManufactureController ( );
    private WarehouseController warehouseController = new WarehouseController ( );
    private List <Manufacture> manufactureList = manufactureController.fetchList ( );
    private List <Warehouse> warehouseList = warehouseController.fetchList ( );
    private ObservableList <String> manufactures;
    private ObservableList <String> warehouses;

    private ObservableList <ProductTable> insertData() {
        ObservableList <ProductTable> data = FXCollections.observableArrayList ( );

        for (int i = 0; i < products.size ( ); i++) {

            data.add (new ProductTable (products.get (i).getId ( ), products.get (i).getName ( ), products.get (i).getQuantiy ( ).toString ( ), products.get (i).getPrice ( ).toString ( ),
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

        productController = new ProductController ( );
        try {
            products = productController.fetchList ( );
        } catch (Exception e) {
            System.out.println (e.getMessage ( ));
        }
        System.out.println (user);
        data = insertData ( );
        tableID.setItems (data);
        showButton (deleteButton, addButton, refreshButton, user);
        if (user != 91001) {
            manufactures = FXCollections.observableArrayList ( );
            warehouses = FXCollections.observableArrayList ( );
            editRow ( );
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
        String id = tableID.getSelectionModel ( ).getSelectedItem ( ).getrID ( );
        Integer status = productController.deleteProduct (id);
        AlertViewController.delete (status, tableID, "product");

    }

    private void editRow() {
        for (int i = 0; i < manufactureList.size ( ); i++) {
            manufactures.add (manufactureList.get (i).getName ( ));
        }
        for (int i = 0; i < warehouseList.size ( ); i++) {
            warehouses.add (warehouseList.get (i).getName ( ));
        }
        iName.setCellFactory (TextFieldTableCell.forTableColumn ( ));
        iQuantity.setCellFactory (TextFieldTableCell.forTableColumn ( ));
        iPrice.setCellFactory (TextFieldTableCell.forTableColumn ( ));
        iManufacture.setCellFactory (ComboBoxTableCell.forTableColumn (new DefaultStringConverter ( ), manufactures));
        iWarehouse.setCellFactory (ComboBoxTableCell.forTableColumn (new DefaultStringConverter ( ), warehouses));
        iManufacture.setOnEditCommit (event -> {
            ProductTable productTable = tableID.getSelectionModel ( ).getSelectedItem ( );
            productTable.setrManufacture (event.getNewValue ( ));
            productController.addOrUpdate (Mapper.transIntoProductObject (productTable));
            tableID.refresh ( );
        });
        iWarehouse.setOnEditCommit (event -> {
            ProductTable productTable = tableID.getSelectionModel ( ).getSelectedItem ( );
            productTable.setrWarehouse (event.getNewValue ( ));
            productController.addOrUpdate (Mapper.transIntoProductObject (productTable));
            tableID.refresh ( );
        });
    }

    public void addButton(ActionEvent actionEvent) {
        try {
            productAddViewController.startStage ( );
        } catch (Exception e) {
            e.getMessage ( );
            e.getLocalizedMessage ( );
        }

    }

    public void accessRefresh(ActionEvent actionEvent) {
        tableID.getItems ( ).clear ( );
        data.clear ( );
        productController = new ProductController ( );
        try {
            products = productController.fetchList ( );
        } catch (Exception e) {
            System.out.println (e.getMessage ( ));
        }
        data = insertData ( );
        tableID.setItems (data);

    }

    public void editColumn(TableColumn.CellEditEvent <Object, String> objectStringCellEditEvent) {
        if (user != 91001) {
            ProductTable productTable = tableID.getSelectionModel ( ).getSelectedItem ( );
            String columnName = objectStringCellEditEvent.getTableColumn ( ).getId ( );
            if (columnName.equals ("iName")) {
                productTable.setrWarehouse (objectStringCellEditEvent.getNewValue ( ));
            } else if (columnName.equals ("iQuantity")) {
                productTable.setrQuantity (objectStringCellEditEvent.getNewValue ( ));
            } else if (columnName.equals ("iPrice")) {
                productTable.setrPrice (objectStringCellEditEvent.getNewValue ( ));
            }
            productController.addOrUpdate (Mapper.transIntoProductObject (productTable));
            tableID.refresh ( );
        }
    }

}

