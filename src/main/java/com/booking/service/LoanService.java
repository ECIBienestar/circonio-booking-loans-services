package com.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.booking.entity.LoanEntity;
import com.booking.repository.LoanRepository;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Transactional
    public LoanEntity save(LoanEntity loan) {
        return loanRepository.save(loan);
    }
}
