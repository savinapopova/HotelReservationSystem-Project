package com.example.hotelreservationsystem.service.serviceImpl;

import com.example.hotelreservationsystem.model.dto.CancelDTO;
import com.example.hotelreservationsystem.model.dto.ReservationDTO;
import com.example.hotelreservationsystem.model.entity.Hotel;
import com.example.hotelreservationsystem.model.entity.Reservation;
import com.example.hotelreservationsystem.model.entity.Room;
import com.example.hotelreservationsystem.model.entity.User;
import com.example.hotelreservationsystem.model.enums.RoomType;
import com.example.hotelreservationsystem.repository.HotelRepository;
import com.example.hotelreservationsystem.repository.ReservationRepository;
import com.example.hotelreservationsystem.repository.RoomRepository;
import com.example.hotelreservationsystem.repository.UserRepository;
import com.example.hotelreservationsystem.service.BankService;
import com.example.hotelreservationsystem.service.ReservationService;
import com.example.hotelreservationsystem.session.LoggedUser;
import com.example.hotelreservationsystem.util.FilePath;
import com.example.hotelreservationsystem.util.InputValidator;
import com.example.hotelreservationsystem.util.InvoiceCreator;
import com.example.hotelreservationsystem.util.Logger;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final RoomRepository roomRepository;

    private final HotelRepository hotelRepository;

    private final UserRepository userRepository;

    private final BankService bankService;

    private final Validator validator;

    private final ModelMapper mapper;

    private final LoggedUser loggedUser;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, RoomRepository roomRepository,
                                  HotelRepository hotelRepository,
                                  UserRepository userRepository, BankService bankService, Validator validator, ModelMapper mapper, LoggedUser loggedUser) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
        this.bankService = bankService;
        this.validator = validator;
        this.mapper = mapper;
        this.loggedUser = loggedUser;
    }


    @Override
    public String makeReservation(ReservationDTO reservationDTO) {

        if (reservationDTO == null) {
            return "Invalid input";
        }

        String validationErrors = InputValidator.validate(reservationDTO);
        if (validationErrors != null) {
            return validationErrors;
        }
        Optional<Hotel> optionalHotel = this.hotelRepository.findByName(reservationDTO.getHotel());

        String invalidData = findInvalidData(reservationDTO, optionalHotel);
        if (invalidData != null) {
            return invalidData;
        }
        Hotel hotel = optionalHotel.get();

        List<Room> rooms = getRooms(reservationDTO.getRoomType(), hotel);

        Room availableRoom = findRoom(rooms, reservationDTO);

        if (availableRoom == null) {
            return "No room available";
        }

        BigDecimal totalCost = getTotalCost(reservationDTO, availableRoom);

        User user = this.userRepository.findById(this.loggedUser.getId()).get();

        Reservation reservation = mapper.map(reservationDTO, Reservation.class);
        reservation.setHotel(hotel)
                .setUser(user)
                .setRoom(availableRoom)
                .setTotalCost(totalCost);


        if (!processPayment(reservation)) {
            return "Failed payment!";
        }

        this.reservationRepository.save(reservation);

        issueInvoice(reservation);


        return "Reservation successful";
    }

    @Override
    public String cancel(CancelDTO cancelDTO) {

        if (cancelDTO == null) {
            return "Invalid input";
        }

        String validationErrors = InputValidator.validate(cancelDTO);
        if (validationErrors != null) {
            return validationErrors;
        }

            Optional<Reservation> optionalReservation = this.reservationRepository.findById(cancelDTO.getId());

            if (optionalReservation.isEmpty()) {
                return "No such reservation";
            }

            Reservation reservation = optionalReservation.get();

            this.bankService.cancelPayment(reservation);

            this.reservationRepository.delete(reservation);

            return "Reservation cancelled";
        }

    @Override
    public String getReservationList() {
      List<Reservation> reservations = this.reservationRepository.findAllByOrderByHotelNameAsc();

      if (reservations.isEmpty()) {
          return "No reservations";
      }

      StringBuilder builder = new StringBuilder();

      builder.append("Reservations:")
              .append(System.lineSeparator())
              .append(System.lineSeparator());

        for (Reservation reservation : reservations) {
            String client = String.format("%s %s%n", reservation.getUser().getFirstName(),
                    reservation.getUser().getLastName());
            String hotel = String.format("Hotel: %s%n", reservation.getHotel().getName());
            String roomType = String.format("Room type: %s%n", reservation.getRoom().getType());
            String room = String.format("Room type: %d%n", reservation.getRoom().getRoomNumber());
            String checkIn = String.format("Check in date: %s%n", reservation.getCheckInDate());
            String checkOut = String.format("Check out date: %s%n", reservation.getCheckOutDate());
            String sum = String.format("Total sum: %s%n%n", reservation.getTotalCost());

            builder
                    .append(client)
                    .append(hotel)
                    .append(roomType)
                    .append(room)
                    .append(checkIn)
                    .append(checkOut)
                    .append(sum);
        }
        return builder.toString();
    }


    private void issueInvoice(Reservation reservation) {

        String invoice = InvoiceCreator.create(reservation);
        Logger.log(FilePath.INVOICE_FILE_PATH, invoice);
    }

    private boolean processPayment(Reservation reservation) {

       return this.bankService.processPayment(reservation);
    }

    private BigDecimal getTotalCost(ReservationDTO reservationDTO, Room availableRoom) {
        long nights = ChronoUnit.DAYS
                .between(reservationDTO.getCheckInDate(), reservationDTO.getCheckOutDate());
        return availableRoom.getPricePerNight().multiply(BigDecimal.valueOf(nights));
    }

    private Room findRoom(List<Room> rooms, ReservationDTO reservationDTO) {

        LocalDate requestCheckInDate = reservationDTO.getCheckInDate();
        LocalDate requestCheckOutDate = reservationDTO.getCheckOutDate();


//        for (Room room : rooms) {
//
//
//
//            Reservation reservation = room.getReservations().stream()
//                    .filter(r -> !((requestCheckInDate.isBefore(r.getCheckInDate()) &&
//                            (requestCheckOutDate.isBefore(r.getCheckInDate())
//                            || requestCheckOutDate.isEqual(r.getCheckInDate())))
//                            ||
//                            (requestCheckInDate.isAfter(r.getCheckOutDate()) ||
//                            requestCheckInDate.isEqual(r.getCheckOutDate()))))
//                    .findFirst().orElse(null);
//            if (reservation == null) {
//                availableRoom = room;
//                break;
//            }
//        }
        return rooms.stream()
                .filter(room -> room.getReservations().stream()
                        .noneMatch(r -> (requestCheckInDate.isBefore(r.getCheckOutDate()) &&
                                requestCheckOutDate.isAfter(r.getCheckInDate())))
                )
                .findFirst()
                .orElse(null);
    }


    private static List<Room> getRooms(RoomType roomType, Hotel hotel) {
        return hotel.getRooms().stream().filter(room -> room.getType().equals(roomType))
                .collect(Collectors.toList());
    }

    private String findInvalidData(ReservationDTO reservationDTO, Optional<Hotel> optionalHotel) {
        if (!reservationDTO.getCheckInDate().isBefore(reservationDTO.getCheckOutDate())) {
            return "Invalid dates";
        }


        if (optionalHotel.isEmpty()) {
            return "Invalid Hotel";
        }
        List<Room> optionalRooms = this.roomRepository.findByType(reservationDTO.getRoomType());
        if (optionalRooms.isEmpty()) {
            return "Invalid Room Type";
        }
        return null;
    }


}

