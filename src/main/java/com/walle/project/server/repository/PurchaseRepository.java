package com.walle.project.server.repository;

import com.walle.project.server.entity.Purchase;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface PurchaseRepository extends CrudRepository <Purchase, Long> {
    Purchase getById(Long id);

    List <Purchase> getByDataBetween(Date start, Date stop);

    List <Purchase> getAllBy();

    @Override
    <S extends Purchase> S save(S entity);

    @Override
    void deleteById(Long aLong);

    @Override
    <S extends Purchase> Iterable <S> saveAll(Iterable <S> entities);


}
