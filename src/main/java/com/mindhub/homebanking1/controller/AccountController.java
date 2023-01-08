package com.mindhub.homebanking1.controller;

import com.mindhub.homebanking1.dtos.AccountDTO;
import com.mindhub.homebanking1.models.Account;
import com.mindhub.homebanking1.models.Client;
import com.mindhub.homebanking1.repositories.AccountRepository;
import com.mindhub.homebanking1.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api") //ESCUCHAN Y RESPONDEN PETICIONES
@RestController
@CrossOrigin("http://192.168.0.18:8080/")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @RequestMapping("/accounts/current/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
    }

    public int randomAccountNumber(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }
    //no repetidos do while

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> postAccount(Authentication authentication){
        Client clientCurrent = clientRepository.findByEmail(authentication.getName());
        if(clientCurrent.getAccounts().size() > 2){
            return new ResponseEntity<>("You already have 3 accounts", HttpStatus.FORBIDDEN);
        }


        Account account = new Account("VIN-" + randomAccountNumber(10000000, 100000001), LocalDateTime.now(), 0.00, clientCurrent );
        accountRepository.save(account);
        return new ResponseEntity<>("Account created" , HttpStatus.CREATED);
    }



}
