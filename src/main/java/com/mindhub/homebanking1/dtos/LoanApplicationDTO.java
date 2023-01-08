package com.mindhub.homebanking1.dtos;

public class LoanApplicationDTO {

    private long loanId;

    private Double amount;

    private int payments;

    private String destinyAccountNumber;

    public LoanApplicationDTO(long loanId, Double amount, int payments, String destinyAccountNumber) {
        this.loanId = loanId;
        this.amount = amount;
        this.payments = payments;
        this.destinyAccountNumber = destinyAccountNumber;
    }

    public long getLoanId() {return loanId;}

    public void setLoanId(long loanId) {this.loanId = loanId;}

    public Double getAmount() {return amount;}

    public void setAmount(Double amount) {this.amount = amount;}

    public int getPayments() {return payments;}

    public void setPayments(int payments) {this.payments = payments;}

    public String getDestinyAccountNumber() {return destinyAccountNumber;}

    public void setDestinyAccountNumber(String destinyAccountNumber) {this.destinyAccountNumber = destinyAccountNumber;}
}
