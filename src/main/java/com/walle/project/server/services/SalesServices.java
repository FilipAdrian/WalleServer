package com.walle.project.server.services;

import com.walle.project.server.entity.Sales;

import java.util.List;

public interface SalesServices {
    Sales getById(Long id);

    List<Sales> getAll();

    void saveOrUpdate(Sales sales);

    void deleteById(Long id);

    void saveOrUpdateAll(List <Sales> sales);

    List<Sales> getAmountOnMonth(String year);
    List<Sales> getByUser(Long id);
}
