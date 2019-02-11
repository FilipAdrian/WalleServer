package com.walle.project.server.repository;

import com.walle.project.server.entity.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
