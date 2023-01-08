package com.mindhub.homebanking1.repositories;

import com.mindhub.homebanking1.models.Card;
import com.mindhub.homebanking1.models.CardColor;
import com.mindhub.homebanking1.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {

   // List<Card> findByCardColorAndClient(CardColor cardColor , Client client);
}
