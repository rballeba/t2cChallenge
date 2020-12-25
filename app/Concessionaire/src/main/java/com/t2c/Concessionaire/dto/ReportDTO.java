package com.t2c.Concessionaire.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ReportDTO {
    @JsonProperty("transactions")
    public List<TransactionDTO> transactions;
    @JsonProperty("overallBenefit")
    public double overallBenefit;

    public ReportDTO(List<TransactionDTO> transactions, double overallBenefit) {
        this.transactions = transactions;
        this.overallBenefit = overallBenefit;
    }
}
