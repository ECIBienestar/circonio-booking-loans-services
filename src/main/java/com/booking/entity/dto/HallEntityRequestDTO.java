package com.booking.entity.dto;

public class HallEntityRequestDTO {
    private String name;
    private String location;
    private String status;
    private String description;
    private int capacity;
    public String getName() {
        return name;
    }

    public HallEntityRequestDTO() {
        
    }

    public HallEntityRequestDTO(String name, String location, String status, String description, int capacity) {
        this.name = name;
        this.location = location;
        this.status = status;
        this.description = description;
        this.capacity = capacity;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}