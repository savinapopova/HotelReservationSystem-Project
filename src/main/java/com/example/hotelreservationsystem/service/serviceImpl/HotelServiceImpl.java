package com.example.hotelreservationsystem.service.serviceImpl;

import com.example.hotelreservationsystem.model.dto.CreateHotelDTO;
import com.example.hotelreservationsystem.model.entity.BankAccount;
import com.example.hotelreservationsystem.model.entity.Hotel;
import com.example.hotelreservationsystem.repository.HotelRepository;
import com.example.hotelreservationsystem.service.BankService;
import com.example.hotelreservationsystem.service.HotelService;
import com.example.hotelreservationsystem.util.InputValidator;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    private final BankService bankService;


    private final Validator validator;

    private final ModelMapper mapper;


    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository, BankService bankService, Validator validator, ModelMapper mapper) {
        this.hotelRepository = hotelRepository;
        this.bankService = bankService;
        this.validator = validator;
        this.mapper = mapper;
    }


    @Override
    public String register(CreateHotelDTO hotelDTO) {

        if (hotelDTO == null) {
            return "Invalid input";
        }

        String validationErrors = InputValidator.validate(hotelDTO);
        if (validationErrors != null) {
            return validationErrors;
        }
        Optional<Hotel> optionalHotel = this.hotelRepository.findByName(hotelDTO.getName());

        if (optionalHotel.isPresent()) {
            return "Hotel already exists";
        }

        Hotel hotel = mapper.map(hotelDTO, Hotel.class);

        BankAccount bankAccount = bankService.register(hotelDTO.getBankAccount());

        if (bankAccount == null) {
            return "Invalid Bank Account!";
        }

        hotel.setBankAccount(bankAccount);

        this.hotelRepository.save(hotel);

        return "Hotel added successfully!";
    }

    @Override
    public boolean alreadySeeded() {
        return this.hotelRepository.count() > 0;
    }

    @Override
    public String getHotelsList() {

        List<Hotel> hotels = this.hotelRepository.findAllByOrderByName();

        if (hotels.isEmpty()) {
            return "No hotels";
        }

        StringBuilder builder = new StringBuilder();

        builder.append("Hotels:")
                .append(System.lineSeparator())
                .append(System.lineSeparator());

        for (Hotel hotel : hotels) {
            String name = String.format("Name: %s%n", hotel.getName());
            String country = String.format("Country: %s%n", hotel.getCountry());
            String stars = String.format("Stars: %s%n", hotel.getStars());
            String floors = String.format("Floors: %d%n", hotel.getFloors());
            String rooms = String.format("Rooms: %d%n%n", hotel.getRooms().size());

            builder.append(name)
                    .append(country)
                    .append(stars)
                    .append(floors)
                    .append(rooms);
        }

        return builder.toString();
    }

    @Override
    public String getDepositList() {

        List<Hotel> hotels = this.hotelRepository.findAllByOrderByName();

        if (hotels.isEmpty()) {
            return "No deposits";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Deposits:")
                .append(System.lineSeparator());

        for (Hotel hotel : hotels) {
            String hotelName = String.format("Hotel: %s%n", hotel.getName());
            String bank = String.format("Bank: %s%n", hotel.getBankAccount().getBank());
            String deposit = String.format("Deposit: %s%n%n", hotel.getBankAccount().getDeposit());

            builder
                    .append(hotelName)
                    .append(bank)
                    .append(deposit);
        }
        return builder.toString();
    }
}
