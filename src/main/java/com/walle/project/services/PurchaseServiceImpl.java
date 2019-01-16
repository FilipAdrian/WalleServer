package com.walle.project.services;

import com.walle.project.entity.Purchase;
import com.walle.project.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseServices {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public Purchase getById(Long id) {
        Purchase purchase = purchaseRepository.getById (id);
        return purchase;
    }

    @Override
    public List <Purchase> getAll() {
        List <Purchase> purchases = purchaseRepository.getAllBy ( );
        return purchases;
    }

    @Override
    public void saveOrUpdate(Purchase purchase) {
        purchaseRepository.save (purchase);

    }

    @Override
    public void deleteById(Long id) {
        purchaseRepository.deleteById (id);
    }

    @Override
    public void saveOrUpdateAll(List <Purchase> purchases) {

        purchaseRepository.saveAll (purchases);
    }
}
