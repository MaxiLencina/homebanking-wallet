package com.mindhub.homebanking1.controller;


import com.mindhub.homebanking1.models.Card;
import com.mindhub.homebanking1.models.CardColor;
import com.mindhub.homebanking1.models.CardType;
import com.mindhub.homebanking1.models.Client;
import com.mindhub.homebanking1.repositories.CardRepository;
import com.mindhub.homebanking1.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;


@RequestMapping("/api")
@RestController
@CrossOrigin("http://192.168.0.18:8080/")
public class CardController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CardRepository cardRepository;

    public int randomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public String cardNumber (int min, int max){
        return randomNumber(min, max) + "-" + randomNumber(min, max) + "-" + randomNumber(min, max) + "-" + randomNumber(min, max);
    }


    @PostMapping("/clients/current/cards")
    public ResponseEntity<?> createCard(
            Authentication authentication,
            @RequestParam CardType cardType,
            @RequestParam CardColor cardColor
            ) {
        Client clientCurrent = clientRepository.findByEmail(authentication.getName());
        if (clientCurrent == null){
            return new ResponseEntity<>("Unknow user", HttpStatus.FORBIDDEN);
        }
       /*
        if(clientCurrent.getCards().stream().filter(card -> card.getType().toString().equals(cardType.toString())).count() > 2 ){
            return new ResponseEntity<>("You already have 3 cards of that type", HttpStatus.FORBIDDEN);
        }
       */
        Set<Card> cardsCurrent = clientCurrent.getCards().stream().filter(card -> card.getType() == cardType).collect(Collectors.toSet());
        Set<Card> cardsCurrentColor = cardsCurrent.stream().filter(card -> card.getColor() == cardColor).collect(Collectors.toSet());

       if(cardsCurrentColor.size() >= 1){
           return new ResponseEntity<>("You already have 1 card of that color", HttpStatus.FORBIDDEN);
       }

       if(cardsCurrent.size() >= 3){
            return new ResponseEntity<>("You already have 3 cards of that type", HttpStatus.FORBIDDEN);
       }


       String cardHolderCurrent = clientCurrent.getFirstName() + " " + clientCurrent.getLastName();
       Card cardCreated = new Card(cardHolderCurrent, cardType , cardColor, cardNumber(1000,9999) , randomNumber(100, 999), LocalDate.now(), LocalDate.now().plusYears(5), clientCurrent);
       cardRepository.save(cardCreated);
       return new ResponseEntity<>("created card" , HttpStatus.CREATED);
    }





}
