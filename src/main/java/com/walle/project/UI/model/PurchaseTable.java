package com.walle.project.UI.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class PurchaseTable {
    private final SimpleLongProperty rID;
    private final SimpleStringProperty rData;
    private final SimpleIntegerProperty rProductQuantity;
    private final SimpleStringProperty rClient;
    private final SimpleStringProperty rIdProduct;
    private final SimpleStringProperty rUser;
    private final SimpleDoubleProperty rAmount;
    private final SimpleDoubleProperty rCost;


    public PurchaseTable(Long sID, String sData, Double sCost, Integer sProductQuantity, String sIdClient,
                         String sIdProduct, String sIdUser, Double sAmount) {
        this.rID = new SimpleLongProperty (sID);
        this.rData = new SimpleStringProperty (sData);
        this.rProductQuantity = new SimpleIntegerProperty (sProductQuantity);
        this.rClient = new SimpleStringProperty (sIdClient);
        this.rIdProduct = new SimpleStringProperty (sIdProduct);
        this.rUser = new SimpleStringProperty (sIdUser);
        this.rAmount = new SimpleDoubleProperty (sAmount);
        this.rCost = new SimpleDoubleProperty (sCost);
    }

    public void setrID(Integer rID) {
        this.rID.set (rID);
    }

    public void setrData(String rData) {
        this.rData.set (rData);
    }

    public void setrAmount (Double rAmount) {this.rAmount.set (rAmount);}
    public void setrCost (Double rCost) {this.rCost.set (rCost);}

    public void setProductQuantity(Integer rProductQuantity) {
        this.rProductQuantity.set (rProductQuantity);
    }

    public void setClient(String rClient) {
        this.rClient.set (rClient);
    }

    public void setIdProduct(String rIdProduct) {
        this.rIdProduct.set (rIdProduct);
    }

    public void setUser(String rUser) {
        this.rUser.set (rUser);
    }


    public Long getrID() {
        return rID.get ( );
    }

    public SimpleLongProperty rIDProperty() {
        return rID;
    }

    public String getrData() {
        return rData.get ( );
    }

    public SimpleStringProperty rDataProperty() {
        return rData;
    }

    public double getrAmount() {
        return rAmount.get ( );
    }

    public SimpleDoubleProperty rAmountProperty() {
        return rAmount;
    }



    public double getrCost() {
        return rCost.get ( );
    }

    public SimpleDoubleProperty rCostProperty() {
        return rCost;
    }

    public Integer getrProductQuantity() {
        return rProductQuantity.get ( );
    }

    public SimpleIntegerProperty rProductQuantityProperty() {
        return rProductQuantity;
    }

    public String getIdClient() {
        return rClient.get ( );
    }

    public SimpleStringProperty rClientProperty() {
        return rClient;
    }

    public String getrIdProduct() {
        return rIdProduct.get ( );
    }

    public SimpleStringProperty rIdProductProperty() {
        return rIdProduct;
    }

    public String getrUser() {
        return rUser.get ( );
    }

    public SimpleStringProperty rUserProperty() {
        return rUser;
    }


}