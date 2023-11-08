package com.example.hotelreservationsystem.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal deposit;

    @Column(nullable = false)
    private String bank;




    public BankAccount(BigDecimal deposit, String bank) {
        this.deposit = deposit;
        this.bank = bank;
    }

    public BankAccount() {
    }

    public Long getId() {
        return id;
    }

    public BankAccount setId(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public BankAccount setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
        return this;
    }

    public String getBank() {
        return bank;
    }

    public BankAccount setBank(String bank) {
        this.bank = bank;
        return this;
    }
}
