package com.walle.project.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.walle.project.entity.Role;
import com.walle.project.services.RoleServices;
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
public class RoleController implements UrlReader{
    @Autowired
    private RoleServices roleServices;
    @GetMapping("/role")
    private ResponseEntity<List<Role>> dislayRole(){
        List<Role> roles = roleServices.getAll ();
        return new ResponseEntity <> (roles, HttpStatus.OK);
    }
    @GetMapping("/role/{id}")
    private ResponseEntity<Role> get(@PathVariable("id") Long id){
        Role role = roleServices.getById (id);
        return  new ResponseEntity <> (role,HttpStatus.OK);
    }
    @PostMapping("/role")
    private ResponseEntity<?> save(@RequestBody Role role){
        roleServices.saveOrUpdate (role);
        return ResponseEntity.ok ( ).body ("Role  has been added successfully");
    }

    @DeleteMapping("/role/{id{")
    private ResponseEntity<?> delete(@PathVariable("id") Long id){
        roleServices.deleteById (id);
        return ResponseEntity.ok ( ).body ("Role has been deleted successfully.");

    }
    public List <Role> fetchList() {
        List <Role> list = null;
        try {
            Gson gson = new Gson ( );
            list = new Gson ( ).fromJson (readUrl ("http://localhost:8080/role"), new TypeToken <List <Role>> ( ) {
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
            ResponseEntity <Role> result
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
