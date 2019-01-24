package com.walle.project.server.services;

import com.walle.project.server.entity.Client;

import java.util.List;

public interface ClientServices {
    Client getById(Long id);

    List<Client> getAll();

    void saveOrUpdate(Client client);

    void deleteById(Long id);

    void saveOrUpdateAll(List <Client> clients);
}
