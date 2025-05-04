package com.booking.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class ItemEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    /**
     * Default constructor for the ItemEntity class.
     * Initializes a new instance of the ItemEntity without setting any fields.
     */
    public ItemEntity() {

    }

    /**
     * Constructs an ItemEntity with the specified details.
     *
     * @param id                the unique identifier of the item
     * @param name              the name of the item
     * @param description       a brief description of the item
     * @param status            the current status of the item (e.g., available, reserved)
     * @param category          the category to which the item belongs
     * @param quantity          the total quantity of the item
     * @param quantityAvailable the quantity of the item currently available
     * @param available         whether the item is available for booking
     * @param hall              the hall associated with the item
     */
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

    /**
     * Retrieves the unique identifier of the item.
     *
     * @return the ID of the item
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the item.
     *
     * @param id the unique identifier to set for the item
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the item.
     *
     * @return the name of the item as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the item.
     *
     * @param name the name to set for the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the description of the item.
     *
     * @return the description of the item as a String.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the item.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the status of the item.
     *
     * @return the current status as a String.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the item.
     *
     * @param status the new status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Retrieves the category of the item.
     *
     * @return the category of the item as a String.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the item.
     *
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Retrieves the quantity of the item.
     *
     * @return the quantity of the item as an integer.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the item.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Retrieves the quantity available for the item.
     *
     * @return the quantity available as an integer.
     */
    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    /**
     * Sets the quantity available for this item.
     *
     * @param quantityAvailable the quantity to set as available
     */
    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    /**
     * Checks if the item is available.
     *
     * @return {@code true} if the item is available, {@code false} otherwise.
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Sets the availability status of the item.
     *
     * @param available a boolean indicating whether the item is available (true) or not (false)
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Retrieves the associated HallEntity for this item.
     *
     * @return the HallEntity object linked to this item.
     */
    public HallEntity getHall() {
        return hall;
    }

    /**
     * Sets the hall associated with this item.
     *
     * @param hall the HallEntity to associate with this item
     */
    public void setHall(HallEntity hall) {
        this.hall = hall;
    }

}
