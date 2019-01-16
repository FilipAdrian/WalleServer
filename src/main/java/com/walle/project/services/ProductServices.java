package com.walle.project.services;

import com.walle.project.entity.Product;

import java.util.List;

public interface ProductServices {
    Product getById(String id);

    List<Product> getAll();

    void saveOrUpdate(Product product);

    void deleteById(String id);

    void saveOrUpdateAll(List <Product> products);
}
