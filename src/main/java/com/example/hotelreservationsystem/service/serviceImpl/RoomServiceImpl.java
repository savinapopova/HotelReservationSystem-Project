package com.example.hotelreservationsystem.service.serviceImpl;

import com.example.hotelreservationsystem.model.dto.CreateRoomDTO;
import com.example.hotelreservationsystem.model.entity.Hotel;
import com.example.hotelreservationsystem.model.entity.Room;
import com.example.hotelreservationsystem.repository.HotelRepository;
import com.example.hotelreservationsystem.repository.RoomRepository;
import com.example.hotelreservationsystem.service.RoomService;
import com.example.hotelreservationsystem.util.InputValidator;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final HotelRepository hotelRepository;

    private final Validator validator;

    private final ModelMapper mapper;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, HotelRepository hotelRepository, Validator validator, ModelMapper mapper) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public String register(CreateRoomDTO roomDTO) {

        if (roomDTO == null) {
            return "Invalid input";
        }

        String validationErrors = InputValidator.validate(roomDTO);
        if (validationErrors != null) {
            return validationErrors;
        }

           Optional<Hotel> optionalHotel = this.hotelRepository.findByName(roomDTO.getHotel());

                if (optionalHotel.isEmpty()) {
                    return "Hotel doesn't exist";
                }

               Optional<Room> optionalRoom = this.roomRepository
                       .findByHotelNameAndRoomNumber(roomDTO.getHotel(), roomDTO.getRoomNumber());

                if (optionalRoom.isPresent()) {
                    return "This room already exists";
                }


                    Hotel hotel = optionalHotel.get();

                    Room room = mapper.map(roomDTO, Room.class);

                    room.setHotel(hotel);

                    this.roomRepository.save(room);

                    return "Room created successfully";

            }



    @Override
    public boolean alreadySeeded() {

        return this.roomRepository.count() > 0;
    }

    @Override
    public String getRoomList() {

        List<Room> rooms = this.roomRepository
                .findAllByOrderByHotelNameAscTypeAscRoomNumberAsc();

        if (rooms.isEmpty()) {
            return "No rooms";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Rooms:")
                .append(System.lineSeparator())
                .append(System.lineSeparator());

        for (Room room : rooms) {
            String hotel = String.format("Hotel: %s%n",room.getHotel().getName());
            String number = String.format("Room number: %d%n",room.getRoomNumber());
            String type = String.format("Room type: %s%n",room.getType());
            String floor = String.format("Floor: %d%n",room.getFloor());
            String price = String.format("Price per night: %s%n",room.getPricePerNight());
            String cancellationFee = String.format("Cancellation fee: %s%n",room.getCancellationFee());
            String reservations = String.format("Reservations: %s%n%n",room.getReservations().size());

            builder
                    .append(number)
                    .append(hotel)
                    .append(type)
                    .append(floor)
                    .append(price)
                    .append(cancellationFee)
                    .append(reservations);

        }

        return builder.toString();
    }
}
