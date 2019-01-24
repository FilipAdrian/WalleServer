package com.walle.project.server.repository;

import com.walle.project.server.entity.Sales;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface SalesRepository extends CrudRepository <Sales, Long> {
    Sales getById(Long id);

    List <Sales> getAllBy();

    List <Sales> getByDataBetween(Date start, Date stop);

    List <Sales> getByUsers_Id(Long id);


    @Override
    void deleteById(Long aLong);

    @Override
    <S extends Sales> S save(S entity);

    @Override
    <S extends Sales> Iterable <S> saveAll(Iterable <S> entities);
}
