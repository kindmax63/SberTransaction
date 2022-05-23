package com.example.maxfil63.service;

import com.example.maxfil63.entities.Bank;
import com.example.maxfil63.entities.Client;
import com.example.maxfil63.repository.BankRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class BankService {

    private static final Logger logger = LoggerFactory.getLogger(BankService.class);
    private final BankRepository bankRepo;

    public Bank getBank(String bankName) {
        return bankRepo.findByName(bankName);
    }

}
