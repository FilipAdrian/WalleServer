package com.walle.project.UI.model;

import com.walle.project.entity.Manufacture;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class ManufactureTable {
    private final SimpleLongProperty rID;
    private final SimpleStringProperty rName;
    private final SimpleStringProperty rAddress;


    public ManufactureTable (Long sID, String sName, String sAddress){
        this.rID = new SimpleLongProperty(sID);
        this.rName = new SimpleStringProperty (sName);
        this.rAddress = new SimpleStringProperty (sAddress);
    }

    public void setrID (Integer rID)  { this.rID.set (rID); }
    public void setrName (String rName) {this.rName.set (rName);}
    public void setrAddress (String rAddress) {this.rAddress.set (rAddress);}


    public Long getrID() {return rID.get ( );}

    public SimpleLongProperty rIDProperty() {return rID;}

    public String getrName() {return rName.get ( );}

    public SimpleStringProperty rNameProperty() { return rName; }

    public String getrAddress() {return rAddress.get ( );}

    public SimpleStringProperty rAddressProperty() {return rAddress;}


    @Override
    public String toString() {
        return "ManufactureTable{" +
                "rID=" + rID +
                ", rName=" + rName +
                ", rAddress=" + rAddress +
                '}';
    }
}