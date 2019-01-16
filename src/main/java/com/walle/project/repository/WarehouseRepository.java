package com.walle.project.repository;

import com.walle.project.entity.Warehouse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends CrudRepository <Warehouse, Long> {
    Warehouse getById(Long id);

    List <Warehouse> getAllBy();


    @Override
    void deleteById(Long aLong);

    @Override
    <S extends Warehouse> S save(S entity);

    @Override
    <S extends Warehouse> Iterable <S> saveAll(Iterable <S> entities);
}
