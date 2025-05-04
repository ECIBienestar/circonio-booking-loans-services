package com.booking.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "booking")
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

    @Column(name = "time_start_booking", nullable = false)
    private LocalDateTime timeStartBooking;

    @Column(name = "time_end_booking", nullable = false)
    private LocalDateTime timeEndBooking;

    @ManyToOne
    @JoinColumn(name = "hall_id", referencedColumnName = "id")
    private HallEntity hallId;

    @Column(name = "status", nullable = false)
    private String status;


}
