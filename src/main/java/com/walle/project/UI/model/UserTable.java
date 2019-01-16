package com.walle.project.UI.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class UserTable {
    private final SimpleLongProperty rID;
    private final SimpleStringProperty rName;
    private final SimpleStringProperty rPhone;
    private final SimpleStringProperty rEmail;
    private final SimpleStringProperty rAddress;
    private final SimpleStringProperty rRole;



    public UserTable(Long sID, String sName, String sPhone
            , String sEmail, String sAddress, String sRole){
        this.rID = new SimpleLongProperty(sID);
        this.rName = new SimpleStringProperty(sName);
        this.rPhone = new SimpleStringProperty (sPhone);
        this.rEmail = new SimpleStringProperty (sEmail);
        this.rAddress = new SimpleStringProperty (sAddress);
        this.rRole = new SimpleStringProperty (sRole);

    }

    public void setrID(int rID) {
        this.rID.set (rID);
    }

    public void setrName(String rName) {
        this.rName.set (rName);
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

    public void setrRole(String rRole) {
        this.rRole.set (rRole);
    }

    public Long getrID() {
        return rID.get ( );
    }

    public SimpleLongProperty rIDProperty() {
        return rID;
    }

    public String getrName() {
        return rName.get ( );
    }

    public SimpleStringProperty rNameProperty() {
        return rName;
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

    public String getrRole() {
        return rRole.get ( );
    }

    public SimpleStringProperty rRoleProperty() {
        return rRole;
    }
}
