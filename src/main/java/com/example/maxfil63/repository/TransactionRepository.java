package com.example.maxfil63.repository;

import com.example.maxfil63.entities.Bill;
import com.example.maxfil63.entities.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findBySenderBillAndRecepientBill(Bill senderBill, Bill recepientBill);

}
