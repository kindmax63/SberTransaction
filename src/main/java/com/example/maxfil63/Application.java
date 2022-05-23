package com.example.maxfil63;

import com.example.maxfil63.entities.Bank;
import com.example.maxfil63.entities.Bill;
import com.example.maxfil63.entities.Client;
import com.example.maxfil63.repository.BankRepository;
import com.example.maxfil63.repository.BillRepository;
import com.example.maxfil63.repository.ClientRepository;
import com.example.maxfil63.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements ApplicationRunner {

	@Autowired
	BankRepository bankRepo;
	@Autowired
	ClientRepository clientRepo;
	@Autowired
	BillRepository billRepo;
	@Autowired
	TransactionService transactionService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Bank bankSber = Bank.builder()
				.id(1L)
				.name("Sber")
				.commission (0.5)
				.build();
		bankRepo.save(bankSber);
		Bank bankAlpha = Bank.builder()
				.id(2L)
				.name("Alpha")
				.commission(0.5)
				.build();
		bankRepo.save(bankAlpha);
		Client clientMax = Client.builder()
				.id(1L)
				.username("Max")
				.build();
		clientRepo.save(clientMax);
		Client clientSergei = Client.builder()
				.id(2L)
				.username("Sergei")
				.build();
		clientRepo.save(clientSergei);
		Bill billMax = Bill.builder()
				.id(1L)
				.currency("dollar")
				.balance(1000L)
				.client(clientMax)
				.bank(bankSber)
				.build();
		Bill billMaxRub = Bill.builder()
				.id(2L)
				.currency("rub")
				.balance(20000L)
				.client(clientMax)
				.bank(bankSber)
				.build();
		Bill billSergei = Bill.builder()
				.id(3L)
				.currency("rub")
				.balance(90000L)
				.client(clientSergei)
				.bank(bankSber)
				.build();
		billRepo.save(billMax);
		billRepo.save(billMaxRub);
		billRepo.save(billSergei);
	}
}
