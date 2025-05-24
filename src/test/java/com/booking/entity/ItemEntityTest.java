package com.booking.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ItemEntityTest {

    @Test
    void testItemId() {
        ItemEntity item = new ItemEntity();
        item.setId(1);

        assertEquals(1, item.getId());
    }

    @Test
    void testItemCreation() {
        ItemEntity item = new ItemEntity();
        item.setName("Projector");
        
        assertEquals("Projector", item.getName());
    }

    @Test
    void testItemDescription() {
        ItemEntity item = new ItemEntity();
        item.setDescription("A high-quality projector for presentations.");

        assertEquals("A high-quality projector for presentations.", item.getDescription());
    }

    @Test
    void testItemStatus() {
        ItemEntity item = new ItemEntity();
        item.setStatus("Available");

        assertEquals("Available", item.getStatus());
    }

    @Test
    void testItemCategory() {
        ItemEntity item = new ItemEntity();
        item.setCategory("Electronics");

        assertEquals("Electronics", item.getCategory());
    }

    @Test
    void testItemQuantity() {
        ItemEntity item = new ItemEntity();
        item.setQuantity(10);

        assertEquals(10, item.getQuantity());
    }

    @Test
    void testItemHallId() {
        ItemEntity item = new ItemEntity();
        item.setId(2);
        
        assertEquals(2, item.getId());
    }

    @Test
    void testItemQuantityAvailable() {
        ItemEntity item = new ItemEntity();
        item.setQuantityAvailable(5);

        assertEquals(5, item.getQuantityAvailable());
    }
    @Test
    void testItemAvailable() {
        ItemEntity item = new ItemEntity();
        item.setAvailable(true);

        assertEquals(true, item.isAvailable());
    }

    @Test
    void testHallLocation() {
        ItemEntity item = new ItemEntity();
        HallEntity hall = new HallEntity(1, "Room 101", "Conference Hall", "Available", "Projector", 100);
        item.setHall(hall);
        assertEquals("Room 101", item.getHall().getName());
    }

    @Test
    void itemEntityConstructorSetsAllFieldsCorrectly() {
        HallEntity hall = new HallEntity(1, "Room 101", "Conference Hall", "Available", "Projector", 100);
        ItemEntity item = new ItemEntity(1, "Projector", "A high-quality projector for presentations.", "Available", "Electronics", 10, 5, true, hall);

        assertEquals(1, item.getId());
        assertEquals("Projector", item.getName());
        assertEquals("A high-quality projector for presentations.", item.getDescription());
        assertEquals("Available", item.getStatus());
        assertEquals("Electronics", item.getCategory());
        assertEquals(10, item.getQuantity());
        assertEquals(5, item.getQuantityAvailable());
        assertEquals(true, item.isAvailable());
        assertEquals(hall, item.getHall());
    }

    @Test
    void itemEntityConstructorHandlesNullHall() {
        ItemEntity item = new ItemEntity(1, "Projector", "A high-quality projector for presentations.", "Available", "Electronics", 10, 5, true, null);

        assertEquals(1, item.getId());
        assertEquals("Projector", item.getName());
        assertEquals("A high-quality projector for presentations.", item.getDescription());
        assertEquals("Available", item.getStatus());
        assertEquals("Electronics", item.getCategory());
        assertEquals(10, item.getQuantity());
        assertEquals(5, item.getQuantityAvailable());
        assertEquals(true, item.isAvailable());
        assertNull(item.getHall());
    }

    @Test
    void itemEntityConstructorHandlesEmptyStrings() {
        HallEntity hall = new HallEntity(1, "Room 101", "Conference Hall", "Available", "Projector", 100);
        ItemEntity item = new ItemEntity(0, "", "", "", "", 0, 0, false, hall);

        assertEquals(0, item.getId());
        assertEquals("", item.getName());
        assertEquals("", item.getDescription());
        assertEquals("", item.getStatus());
        assertEquals("", item.getCategory());
        assertEquals(0, item.getQuantity());
        assertEquals(0, item.getQuantityAvailable());
        assertEquals(false, item.isAvailable());
        assertEquals(hall, item.getHall());
    }
    
}