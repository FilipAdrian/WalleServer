package com.walle.project.UI.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;


public class ClientTable {

    private final SimpleLongProperty rID;
    private final SimpleStringProperty rClient;
    private final SimpleStringProperty rPhone;
    private final SimpleStringProperty rEmail;
    private final SimpleStringProperty rAddress;
    private final SimpleStringProperty rType;



    public ClientTable(Long sID, String sClient, String sPhone
            , String sEmail, String sAddress, String sType){

        this.rID = new SimpleLongProperty(sID);
        this.rClient = new SimpleStringProperty(sClient);
        this.rPhone = new SimpleStringProperty (sPhone);
        this.rEmail = new SimpleStringProperty (sEmail);
        this.rAddress = new SimpleStringProperty (sAddress);
        this.rType = new SimpleStringProperty (sType);

    }

    public void setrID(Integer rID) {
        this.rID.set (rID);
    }

    public void setrName(String rClient) {
        this.rClient.set (rClient);
    }

    public void setrPhone(String rPhone) {
        this.rPhone.set (rPhone);
    }

    public void setrEmail(String rEmail) {
        this.rEmail.set (rEmail);
    }


    public void setrAddress(String rAddress) {
        this.rAddress.set (rAddress);
    }

    public void setrType(String rType) {
        this.rType.set (rType);
    }

    public Long getrID() {
        return rID.get ( );
    }

    public SimpleLongProperty rIDProperty() {
        return rID;
    }

    public String getrClient() {
        return rClient.get ( );
    }

    public SimpleStringProperty rClientProperty() {
        return rClient;
    }

    public String getrPhone() {
        return rPhone.get ( );
    }

    public SimpleStringProperty rPhoneProperty() {
        return rPhone;
    }

    public String getrEmail() {
        return rEmail.get ( );
    }

    public SimpleStringProperty rEmailProperty() {
        return rEmail;
    }

    public String getrAddress() {
        return rAddress.get ( );
    }

    public SimpleStringProperty rAddressProperty() {
        return rAddress;
    }

    public String getrType() {
        return rType.get ( );
    }

    public SimpleStringProperty rTypeProperty() {
        return rType;
    }

    public void setrClient(String rClient) {
        this.rClient.set (rClient);
    }
}