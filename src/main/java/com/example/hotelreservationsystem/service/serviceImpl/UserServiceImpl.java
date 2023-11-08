package com.example.hotelreservationsystem.service.serviceImpl;

import com.example.hotelreservationsystem.model.dto.LoginDTO;
import com.example.hotelreservationsystem.model.dto.UserRegisterDTO;
import com.example.hotelreservationsystem.model.entity.BankAccount;
import com.example.hotelreservationsystem.model.entity.User;
import com.example.hotelreservationsystem.repository.UserRepository;
import com.example.hotelreservationsystem.service.BankService;
import com.example.hotelreservationsystem.service.UserService;
import com.example.hotelreservationsystem.session.LoggedUser;
import com.example.hotelreservationsystem.util.InputValidator;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final Validator validator;

    private final UserRepository userRepository;

    private final LoggedUser userSession;

    private final ModelMapper mapper;

    private final PasswordEncoder passwordEncoder;

    private final BankService bankService;

    @Autowired
    public UserServiceImpl(Validator validator, UserRepository userRepository, LoggedUser userSession, ModelMapper mapper, PasswordEncoder passwordEncoder, BankService bankService) {
        this.validator = validator;
        this.userRepository = userRepository;
        this.userSession = userSession;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.bankService = bankService;
    }

    @Override
    public String register(UserRegisterDTO userRegisterDTO) {

        if (userRegisterDTO == null) {
            return "Invalid input";
        }

        String validationErrors = InputValidator.validate(userRegisterDTO);
        if (validationErrors != null) {
            return validationErrors;
        }
        Optional<User> optionalUser = this.userRepository
                .findByUsername(userRegisterDTO.getUsername());
        if (optionalUser.isPresent()) {
            return "Username not available!";
        }
        optionalUser = this.userRepository
                .findByEmail(userRegisterDTO.getEmail());
        if (optionalUser.isPresent()) {
            return "Email not available!";
        }
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            return "Passwords don't match!";
        }

        User user = mapper.map(userRegisterDTO, User.class);

        BankAccount bankAccount = this.bankService.register(userRegisterDTO.getBankAccountDTO());

        if (bankAccount == null) {
            return "Invalid Bank Account!";
        }

        String encryptedPassword = passwordEncoder.encode(userRegisterDTO.getPassword());
        user.setPassword(encryptedPassword);
        user.setBankAccount(bankAccount);

        this.userRepository.save(user);


        return "User Registration Successful";
    }

    @Override
    public String login(LoginDTO loginDTO) {

        if (loginDTO == null) {
            return "Invalid input";
        }

        String validationErrors = InputValidator.validate(loginDTO);
        if (validationErrors != null) {
            return validationErrors;
        }

        Optional<User> optionalUser = this.userRepository
                .findByUsername(loginDTO.getUsername());

        if (optionalUser.isEmpty()) {
            return "Wrong Username!";
        }
        User user = optionalUser.get();

        String rawPassword = loginDTO.getPassword();
        String encryptedPassword = user.getPassword();

        if (!passwordEncoder.matches(rawPassword, encryptedPassword)) {
            return "Wrong Password!";
        }
        this.userSession.login(user);
        return "Welcome " + user.getUsername();
    }

    @Override
    public boolean isLogged(String username) {
        Optional<User> optionalUser = this.userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return false;
        }
        User user = optionalUser.get();

        return user.getId().equals(this.userSession.getId());
    }

    @Override
    public String logout() {
        this.userSession.logout();
        return "No user logged";
    }


}
