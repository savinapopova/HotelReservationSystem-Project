package com.example.hotelreservationsystem.repository;

import com.example.hotelreservationsystem.model.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<BankAccount, Long> {
}
