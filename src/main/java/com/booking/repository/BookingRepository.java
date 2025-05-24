package com.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.entity.BookingEntity;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {
    List<BookingEntity> findByIdUser(String idUser);
    //List<BookingEntity> findByHallId(int id);
}