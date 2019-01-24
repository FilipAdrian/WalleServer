package com.walle.project.server.controller;

import com.walle.project.server.entity.Country;
import com.walle.project.server.services.CountryServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CountryController  {
    @Autowired
    private CountryServices countryServices;

    @GetMapping("/country")
    private ResponseEntity <List <Country>> displayCountry() {
        List <Country> countries = countryServices.getAll ( );
        return new ResponseEntity <> (countries, HttpStatus.OK);
    }

    @GetMapping("/country/{id}")
    private ResponseEntity <Country> get(@PathVariable("id") Long id) {
        Country country = countryServices.getById (id);
        return new ResponseEntity <> (country, HttpStatus.OK);
    }


    @DeleteMapping("/country/{id}")
    private ResponseEntity <?> delete(@PathVariable("id") Long id) {
        countryServices.deleteById (id);
        return ResponseEntity.ok ( ).body ("Country has been deleted successfully.");

    }

    @PostMapping("/country")
    private ResponseEntity <?> save(@RequestBody Country country) {
        countryServices.saveOrUpdate (country);
        return ResponseEntity.ok ( ).body ("Country  " + country.getName ( ) + "has been added");
    }

}
