package com.walle.project.services;

import com.walle.project.entity.Client;

import java.util.List;

public interface ClientServices {
    Client getById(Long id);

    List<Client> getAll();

    void saveOrUpdate(Client client);

    void deleteById(Long id);

    void saveOrUpdateAll(List <Client> clients);
}
