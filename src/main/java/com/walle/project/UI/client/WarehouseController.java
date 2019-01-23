package com.walle.project.UI.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.walle.project.server.entity.Warehouse;
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

public class WarehouseController implements UrlReader, RequestResponse {
    public List<Warehouse> fetchList() {
        List <Warehouse> list = null;
        try {
            Gson gson = new Gson ( );
            list = new Gson ( ).fromJson (readUrl ("http://localhost:8080/warehouse"), new TypeToken<List <Warehouse>> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return list;
    }

    public Warehouse getWarehouse(Long id) {
        Warehouse warehouse = null;
        String url = "http://localhost:8080/warehouse/" + id;
        try {
            warehouse = new Gson ( ).fromJson (readUrl (url), new TypeToken <Warehouse> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return warehouse;
    }

    public Integer addOrUpdate(Warehouse warehouse) {
        Integer responseCode = null;
        try {
            URL obj = new URL("http://localhost:8080/warehouse");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("User-Agent", USER_AGENT);

            // For POST only - START
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            Gson gson = new Gson ();
            os.write(gson.toJson (warehouse).getBytes ());
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

    public Integer deleteWarehouse(Long id) {
        Integer status = null;
        try {
            HttpClient client = HttpClients.createDefault ( );
            HttpDelete delete = new HttpDelete ("http://localhost:8080/warehouse/" + id);
            HttpResponse response = client.execute (delete);
            if (response.getStatusLine ( ).getStatusCode ( ) == 202) {
                Thread.sleep (3500);
            }
            status = response.getStatusLine ( ).getStatusCode ( );
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return  status;

    }

}
