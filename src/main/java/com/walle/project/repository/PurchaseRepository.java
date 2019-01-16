package com.walle.project.repository;

import com.walle.project.entity.Purchase;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.core.CrudMethods;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends CrudRepository <Purchase, Long> {
    Purchase getById(Long id);

    List <Purchase> getAllBy();

    @Override
    <S extends Purchase> S save(S entity);

    @Override
    void deleteById(Long aLong);

    @Override
    <S extends Purchase> Iterable <S> saveAll(Iterable <S> entities);
}
