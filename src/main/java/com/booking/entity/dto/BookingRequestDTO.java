package com.booking.entity.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Data;

@Data
public class BookingRequestDTO {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int hallId;
    private List<LoanRequestDTO> itemsLoans;
}
