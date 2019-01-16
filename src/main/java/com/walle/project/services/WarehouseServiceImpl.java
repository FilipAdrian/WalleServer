package com.walle.project.services;

import com.walle.project.entity.Warehouse;
import com.walle.project.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseServices {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override

    public Warehouse getById(Long id) {
        Warehouse warehouse = warehouseRepository.getById (id);
        return warehouse;
    }

    @Override

    public List <Warehouse> getAll() {
        List <Warehouse> warehouses = warehouseRepository.getAllBy ( );
        return warehouses;
    }

    @Override
    public void saveOrUpdate(Warehouse warehouse) {
        warehouseRepository.save (warehouse);
    }

    @Override
    public void saveOrUpdateAll(List <Warehouse> warehouses) {
        warehouseRepository.saveAll (warehouses);
    }


    @Override
    public void deleteById(Long id) {
        warehouseRepository.deleteById (id);
    }
}
