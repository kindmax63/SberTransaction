package com.example.maxfil63.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bill")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String currency;
    public Long balance;

    @ManyToOne
    @JoinColumn(name = "clientId", referencedColumnName = "id")
    public Client client;

    @ManyToOne
    @JoinColumn(name = "bankId", referencedColumnName = "id")
    private Bank bank;

}
