package com.example.hotelreservationsystem.model.dto;

import com.example.hotelreservationsystem.model.enums.RoomType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CreateRoomDTO {

    @Positive
    @NotNull
    private int roomNumber;

    @Positive
    private int floor;

    @NotNull
    private RoomType type;

    @Positive
    @NotNull
    private BigDecimal pricePerNight;

    @PositiveOrZero
    @NotNull
    private BigDecimal cancellationFee;

    @Positive
    @NotNull
    private int maximumOccupancy;



    @Size(min = 3, max = 20)
    private String imageUrl;

    @NotBlank
    @Size(min = 2, max = 20)
    private String hotel;

    public CreateRoomDTO() {
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public CreateRoomDTO setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
        return this;
    }

    public int getFloor() {
        return floor;
    }

    public CreateRoomDTO setFloor(int floor) {
        this.floor = floor;
        return this;
    }

    public RoomType getType() {
        return type;
    }

    public CreateRoomDTO setType(RoomType type) {
        this.type = type;
        return this;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public CreateRoomDTO setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
        return this;
    }

    public BigDecimal getCancellationFee() {
        return cancellationFee;
    }

    public CreateRoomDTO setCancellationFee(BigDecimal cancellationFee) {
        this.cancellationFee = cancellationFee;
        return this;
    }

    public int getMaximumOccupancy() {
        return maximumOccupancy;
    }

    public CreateRoomDTO setMaximumOccupancy(int maximumOccupancy) {
        this.maximumOccupancy = maximumOccupancy;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CreateRoomDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getHotel() {
        return hotel;
    }

    public CreateRoomDTO setHotel(String hotel) {
        this.hotel = hotel;
        return this;
    }
}
