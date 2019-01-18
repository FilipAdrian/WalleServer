package com.walle.project.UI.sample;

import com.walle.project.UI.model.ManufactureTable;
import com.walle.project.controller.ManufactureController;
import com.walle.project.entity.Manufacture;

public class Mapper {
    private ManufactureController manufactureController = new ManufactureController ();
    public   Manufacture transformIntoManufactureObject  (ManufactureTable manufactureTable){
        Manufacture manufacture = manufactureController.getManufacture (manufactureTable.getrID ());
        manufacture.setName (manufactureTable.getrName ());
        manufacture.setAddress (manufactureTable.getrAddress ());
        manufactureController = null;
        return manufacture;
    }

}
