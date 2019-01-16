package com.walle.project.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.walle.project.entity.Product;
import com.walle.project.services.ProductServices;
import javafx.concurrent.Task;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

@RestController
public class ProductController implements UrlReader {
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

    @PostMapping("/products")
    private ResponseEntity <?> save(@RequestBody Product product) {
        productServices.saveOrUpdate (product);
        return ResponseEntity.ok ( ).body ("Product  " + product.getName ( ) + " has been added");
    }

    @PutMapping("/products")
    private ResponseEntity <?> update(@RequestBody Product product) {
        productServices.saveOrUpdate (product);
        return ResponseEntity.ok ( ).body ("Product has been updated successfully.");
    }

    @DeleteMapping("/products/{id}")
    private ResponseEntity <?> delete(@PathVariable("id") String id) {
        productServices.deleteById (id);
        return ResponseEntity.ok ( ).body ("Product has been deleted successfully.");
    }


    public List <Product> fetchList() {
        List <Product> list = null;
        try {
            Gson gson = new Gson ( );
            list = new Gson ( ).fromJson (readUrl ("http://localhost:8080/products"), new TypeToken <List <Product>> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return list;

    }

    ;

    public Product getProduct(String id) {
        Product product = null;
        String url = "http://localhost:8080/products/" + id;
        try {
            product = new Gson ( ).fromJson (readUrl (url), new TypeToken <Product> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return product;
    }

    public Integer addOrUpdate(Product product) {
        Integer status = new Integer (0);
        HttpHeaders headers = new HttpHeaders ( );
        headers.add ("Accept", MediaType.APPLICATION_JSON_VALUE);
        try {

            RestTemplate restTemplate = new RestTemplate ( );
            ResponseEntity <Product> result
                    = restTemplate.postForEntity ("http://localhost:8080/products", product, Product.class);
            status = result.getStatusCode ().value ();
        } catch (Exception e) {
            e.getMessage ( );
            e.getLocalizedMessage ( );
        }
        System.out.println (status );
        return status;
    }

    public Integer deleteProduct(String id) {
        Integer status = new Integer (0);
        try {
            HttpClient client = HttpClients.createDefault ( );
            HttpDelete delete = new HttpDelete ("http://localhost:8080/products/" + id);
            HttpResponse response = client.execute (delete);
            if (response.getStatusLine ( ).getStatusCode ( ) == 202) {
                Thread.sleep (3500);
            }
            status = response.getStatusLine ( ).getStatusCode ();

        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return status;
    }

}
