package com.example.hotelreservationsystem.controller;

import com.example.hotelreservationsystem.model.dto.LoginDTO;
import com.example.hotelreservationsystem.model.dto.UserRegisterDTO;
import com.example.hotelreservationsystem.service.UserService;
import com.example.hotelreservationsystem.util.FilePath;
import com.example.hotelreservationsystem.util.JsonParser;
import com.example.hotelreservationsystem.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class UserController {

    private UserService userService;


   @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void register() throws IOException {


        UserRegisterDTO userRegisterDTO = JsonParser.getDTO(UserRegisterDTO.class, FilePath.REGISTER_FILE_PATH);
        String result = this.userService.register(userRegisterDTO);

        Logger.log(FilePath.RESULT_FILE_PATH, result);

}

    public LoginDTO loginUser() throws IOException {
        LoginDTO loginDTO = JsonParser.getDTO(LoginDTO.class, FilePath.USER_LOGIN_FILE_PATH);
        String result = this.userService.login(loginDTO);

        Logger.log(FilePath.RESULT_FILE_PATH, result);
        return loginDTO;
    }



    public void logout() throws IOException {
        String result = this.userService.logout();
        Logger.log(FilePath.RESULT_FILE_PATH, result);

    }
}
