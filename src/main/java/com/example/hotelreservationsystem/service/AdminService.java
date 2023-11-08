package com.example.hotelreservationsystem.service;

import com.example.hotelreservationsystem.model.dto.AdminDTO;
import com.example.hotelreservationsystem.model.dto.LoginDTO;

public interface AdminService {


    void register(AdminDTO adminDTO);


    boolean alreadySeeded();

    String login(LoginDTO adminDTO);

    boolean isLogged(String username);

    String viewHotels();

    String viewRooms();

    String viewBookings();

    String viewDeposits();

    String logout();
}
