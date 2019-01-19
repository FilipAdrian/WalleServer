package com.walle.project.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.walle.project.entity.Type;
import com.walle.project.services.TypeService;
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
public class TypeController implements UrlReader{
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
    public List <Type> fetchList() {
        List <Type> list = null;
        try {
            Gson gson = new Gson ( );
            list = new Gson ( ).fromJson (readUrl ("http://localhost:8080/type"), new TypeToken <List <Type>> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return list;

    }
    public Type getType (Long id) {
        Type type = null;
        String url="http://localhost:8080/type/"+id;
        try {
            Gson gson = new Gson ( );
            type = new Gson ( ).fromJson (readUrl (url), new TypeToken<Type> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return type;
    }

    public void addOrUpdate(Type type) {
        HttpHeaders headers = new HttpHeaders ( );
        headers.add ("Accept", MediaType.APPLICATION_JSON_VALUE);
        try {

            RestTemplate restTemplate = new RestTemplate ( );
            ResponseEntity <Type> result
                    = restTemplate.postForEntity ("http://localhost:8080/type", type, Type.class);
        } catch (Exception e) {
            e.getMessage ( );
            e.getLocalizedMessage ( );
        }
    }

    public void deleteType(Long id){
        try{
            HttpClient client = HttpClients.createDefault ( );
            HttpDelete delete = new HttpDelete("http://localhost:8080/type/"+id);
            HttpResponse response = client.execute(delete);
            if(response.getStatusLine().getStatusCode()== 202){
                Thread.sleep(3500);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
