package com.walle.project.UI.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.walle.project.server.entity.Type;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class TypeController implements UrlReader {
    public List<Type> fetchList() {
        List <Type> list = null;
        try {
            Gson gson = new Gson ( );
            list = new Gson ( ).fromJson (readUrl ("http://localhost:8080/type"), new TypeToken<List <Type>> ( ) {
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
            ResponseEntity<Type> result
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
