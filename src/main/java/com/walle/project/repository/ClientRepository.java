package com.walle.project.repository;

import com.walle.project.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository <Client, Long> {
    Client getById(Long id);

    List <Client> getAllBy();

    @Override
    void deleteById(Long aLong);

    @Override
    <S extends Client> S save(S entity);

    @Override
    <S extends Client> Iterable <S> saveAll(Iterable <S> entities);
}
