package com.mindhub.homebanking1.dtos;

import com.mindhub.homebanking1.models.Card;
import com.mindhub.homebanking1.models.CardColor;
import com.mindhub.homebanking1.models.CardType;

import java.time.LocalDate;

public class CardDTO {
    private Long id;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private int cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;

    public CardDTO(){}

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.type = card.getType();
        this.color = card.getColor();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
    }

    public Long getId() {return id;}


    public String getCardHolder() {return cardHolder;}

    public void setCardHolder(String cardHolder) {this.cardHolder = cardHolder;}

    public CardType getType() {return type;}

    public void setType(CardType type) {this.type = type;}

    public CardColor getColor() {return color;}

    public void setColor(CardColor color) {this.color = color;}

    public String getNumber() {return number;}

    public void setNumber(String number) {this.number = number;}

    public int getCvv() {return cvv;}

    public void setCvv(int cvv) {this.cvv = cvv;}

    public LocalDate getFromDate() {return fromDate;}

    public void setFromDate(LocalDate fromDate) {this.fromDate = fromDate;}

    public LocalDate getThruDate() {return thruDate;}

    public void setThruDate(LocalDate thruDate) {this.thruDate = thruDate;}
}
