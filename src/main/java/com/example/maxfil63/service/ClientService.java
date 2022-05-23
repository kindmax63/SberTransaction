package com.example.maxfil63.service;

import com.example.maxfil63.entities.Client;
import com.example.maxfil63.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);
    private final ClientRepository clientRepo;

    public Client getClient(String clientName) {
        return clientRepo.findByUsername(clientName);
    }

}
