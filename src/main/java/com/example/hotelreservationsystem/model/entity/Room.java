package com.example.hotelreservationsystem.model.entity;

import com.example.hotelreservationsystem.model.enums.RoomType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number", nullable = false)
    private int roomNumber;


    private int floor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomType type;

    @Column(name = "price_per_night", nullable = false)
    private BigDecimal pricePerNight;

    @Column(name = "cancellation_fee", nullable = false)
    private BigDecimal cancellationFee;

    @Column(name = "maximum_occupancy", nullable = false)
    private int maximumOccupancy;


    @Column(name = "image_url")
    private String imageUrl;


    @OneToMany(mappedBy = "room", targetEntity = Reservation.class,
    fetch = FetchType.EAGER)
    private List<Reservation> reservations;


    @ManyToOne(optional = false)
    private Hotel hotel;

    public Room(int roomNumber, int floor, RoomType type, BigDecimal pricePerNight,
                BigDecimal cancellationFee, int maximumOccupancy,
                String imageUrl, Hotel hotel) {
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.cancellationFee = cancellationFee;
        this.maximumOccupancy = maximumOccupancy;
        this.imageUrl = imageUrl;
        this.hotel = hotel;
        this.reservations = new ArrayList<>();
    }

    public Room() {
        this.reservations = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public Room setId(Long id) {
        this.id = id;
        return this;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Room setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
        return this;
    }

    public int getFloor() {
        return floor;
    }

    public Room setFloor(int floor) {
        this.floor = floor;
        return this;
    }

    public RoomType getType() {
        return type;
    }

    public Room setType(RoomType type) {
        this.type = type;
        return this;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public Room setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
        return this;
    }

    public BigDecimal getCancellationFee() {
        return cancellationFee;
    }

    public Room setCancellationFee(BigDecimal cancellationFee) {
        this.cancellationFee = cancellationFee;
        return this;
    }

    public int getMaximumOccupancy() {
        return maximumOccupancy;
    }

    public Room setMaximumOccupancy(int maximumOccupancy) {
        this.maximumOccupancy = maximumOccupancy;
        return this;
    }



    public String getImageUrl() {
        return imageUrl;
    }

    public Room setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Room setHotel(Hotel hotel) {
        this.hotel = hotel;
        return this;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public Room setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
        return this;
    }
}
