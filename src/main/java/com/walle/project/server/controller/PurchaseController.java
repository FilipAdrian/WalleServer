package com.walle.project.server.controller;

import com.walle.project.UI.client.Record;
import com.walle.project.server.entity.Purchase;
import com.walle.project.server.services.PurchaseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
public class PurchaseController  {

    @Autowired
    private PurchaseServices purchaseServices;

    @GetMapping("/purchase")
    private ResponseEntity <List <Purchase>> displayPurchase() {
        List <Purchase> purchases = purchaseServices.getAll ( );
        return new ResponseEntity <> (purchases, HttpStatus.OK);
    }

    @GetMapping("/purchase/{id}")
    private ResponseEntity <Purchase> get(@PathVariable("id") Long id) {
        Purchase purchase = purchaseServices.getById (id);
        return new ResponseEntity <> (purchase, HttpStatus.OK);
    }

    @GetMapping("/purchase/year/{year}")
    private ResponseEntity <List<Double> > getAmount(@PathVariable("year") String year) {
        List<Purchase> purchaseList = purchaseServices.getAmountOnYear (year);
        List <Record> records = new ArrayList<> ( );
        List<Double> amountList = new ArrayList <> ();
        Integer month, yearCurrent =0;
        Double amount;
        Double sumAmount = 0.0;
        Calendar calendar = Calendar.getInstance ( );
        for (int i = 0; i < purchaseList.size ( ); i++) {
            calendar.setTime (purchaseList.get (i).getData ( ));
            yearCurrent = calendar.get (Calendar.YEAR);
            month = calendar.get (Calendar.MONTH);
            amount = purchaseList.get (i).getAmount ( ).doubleValue ( );
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

    @PostMapping("/purchase")
    private ResponseEntity <?> save(@RequestBody Purchase purchase) {
        purchaseServices.saveOrUpdate (purchase);
        return new ResponseEntity <> (purchase, HttpStatus.OK);
    }


    @DeleteMapping("/purchase/{id}")
    private ResponseEntity <?> delete(@PathVariable("id") Long id) {
        purchaseServices.deleteById (id);
        return ResponseEntity.ok ( ).body ("Purchase has been deleted successfully ");
    }


}
