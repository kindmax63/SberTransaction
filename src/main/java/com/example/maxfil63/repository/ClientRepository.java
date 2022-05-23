package com.example.maxfil63.repository;

import com.example.maxfil63.entities.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    Client findByUsername(String name);
}
