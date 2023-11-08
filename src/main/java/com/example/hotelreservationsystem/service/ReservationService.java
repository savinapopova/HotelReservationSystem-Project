package com.example.hotelreservationsystem.service;

import com.example.hotelreservationsystem.model.dto.CancelDTO;
import com.example.hotelreservationsystem.model.dto.ReservationDTO;

public interface ReservationService {
    String makeReservation(ReservationDTO reservationDTO);

    String cancel(CancelDTO cancelDTO);

    String getReservationList();
}
