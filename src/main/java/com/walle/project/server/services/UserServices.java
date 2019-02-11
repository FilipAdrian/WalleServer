package com.walle.project.server.services;

import com.walle.project.server.entity.Users;

import java.util.List;

public interface UserServices {
    Users getById(Long id);

    List<Users> getAll();

    void saveOrUpdate(Users users);

    void saveOrUpdateAll(List <Users> users);

    void deleteById(Long id);

    Users checkUser(String password , String login);


}
