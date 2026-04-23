package com.demo.ss14_btth.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transaction_history")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    public TransactionHistory(Double amount, String description, Wallet wallet) {
        this.amount = amount;
        this.description = description;
        this.wallet = wallet;
    }

}
