package com.walle.project.repository;

import com.walle.project.entity.Sales;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends CrudRepository <Sales, Long> {
    Sales getById(Long id);

    List <Sales> getAllBy();


    @Override
    void deleteById(Long aLong);

    @Override
    <S extends Sales> S save(S entity);

    @Override
    <S extends Sales> Iterable <S> saveAll(Iterable <S> entities);
}
