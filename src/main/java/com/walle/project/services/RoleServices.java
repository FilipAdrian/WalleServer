package com.walle.project.services;

import com.walle.project.entity.Role;

import java.util.List;

public interface RoleServices {
    Role getById(Long id);

    List<Role> getAll();

    void saveOrUpdate(Role role);

    void deleteById(Long id);

    void saveOrUpdateAll(List <Role> roles);
}
