package com.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.entity.LoanEntity;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Integer> {
    
}
