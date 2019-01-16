package com.walle.project.repository;

import com.walle.project.entity.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends CrudRepository <Country, Long> {
    Country getById(Long id);

    List <Country> getAllBy();

    @Override
    <S extends Country> S save(S entity);

    void deleteById(Long id);

    @Override
    <S extends Country> Iterable <S> saveAll(Iterable <S> entities);
}
