package com.booking.entity.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Data;

@Data
public class BookingRequestDTO {
    private String nameUser;
    private int idUser;
    private String rolUser;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int hallId;
    private List<LoanRequestDTO> itemsLoans;
}
