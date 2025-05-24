package com.booking.entity.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class HallEntityRequestDTOTest {

    @Test
    public void hallEntityRequestDTOConstructorSetsAllFieldsCorrectly() {
        HallEntityRequestDTO hall = new HallEntityRequestDTO("Main Hall", "First Floor", "Available", "Spacious hall for events", 200);

        assertEquals("Main Hall", hall.getName());
        assertEquals("First Floor", hall.getLocation());
        assertEquals("Available", hall.getStatus());
        assertEquals("Spacious hall for events", hall.getDescription());
        assertEquals(200, hall.getCapacity());
    }

    @Test
    public void hallEntityRequestDTOHandlesNullValues() {
        HallEntityRequestDTO hall = new HallEntityRequestDTO(null, null, null, null, 0);

        assertNull(hall.getName());
        assertNull(hall.getLocation());
        assertNull(hall.getStatus());
        assertNull(hall.getDescription());
        assertEquals(0, hall.getCapacity());
    }

    @Test
    public void hallEntityRequestDTOHandlesEmptyStrings() {
        HallEntityRequestDTO hall = new HallEntityRequestDTO("", "", "", "", 0);

        assertEquals("", hall.getName());
        assertEquals("", hall.getLocation());
        assertEquals("", hall.getStatus());
        assertEquals("", hall.getDescription());
        assertEquals(0, hall.getCapacity());
    }

    @Test
    public void hallEntityRequestDTOSettersUpdateFieldsCorrectly() {
        HallEntityRequestDTO hall = new HallEntityRequestDTO();
        hall.setName("Conference Room");
        hall.setLocation("Second Floor");
        hall.setStatus("Occupied");
        hall.setDescription("Room equipped with a projector");
        hall.setCapacity(50);

        assertEquals("Conference Room", hall.getName());
        assertEquals("Second Floor", hall.getLocation());
        assertEquals("Occupied", hall.getStatus());
        assertEquals("Room equipped with a projector", hall.getDescription());
        assertEquals(50, hall.getCapacity());
    }
}
