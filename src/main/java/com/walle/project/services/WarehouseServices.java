package com.walle.project.services;

import com.walle.project.entity.Warehouse;

import java.util.List;

public interface WarehouseServices {
    Warehouse getById(Long id);
    List<Warehouse> getAll();
    void deleteById(Long id);
    void saveOrUpdate(Warehouse warehouse);
    void saveOrUpdateAll(List <Warehouse> warehouses);

}
