package com.walle.project.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.walle.project.entity.Manufacture;
import com.walle.project.services.ManufactureServices;
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
public class ManufactureController implements UrlReader {
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
        return ResponseEntity.ok ( ).body ("Manufacture  " + manufacture.getName ( ) + " has been added");

    }

    @DeleteMapping("manufacture/{id}")
    private ResponseEntity <?> delete(@PathVariable("id") Long id) {
        manufactureServices.deleteByID (id);
        return ResponseEntity.ok ( ).body ("Manufacture has been deleted successfully.");
    }

    public List<Manufacture> fetchList  () {
        List<Manufacture> list = null;
        try {
            Gson gson = new Gson();
            list = new Gson().fromJson(readUrl("http://localhost:8080/manufacture"), new TypeToken<List<Manufacture>> () {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Manufacture getManufacture (Long id) {
        Manufacture manufacture = null;
        String url="http://localhost:8080/manufacture/"+id;
        try {
            manufacture = new Gson ( ).fromJson (readUrl (url), new TypeToken<Manufacture> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return manufacture;
    }

    public void addOrUpdate(Manufacture manufacture) {
        HttpHeaders headers = new HttpHeaders ( );
        headers.add ("Accept", MediaType.APPLICATION_JSON_VALUE);
        try {

            RestTemplate restTemplate = new RestTemplate ( );
            ResponseEntity <Manufacture> result
                    = restTemplate.postForEntity ("http://localhost:8080/manufacture", manufacture, Manufacture.class);
        } catch (Exception e) {
            e.getMessage ( );
            e.getLocalizedMessage ( );
        }
    }

    public void deleteManufacture(Long id){
        try{
            HttpClient client = HttpClients.createDefault ( );
            HttpDelete delete = new HttpDelete("http://localhost:8080/manufacture/"+id);
            HttpResponse response = client.execute(delete);
            if(response.getStatusLine().getStatusCode()== 202){
                Thread.sleep(3500);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}

