package com.booking.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "booking")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @Column(name = "name_user", nullable = false)
    private String nameUser;

    @Column(name = "id_user", nullable = false)
    private int idUser;

    @Column(name = "rol_user", nullable = false)
    private String rolUser;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time_start_booking", nullable = false)
    private LocalTime timeStartBooking;

    @Column(name = "time_end_booking", nullable = false)
    private LocalTime timeEndBooking;

    @ManyToOne
    @JoinColumn(name = "hall_id", referencedColumnName = "id")
    private HallEntity hallId;

    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "bookingId", targetEntity = LoanEntity.class)
    @JsonManagedReference
    @Builder.Default
    private List<LoanEntity> itemsLoans = new ArrayList<>();

}
