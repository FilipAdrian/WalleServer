package com.walle.project.UI.client;

import com.walle.project.UI.tables.*;
import com.walle.project.server.entity.*;

import java.util.*;

public class Mapper {
    private static ManufactureController manufactureController = new ManufactureController ( );
    private static ProductController productController = new ProductController ( );
    private static WarehouseController warehouseController = new WarehouseController ( );
    private static TypeController typeController = new TypeController ( );
    private static RoleController roleController = new RoleController ( );
    private static ClientController clientController = new ClientController ( );
    private static UserController userController = new UserController ( );

    public static Manufacture transformIntoManufactureObject(ManufactureTable manufactureTable) {
        Manufacture manufacture = manufactureController.getManufacture (manufactureTable.getrID ( ));
        manufacture.setName (manufactureTable.getrName ( ));
        manufacture.setAddress (manufactureTable.getrAddress ( ));
        manufactureController = null;
        return manufacture;
    }

    public static Warehouse transformIntoWarehouseObject(WarehouseTable warehouseTable) {
        Warehouse warehouse = warehouseController.getWarehouse (warehouseTable.getrID ( ));
        warehouse.setName (warehouseTable.getrName ( ));
        warehouse.setAddress (warehouseTable.getrAddress ( ));
        warehouseController = null;
        return warehouse;
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

    public static Users transformtIntoUserObject(UserTable userTable){
        Users user = userController.getUser (userTable.getrID ());
        List<Role> roleList = roleController.fetchList ();
        Role role = new Role ();
        for (int i = 0; i <roleList.size () ; i++) {
            if (roleList.get (i).getName ().contains (userTable.getrRole ())){
                role = roleList.get (i);
            }
        }
        user.setName (splitBySpace (userTable.getrName ()).get (0));
        user.setSurname (splitBySpace (userTable.getrName ()).get (1));
        user.setAddress (userTable.getrAddress ());
        user.setEmail (userTable.getrEmail ());
        user.setPhone (userTable.getrPhone ());
        user.setRole (role);
        return user;

    }

    public static ArrayList <String> splitBySpace(String nameSurname) {
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

    public static List<String> productQuantity(){
        SalesController salesController = new SalesController ( );
        ProductController productController = new ProductController ( );
        List <Product> productList = productController.fetchList ( );
        List <Sales> salesList = salesController.fetchList ( );
        HashMap <Integer, String> productQuantity = new HashMap <> ( );
        List <String> prQuSort = new ArrayList <> ( );
        Integer quantity = 0;
        for (int i = 0; i < productList.size ( ); i++) {
            for (int j = 0; j < salesList.size ( ); j++) {
                if (productList.get (i).getId ( ).equals (salesList.get (j).getProduct ( ).getId ( ))) {
                    quantity += salesList.get (j).getQuantity ( );
                }
            }
            productQuantity.put (quantity, productList.get (i).getName ());
            quantity = 0;
        }
        productQuantity = sortHashMapByValues (productQuantity);
        System.out.println (productQuantity);
        List <Integer> sortList = new ArrayList <> ( );
        sortList.addAll (productQuantity.keySet ( ));

        Collections.sort (sortList);
        Collections.reverse (sortList);
        System.out.println (sortList.get (0));
        for (int i = 0; i < 5; i++) {
            Integer j = sortList.get (i);
            prQuSort.add (j.toString ( ));
            prQuSort.add (productQuantity.get (j));
        }
        return prQuSort;
    }

    public static LinkedHashMap<Integer, String> sortHashMapByValues(
            HashMap <Integer, String> passedMap) {
        List <Integer> mapKeys = new ArrayList <> (passedMap.keySet ( ));
        List <String> mapValues = new ArrayList <> (passedMap.values ( ));
        Collections.sort (mapValues);
        Collections.sort (mapKeys);

        LinkedHashMap <Integer, String> sortedMap =
                new LinkedHashMap <> ( );

        Iterator <String> valueIt = mapValues.iterator ( );
        while (valueIt.hasNext ( )) {
            String val = valueIt.next ( );
            Iterator <Integer> keyIt = mapKeys.iterator ( );

            while (keyIt.hasNext ( )) {
                Integer key = keyIt.next ( );
                String comp1 = passedMap.get (key);
                String comp2 = val;

                if (comp1.equals (comp2)) {
                    keyIt.remove ( );
                    sortedMap.put (key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }


}
