package com.example.maxfil63.repository;

import com.example.maxfil63.entities.Bank;
import com.example.maxfil63.entities.Bill;
import com.example.maxfil63.entities.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends CrudRepository<Bill, Long> {

    List<Bill> findAllByClient(Client client);
    List<Bill> findBillByClientAndBank(Client client, Bank bank);

}
