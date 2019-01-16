package com.walle.project.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.walle.project.entity.Warehouse;
import com.walle.project.services.WarehouseServices;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class WarehouseController implements UrlReader {
    @Autowired
    private WarehouseServices warehouseServices;

    @GetMapping("/warehouse")
    private ResponseEntity <List <Warehouse>> displayWarehouse() {
        List <Warehouse> warehouses = warehouseServices.getAll ( );
        return new ResponseEntity <> (warehouses, HttpStatus.OK);
    }

    @GetMapping("warehouse/{id}")
    private ResponseEntity <Warehouse> get(@PathVariable("id") Long id) {
        Warehouse warehouse = warehouseServices.getById (id);
        return new ResponseEntity <> (warehouse, HttpStatus.OK);
    }

    @PostMapping("/warehouse")
    private ResponseEntity <?> save(@RequestBody Warehouse warehouse) {
        warehouseServices.saveOrUpdate (warehouse);
        return ResponseEntity.ok ( ).body ("Warehouse  has been added successfully");
    }

    @PutMapping("/warehouse")
    private ResponseEntity <?> update(@RequestBody Warehouse warehouse) {
        warehouseServices.saveOrUpdate (warehouse);
        return ResponseEntity.ok ( ).body ("Warehouse has been updated successfully");
    }

    @DeleteMapping("/warehouse/{id}")
    private ResponseEntity <?> delete(@PathVariable("id") Long id) {
        warehouseServices.deleteById (id);
        return ResponseEntity.ok ( ).body ("Type has been deleted successfully.");
    }

    public List <Warehouse> fetchList() {
        List <Warehouse> list = null;
        try {
            Gson gson = new Gson ( );
            list = new Gson ( ).fromJson (readUrl ("http://localhost:8080/warehouse"), new TypeToken <List <Warehouse>> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return list;
    }

    public Warehouse getWarehouse(Long id) {
        Warehouse warehouse = null;
        String url = "http://localhost:8080/warehouse/" + id;
        try {
            warehouse = new Gson ( ).fromJson (readUrl (url), new TypeToken <Warehouse> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return warehouse;
    }

    public void addOrUpdate(Warehouse warehouse) {
        HttpHeaders headers = new HttpHeaders ( );
        headers.add ("Accept", MediaType.APPLICATION_JSON_VALUE);
        try {

            RestTemplate restTemplate = new RestTemplate ( );
            ResponseEntity <Warehouse> result
                    = restTemplate.postForEntity ("http://localhost:8080/warehouse", warehouse, Warehouse.class);
        } catch (Exception e) {
            e.getMessage ( );
            e.getLocalizedMessage ( );
        }
    }

    public void deleteWarehouse(Long id) {
        try {
            HttpClient client = HttpClients.createDefault ( );
            HttpDelete delete = new HttpDelete ("http://localhost:8080/warehouse/" + id);
            HttpResponse response = client.execute (delete);
            if (response.getStatusLine ( ).getStatusCode ( ) == 202) {
                Thread.sleep (3500);
            }
        } catch (Exception e) {
            e.printStackTrace ( );
        }

    }

}
