package com.example.hotelreservationsystem.model.entity;

import com.example.hotelreservationsystem.model.enums.Stars;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "hotel", targetEntity = Room.class,
    fetch = FetchType.EAGER)
    private List<Room> rooms;

    @Column(nullable = false)
    private int floors;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Stars stars;

    @ManyToOne(optional = false)
    private BankAccount bankAccount;

    public Hotel(String name, List<Room> rooms, int floors, String country, Stars stars,
                 BankAccount bankAccount) {
        this.name = name;
        this.rooms = rooms;
        this.floors = floors;
        this.country = country;
        this.stars = stars;
        this.bankAccount = bankAccount;
    }

    public Hotel() {
        this.rooms = new ArrayList<>();

    }

    public Long getId() {
        return id;
    }

    public Hotel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Hotel setName(String name) {
        this.name = name;
        return this;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Hotel setRooms(List<Room> rooms) {
        this.rooms = rooms;
        return this;
    }

    public int getFloors() {
        return floors;
    }

    public Hotel setFloors(int floors) {
        this.floors = floors;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Hotel setCountry(String country) {
        this.country = country;
        return this;
    }

    public Stars getStars() {
        return stars;
    }

    public Hotel setStars(Stars stars) {
        this.stars = stars;
        return this;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public Hotel setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
        return this;
    }
}
