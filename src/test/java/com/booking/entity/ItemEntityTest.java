package com.booking.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ItemEntityTest {

    @Test
    public void testItemCreation() {
        ItemEntity item = new ItemEntity();
        item.setName("Projector");
        
        assertEquals("Projector", item.getName());
    }

    @Test
    public void testItemId() {
        ItemEntity item = new ItemEntity();
        item.setId(1);
        
        assertEquals(1, item.getId());
    }

    @Test
    public void testItemHallId() {
        ItemEntity item = new ItemEntity();
        item.setId(2);
        
        assertEquals(2, item.getId());
    }

    @Test
    public void testItemStatus() {
        ItemEntity item = new ItemEntity();
        item.setStatus("Available");
        
        assertEquals("Available", item.getStatus());
    }

    @Test
    public void testHallLocation() {
        ItemEntity item = new ItemEntity();
        HallEntity hall = new HallEntity(1, "Room 101", "Conference Hall", "Available", "Projector", 100);
        item.setHall(hall);
        assertEquals("Room 101", item.getHall().getName());
    }
    
}