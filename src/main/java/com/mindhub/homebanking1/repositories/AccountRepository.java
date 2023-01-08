package com.mindhub.homebanking1.repositories;

import com.mindhub.homebanking1.models.Account;
import com.mindhub.homebanking1.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByNumber (String number);

}
