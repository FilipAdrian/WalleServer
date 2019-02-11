package com.walle.project.server.controller;

import com.walle.project.server.entity.Manufacture;
import com.walle.project.server.services.ManufactureServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ManufactureController  {
    @Autowired
    private ManufactureServices manufactureServices;

    @GetMapping("/manufacture")
    private ResponseEntity <List <Manufacture>> displayManufacture() {
        List <Manufacture> manufactures = manufactureServices.getAll ( );
        return new ResponseEntity <> (manufactures, HttpStatus.OK);
    }

    @GetMapping("/manufacture/{id}")
    private ResponseEntity <Manufacture> get(@PathVariable("id") Long id) {
        Manufacture manufacture = manufactureServices.getById (id);
        return new ResponseEntity <> (manufacture, HttpStatus.OK);
    }

    @PostMapping("/manufacture")
    private ResponseEntity <?> save(@RequestBody Manufacture manufacture) {
        manufactureServices.saveOrUpdate (manufacture);
        return new ResponseEntity <> (manufacture, HttpStatus.OK);
    }

    @DeleteMapping("manufacture/{id}")
    private ResponseEntity <?> delete(@PathVariable("id") Long id) {
        manufactureServices.deleteByID (id);
        return ResponseEntity.ok ( ).body ("Manufacture has been deleted successfully.");
    }

}

