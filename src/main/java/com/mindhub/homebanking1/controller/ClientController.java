
package com.mindhub.homebanking1.controller;
import com.mindhub.homebanking1.dtos.ClientDTO;
import com.mindhub.homebanking1.models.Account;
import com.mindhub.homebanking1.models.Client;
import com.mindhub.homebanking1.repositories.AccountRepository;
import com.mindhub.homebanking1.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
@CrossOrigin("http://192.168.0.18:8080/")

public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);
    }

    public int randomAccountNumber(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }


    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password){

        if (firstName.isEmpty()) {
            return new ResponseEntity<>("Missing data: firstName", HttpStatus.FORBIDDEN);
        }
        else if (lastName.isEmpty()){
            return new ResponseEntity<>("Missing data: lastName", HttpStatus.FORBIDDEN);
        }
        else if (email.isEmpty()){
            return new ResponseEntity<>("Missing data: email", HttpStatus.FORBIDDEN);
        }
        else if(password.isEmpty()){
            return new ResponseEntity<>("Missing data: password", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) !=  null) {

            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);

        }
        Client clientCreated = new Client(firstName, lastName , email, passwordEncoder.encode(password));
        Account firstAccount = new Account("VIN-" + randomAccountNumber(10000000, 100000001), LocalDateTime.now(), 0.00, clientCreated );
        clientRepository.save(clientCreated);
        accountRepository.save(firstAccount);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("clients/current")
    public ClientDTO getAll(Authentication authentication){
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }

}
