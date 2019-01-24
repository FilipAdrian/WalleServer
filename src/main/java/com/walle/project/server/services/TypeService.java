package com.walle.project.server.services;

import com.walle.project.server.entity.Type;

import java.util.List;

public interface TypeService {
    Type getById(Long id);

    List<Type> getAll();

    void saveOrUpdate(Type type);

    void deleteById(Long id);

    void saveOrUpdateAll(List <Type> types);
}
