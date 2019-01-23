package com.walle.project.server.services;

import com.walle.project.server.entity.Role;
import com.walle.project.server.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleServices {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getById(Long id) {
        Role role = roleRepository.getById (id);
        return role;
    }

    @Override
    public List <Role> getAll() {
        List <Role> roles = roleRepository.getAllBy ( );
        return roles;
    }

    @Override
    public void saveOrUpdate(Role role) {

        roleRepository.save (role);
    }

    @Override
    public void deleteById(Long id) {

        roleRepository.deleteById (id);
    }

    @Override
    public void saveOrUpdateAll(List <Role> roles) {
        roleRepository.saveAll (roles);
    }
}
