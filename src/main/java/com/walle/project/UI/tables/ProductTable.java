package com.walle.project.UI.tables;

import javafx.beans.property.SimpleStringProperty;

public class ProductTable {
    private final SimpleStringProperty rID;
    private final SimpleStringProperty rName;
    private final SimpleStringProperty rQuantity;
    private final SimpleStringProperty rPrice;
    private final SimpleStringProperty rManufacture;
    private final SimpleStringProperty rWarehouse;

    public ProductTable (String sID, String sName, String sQuantity, String sPrice, String sManufacture, String sWarehouse){
        this.rID = new SimpleStringProperty(sID);
        this.rName = new SimpleStringProperty (sName);
        this.rQuantity= new SimpleStringProperty (sQuantity);
        this.rPrice = new SimpleStringProperty (sPrice);
        this.rManufacture = new SimpleStringProperty (sManufacture);
        this.rWarehouse = new SimpleStringProperty (sWarehouse);
    }

    public void setrID (String rID)  { this.rID.set (rID); }
    public void setrName (String rName) {this.rName.set (rName);}
    public void setrQuantity (String rQuantity) {this.rQuantity.set (rQuantity);}
    public void setrPrice (String rPrice) {this.rPrice.set (rPrice);}
    public void setrManufacture (String rManufacture) {this.rManufacture.set (rManufacture);}
    public void setrWarehouse (String rWarehouse) {this.rWarehouse.set (rWarehouse);}

    public String getrID() {
        return rID.get ( );
    }

    public SimpleStringProperty rIDProperty() {
        return rID;
    }

    public String getrName() {
        return rName.get ( );
    }

    public SimpleStringProperty rNameProperty() { return rName; }

    public String getrQuantity() {
        return rQuantity.get ( );
    }

    public SimpleStringProperty rQuantityProperty() {
        return rQuantity;
    }

    public String getrPrice() {
        return rPrice.get ( );
    }

    public SimpleStringProperty rPriceProperty() {
        return rPrice;
    }

    public String getrManufacture() {
        return rManufacture.get ( );
    }

    public SimpleStringProperty rManufactureProperty() {
        return rManufacture;
    }

    public String getrWarehouse() {
        return rWarehouse.get ( );
    }

    public SimpleStringProperty rWarehouseProperty() {
        return rWarehouse;
    }
}
