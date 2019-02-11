package com.walle.project.server.repository;

import com.walle.project.server.entity.Manufacture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufactureRepository extends CrudRepository <Manufacture, Long> {
    Manufacture getById(Long id);

    List <Manufacture> getAllBy();

    void deleteById(Long id);

    @Override
    <S extends Manufacture> S save(S entity);

    @Override
    <S extends Manufacture> Iterable <S> saveAll(Iterable <S> entities);
}

