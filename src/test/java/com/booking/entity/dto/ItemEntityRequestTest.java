package com.booking.entity.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemEntityRequestTest {

    @Test
    public void itemEntityRequestConstructorSetsAllFieldsCorrectly() {
        ItemEntityRequest item = new ItemEntityRequest("Laptop", "High-end gaming laptop", "Available", "Electronics", 10, true, 1);

        assertEquals("Laptop", item.getName());
        assertEquals("High-end gaming laptop", item.getDescription());
        assertEquals("Available", item.getStatus());
        assertEquals("Electronics", item.getCategory());
        assertEquals(10, item.getQuantity());
        assertTrue(item.isAvailable());
        assertEquals(1, item.getHall());
    }

    @Test
    public void itemEntityRequestHandlesNullValues() {
        ItemEntityRequest item = new ItemEntityRequest(null, null, null, null, 0, false, 0);

        assertNull(item.getName());
        assertNull(item.getDescription());
        assertNull(item.getStatus());
        assertNull(item.getCategory());
        assertEquals(0, item.getQuantity());
        assertFalse(item.isAvailable());
        assertEquals(0, item.getHall());
    }

    @Test
    public void itemEntityRequestHandlesEmptyStrings() {
        ItemEntityRequest item = new ItemEntityRequest("", "", "", "", 0, false, 0);

        assertEquals("", item.getName());
        assertEquals("", item.getDescription());
        assertEquals("", item.getStatus());
        assertEquals("", item.getCategory());
        assertEquals(0, item.getQuantity());
        assertFalse(item.isAvailable());
        assertEquals(0, item.getHall());
    }

    @Test
    public void itemEntityRequestSettersUpdateFieldsCorrectly() {
        ItemEntityRequest item = new ItemEntityRequest();
        item.setName("Projector");
        item.setDescription("4K resolution projector");
        item.setStatus("In Use");
        item.setCategory("Electronics");
        item.setQuantity(5);
        item.setAvailable(true);
        item.setHall(2);

        assertEquals("Projector", item.getName());
        assertEquals("4K resolution projector", item.getDescription());
        assertEquals("In Use", item.getStatus());
        assertEquals("Electronics", item.getCategory());
        assertEquals(5, item.getQuantity());
        assertTrue(item.isAvailable());
        assertEquals(2, item.getHall());
    }
}
