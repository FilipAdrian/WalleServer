package com.walle.project.services;

import com.walle.project.entity.Country;

import java.util.List;

public interface CountryServices {
    Country getById(Long id);

    List<Country> getAll();

    void saveOrUpdate(Country country);

    void deleteById(Long id);

    void saveOrUpdateAll(List <Country> countries);
}
