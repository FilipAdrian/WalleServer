package com.walle.project.UI.sample;

import com.walle.project.UI.model.ClientTable;
import com.walle.project.UI.model.ManufactureTable;
import com.walle.project.UI.model.ProductTable;
import com.walle.project.controller.*;
import com.walle.project.entity.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Mapper {
    private static ManufactureController manufactureController = new ManufactureController ( );
    private static ProductController productController = new ProductController ( );
    private static WarehouseController warehouseController = new WarehouseController ( );
    private static CountryController countryController = new CountryController ( );
    private static TypeController typeController = new TypeController ( );
    private static RoleController roleController = new RoleController ( );
    private static ClientController clientController = new ClientController ( );

    public static Manufacture transformIntoManufactureObject(ManufactureTable manufactureTable) {
        Manufacture manufacture = manufactureController.getManufacture (manufactureTable.getrID ( ));
        manufacture.setName (manufactureTable.getrName ( ));
        manufacture.setAddress (manufactureTable.getrAddress ( ));
        manufactureController = null;
        return manufacture;
    }

    public static Product transIntoProductObject(ProductTable productTable) {
        Product product = productController.getProduct (productTable.getrID ( ));
        List <Warehouse> warehousesLis = warehouseController.fetchList ( );
        List <Manufacture> manufactureList = manufactureController.fetchList ( );
        Warehouse warehouse = new Warehouse ( );
        Manufacture manufacture = new Manufacture ( );
        for (int i = 0; i < manufactureList.size ( ); i++) {
            if (manufactureList.get (i).getName ( ).contains (productTable.getrManufacture ( ))) {
                manufacture = manufactureList.get (i);
            }
        }
        for (int i = 0; i < warehousesLis.size ( ); i++) {
            if (warehousesLis.get (i).getName ( ).contains (productTable.getrWarehouse ( ))) {
                warehouse = warehousesLis.get (i);
            }
        }
        product.setName (productTable.getrName ( ));
        product.setManufacture (manufacture);
        product.setWarehouse (warehouse);
        try {
            product.setQuantiy (Integer.parseInt (productTable.getrPrice ( )));
            Double price = Double.parseDouble (productTable.getrPrice ( ));
            product.setPrice (price);
        } catch (NumberFormatException e) {
            e.getLocalizedMessage ( );
            e.getMessage ( );
        }

        return product;

    }

    public static Client transformIntoClientObject(ClientTable clientTable) {
        Client client = clientController.getClient (clientTable.getrID ( ));
        List <Type> typeList = typeController.fetchList ( );
        Type type = new Type ( );
        for (int i = 0; i < typeList.size ( ); i++) {
            if (typeList.get (i).getName ( ).contains (clientTable.getrType ( ))) {
                type = typeList.get (i);
            }
        }
        client.setName (splitBySpace (clientTable.getrClient ()).get (0));
        client.setSurname (splitBySpace (clientTable.getrClient ()).get (1));
        client.setAddress (clientTable.getrAddress ());
        client.setEmail (clientTable.getrEmail ());
        client.setPhone (clientTable.getrPhone ());
        client.setType (type);

        return client;
    }

    private static ArrayList <String> splitBySpace(String nameSurname) {
        String[] split = nameSurname.split (" ");
        ArrayList <String> arrayList = new ArrayList <> ( );
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals ("")) {
                i = i + 1;
            } else {
                arrayList.add (split[i]);
            }
        }
        return arrayList;
    }


}
