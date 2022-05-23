package com.example.maxfil63.controller;

import com.example.maxfil63.entities.Transaction;
import com.example.maxfil63.repository.ClientRepository;
import com.example.maxfil63.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TransactionController {

    private final ClientRepository clientRepository;
    private final TransactionService transactionService;

    @PostMapping("/send/money")
    public ResponseEntity sendMoney(@RequestParam(name = "sender") String sender,
                                                @RequestParam(name = "senderBank") String senderBank,
                                                @RequestParam(name = "recepient") String recepient,
                                                @RequestParam(name = "recepientBank") String recepientBank,
                                                @RequestParam(name = "among") long among) throws Exception {
        transactionService.createTransaction(sender, senderBank, recepient, recepientBank, among);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/transactions")
    public ResponseEntity getTransactions(@RequestParam(name = "sender") String sender,
                                    @RequestParam(name = "senderBank") String senderBank,
                                    @RequestParam(name = "recepient") String recepient,
                                    @RequestParam(name = "recepientBank") String recepientBank) throws Exception {
        List<Transaction> transactions = transactionService.getTransactions(sender, senderBank, recepient, recepientBank);
        return ResponseEntity.ok(transactions);
    }

}
