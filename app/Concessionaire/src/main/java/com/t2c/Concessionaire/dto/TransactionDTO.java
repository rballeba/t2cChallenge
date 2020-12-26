package com.t2c.Concessionaire.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class TransactionDTO {
    @JsonProperty("buyDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date buyDate;
    @JsonProperty("saleDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date saleDate;
    @JsonProperty("buyPrice")
    private double buyPrice;
    @JsonProperty("salePrice")
    private Double salePrice;
    @JsonProperty("benefit")
    private double benefit;
    public TransactionDTO(Date buyDate, Date saleDate, double buyPrice, Double salePrice, double benefit) {
        this.buyDate = buyDate;
        this.saleDate = saleDate;
        this.buyPrice = buyPrice;
        this.salePrice = salePrice;
        this.benefit = benefit;
    }
}
