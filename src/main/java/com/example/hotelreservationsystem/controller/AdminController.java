package com.example.hotelreservationsystem.controller;

import com.example.hotelreservationsystem.model.dto.CreateHotelDTO;
import com.example.hotelreservationsystem.model.dto.CreateRoomDTO;
import com.example.hotelreservationsystem.model.dto.LoginDTO;
import com.example.hotelreservationsystem.service.AdminService;
import com.example.hotelreservationsystem.service.HotelService;
import com.example.hotelreservationsystem.service.RoomService;
import com.example.hotelreservationsystem.util.FilePath;
import com.example.hotelreservationsystem.util.JsonParser;
import com.example.hotelreservationsystem.util.Logger;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class AdminController {

    private final AdminService adminService;
    private final HotelService hotelService;

    private final RoomService roomService;


    public AdminController(AdminService adminService, HotelService hotelService, RoomService roomService) {
        this.adminService = adminService;
        this.hotelService = hotelService;
        this.roomService = roomService;
    }

    public LoginDTO loginAdmin() throws IOException {
        LoginDTO adminDTO = JsonParser.getDTO(LoginDTO.class, FilePath.ADMIN_LOGIN_FILE_PATH);
        String result = this.adminService.login(adminDTO);
        Logger.log(FilePath.RESULT_FILE_PATH, result);

        return adminDTO;
    }


    public void viewHotels() {
        String result = this.adminService.viewHotels();
        Logger.log(FilePath.ADMIN_RESULT_FILE_PATH, result);

    }

    public void viewRooms() {
        String result = this.adminService.viewRooms();
        Logger.log(FilePath.ADMIN_RESULT_FILE_PATH, result);
    }

    public void viewBookings() {
        String result = this.adminService.viewBookings();
        Logger.log(FilePath.ADMIN_RESULT_FILE_PATH, result);

    }

    public void addHotel() throws IOException {
        CreateHotelDTO hotelDTO = JsonParser.getDTO(CreateHotelDTO.class, FilePath.ADD_HOTEL_FILE_PATH);
        String result = this.hotelService.register(hotelDTO);
        Logger.log(FilePath.ADMIN_RESULT_FILE_PATH, result);
    }


    public void addRoom() throws IOException {
        CreateRoomDTO createRoomDTO = JsonParser.getDTO(CreateRoomDTO.class, FilePath.ADD_ROOM_FILE_PATH);
        String result = this.roomService.register(createRoomDTO);
        Logger.log(FilePath.ADMIN_RESULT_FILE_PATH, result);
    }

    public void checkDeposits() {
       String result = this.adminService.viewDeposits();
       Logger.log(FilePath.ADMIN_RESULT_FILE_PATH, result);
    }

    public void adminLogout() {
       String result = this.adminService.logout();
       Logger.log(FilePath.RESULT_FILE_PATH, result);
    }
}
