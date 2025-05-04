package com.booking.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "items_loans")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    @JsonBackReference
    private BookingEntity bookingId;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private ItemEntity itemId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "return_status", length = 50)
    private String returnStatus;

}
