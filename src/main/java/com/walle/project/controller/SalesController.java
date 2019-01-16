package com.walle.project.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.walle.project.entity.Sales;
import com.walle.project.services.SalesServices;
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
public class SalesController implements UrlReader {
    @Autowired
    private SalesServices salesServices;

    @GetMapping("/sales")
    private ResponseEntity <List <Sales>> displaySales() {
        List <Sales> sales = salesServices.getAll ( );
        return new ResponseEntity <> (sales, HttpStatus.OK);
    }

    @GetMapping("/sales/{id}")
    private ResponseEntity <Sales> get(@PathVariable("id") Long id) {
        Sales sales = salesServices.getById (id);
        return new ResponseEntity <> (sales, HttpStatus.OK);
    }

    @PostMapping("/sales")
    private ResponseEntity <?> save(@RequestBody Sales sales) {
        salesServices.saveOrUpdate (sales);
        return ResponseEntity.ok ( ).body ("Sales  has been added successfully");
    }

    @DeleteMapping("/sales/{id}")
    private ResponseEntity <?> delete(@PathVariable("id") Long id) {
        salesServices.deleteById (id);
        return ResponseEntity.ok ( ).body ("Sales has been deleted successfully.");
    }

    public List<Sales> fetchList  () {
        List<Sales> list = null;
        try {
            Gson gson = new Gson();
            list = new Gson().fromJson(readUrl("http://localhost:8080/sales"), new TypeToken<List<Sales>> () {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    };


    public void addOrUpdate(Sales sales) {
        HttpHeaders headers = new HttpHeaders ( );
        headers.add ("Accept", MediaType.APPLICATION_JSON_VALUE);
        try {

            RestTemplate restTemplate = new RestTemplate ( );
            ResponseEntity <Sales> result
                    = restTemplate.postForEntity ("http://localhost:8080/sales", sales, Sales.class);
        } catch (Exception e) {
            e.getMessage ( );
            e.getLocalizedMessage ( );
        }
    }

    public void deleteSales(Long id){
        try{
            HttpClient client = HttpClients.createDefault ( );
            HttpDelete delete = new HttpDelete("http://localhost:8080/sales/"+id);
            HttpResponse response = client.execute(delete);
            if(response.getStatusLine().getStatusCode()== 202){
                Thread.sleep(3500);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Sales getSales (Long id) {
        Sales sales = null;
        String url="http://localhost:8080/sales/"+id;
        try {
            sales = new Gson ( ).fromJson (readUrl (url), new TypeToken<Sales> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return sales;
    }

}
