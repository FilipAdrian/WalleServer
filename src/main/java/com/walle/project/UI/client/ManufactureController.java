package com.walle.project.UI.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.walle.project.server.entity.Manufacture;
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

public class ManufactureController implements UrlReader, RequestResponse {

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
            responseCode = checkResponse (con);

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
