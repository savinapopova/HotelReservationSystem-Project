package com.example.hotelreservationsystem.repository;

import com.example.hotelreservationsystem.model.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByName(String name);

    List<Hotel> findAllByOrderByName();
}
