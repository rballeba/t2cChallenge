package com.t2c.Concessionaire.model;

import java.util.Date;

public class Transaction {
    private Date buyDate;
    private Date saleDate;
    private double buyPrice;
    private Double salePrice;

    public Date getBuyDate() {
        return buyDate;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }
    public Transaction(Date buyDate, Date saleDate, double buyPrice, Double salePrice) {
        this.buyDate = buyDate;
        this.saleDate = saleDate;
        this.buyPrice = buyPrice;
        this.salePrice = salePrice;
    }
    public double computeTransactionBenefit() {
        double benefits = 0;
        if(this.saleDate != null && this.salePrice != null)
            benefits = this.salePrice;
        return benefits - this.buyPrice;
    }
}
