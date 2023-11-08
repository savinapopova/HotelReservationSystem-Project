package com.example.hotelreservationsystem.service.serviceImpl;

import com.example.hotelreservationsystem.model.dto.AdminDTO;
import com.example.hotelreservationsystem.model.dto.LoginDTO;
import com.example.hotelreservationsystem.model.entity.Admin;
import com.example.hotelreservationsystem.repository.AdminRepository;
import com.example.hotelreservationsystem.service.AdminService;
import com.example.hotelreservationsystem.service.HotelService;
import com.example.hotelreservationsystem.service.ReservationService;
import com.example.hotelreservationsystem.service.RoomService;
import com.example.hotelreservationsystem.session.LoggedAdmin;
import com.example.hotelreservationsystem.util.InputValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AdminServiceImpl implements AdminService {



    private final AdminRepository adminRepository;

    private final HotelService hotelService;

    private final RoomService roomService;

    private final ReservationService reservationService;

    private final Validator validator;

    private final ModelMapper mapper;

    private final PasswordEncoder passwordEncoder;

    private final LoggedAdmin loggedAdmin;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, HotelService hotelService, RoomService roomService, ReservationService reservationService, Validator validator, ModelMapper mapper, PasswordEncoder passwordEncoder, LoggedAdmin loggedAdmin) {
        this.adminRepository = adminRepository;
        this.hotelService = hotelService;
        this.roomService = roomService;
        this.reservationService = reservationService;
        this.validator = validator;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.loggedAdmin = loggedAdmin;
    }


    @Override
    public void register(AdminDTO adminDTO) {
        if (adminDTO == null) {

            return ;
        }

        Set<ConstraintViolation<AdminDTO>> violations = validator.validate(adminDTO);

        if (!violations.isEmpty()) {
            return;
        }
        if (!adminDTO.getPassword().equals(adminDTO.getConfirmPassword())) {
            return;
        }
        Optional<Admin> optionalAdmin = this.adminRepository
                .findByUsernameOrEmail(adminDTO.getUsername(), adminDTO.getEmail());
        if (optionalAdmin.isPresent()) {
            return;
        }
        Admin admin = mapper.map(adminDTO, Admin.class);
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        this.adminRepository.save(admin);
    }

    @Override
    public boolean alreadySeeded() {
        return this.adminRepository.count() > 0;
    }

    @Override
    public String login(LoginDTO adminDTO) {

        if (adminDTO == null) {
            return "Invalid input";
        }

        String validationErrors = InputValidator.validate(adminDTO);
        if (validationErrors != null) {
            return validationErrors;
        }

        Optional<Admin> optionalAdmin = this.adminRepository.findByUsername(adminDTO.getUsername());

        if (optionalAdmin.isEmpty()) {
            return "Wrong Username";
        }

        Admin admin = optionalAdmin.get();

        String rawPassword = adminDTO.getPassword();
        String encryptedPassword = admin.getPassword();

        if (!passwordEncoder.matches(rawPassword, encryptedPassword)) {
            return "Wrong Password!";
        }
        this.loggedAdmin.login(admin);
        return "ADMIN MODE ON - Welcome " + admin.getUsername() + " - Go To Admin Console";
    }

    @Override
    public boolean isLogged(String username) {
        Optional<Admin> optionalAdmin = this.adminRepository.findByUsername(username);
        if (optionalAdmin.isEmpty()) {
            return false;
        }
        Admin admin = optionalAdmin.get();

        return admin.getId().equals(this.loggedAdmin.getId());
    }

    @Override
    public String viewHotels() {

        String result = this.hotelService.getHotelsList();

        return result;
    }

    @Override
    public String viewRooms() {
       String result = this.roomService.getRoomList();

       return result;
    }

    @Override
    public String viewBookings() {
        String result = this.reservationService.getReservationList();

        return result;
    }

    @Override
    public String viewDeposits() {
       String result = this.hotelService.getDepositList();
       return result;
    }

    @Override
    public String logout() {
        this.loggedAdmin.logout();
        return "No user logged";
    }


}
