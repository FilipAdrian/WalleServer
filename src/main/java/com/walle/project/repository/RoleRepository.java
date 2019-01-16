package com.walle.project.repository;

import com.walle.project.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository <Role, Long> {
    Role getById(Long id);

    List <Role> getAllBy();

    @Override
    void deleteById(Long aLong);

    @Override
    <S extends Role> S save(S entity);

    @Override
    <S extends Role> Iterable <S> saveAll(Iterable <S> entities);
}
