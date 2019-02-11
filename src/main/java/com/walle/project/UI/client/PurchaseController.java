package com.walle.project.UI.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.walle.project.server.entity.Purchase;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class PurchaseController implements UrlReader, RequestResponse {


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

    public Integer addOrUpdate(Purchase purchase) {
        Integer status = null;
        HttpHeaders headers = new HttpHeaders ( );
        headers.add ("Accept", MediaType.APPLICATION_JSON_VALUE);
        try {

            RestTemplate restTemplate = new RestTemplate ( );
            ResponseEntity<Purchase> result
                    = restTemplate.postForEntity ("http://localhost:8080/purchase", purchase, Purchase.class);
            status = result.getStatusCode().value ();
            if (result.getStatusCode().equals(HttpStatus.OK)) {
                System.out.println ("ok");
            }
        } catch (Exception e) {
            e.printStackTrace ();
            e.getMessage ( );
            e.getLocalizedMessage ( );
        }
        return status;
    }

    public Integer deletePurchase(Long id){
        Integer status = null;
        try{
            HttpClient client = HttpClients.createDefault ( );
            HttpDelete delete = new HttpDelete("http://localhost:8080/purchase/"+id);
            HttpResponse response = client.execute(delete);
            if(response.getStatusLine().getStatusCode()== 202){
                Thread.sleep(3500);
            }
            status = response.getStatusLine().getStatusCode();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return status;

    }

    public List<Double> getAmountPerYear(Integer year){
        List <Double> records = null;
        String url = "http://localhost:8080/purchase/year/"+year;
        Gson gson = new Gson ( );
        try {
            records = new Gson ( ).fromJson (readUrl (url), new TypeToken <List <Double>> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return records;
    }

}
