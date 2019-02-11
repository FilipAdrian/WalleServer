package com.walle.project.server.repository;

import com.walle.project.server.entity.Type;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TypeRepository extends CrudRepository<Type,Long> {
    Type getById(Long id);

    List<Type> getAllBy();

    @Override
    <S extends Type> S save(S entity);

    @Override
    void delete(Type entity);

    @Override
    <S extends Type> Iterable <S> saveAll(Iterable <S> entities);
}

