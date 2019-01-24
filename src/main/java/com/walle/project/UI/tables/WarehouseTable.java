package com.walle.project.UI.tables;


import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class WarehouseTable {
    private final SimpleLongProperty rID;
    private final SimpleStringProperty rName;
    private final SimpleStringProperty rAddress;
    private final SimpleStringProperty rPhone;


    public WarehouseTable (Long sID, String sName, String sAddress, String sPhone){
        this.rID = new SimpleLongProperty(sID);
        this.rName = new SimpleStringProperty (sName);
        this.rAddress = new SimpleStringProperty (sAddress);
        this.rPhone = new SimpleStringProperty (sPhone);
    }

    public void setrID (Long rID)  { this.rID.set (rID); }
    public void setrName (String rName) {this.rName.set (rName);}
    public void setrAddress (String rAddress) {this.rAddress.set (rAddress);}
    public void setrPhone (String rPhone) {this.rPhone.set (rPhone);}


    public Long getrID() {return rID.get ( );}

    public SimpleLongProperty rIDProperty() {return rID;}

    public String getrName() {return rName.get ( );}

    public SimpleStringProperty rNameProperty() { return rName; }

    public String getrAddress() {return rAddress.get ( );}

    public SimpleStringProperty rAddressProperty() {return rAddress;}

    public String getrPhone() {return rPhone.get ( );}

    public SimpleStringProperty rPhoneProperty() {return rPhone;}


}