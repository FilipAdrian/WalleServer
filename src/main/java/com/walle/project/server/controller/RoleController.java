package com.walle.project.server.controller;

import com.walle.project.server.entity.Role;
import com.walle.project.server.services.RoleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController  {
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


}
