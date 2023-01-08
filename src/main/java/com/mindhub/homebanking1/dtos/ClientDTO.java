package com.mindhub.homebanking1.dtos;
import com.mindhub.homebanking1.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private Set<AccountDTO> accountsDTO;

    private Set<ClientLoanDTO> loans;

    private Set<CardDTO> cards;

    public ClientDTO(Client client){
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accountsDTO = client.getAccounts().stream().map(AccountDTO::new).collect(toSet());
        this.loans = client.getClientLoans().stream().map(ClientLoanDTO::new).collect(toSet());
        this.cards = client.getCards().stream().map(card -> new CardDTO(card)).collect(toSet());
    }

    public Long getId() {return id;}

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public Set<AccountDTO> getAccountsDTO() {return accountsDTO;}

    public void setAccountsDTO(Set<AccountDTO> accountsDTO) {this.accountsDTO = accountsDTO;}

    public Set<ClientLoanDTO> getLoans() {return loans;}

    public void setLoans(Set<ClientLoanDTO> loans) {this.loans = loans;}

    public Set<CardDTO> getCards() {return cards;}

    public void setCards(Set<CardDTO> cards) {this.cards = cards;}
}
