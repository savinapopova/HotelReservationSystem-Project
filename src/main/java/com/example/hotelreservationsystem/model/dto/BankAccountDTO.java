package com.example.hotelreservationsystem.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class BankAccountDTO {

    @PositiveOrZero
    @NotNull
    private BigDecimal deposit;

    @NotBlank
    @Size(min = 2, max = 20)
    private String bank;

    public BankAccountDTO() {
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public BankAccountDTO setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
        return this;
    }

    public String getBank() {
        return bank;
    }

    public BankAccountDTO setBank(String bank) {
        this.bank = bank;
        return this;
    }
}
