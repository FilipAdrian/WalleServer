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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static org.apache.http.protocol.HTTP.USER_AGENT;

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

    @PostMapping("/country")
    private ResponseEntity <?> save(@RequestBody Country country) {
        countryServices.saveOrUpdate (country);
        return ResponseEntity.ok ( ).body ("Country  " + country.getName ( ) + "has been added");
    }

    public void addOrUpdate(Country country){
        try {
            URL obj = new URL("http://localhost:8080/country");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("User-Agent", USER_AGENT);

            // For POST only - START
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            Gson gson = new Gson ();
            os.write(gson.toJson (country).getBytes ());
            os.flush();
            os.close();
            // For POST only - END

            int responseCode = con.getResponseCode();
            System.out.println (    con.getContent ().getClass ().getName ());
            System.out.println("POST Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader (
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                System.out.println(response.toString());
            } else {
                System.out.println("POST request not worked");
            }

        }
        catch (IOException e){
            e.getMessage ();
            e.printStackTrace ();
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
