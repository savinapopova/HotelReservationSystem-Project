package com.example.hotelreservationsystem.controller;

import com.example.hotelreservationsystem.model.dto.AdminDTO;
import com.example.hotelreservationsystem.model.dto.CreateHotelDTO;
import com.example.hotelreservationsystem.model.dto.CreateRoomDTO;
import com.example.hotelreservationsystem.service.AdminService;
import com.example.hotelreservationsystem.service.HotelService;
import com.example.hotelreservationsystem.service.RoomService;
import com.example.hotelreservationsystem.util.FilePath;
import com.example.hotelreservationsystem.util.JsonParser;
import com.example.hotelreservationsystem.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class SeedController {

    private final AdminService adminService;

    private final HotelService hotelService;

    private final RoomService roomService;


    @Autowired
    public SeedController(AdminService adminService, HotelService hotelService, RoomService roomService) {
        this.adminService = adminService;
        this.hotelService = hotelService;
        this.roomService = roomService;
    }

    public void seedAdmins() throws IOException {


        if (this.adminService.alreadySeeded()) {

            return;
        }
        AdminDTO[] adminDTOS = JsonParser.getDTOArray(AdminDTO[].class, FilePath.ADMINS_FILE_PATH);

        if (adminDTOS == null) {
            Logger.log(FilePath.RESULT_FILE_PATH, "Invalid input");
            return;
        }

        for (AdminDTO adminDTO : adminDTOS) {
            this.adminService.register(adminDTO);
        }
    }

    public void seedHotels() throws IOException {

        if (this.hotelService.alreadySeeded()) {
            return;
        }

        CreateHotelDTO[] hotelDTOS = JsonParser.getDTOArray(CreateHotelDTO[].class, FilePath.HOTELS_FILE_PATH);

        if (hotelDTOS == null) {
            Logger.log(FilePath.RESULT_FILE_PATH, "Invalid input");
            return;
        }

        for (CreateHotelDTO hotelDTO : hotelDTOS) {
            String result = this.hotelService.register(hotelDTO);
            Logger.log(FilePath.RESULT_FILE_PATH, result);
        }

    }

    public void seedRooms() throws IOException {

        if (this.roomService.alreadySeeded()) {
            return;
        }


        CreateRoomDTO[] createRoomDTOS = JsonParser.getDTOArray(CreateRoomDTO[].class, FilePath.ROOMS_FILE_PATH);

        if (createRoomDTOS == null) {
            Logger.log(FilePath.RESULT_FILE_PATH, "Invalid input");
            return;
        }

        for (CreateRoomDTO roomDTO : createRoomDTOS) {
            String result = this.roomService.register(roomDTO);

            Logger.log(FilePath.RESULT_FILE_PATH, result);

        }
    }
}






