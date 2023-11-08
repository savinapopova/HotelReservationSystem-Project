package com.example.hotelreservationsystem.service;

import com.example.hotelreservationsystem.model.dto.LoginDTO;
import com.example.hotelreservationsystem.model.dto.UserRegisterDTO;

public interface UserService {
    String register(UserRegisterDTO userRegisterDTO);

    String login(LoginDTO loginDTO);

    boolean isLogged(String username);

    String logout();
}
