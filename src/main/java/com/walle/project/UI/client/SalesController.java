package com.walle.project.UI.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.walle.project.server.entity.Sales;
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

public class SalesController implements UrlReader, RequestResponse {

    public List<Sales> fetchList() {
        List <Sales> list = null;
        try {
            Gson gson = new Gson ( );
            list = new Gson ( ).fromJson (readUrl ("http://localhost:8080/sales"), new TypeToken<List <Sales>> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return list;

    }

    ;


    public Integer addOrUpdate(Sales sales) {
        HttpHeaders headers = new HttpHeaders ( );
        Integer status = null;
        headers.add ("Accept", MediaType.APPLICATION_JSON_VALUE);
        try {

            RestTemplate restTemplate = new RestTemplate ( );
            ResponseEntity<Sales> result
                    = restTemplate.postForEntity ("http://localhost:8080/sales", sales, Sales.class);
            status = result.getStatusCode ( ).value ( );
            if (result.getStatusCode ( ).equals (HttpStatus.OK)) {
                System.out.println ("ok");
            }
        } catch (Exception e) {
            e.getMessage ( );
            e.getLocalizedMessage ( );
        }
        return status;
    }

    public Integer deleteSales(Long id) {
        Integer status = null;
        try {
            HttpClient client = HttpClients.createDefault ( );
            HttpDelete delete = new HttpDelete ("http://localhost:8080/sales/" + id);
            HttpResponse response = client.execute (delete);
            if (response.getStatusLine ( ).getStatusCode ( ) == 202) {
                Thread.sleep (3500);
            }
            status = response.getStatusLine ( ).getStatusCode ( );
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return status;
    }

    public Sales getSales(Long id) {
        Sales sales = null;
        String url = "http://localhost:8080/sales/" + id;
        try {
            sales = new Gson ( ).fromJson (readUrl (url), new TypeToken <Sales> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return sales;
    }

    public List <Double> getAmountPerMonthOnYear(Integer year) {
        List <Double> records = null;
        String url = "http://localhost:8080/sales/year/" + year ;
        Gson gson = new Gson ( );
        try {
            records = new Gson ( ).fromJson (readUrl (url), new TypeToken <List <Double>> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return records;

    }

    public List<Double> getByUserID(Long id){
        List <Double> records = null;
        String url = "http://localhost:8080/sales//user/" + id ;
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
