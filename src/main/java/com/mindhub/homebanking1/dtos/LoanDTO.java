package com.mindhub.homebanking1.dtos;

import com.mindhub.homebanking1.models.Loan;

import java.util.List;

public class LoanDTO {
    private long id;

    private String name;

    private Double maxAmount;

    private double amountInterestRate;

    private List<Integer> payments;

    public LoanDTO(){}

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
        this.amountInterestRate = loan.getAmountInterestRate();
    }

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public Double getMaxAmount() {return maxAmount;}

    public void setMaxAmount(Double maxAmount) {this.maxAmount = maxAmount;}

    public List<Integer> getPayments() {return payments;}

    public void setPayments(List<Integer> payments) {this.payments = payments;}

    public double getAmountInterestRate() {return amountInterestRate;}

    public void setAmountInterestRate(double amountInterestRate) {this.amountInterestRate = amountInterestRate;}
}
