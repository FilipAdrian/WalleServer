package com.walle.project.server.controller;

import com.walle.project.server.entity.Users;
import com.walle.project.server.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController  {
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



}
