package com.walle.project.UI.client;

public class Record {
    public Integer year;
    public Integer month;
    public Double amount;

    public Record() { }

    @Override
    public String toString() {
        return "Record{" +
                "year=" + year +
                ", month=" + month +
                ", amount=" + amount +
                '}';
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Record(Integer year, Integer month, Double amount) {
        this.month = month;
        this.amount = amount;
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
