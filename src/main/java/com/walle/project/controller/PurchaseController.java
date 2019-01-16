package com.walle.project.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.walle.project.entity.Purchase;
import com.walle.project.services.PurchaseServices;
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
public class PurchaseController  implements UrlReader{

    @Autowired
    private PurchaseServices purchaseServices;

    @GetMapping("/purchase")
    private ResponseEntity <List <Purchase>> displayPurchase() {
        List <Purchase> purchases = purchaseServices.getAll ( );
        return new ResponseEntity <> (purchases, HttpStatus.OK);
    }

    @GetMapping("/purchase/{id}")
    private ResponseEntity <Purchase> get(@PathVariable("id") Long id) {
        Purchase purchase = purchaseServices.getById (id);
        return new ResponseEntity <> (purchase, HttpStatus.OK);
    }

    @PostMapping("/purchase")
    private ResponseEntity <?> save(@RequestBody Purchase purchase) {
        purchaseServices.saveOrUpdate (purchase);
        return ResponseEntity.ok ( ).body ("Purchase  has been added successfully");
    }


    @DeleteMapping("/purchase/{id}")
    private ResponseEntity <?> delete(@PathVariable("id") Long id) {
        purchaseServices.deleteById (id);
        return ResponseEntity.ok ( ).body ("Purchase has been deleted successfully fuck.");
    }

    public List<Purchase> fetchList  () {
        List<Purchase> list = null;
        try {
            Gson gson = new Gson();
            list = new Gson().fromJson(readUrl("http://localhost:8080/purchase"), new TypeToken<List<Purchase>> () {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    };


    public Purchase getPurchase (Long id) {
        Purchase purchase = null;
        String url="http://localhost:8080/purchase/"+id;
        try {
            purchase = new Gson ( ).fromJson (readUrl (url), new TypeToken<Purchase> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return purchase;
    }

    public void addOrUpdate(Purchase purchase) {
        HttpHeaders headers = new HttpHeaders ( );
        headers.add ("Accept", MediaType.APPLICATION_JSON_VALUE);
        try {

            RestTemplate restTemplate = new RestTemplate ( );
            ResponseEntity <Purchase> result
                    = restTemplate.postForEntity ("http://localhost:8080/purchase", purchase, Purchase.class);
        } catch (Exception e) {
            e.getMessage ( );
            e.getLocalizedMessage ( );
        }
    }

    public void deletePurchase(Long id){
        try{
            HttpClient client = HttpClients.createDefault ( );
            HttpDelete delete = new HttpDelete("http://localhost:8080/purchase/"+id);
            HttpResponse response = client.execute(delete);
            if(response.getStatusLine().getStatusCode()== 202){
                Thread.sleep(3500);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }


}
