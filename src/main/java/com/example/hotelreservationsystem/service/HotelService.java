package com.example.hotelreservationsystem.service;

import com.example.hotelreservationsystem.model.dto.CreateHotelDTO;

public interface HotelService {

    String register(CreateHotelDTO hotelDTO);

    boolean alreadySeeded();

    String getHotelsList();

    String getDepositList();
}
