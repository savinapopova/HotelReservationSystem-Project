package com.example.hotelreservationsystem.service;

import com.example.hotelreservationsystem.model.dto.CreateRoomDTO;

public interface RoomService {


    String register(CreateRoomDTO roomDTO);

    boolean alreadySeeded();

    String getRoomList();
}
