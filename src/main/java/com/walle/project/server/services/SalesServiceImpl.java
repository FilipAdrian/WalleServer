package com.walle.project.server.services;


import com.walle.project.server.entity.Sales;
import com.walle.project.server.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public List <Sales> getAmountOnMonth(String year) {
        String start = year + "-01-01";
        String stop = year +"-12-31";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        List<Sales> data = new ArrayList <> ();
        try {
            Date dataStart = simpleDateFormat.parse (start);
            Date dataEnd = simpleDateFormat.parse (stop);
            java.sql.Date sqlDataStart = new java.sql.Date (dataStart.getTime ( ));
            java.sql.Date sqlDataEnd = new java.sql.Date (dataEnd.getTime ( ));
            data = salesRepository.getByDataBetween(sqlDataStart,sqlDataEnd);

        } catch (ParseException e) {
            e.printStackTrace ( );
        }
        return data;
    }

    @Override
    public List <Sales> getByUser(Long id) {
       return salesRepository.getByUsers_Id (id);
    }

}
