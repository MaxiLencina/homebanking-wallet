package com.mindhub.homebanking1;

import com.mindhub.homebanking1.models.*;
import com.mindhub.homebanking1.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mindhub.homebanking1.models.CardColor.GOLD;
import static com.mindhub.homebanking1.models.CardColor.TITANIUM;
import static com.mindhub.homebanking1.models.CardType.CREDIT;
import static com.mindhub.homebanking1.models.CardType.DEBIT;

@SpringBootApplication
public class Homebanking1Application {

	public static void main(String[] args) {
		SpringApplication.run(Homebanking1Application.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return args -> {

			Client cliente1 = new Client("Melba", "Lorenzo", "melba@mindhub.com", passwordEncoder.encode("123456"));
			clientRepository.save(cliente1);
			System.out.println(cliente1);

			Client cliente2 = new Client("Melba", "Lorenzo", "adminmelba@mindhub.com", passwordEncoder.encode("123456"));
			clientRepository.save(cliente2);
			System.out.println(cliente2);

			Client cliente3 = new Client("Pablo", "Ramirez", "pablo@mindhub.com", passwordEncoder.encode("123456"));
			clientRepository.save(cliente3);
			System.out.println(cliente3);

			Account cuenta1 = new Account("VIN001" , LocalDateTime.now() , 5000, cliente1);
			Account cuenta2 = new Account("VIN002" , LocalDateTime.now().plusDays(1) , 7500, cliente1);
			accountRepository.save(cuenta1);
			accountRepository.save(cuenta2);
			System.out.println(cuenta1);

			Account cuentaPablo = new Account("VIN003" , LocalDateTime.now(), 200000, cliente3);
			accountRepository.save(cuentaPablo);


			Transaction transaccion1 = new Transaction(TransactionType.DEBITO, -500.00 , "Gastos varios", cuenta1,LocalDateTime.now());
			Transaction transaccion2 = new Transaction(TransactionType.CREDITO, 50000.00 , "Me pagaron Deuda", cuenta1,LocalDateTime.now());
			transactionRepository.save(transaccion1);
			transactionRepository.save(transaccion2);
			System.out.println(transaccion1);
			System.out.println(transaccion2);

			Loan prestamo1 = new Loan("Hipotecario" , 500000.00, new ArrayList<Integer>(){{ add(12); add(24); add(36); add(48); add(60); }}, 0.4);
			Loan prestamo2 = new Loan("Personal" , 100000.00, new ArrayList<Integer>(){{ add(6); add(12); add(24); }},0.3);
			Loan prestamo3 = new Loan("Automotriz" , 300000.00, new ArrayList<Integer>(){{ add(6); add(12); add(24); add(36); }},0.2);
			Loan prestamo4 = new Loan("prueba", 250000.00, List.of(6,12,24,36), 0.2);
			loanRepository.save(prestamo1);
			loanRepository.save(prestamo2);
			loanRepository.save(prestamo3);
			System.out.println(prestamo1);
			System.out.println(prestamo2);
			System.out.println(prestamo3);

			ClientLoan clientLoan1 = new ClientLoan(400000.00, 60, cliente1, prestamo1);
			clientLoanRepository.save(clientLoan1);
			System.out.println(clientLoan1);

			LocalDate fromDate = LocalDate.now();
			LocalDate thruDate = fromDate.plusYears(5);

			Card card1 = new Card("Melba Lorenzo", CREDIT, GOLD, "1321-5664-546-5464",990, fromDate, thruDate, cliente1);
			cardRepository.save(card1);
			Card card2 = new Card("Melba Lorenzo", DEBIT, TITANIUM, "2234-6745-552-7888",750,fromDate,thruDate,cliente1);
			cardRepository.save(card2);
			System.out.println(card1);


		};
	}

}
