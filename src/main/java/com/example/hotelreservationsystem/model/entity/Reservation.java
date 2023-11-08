package com.example.hotelreservationsystem.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    @Column(name = "total_cost", nullable = false)
    private BigDecimal totalCost;

    @Column(name = "special_requests", columnDefinition = "TEXT")
    private String specialRequests;

    @ManyToOne(optional = false)
    private Room room;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Hotel hotel;

    public Reservation(LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuests,
                       BigDecimal totalCost, String specialRequests, Room room, User user, Hotel hotel) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.totalCost = totalCost;
        this.specialRequests = specialRequests;
        this.room = room;
        this.user = user;
        this.hotel = hotel;
    }

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public Reservation setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public Reservation setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
        return this;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public Reservation setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
        return this;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public Reservation setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
        return this;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public Reservation setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public Reservation setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
        return this;
    }

    public Room getRoom() {
        return room;
    }

    public Reservation setRoom(Room room) {
        this.room = room;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Reservation setUser(User user) {
        this.user = user;
        return this;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Reservation setHotel(Hotel hotel) {
        this.hotel = hotel;
        return this;
    }
}
