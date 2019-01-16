package com.walle.project.repository;

import com.walle.project.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository <Product, String> {
    Product getById(String id);

    List <Product> getAllBy();

    @Override
    <S extends Product> S save(S entity);

    @Override
    void deleteById(String s);

    @Override
    <S extends Product> Iterable <S> saveAll(Iterable <S> entities);
}
