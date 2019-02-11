package com.walle.project.server.services;

import com.walle.project.server.entity.Country;
import com.walle.project.server.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryServices {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public Country getById(Long id) {
        Country country = countryRepository.getById (id);
        return country;
    }

    @Override
    public List <Country> getAll() {

        List <Country> countries = countryRepository.getAllBy ( );
        return countries;
    }

    @Override
    public void saveOrUpdate(Country country) {
        countryRepository.save (country);
    }

    @Override
    public void deleteById(Long id) {
        countryRepository.deleteById (id);
    }

    @Override
    public void saveOrUpdateAll(List <Country> countries) {
        countryRepository.saveAll (countries);
    }


}
