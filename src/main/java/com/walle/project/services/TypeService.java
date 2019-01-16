package com.walle.project.services;

import com.walle.project.entity.Type;

import java.util.List;

public interface TypeService {
    Type getById(Long id);

    List<Type> getAll();

    void saveOrUpdate(Type type);

    void deleteById(Long id);

    void saveOrUpdateAll(List <Type> types);
}
