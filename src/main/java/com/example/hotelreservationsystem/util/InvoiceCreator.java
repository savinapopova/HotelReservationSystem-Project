package com.example.hotelreservationsystem.util;

import com.example.hotelreservationsystem.model.entity.Reservation;

import java.time.LocalDate;

public class InvoiceCreator {

    public static String create(Reservation reservation) {

        StringBuilder builder = new StringBuilder();

        Long id = reservation.getId();

        String name = String.format("%s %s%n", reservation.getUser().getFirstName(),
                reservation.getUser().getLastName());
        String hotel = String.format("Hotel: %s%n", reservation.getHotel().getName());
        String room = String.format("Room: %s%n", reservation.getRoom().getRoomNumber());
        String period = String.format("Period: %s - %s%n", reservation.getCheckInDate(), reservation.getCheckOutDate());
        String roomType = String.format("Room Type: %s%n", reservation.getRoom().getType().toString());
        String totalCost = String.format("Total Sum: %s%n", reservation.getTotalCost().toString());
        String currentDate = String.format("Date: %s%n%n", LocalDate.now().toString());

        builder.append("INVOICE ").append(id)
                .append(System.lineSeparator())
                .append(System.lineSeparator())
                .append(name)
                .append(hotel)
                .append(room)
                .append(roomType)
                .append(period)
                .append(System.lineSeparator())
                .append(totalCost)
                .append(currentDate);

        return builder.toString();
}
}
