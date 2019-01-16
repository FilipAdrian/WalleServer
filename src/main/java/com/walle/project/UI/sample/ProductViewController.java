package com.walle.project.UI.sample;


import com.walle.project.UI.model.ProductTable;
import com.walle.project.entity.Product;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ProductViewController implements Initializable {

    @FXML
    public static TableView <ProductTable> tableID;
    @FXML
    private TableColumn <Object, String> iID;
    @FXML
    private TableColumn <Object, String> iName;
    @FXML
    private TableColumn <Object, Object> iQuantity;
    @FXML
    private TableColumn <Object, Object> iPrice;
    @FXML
    private TableColumn <Object, Object> iManufacture;
    @FXML
    private TableColumn <Object, Object> iWarehouse;
    @FXML
    public Pane pnStaff;
    @FXML
    private TextField search;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addButton;

    public Integer flag = 0;
    private ProductAddViewController productAddViewController = new ProductAddViewController ();
    private Long user = LoginViewController.roleUser;
    @Autowired
    private com.walle.project.controller.ProductController productController;
    private List <Product> products;
    private ObservableList <ProductTable> data;

    private ObservableList <ProductTable> insertData() {
        ObservableList <ProductTable> data = FXCollections.observableArrayList ( );

        for (int i = 0; i < products.size ( ); i++) {

            data.add (new ProductTable (products.get (i).getId ( ), products.get (i).getName ( ), products.get (i).getQuantiy ( ), products.get (i).getPrice ( ).doubleValue ( ),
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
        tableID.refresh ( );
        if (user != 91001) {
            deleteButton.setOpacity (1);
            addButton.setOpacity (1);
            deleteButton.setDisable (false);
            addButton.setDisable (false);
            iName.setCellFactory (TextFieldTableCell.forTableColumn ( ));

        }

    }

    public void addToTable(ProductTable productTable) {
        tableID.getItems ( ).add (productTable);

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
            if (status == 500) {
                Alert alert = new Alert (Alert.AlertType.WARNING);
                alert.setTitle ("Warning");
                alert.setContentText ("This product can be deleted !" +
                        "\nPlease check if it does not appear somewhere.");
                alert.setHeaderText (null);
                alert.showAndWait ( );
            } else if (status == 200) {
                Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
                alert.setTitle ("Confirmation");
                alert.setContentText ("This product has been deleted successfully");
                alert.setHeaderText (null);
                alert.showAndWait ( );
                ObservableList <ProductTable> allProduct, SingleProduct;
                allProduct = tableID.getItems ( );
                SingleProduct = tableID.getSelectionModel ( ).getSelectedItems ( );
                SingleProduct.forEach (allProduct::remove);
                tableID.refresh ( );

            }
            tableID.refresh ( );
        }

    }

    public void onSelect(TableColumn.CellEditEvent <Object, Object> objectObjectCellEditEvent) {

        ProductTable client = tableID.getSelectionModel ( ).getSelectedItem ( );
        System.out.println (client.getrID ( ));

    }

    public void addButton(ActionEvent actionEvent) {
        try{
            productAddViewController.startStage ( );
        }
        catch (Exception e){
            e.getMessage ();
            e.getLocalizedMessage ();
        }

    }
}

