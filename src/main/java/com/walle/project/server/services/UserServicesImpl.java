package com.walle.project.server.services;

import com.walle.project.server.entity.Users;
import com.walle.project.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Users getById(Long id) {
        Users users = userRepository.getById (id);
        return users;
    }

    @Override
    public List <Users> getAll() {
        List <Users> users = userRepository.getAllBy ( );
        return users;
    }

    @Override
    public void saveOrUpdate(Users users) {
        userRepository.save (users);
    }

    @Override
    public void saveOrUpdateAll(List <Users> users) {
        userRepository.saveAll (users);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById (id);
    }

    @Override
    public Users checkUser(String password, String login) {
        Users user = userRepository.getByPasswordAndLogin (password, login);
        return  user;
    }
}
