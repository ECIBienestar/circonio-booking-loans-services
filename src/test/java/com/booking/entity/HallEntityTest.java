
package com.booking.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;


public class HallEntityTest {


    @Test
    public void testHallCreation() {
        HallEntity hall = new HallEntity();
        hall.setName("Main Hall");
        
        assertEquals("Main Hall", hall.getName());
    }

    @Test
    public void testHallId() {
        HallEntity hall = new HallEntity();
        hall.setId(1);
        
        assertEquals(1, hall.getId());
    }

    @Test
    public void testHallLocation() {
        HallEntity hall = new HallEntity();
        hall.setLocation("Sala Crea");
        
        assertEquals("Sala Crea", hall.getLocation());
    }

    @Test
    public void testHallStatus() {
        HallEntity hall = new HallEntity();
        hall.setStatus("Available");
        
        assertEquals("Available", hall.getStatus());
    }

    @Test
    public void testHallCapacity() {
        HallEntity hall = new HallEntity();
        hall.setCapacity(100);

        assertEquals(100, hall.getCapacity());
    }

    @Test
    public void testHallDescription() {
        HallEntity hall = new HallEntity();
        hall.setDescription("A spacious hall for events.");

        assertEquals("A spacious hall for events.", hall.getDescription());
    }
    
}