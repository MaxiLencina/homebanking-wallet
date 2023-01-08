package com.mindhub.homebanking1.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO , generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private long id;

    private String name;

    private Double maxAmount;

    @ElementCollection
    private List<Integer> payments;

    private double amountInterestRate;



    @OneToMany(mappedBy="loan", fetch=FetchType.EAGER)
    Set<ClientLoan> clientLoans = new HashSet<>();

    public Loan() {
    }

    public Loan(String name, Double maxAmount, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    public Loan(String name, Double maxAmount, List<Integer> payments, double amountInterestRate) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.amountInterestRate = amountInterestRate;
    }

    public long getId() {return id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public Double getMaxAmount() {return maxAmount;}

    public void setMaxAmount(double maxAmount) {this.maxAmount = maxAmount;}

    public List<Integer> getPayments() {return payments;}

    public void setPayments(List<Integer> payments) {this.payments = payments;}

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public double getAmountInterestRate() {
        return amountInterestRate;
    }

    public void setAmountInterestRate(double amountInterestRate) {
        this.amountInterestRate = amountInterestRate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxAmount=" + maxAmount +
                ", payments=" + payments +
                '}';
    }
}
