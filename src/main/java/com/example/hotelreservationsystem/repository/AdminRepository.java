package com.example.hotelreservationsystem.repository;

import com.example.hotelreservationsystem.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsernameOrEmail(String username, String email);

    Optional<Admin> findByUsername(String username);
}
