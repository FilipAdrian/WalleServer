package com.walle.project.server.repository;

import com.walle.project.server.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository <Users, Long> {

    Users getById(Long id);

    List <Users> getAllBy();

    @Override
    void deleteById(Long aLong);

    @Override
    <S extends Users> S save(S entity);

    @Override
    <S extends Users> Iterable <S> saveAll(Iterable <S> entities);

    Users getByPasswordAndLogin(String password, String login);
}