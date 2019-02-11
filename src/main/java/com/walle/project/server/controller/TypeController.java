package com.walle.project.server.controller;

import com.walle.project.server.entity.Type;
import com.walle.project.server.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TypeController  {
    @Autowired
    private TypeService typeService;

    @GetMapping("/type")
    private ResponseEntity <List <Type>> diaplayType() {
        List <Type> types = typeService.getAll ( );
        return new ResponseEntity <> (types, HttpStatus.OK);
    }

    @GetMapping("/type/{id}")
    private ResponseEntity <Type> get(@PathVariable("id") Long id) {
        Type type = typeService.getById (id);
        return new ResponseEntity <> (type, HttpStatus.OK);
    }

    @PostMapping("/type")
    private ResponseEntity <?> save(@RequestBody Type type) {
        typeService.saveOrUpdate (type);
        return ResponseEntity.ok ( ).body ("Type  has been added successfully");
    }

    @DeleteMapping("/type/{id}")
    private ResponseEntity <?> delete(@PathVariable("id") Long id) {
        typeService.deleteById (id);
        return ResponseEntity.ok ( ).body ("Type has been deleted successfully.");
    }

}
