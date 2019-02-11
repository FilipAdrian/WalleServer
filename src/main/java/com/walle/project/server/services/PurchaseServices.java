package com.walle.project.server.services;

import com.walle.project.server.entity.Purchase;

import java.util.List;

public interface PurchaseServices {
    Purchase getById(Long id);

    List <Purchase> getAll();

    void saveOrUpdate(Purchase purchase);

    void deleteById(Long id);

    void saveOrUpdateAll(List <Purchase> purchases);

    List <Purchase> getAmountOnYear(String year);
}
