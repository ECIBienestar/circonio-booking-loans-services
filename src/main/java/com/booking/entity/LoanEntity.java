package com.booking.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "items_loans")
@Entity
public class LoanEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_item", nullable = false)
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
