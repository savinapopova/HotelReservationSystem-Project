package com.example.hotelreservationsystem.service;

import com.example.hotelreservationsystem.model.dto.BankAccountDTO;
import com.example.hotelreservationsystem.model.entity.BankAccount;
import com.example.hotelreservationsystem.model.entity.Reservation;

public interface BankService {
    BankAccount register(BankAccountDTO bankAccountDTO);

    boolean processPayment(Reservation reservation);

    void cancelPayment(Reservation reservation);
}
