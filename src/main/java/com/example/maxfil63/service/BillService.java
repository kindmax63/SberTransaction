package com.example.maxfil63.service;

import com.example.maxfil63.entities.Bank;
import com.example.maxfil63.entities.Bill;
import com.example.maxfil63.entities.Client;
import com.example.maxfil63.repository.BillRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class BillService {

    private static final Logger logger = LoggerFactory.getLogger(BillService.class);
    private final BillRepository billRepo;

    public Bill getBill(Client client, Bank bank) throws Exception {

        return billRepo.findBillByClientAndBank(client, bank).stream().findFirst().orElseThrow(Exception::new);
    }

    public Bill updateBill(Bill bill) throws Exception {
        return billRepo.save(bill);
    }

}
