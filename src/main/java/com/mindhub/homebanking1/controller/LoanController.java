package com.mindhub.homebanking1.controller;

import com.mindhub.homebanking1.dtos.LoanApplicationDTO;
import com.mindhub.homebanking1.dtos.LoanDTO;
import com.mindhub.homebanking1.models.*;
import com.mindhub.homebanking1.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://192.168.0.18:8080/")
public class LoanController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Autowired
    LoanRepository loanRepository;

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<?> getLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){
        Client clientCurrent = clientRepository.findByEmail(authentication.getName());
        Account accountDestiny = accountRepository.findByNumber(loanApplicationDTO.getDestinyAccountNumber());
        Loan typeOfLoan = loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);
        Set<ClientLoan> clientLoanRepeat = clientCurrent.getClientLoans().stream().filter(loan -> loan.getLoan().getId() == loanApplicationDTO.getLoanId()).collect(toSet());

        if(accountDestiny == null){
            return new ResponseEntity<>("The destination account does not correspond to any registered account", HttpStatus.FORBIDDEN);
        }

        if (!clientCurrent.getAccounts().contains(accountDestiny)) {
            return new ResponseEntity<>("The owner of the account is not authenticated", HttpStatus.FORBIDDEN);
        }

        if(loanApplicationDTO.getAmount() <= 0){
            return new ResponseEntity<>("Missing data: amount", HttpStatus.FORBIDDEN);
        }

        if(loanApplicationDTO.getLoanId() == 0){
            return new ResponseEntity<>("Missing data: id", HttpStatus.FORBIDDEN);
        }
        if(loanApplicationDTO.getPayments() <= 0){
            return new ResponseEntity<>("Missing data: payments", HttpStatus.FORBIDDEN);
        }
        if(typeOfLoan == null){
            return new ResponseEntity<>("the type of loan does not exist", HttpStatus.FORBIDDEN);
        }
        if(typeOfLoan.getMaxAmount() < loanApplicationDTO.getAmount()){
            return new ResponseEntity<>("The maximum amount for this type of loan was exceeded", HttpStatus.FORBIDDEN);
        }

        if(!typeOfLoan.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("Quotas are not valid", HttpStatus.FORBIDDEN);
        }

        if(clientLoanRepeat.size() > 0){
            return new ResponseEntity<>("ya tienes un prestamo de este tipo", HttpStatus.FORBIDDEN);
        }





        ClientLoan createClientLoan = new ClientLoan(loanApplicationDTO.getAmount() + (loanApplicationDTO.getAmount() * typeOfLoan.getAmountInterestRate()), loanApplicationDTO.getPayments(),  clientCurrent, typeOfLoan);
        clientLoanRepository.save(createClientLoan);
        Transaction transactionD = new Transaction(TransactionType.CREDITO , loanApplicationDTO.getAmount() , typeOfLoan.getName() + " " + "accepted loan", accountDestiny, LocalDateTime.now());
        transactionRepository.save(transactionD);
        Double balanceClientLoan = accountDestiny.getBalance() + loanApplicationDTO.getAmount();
        accountDestiny.setBalance(balanceClientLoan);
        accountRepository.save(accountDestiny);

        return new ResponseEntity<>("created Loan" , HttpStatus.CREATED);

    }

    @RequestMapping("/loans")
    public List<LoanDTO> getAll() {
            return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(toList());
    }

}
