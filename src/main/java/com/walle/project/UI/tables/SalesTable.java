package com.walle.project.UI.tables;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class SalesTable {
    private final SimpleLongProperty rID;
    private final SimpleStringProperty rData;
    private final SimpleIntegerProperty rProductQuantity;
    private final SimpleStringProperty rClient;
    private final SimpleStringProperty rProduct;
    private final SimpleStringProperty rUser;
    private final SimpleDoubleProperty rAmount;
    private final SimpleDoubleProperty rPrice;


    public SalesTable (Long sID, String sData, Integer sProductQuantity, String sClient ,String sProduct, String sUser, Double sAmount,Double sPrice){
        this.rID = new SimpleLongProperty(sID);
        this.rData = new SimpleStringProperty (sData);
        this.rProductQuantity= new SimpleIntegerProperty (sProductQuantity);
        this.rClient = new SimpleStringProperty (sClient);
        this.rProduct = new SimpleStringProperty (sProduct);
        this.rUser = new SimpleStringProperty (sUser);
        this.rAmount = new SimpleDoubleProperty (sAmount);
        this.rPrice = new SimpleDoubleProperty (sPrice);
    }

    public void setrID (Integer rID)  { this.rID.set (rID); }
    public void setrData (String rData) {this.rData.set (rData);}
    public void setProductQuantity (Integer rProductQuantity) {this.rProductQuantity.set (rProductQuantity);}
    public void setrClient (String rClient) {this.rClient.set (rClient);}
    public void setrProduct (String rProduct) {this.rProduct.set (rProduct);}
    public void setrUser (String rUser) {this.rUser.set (rUser);}
    public void setrAmount (Double rAmount) {this.rAmount.set (rAmount);}
    public void setrPrice (Double rPrice) {this.rPrice.set (rPrice);}

    public Long getrID() {return rID.get ( );}
    public SimpleLongProperty rIDProperty() {return rID;}

    public String getrData() {return rData.get ( );}
    public SimpleStringProperty rDataProperty() { return rData; }

    public Integer getrProductQuantity() {return rProductQuantity.get ( );}
    public SimpleIntegerProperty rProductQuantityProperty() {return rProductQuantity;}

    public String getrClient() {return rClient.get ( );}
    public SimpleStringProperty rClientProperty() {return rClient;}

    public String getrProduct() {return rProduct.get ( );}
    public SimpleStringProperty rProductProperty() {return rProduct;}

    public String getrUser() { return rUser.get ( );}
    public SimpleStringProperty rUserProperty() {return rUser;}

    public double getrAmount() {
        return rAmount.get ( );
    }

    public SimpleDoubleProperty rAmountProperty() {
        return rAmount;
    }


    public double getrPrice() {
        return rPrice.get ( );
    }

    public SimpleDoubleProperty rPriceProperty() {
        return rPrice;
    }
}