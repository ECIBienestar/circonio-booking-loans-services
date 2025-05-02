package com.booking.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.booking.entity.HallEntity;

@Repository
public interface HallRepository extends JpaRepository<HallEntity, Integer> {
    
}
