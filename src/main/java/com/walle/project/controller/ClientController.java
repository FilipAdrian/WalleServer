package com.walle.project.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.walle.project.entity.Client;
import com.walle.project.entity.Product;
import com.walle.project.services.ClientServices;
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
public class ClientController implements UrlReader {
    @Autowired
    private ClientServices clientServices;

    @GetMapping("/clients")
    private ResponseEntity <List <Client>> displayClients() {
        List <Client> clients = clientServices.getAll ( );
        return new ResponseEntity <> (clients, HttpStatus.OK);
    }

    @GetMapping("clients/{id}")
    private ResponseEntity <Client> get(@PathVariable("id") Long id) {
        Client client = clientServices.getById (id);
        return new ResponseEntity <> (client, HttpStatus.OK);
    }

    @PostMapping(value = "/clients", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    private ResponseEntity <?> save(@RequestBody Client client) {
        clientServices.saveOrUpdate (client);
        return ResponseEntity.ok ( ).body ("Client  " + client.getName ( ) + " has been added");
    }


    @DeleteMapping("/clients/{id}")
    private ResponseEntity <?> delete(@PathVariable("id") Long id) {
        clientServices.deleteById (id);
        return ResponseEntity.ok ( ).body ("Client has been deleted successfully.");

    }

    public List <Client> fetchList() {
        List <Client> list = null;
        try {
            Gson gson = new Gson ( );
            list = new Gson ( ).fromJson (readUrl ("http://localhost:8080/clients"), new TypeToken <List <Client>> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return list;

    }

    ;

    public void addOrUpdate(Client client) {
        HttpHeaders headers = new HttpHeaders ( );
        headers.add ("Accept", MediaType.APPLICATION_JSON_VALUE);
        try {

            RestTemplate restTemplate = new RestTemplate ( );
            ResponseEntity <Client> result
                    = restTemplate.postForEntity ("http://localhost:8080/clients", client, Client.class);
            System.out.println ("code of result is" + result.getStatusCode ( ));
        } catch (Exception e) {
            e.getMessage ( );
            e.getLocalizedMessage ( );
        }
    }

    public void deleteCountry(Long id) {
        try {
            HttpClient client = HttpClients.createDefault ( );
            HttpDelete delete = new HttpDelete ("http://localhost:8080/clients/" + id);
            HttpResponse response = client.execute (delete);
            if (response.getStatusLine ( ).getStatusCode ( ) == 202) {
                Thread.sleep (3500);
            }
        } catch (Exception e) {
            e.printStackTrace ( );
        }

    }

    public Client getClient(Long id) {
        Client client = null;
        String url = "http://localhost:8080/clients/" + id;
        try {
            Gson gson = new Gson ( );
            client = new Gson ( ).fromJson (readUrl (url), new TypeToken <Client> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return client;
    }

}

