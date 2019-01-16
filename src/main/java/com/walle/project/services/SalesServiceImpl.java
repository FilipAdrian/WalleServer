package com.walle.project.services;


import com.walle.project.entity.Sales;
import com.walle.project.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesServiceImpl implements SalesServices {
    @Autowired
    private SalesRepository salesRepository;

    @Override
    public Sales getById(Long id) {
        Sales sales = salesRepository.getById (id);
        return sales;
    }

    @Override
    public List <Sales> getAll() {
        List <Sales> sales = salesRepository.getAllBy ( );
        return sales;
    }

    @Override
    public void saveOrUpdate(Sales sales) {
        salesRepository.save (sales);
    }

    @Override
    public void deleteById(Long id) {
        salesRepository.deleteById (id);
    }

    @Override
    public void saveOrUpdateAll(List <Sales> sales) {
        salesRepository.saveAll (sales);
    }

}
