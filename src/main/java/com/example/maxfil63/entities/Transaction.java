package com.example.maxfil63.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "senderBillId", referencedColumnName = "id")
    private Bill senderBill;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recepientBillId", referencedColumnName = "id")
    private Bill recepientBill;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

}
