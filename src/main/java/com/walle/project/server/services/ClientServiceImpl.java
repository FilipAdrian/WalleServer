package com.walle.project.server.services;

import com.walle.project.server.entity.Client;
import com.walle.project.server.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientServices{
    @Autowired
    private ClientRepository clientRepository;
    @Override
    public Client getById(Long id) {
        Client client = clientRepository.getById (id);
        return client;
    }
    @Override
    public List <Client> getAll() {
        List <Client> clients = clientRepository.getAllBy ( );
        return clients;
    }
    @Override
    public void saveOrUpdate(Client client) {
        clientRepository.save (client);
    }
    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById (id);

    }
    @Override
    public void saveOrUpdateAll(List <Client> clients) {
        clientRepository.saveAll (clients);
    }
}
