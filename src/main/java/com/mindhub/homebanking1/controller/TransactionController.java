package com.mindhub.homebanking1.controller;

import com.mindhub.homebanking1.models.Account;
import com.mindhub.homebanking1.models.Client;
import com.mindhub.homebanking1.models.Transaction;
import com.mindhub.homebanking1.models.TransactionType;
import com.mindhub.homebanking1.repositories.AccountRepository;
import com.mindhub.homebanking1.repositories.ClientRepository;
import com.mindhub.homebanking1.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class TransactionController {

    @GetMapping("/hola")
    public String saludo(){
        return "hola";
    }

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<?> getTransaction(
            Authentication authentication,
            @RequestParam Double amount,
            @RequestParam String description,
            @RequestParam String originAccount,
            @RequestParam String destinyAccount
            ) {


    Client clientCurrent = clientRepository.findByEmail(authentication.getName());
    Account accountOrigin = accountRepository.findByNumber(originAccount);
    Account accountDestiny = accountRepository.findByNumber(destinyAccount);
    Set<Account> accountExist = clientCurrent.getAccounts().stream().filter(account -> account.getNumber().equals(originAccount)).collect(Collectors.toSet());


    if (amount <= 0) {
            return new ResponseEntity<>("The amount is missing", HttpStatus.EXPECTATION_FAILED);
        }
    if (description.isEmpty()) {
            return new ResponseEntity<>("The description is missing", HttpStatus.FORBIDDEN);
        }
    if (originAccount.isEmpty() ) {
            return new ResponseEntity<>("Source account is missing", HttpStatus.FORBIDDEN);
        }
    if (destinyAccount.isEmpty()) {
            return new ResponseEntity<>("The destination account is missing", HttpStatus.FORBIDDEN);
        }

    if (originAccount.equals(destinyAccount)){
            return new ResponseEntity<>("The source account cannot be the same as the destination account", HttpStatus.FORBIDDEN);
        }

    if(accountExist.size() == 0){
            return new ResponseEntity<>("Your account does not exist", HttpStatus.FORBIDDEN);
        }
    if(accountDestiny == null){
            return new ResponseEntity<>("Destination account does not exist", HttpStatus.FORBIDDEN);
        }
    if(accountOrigin == null){
            return new ResponseEntity<>("Origin account does not exist", HttpStatus.FORBIDDEN);
        }

    if(accountOrigin.getBalance() < amount){
            return new ResponseEntity<>("the account has no money", HttpStatus.FORBIDDEN);
        }
    Transaction transactionOrigin = new Transaction(TransactionType.DEBITO, - amount, description + " " + originAccount, accountOrigin , LocalDateTime.now());
    Transaction transactionDestin = new Transaction(TransactionType.CREDITO, + amount, description + " " + destinyAccount , accountDestiny,LocalDateTime.now());



    transactionRepository.save(transactionOrigin);
    transactionRepository.save(transactionDestin);

    accountOrigin.setBalance(accountOrigin.getBalance() - amount);
    accountDestiny.setBalance(accountDestiny.getBalance() + amount);

    accountRepository.save(accountOrigin);
    accountRepository.save(accountDestiny);

    return new ResponseEntity<>(HttpStatus.CREATED);

    }



}
