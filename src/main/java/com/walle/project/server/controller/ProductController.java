package com.walle.project.server.controller;


import com.walle.project.server.entity.Product;
import com.walle.project.server.services.ProductServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProductController {
    @Autowired
    private ProductServices productServices;

    @GetMapping("/products")
    private ResponseEntity <List <Product>> displayProduct() {
        List <Product> products = productServices.getAll ( );
        return new ResponseEntity <> (products, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    private ResponseEntity <Product> get(@PathVariable("id") String id) {
        Product product = productServices.getById (id);
        return new ResponseEntity <> (product, HttpStatus.OK);
    }

    @PostMapping(value = "/products")
    private ResponseEntity <?> save(@RequestBody Product product) {
        productServices.saveOrUpdate (product);
        return new ResponseEntity <> (product,HttpStatus.OK);    }


    @DeleteMapping("/products/{id}")
    private ResponseEntity <?> delete(@PathVariable("id") String id) {
        productServices.deleteById (id);
        return ResponseEntity.ok ( ).body ("Product has been deleted successfully.");
    }


}
