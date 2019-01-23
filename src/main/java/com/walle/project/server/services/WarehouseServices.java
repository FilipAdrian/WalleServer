package com.walle.project.server.services;

import com.walle.project.server.entity.Warehouse;

import java.util.List;

public interface WarehouseServices {
    Warehouse getById(Long id);
    List<Warehouse> getAll();
    void deleteById(Long id);
    void saveOrUpdate(Warehouse warehouse);
    void saveOrUpdateAll(List <Warehouse> warehouses);

}
