package com.example.hotelreservationsystem.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Person{

    @OneToMany(mappedBy = "user", targetEntity = Reservation.class,
    fetch = FetchType.EAGER)
    private List<Reservation> reservations;

    @ManyToOne(optional = false)
    private BankAccount bankAccount;

    public User(String username, String password, String email, String firstName, String lastName, BankAccount bankAccount) {
        super(username, password, email, firstName, lastName);
        this.bankAccount = bankAccount;
        this.reservations = new ArrayList<>();
    }

    public User() {
        this.reservations = new ArrayList<>();
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public User setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
        return this;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public User setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
        return this;
    }
}
