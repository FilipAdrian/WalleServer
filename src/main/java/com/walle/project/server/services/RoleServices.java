package com.walle.project.server.services;

import com.walle.project.server.entity.Role;

import java.util.List;

public interface RoleServices {
    Role getById(Long id);

    List<Role> getAll();

    void saveOrUpdate(Role role);

    void deleteById(Long id);

    void saveOrUpdateAll(List <Role> roles);
}
