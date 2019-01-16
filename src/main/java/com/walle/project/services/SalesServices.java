package com.walle.project.services;

import com.walle.project.entity.Sales;

import java.util.List;

public interface SalesServices {
    Sales getById(Long id);

    List<Sales> getAll();

    void saveOrUpdate(Sales sales);

    void deleteById(Long id);

    void saveOrUpdateAll(List <Sales> sales);
}
