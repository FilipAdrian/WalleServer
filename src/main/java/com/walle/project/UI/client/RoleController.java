package com.walle.project.UI.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.walle.project.server.entity.Role;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RoleController implements UrlReader {

    public List<Role> fetchList() {
        List <Role> list = null;
        try {
            Gson gson = new Gson ( );
            list = new Gson ( ).fromJson (readUrl ("http://localhost:8080/role"), new TypeToken<List <Role>> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return list;

    }

    public Role getRole (Long id) {
        Role role = null;
        String url="http://localhost:8080/role/"+id;
        try {
            Gson gson = new Gson ( );
            role = new Gson ( ).fromJson (readUrl (url), new TypeToken<Role> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return role;
    }


    public void addOrUpdate(Role role) {
        HttpHeaders headers = new HttpHeaders ( );
        headers.add ("Accept", MediaType.APPLICATION_JSON_VALUE);
        try {

            RestTemplate restTemplate = new RestTemplate ( );
            ResponseEntity<Role> result
                    = restTemplate.postForEntity ("http://localhost:8080/role", role, Role.class);
        } catch (Exception e) {
            e.getMessage ( );
            e.getLocalizedMessage ( );
        }
    }

    public void deleteRole(Long id){
        try{
            HttpClient client = HttpClients.createDefault ( );
            HttpDelete delete = new HttpDelete("http://localhost:8080/role/"+id);
            HttpResponse response = client.execute(delete);
            if(response.getStatusLine().getStatusCode()== 202){
                Thread.sleep(3500);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
