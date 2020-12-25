package com.t2c.Concessionaire.model;

import com.t2c.Concessionaire.dto.ReportDTO;
import com.t2c.Concessionaire.dto.TransactionDTO;

import java.util.List;
import java.util.stream.Collectors;

public class Benefits {
    private List<Transaction> transactions;
    public Benefits(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    public double computeOverallBenefit() {
        return transactions.stream()
                .map(transaction -> transaction.computeTransactionBenefit())
                .reduce(0.0, (totalBenefits, newBenefit) -> totalBenefits + newBenefit);
    }
    public ReportDTO generateReport() {
        List<TransactionDTO> transactionDTOS = transactions.stream().map(transaction ->
                new TransactionDTO(transaction.getBuyDate(), transaction.getSaleDate(), transaction.getBuyPrice(),
                        transaction.getSalePrice(), transaction.computeTransactionBenefit()))
                .collect(Collectors.toList());
        return new ReportDTO(transactionDTOS, computeOverallBenefit());
    }
}
