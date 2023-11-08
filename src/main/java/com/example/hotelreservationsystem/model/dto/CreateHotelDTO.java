package com.example.hotelreservationsystem.model.dto;

import com.example.hotelreservationsystem.model.enums.Stars;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateHotelDTO {

    @NotBlank
    @Size(min = 2, max = 20)
    private String name;


    @Positive
    @NotNull
    private int floors;

    @NotBlank
    @Size(min = 3, max = 20)
    private String country;

    @NotNull
    private Stars stars;

    @NotNull
    private BankAccountDTO bankAccount;

    public CreateHotelDTO() {

    }

    public String getName() {
        return name;
    }

    public CreateHotelDTO setName(String name) {
        this.name = name;
        return this;
    }



    public int getFloors() {
        return floors;
    }

    public CreateHotelDTO setFloors(int floors) {
        this.floors = floors;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public CreateHotelDTO setCountry(String country) {
        this.country = country;
        return this;
    }

    public Stars getStars() {
        return stars;
    }

    public CreateHotelDTO setStars(Stars stars) {
        this.stars = stars;
        return this;
    }

    public BankAccountDTO getBankAccount() {
        return bankAccount;
    }

    public CreateHotelDTO setBankAccount(BankAccountDTO bankAccount) {
        this.bankAccount = bankAccount;
        return this;
    }
}
