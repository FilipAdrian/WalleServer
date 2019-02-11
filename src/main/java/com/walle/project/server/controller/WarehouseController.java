package com.walle.project.server.controller;

import com.walle.project.server.entity.Warehouse;
import com.walle.project.server.services.WarehouseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WarehouseController  {
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


}
