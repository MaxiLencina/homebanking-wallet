package com.mindhub.homebanking1.dtos;
import com.mindhub.homebanking1.models.Account;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;


public class AccountDTO {
    private Long id;
    private String number;
    private LocalDateTime creationTime;
    private Double balance;

    private Set<TransactionDTO> transactions;

    public AccountDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationTime = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map(TransactionDTO::new).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Set<TransactionDTO> getTransactions() {return transactions;}

    public void setTransactions(Set<TransactionDTO> transactions) {this.transactions = transactions;}
}
