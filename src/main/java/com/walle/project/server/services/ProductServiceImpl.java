package com.walle.project.server.services;

import com.walle.project.server.entity.Product;
import com.walle.project.server.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductServices {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getById(String id) {
        Product product = productRepository.getById (id);
        return product;
    }

    @Override
    public List <Product> getAll() {
        List <Product> products = productRepository.getAllBy ( );
        return products;
    }

    @Override
    public void saveOrUpdate(Product product) {
        productRepository.save (product);
    }

    @Override
    public void deleteById(String id) {
        productRepository.deleteById (id);
    }

    @Override
    public void saveOrUpdateAll(List <Product> products) {
        productRepository.saveAll (products);
    }
}
