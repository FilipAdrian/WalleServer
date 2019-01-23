package com.walle.project.server.controller;

import com.walle.project.server.entity.Client;
import com.walle.project.server.services.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {
    @Autowired
    private ClientServices clientServices;

    @GetMapping("/clients")
    private ResponseEntity <List <Client>> displayClients() {
        List <Client> clients = clientServices.getAll ( );
        return new ResponseEntity <> (clients, HttpStatus.OK);
    }

    @GetMapping("clients/{id}")
    private ResponseEntity <Client> get(@PathVariable("id") Long id) {
        Client client = clientServices.getById (id);
        return new ResponseEntity <> (client, HttpStatus.OK);
    }

    @PostMapping("/clients")
    private ResponseEntity <?> save(@RequestBody Client client) {
        clientServices.saveOrUpdate (client);
        return new ResponseEntity <> (client,HttpStatus.OK); }


    @DeleteMapping("/clients/{id}")
    private ResponseEntity <?> delete(@PathVariable("id") Long id) {
        clientServices.deleteById (id);
        return ResponseEntity.ok ( ).body ("Client has been deleted successfully.");

    }


}

