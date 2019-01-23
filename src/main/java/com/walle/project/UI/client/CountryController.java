package com.walle.project.UI.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.walle.project.server.entity.Country;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static org.apache.http.protocol.HTTP.USER_AGENT;

public class CountryController implements UrlReader, RequestResponse {

    public Integer addOrUpdate(Country country){
        Integer responseCode = null;
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

            responseCode = checkResponse (con);

        }
        catch (IOException e){
            e.getMessage ();
            e.printStackTrace ();
        }
        return  responseCode;
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
