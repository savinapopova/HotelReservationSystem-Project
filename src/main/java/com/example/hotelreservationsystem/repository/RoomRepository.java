package com.example.hotelreservationsystem.repository;

import com.example.hotelreservationsystem.model.entity.Room;
import com.example.hotelreservationsystem.model.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByType(RoomType roomType);

    List<Room> findAllByOrderByHotelNameAscTypeAscRoomNumberAsc();

    Optional<Room> findByHotelNameAndRoomNumber(String hotel, int roomNumber);
}
