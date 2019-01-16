package com.walle.project.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.walle.project.entity.Country;
import com.walle.project.services.CountryServices;
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
public class CountryController implements UrlReader {
    @Autowired
    private CountryServices countryServices;

    @GetMapping("/country")
    private ResponseEntity <List <Country>> displayCountry() {
        List <Country> countries = countryServices.getAll ( );
        return new ResponseEntity <> (countries, HttpStatus.OK);
    }

    @GetMapping("/country/{id}")
    private ResponseEntity <Country> get(@PathVariable("id") Long id) {
        Country country = countryServices.getById (id);
        return new ResponseEntity <> (country, HttpStatus.OK);
    }


    @DeleteMapping("/country/{id}")
    private ResponseEntity <?> delete(@PathVariable("id") Long id) {
        countryServices.deleteById (id);
        return ResponseEntity.ok ( ).body ("Country has been deleted successfully.");

    }

    @PostMapping(value = "/country",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    private ResponseEntity <?> save(@RequestBody Country country) {
        countryServices.saveOrUpdate (country);
        return ResponseEntity.ok ( ).body ("Country  " + country.getName ( ) + "has been added");
    }

    public void addOrUpdate(Country country){
        HttpHeaders headers = new HttpHeaders ();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        try {

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Country> result
                    = restTemplate.postForEntity("http://localhost:8080/country",country, Country.class);
            System.out.println ("code of result is"+ result.getStatusCode () );
        }
        catch (Exception e){
            e.getMessage ();
            e.getLocalizedMessage ();
        }
    }

    public void deleteCountry(Long id){
        try{
            HttpClient client = HttpClients.createDefault ( );
            HttpDelete delete = new HttpDelete("http://localhost:8080/country/"+id);
            HttpResponse response = client.execute(delete);
            if(response.getStatusLine().getStatusCode()== 202){
                Thread.sleep(3500);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Country getCountry (Long id) {
        Country country = null;
        String url="http://localhost:8080/country/"+id;
        try {
            country = new Gson ( ).fromJson (readUrl (url), new TypeToken<Country> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return country;
    }

    public List<Country> fetchList  () {
        List<Country> list = null;
        try {
            Gson gson = new Gson();
            list = new Gson().fromJson(readUrl("http://localhost:8080/country"), new TypeToken<List<Country>> () {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
