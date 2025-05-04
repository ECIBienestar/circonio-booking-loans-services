package com.booking.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class ItemEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private int id;
    @Column(name = "name", nullable = true, unique = true)
    private String name;
    @Column(name = "description", nullable = true)
    private String description;
    @Column(name = "status", nullable = true)
    private String status;
    @Column(name = "category", nullable = true)
    private String category;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "quantity_available", nullable = false)
    private int quantityAvailable;
    @Column(name = "available", nullable = false)
    private boolean available;
    @ManyToOne
    @JoinColumn(name = "hall_id", referencedColumnName = "id")
    private HallEntity hall;

    public ItemEntity() {

    }

    public ItemEntity(int id, String name, String description, String status, String category, int quantity,
            int quantityAvailable, boolean available, HallEntity hall) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.category = category;
        this.quantity = quantity;
        this.quantityAvailable = quantityAvailable;
        this.available = available;
        this.hall = hall;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public HallEntity getHall() {
        return hall;
    }

    public void setHall(HallEntity hall) {
        this.hall = hall;
    }

}