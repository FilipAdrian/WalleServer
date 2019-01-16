package com.walle.project.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.walle.project.entity.Users;
import com.walle.project.services.UserServices;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController implements UrlReader {
    @Autowired
    private UserServices userServices;

    @GetMapping("/users")
    private ResponseEntity <List <Users>> displayUsers() {
        List <Users> users = userServices.getAll ( );
        return new ResponseEntity <> (users, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    private ResponseEntity <Users> get(@PathVariable("id") Long id) {
        Users users = userServices.getById (id);
        return new ResponseEntity <> (users, HttpStatus.OK);
    }

    @PostMapping("/users")
    private ResponseEntity <?> save(@RequestBody Users users) {
        userServices.saveOrUpdate (users);
        return ResponseEntity.ok ( ).body ("User  " + users.getName ( ) + " has been added");

    }

    @DeleteMapping("/users/{id}")
    private ResponseEntity <?> delete(@PathVariable("id") Long id) {
        userServices.deleteById (id);
        return ResponseEntity.ok ( ).body ("User has been deleted successfully.");

    }

    @GetMapping("/users/{login}/{password}")
    private Users verifyUser(@PathVariable("login") String login, @PathVariable("password") String password) {
        Users user = userServices.checkUser (password, login);
        return user;
    }

    public List <Users> fetchList() {
        List <Users> list = null;
        try {
            Gson gson = new Gson ( );
            list = new Gson ( ).fromJson (readUrl ("http://localhost:8080/users"), new TypeToken <List <Users>> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return list;
    }

    public Users readCheckedUser(String login, String password) {
        Users user = null;
        String url = "http://localhost:8080/users/" + login + "/" + password;
        try {
            Gson gson = new Gson ( );
            user = new Gson ( ).fromJson (readUrl (url), new TypeToken <Users> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return user;
    }

    public Users getUser(Long id) {
        Users user = null;
        String url = "http://localhost:8080/users/" + id;
        try {
            user = new Gson ( ).fromJson (readUrl (url), new TypeToken <Users> ( ) {
            }.getType ( ));
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return user;
    }

    public void addOrUpdate(Users user) {
        HttpHeaders headers = new HttpHeaders ( );
        headers.add ("Accept", MediaType.APPLICATION_JSON_VALUE);
        try {

            RestTemplate restTemplate = new RestTemplate ( );
            ResponseEntity <Users> result
                    = restTemplate.postForEntity ("http://localhost:8080/users", user, Users.class);
        } catch (Exception e) {
            e.getMessage ( );
            e.getLocalizedMessage ( );
        }
    }

    public void deleteUser(Long id) {
        try {
            HttpClient client = HttpClients.createDefault ( );
            HttpDelete delete = new HttpDelete ("http://localhost:8080/users/" + id);
            HttpResponse response = client.execute (delete);
            if (response.getStatusLine ( ).getStatusCode ( ) == 202) {
                Thread.sleep (3500);
            }
        } catch (Exception e) {
            e.printStackTrace ( );
        }

    }


}
