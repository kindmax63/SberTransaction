package com.example.maxfil63.repository;

import com.example.maxfil63.entities.Bank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends CrudRepository<Bank, Long> {

    Bank findByName(String name);
}
