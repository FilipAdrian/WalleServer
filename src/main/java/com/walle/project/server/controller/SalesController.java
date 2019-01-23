package com.walle.project.server.controller;

import com.walle.project.UI.client.Record;
import com.walle.project.server.entity.Sales;
import com.walle.project.server.services.SalesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

@RestController
public class SalesController {
    @Autowired
    private SalesServices salesServices;

    @GetMapping("/sales")
    private ResponseEntity <List <Sales>> displaySales() {
        List <Sales> sales = salesServices.getAll ( );
        return new ResponseEntity <> (sales, HttpStatus.OK);
    }

    @GetMapping("/sales/{id}")
    private ResponseEntity <Sales> get(@PathVariable("id") Long id) {
        Sales sales = salesServices.getById (id);
        return new ResponseEntity <> (sales, HttpStatus.OK);
    }

    @GetMapping("/sales/year/{year}")
    public ResponseEntity <List <Double>> getAmountOnMonth(@PathVariable("year") String year) throws ParseException {
        List<Sales> salesList = salesServices.getAmountOnMonth (year);
        List <Record> records = new ArrayList<> ( );
        List<Double> amountList = new ArrayList <> ();
        Integer month, yearCurrent =0;
        Double amount;
        Double sumAmount = 0.0;
        Calendar calendar = Calendar.getInstance ( );
        for (int i = 0; i < salesList.size ( ); i++) {
            calendar.setTime (salesList.get (i).getData ( ));
            yearCurrent = calendar.get (Calendar.YEAR);
            month = calendar.get (Calendar.MONTH);
            amount = salesList.get (i).getAmount ( ).doubleValue ( );
            Record record = new Record (yearCurrent, month, amount);
            records.add (record);
        }
        DecimalFormat df = new DecimalFormat ("#.##");
        df.setRoundingMode (RoundingMode.CEILING);
        for (int j = 0; j < 12; j++) {
            for (int i = 0; i < records.size ( ); i++) {
                if (records.get (i).month == j) {
                    sumAmount += records.get (i).getAmount ( );
                }
            }
            amountList.add (Double.parseDouble (df.format (sumAmount)));
            sumAmount = 0.0;
        }
        return new ResponseEntity <> (amountList, HttpStatus.OK);
    }

    @PostMapping("/sales")
    private ResponseEntity <?> save(@RequestBody Sales sales) {
        salesServices.saveOrUpdate (sales);
        return new ResponseEntity <> (sales, HttpStatus.OK);
    }

    @GetMapping("/sales/user/{id}")
    private ResponseEntity <List<Double>> getByUser(@PathVariable("id") Long id) {
        List <Sales> sales = salesServices.getByUser (id);
        List<Double> amountAndQuantity = new ArrayList <> ();
        DecimalFormat df = new DecimalFormat ("#.##");
        df.setRoundingMode (RoundingMode.CEILING);
        Integer quantity=0;
        Double amount=0.0;
        for (int i = 0; i <sales.size () ; i++) {
            System.out.println (sales.get (i).getQuantity () );
            quantity+=sales.get (i).getQuantity ();
            amount+=sales.get (i).getAmount ().doubleValue ();
        }
        amountAndQuantity.add (Double.valueOf (quantity));
        amountAndQuantity.add (Double.valueOf (df.format (amount)));
        return new ResponseEntity <> (amountAndQuantity, HttpStatus.OK);
    }
    @DeleteMapping("/sales/{id}")
    private ResponseEntity <?> delete(@PathVariable("id") Long id) {
        salesServices.deleteById (id);
        return ResponseEntity.ok ( ).body ("Sales has been deleted successfully.");
    }

}
