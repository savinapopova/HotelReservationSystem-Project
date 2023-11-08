package com.example.hotelreservationsystem.model.dto;

import com.example.hotelreservationsystem.model.enums.RoomType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class ReservationDTO {

    @NotNull
    @FutureOrPresent
    private LocalDate checkInDate;

    @NotNull
    @FutureOrPresent
    private LocalDate checkOutDate;

    @Positive
    @NotNull
    private int numberOfGuests;


    private String specialRequests;

    @NotBlank
    private String hotel;

    @NotNull
    private RoomType roomType;

    public ReservationDTO() {
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public ReservationDTO setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
        return this;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public ReservationDTO setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
        return this;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public ReservationDTO setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
        return this;
    }


    public String getSpecialRequests() {
        return specialRequests;
    }

    public ReservationDTO setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
        return this;
    }

    public String getHotel() {
        return hotel;
    }

    public ReservationDTO setHotel(String hotel) {
        this.hotel = hotel;
        return this;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public ReservationDTO setRoomType(RoomType roomType) {
        this.roomType = roomType;
        return this;
    }
}
