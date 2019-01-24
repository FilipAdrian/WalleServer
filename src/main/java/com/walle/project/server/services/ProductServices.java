package com.walle.project.server.services;

import com.walle.project.server.entity.Product;

import java.util.List;

public interface ProductServices {
    Product getById(String id);

    List<Product> getAll();

    void saveOrUpdate(Product product);

    void deleteById(String id);

    void saveOrUpdateAll(List <Product> products);
}
