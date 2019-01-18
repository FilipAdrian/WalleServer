package com.walle.project.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.walle.project.entity.Manufacture;
import com.walle.project.services.ManufactureServices;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.HttpClients;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static org.apache.http.protocol.HTTP.USER_AGENT;

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
//        return ResponseEntity.ok ( ).body ("Manufacture  " + manufacture.getName ( ) + " has been added");
        return new ResponseEntity <> (manufacture, HttpStatus.OK);
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

    public Integer addOrUpdate(Manufacture manufacture) {
        Integer responseCode = null;
        try {
            URL obj = new URL("http://localhost:8080/manufacture");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("User-Agent", USER_AGENT);

            // For POST only - START
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            Gson gson = new Gson ();
            os.write(gson.toJson (manufacture).getBytes ());
            os.flush();
            os.close();
            // For POST only - END

            responseCode = con.getResponseCode();
            System.out.println (    con.getResponseMessage () );
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
                JSONObject myResponse = new JSONObject (response.toString ());
                System.out.println ("Id -ul :"+myResponse.getInt ("id"));
                // print result
            } else {
                System.out.println("POST request not worked");
            }

        }
        catch (IOException e){
            e.getMessage ();
            e.printStackTrace ();
        }
        return  responseCode;
    }

    public Integer deleteManufacture(Long id){
        Integer status = new Integer (0);
        try{
            HttpClient client = HttpClients.createDefault ( );
            HttpDelete delete = new HttpDelete("http://localhost:8080/manufacture/"+id);
            HttpResponse response = client.execute(delete);
            if(response.getStatusLine().getStatusCode()== 202){
                Thread.sleep(3500);
            }
            status = response.getStatusLine ().getStatusCode ();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

}

