package com.example.hotelreservationsystem.service.serviceImpl;

import com.example.hotelreservationsystem.model.dto.BankAccountDTO;
import com.example.hotelreservationsystem.model.entity.BankAccount;
import com.example.hotelreservationsystem.model.entity.Reservation;
import com.example.hotelreservationsystem.repository.BankRepository;
import com.example.hotelreservationsystem.service.BankService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;


@Service
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    private final Validator validator;

    private final ModelMapper mapper;

    public BankServiceImpl(BankRepository bankRepository, Validator validator, ModelMapper mapper) {
        this.bankRepository = bankRepository;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public BankAccount register(BankAccountDTO bankAccountDTO) {

        if (bankAccountDTO == null) {
            return null;
        }

        Set<ConstraintViolation<BankAccountDTO>> violations = validator.validate(bankAccountDTO);
        if (!violations.isEmpty()) {
            return null;
        }
        BankAccount bankAccount = mapper.map(bankAccountDTO, BankAccount.class);
        this.bankRepository.save(bankAccount);
        return bankAccount;
    }

    @Override
    public boolean processPayment(Reservation reservation) {

        BankAccount userAccount = reservation.getUser().getBankAccount();
        BankAccount hotelAccount = reservation.getHotel().getBankAccount();

        BigDecimal userAccountDeposit = reservation.getUser().getBankAccount().getDeposit();
        BigDecimal hotelAccountDeposit = reservation.getHotel().getBankAccount().getDeposit();
        BigDecimal totalCost = reservation.getTotalCost();

        if (totalCost.compareTo(userAccountDeposit) > 0) {
            return false;
        }

        userAccountDeposit = userAccountDeposit.subtract(totalCost);
        hotelAccountDeposit = hotelAccountDeposit.add(totalCost);
        userAccount.setDeposit(userAccountDeposit);
        hotelAccount.setDeposit(hotelAccountDeposit);
        this.bankRepository.save(userAccount);
        this.bankRepository.save(hotelAccount);

        return true;


    }

    @Override
    public void cancelPayment(Reservation reservation) {
        BigDecimal totalAmount = reservation.getTotalCost();
        BigDecimal cancellationFee = reservation.getRoom().getCancellationFee();

        BankAccount userAccount = reservation.getUser().getBankAccount();
        BigDecimal userDeposit = userAccount.getDeposit();

        BankAccount hotelAccount = reservation.getHotel().getBankAccount();
        BigDecimal hotelDeposit = hotelAccount.getDeposit();

        BigDecimal returnAmount = totalAmount.subtract(cancellationFee);

        userDeposit = userDeposit.add(returnAmount);
        hotelDeposit = hotelDeposit.subtract(returnAmount);

        userAccount.setDeposit(userDeposit);
        hotelAccount.setDeposit(hotelDeposit);

        this.bankRepository.save(userAccount);
        this.bankRepository.save(hotelAccount);
    }
}
