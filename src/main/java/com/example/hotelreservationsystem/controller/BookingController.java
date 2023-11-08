package com.example.hotelreservationsystem.controller;

import com.example.hotelreservationsystem.model.dto.CancelDTO;
import com.example.hotelreservationsystem.model.dto.ReservationDTO;
import com.example.hotelreservationsystem.service.ReservationService;
import com.example.hotelreservationsystem.util.FilePath;
import com.example.hotelreservationsystem.util.JsonParser;
import com.example.hotelreservationsystem.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class BookingController {


    private final ReservationService reservationService;

    @Autowired
    public BookingController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void cancelReservation() throws IOException {

        CancelDTO cancelDTO = JsonParser.getDTO(CancelDTO.class, FilePath.CANCEL_FILE_PATH);

        String result = this.reservationService.cancel(cancelDTO);

        Logger.log(FilePath.RESULT_FILE_PATH, result);
    }

    public void bookRoom() throws IOException {
        ReservationDTO reservationDTO = JsonParser.getDTO(ReservationDTO.class, FilePath.RESERVATION_FILE_PATH);

        String result = this.reservationService.makeReservation(reservationDTO);

        Logger.log(FilePath.RESULT_FILE_PATH, result);

    }

    }
