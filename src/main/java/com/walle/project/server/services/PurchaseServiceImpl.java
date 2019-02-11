package com.walle.project.server.services;

import com.walle.project.server.entity.Purchase;
import com.walle.project.server.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public List <Purchase> getAmountOnYear(String year) {
        String start = year + "-01-01";
        String stop = year +"-12-31";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        List<Purchase> data = new ArrayList<> ();
        try {

            Date dataStart = simpleDateFormat.parse (start);
            Date dataEnd = simpleDateFormat.parse (stop);
            java.sql.Date sqlDataStart = new java.sql.Date (dataStart.getTime ( ));
            java.sql.Date sqlDataEnd = new java.sql.Date (dataEnd.getTime ( ));

            data = purchaseRepository.getByDataBetween(sqlDataStart,sqlDataEnd);
        } catch (ParseException e) {
            e.printStackTrace ( );
        }
        return data;
    }
}
