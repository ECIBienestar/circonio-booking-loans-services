package com.booking.entity.dto;

public class ItemEntityRequest {
    private String name;
    private String description;
    private String status;
    private String category;
    private int quantity;
    private boolean available;
    private int hall;

    public ItemEntityRequest(String name, String description, String status, String category, int quantity, boolean available, int hall) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.category = category;
        this.quantity = quantity;
        this.available = available;
        this.hall = hall;
    }

    public ItemEntityRequest(){
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
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
    public int getHall() {
        return hall;
    }
    public void setHall(Integer hall) {
        this.hall = hall;
    }
}