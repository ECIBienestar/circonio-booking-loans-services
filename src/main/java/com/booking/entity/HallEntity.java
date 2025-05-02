package com.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "halls")
public class HallEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private int id;
    @Column(name = "name", nullable = true, unique = true)
    private String name;
    @Column(name = "location", nullable = true)
    private String location;
    @Column(name = "status", nullable = true)
    private String status;
    @Column(name = "description", nullable = true)
    private String description;
    @Column(name = "capacity", nullable = false)
    private int capacity;
}
