package com.booking.entity;

import jakarta.persistence.*;

/**
 * Represents a hall entity in the system.
 * This entity is mapped to the "halls" table in the database.
 * It contains information about a hall such as its ID, name, location, status, description, and capacity.
 * 
 * <p>Fields:</p>
 * <ul>
 *   <li>id - The unique identifier for the hall (primary key).</li>
 *   <li>name - The name of the hall (unique, nullable).</li>
 *   <li>location - The location of the hall (nullable).</li>
 *   <li>status - The status of the hall (e.g., available, unavailable) (nullable).</li>
 *   <li>description - A brief description of the hall (nullable).</li>
 *   <li>capacity - The maximum capacity of the hall (non-nullable).</li>
 * </ul>
 * 
 */
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

    /**
     * Default constructor for the HallEntity class.
     * Initializes a new instance of the HallEntity without setting any properties.
     */
    public HallEntity() {
        
    }

    /**
     * Constructs a new HallEntity with the specified details.
     *
     * @param id          the unique identifier of the hall
     * @param name        the name of the hall
     * @param location    the location of the hall
     * @param status      the current status of the hall (e.g., available, unavailable)
     * @param description a brief description of the hall
     * @param capacity    the maximum capacity of the hall
     */
    public HallEntity(int id, String name, String location, String status, String description, int capacity) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.status = status;
        this.description = description;
        this.capacity = capacity;
    }

    /**
     * Retrieves the unique identifier of the hall.
     *
     * @return the ID of the hall
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the hall.
     *
     * @param id the unique identifier to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the hall.
     *
     * @return the name of the hall as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the hall.
     *
     * @param name the name to set for the hall
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the location of the hall.
     *
     * @return the location of the hall as a String.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the hall.
     *
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Retrieves the status of the hall.
     *
     * @return the current status of the hall as a String.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the hall.
     *
     * @param status the new status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Retrieves the description of the hall.
     *
     * @return the description of the hall as a String.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the hall.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the capacity of the hall.
     *
     * @return the capacity of the hall as an integer.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the hall.
     *
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}