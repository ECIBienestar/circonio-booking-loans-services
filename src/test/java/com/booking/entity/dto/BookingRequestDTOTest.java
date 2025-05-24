package com.booking.entity.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingRequestDTOTest {
    @Test
    public void bookingRequestDTOHandlesValidDataCorrectly() {
        List<LoanRequestDTO> itemsLoans = new ArrayList<>();
        BookingRequestDTO bookingRequest = new BookingRequestDTO();
        bookingRequest.setDate(LocalDate.of(2023, 10, 15));
        bookingRequest.setStartTime(LocalTime.of(9, 0));
        bookingRequest.setEndTime(LocalTime.of(11, 0));
        bookingRequest.setHallId(5);
        bookingRequest.setItemsLoans(itemsLoans);

        assertEquals(LocalDate.of(2023, 10, 15), bookingRequest.getDate());
        assertEquals(LocalTime.of(9, 0), bookingRequest.getStartTime());
        assertEquals(LocalTime.of(11, 0), bookingRequest.getEndTime());
        assertEquals(5, bookingRequest.getHallId());
        assertEquals(itemsLoans, bookingRequest.getItemsLoans());
    }

    @Test
    public void bookingRequestDTOEqualsMethod() {
        BookingRequestDTO bookingRequest1 = new BookingRequestDTO();

        bookingRequest1.setDate(LocalDate.of(2023, 10, 15));
        bookingRequest1.setStartTime(LocalTime.of(9, 0));
        bookingRequest1.setEndTime(LocalTime.of(11, 0));
        bookingRequest1.setHallId(5);
        bookingRequest1.setItemsLoans(new ArrayList<>());

        BookingRequestDTO bookingRequest2 = new BookingRequestDTO();

        bookingRequest2.setDate(LocalDate.of(2023, 10, 15));
        bookingRequest2.setStartTime(LocalTime.of(9, 0));
        bookingRequest2.setEndTime(LocalTime.of(11, 0));
        bookingRequest2.setHallId(5);
        bookingRequest2.setItemsLoans(new ArrayList<>());

        BookingRequestDTO bookingRequest3 = new BookingRequestDTO();

        bookingRequest3.setDate(LocalDate.of(2021, 1, 9));
        bookingRequest3.setStartTime(LocalTime.of(5, 30));
        bookingRequest3.setEndTime(LocalTime.of(4, 15));
        bookingRequest3.setHallId(3);
        bookingRequest3.setItemsLoans(new ArrayList<>());

        assertNotEquals(bookingRequest1, bookingRequest3);
        assertEquals(bookingRequest1, bookingRequest2);
    }

    @Test
    public void bookingRequestDTOHashCodeMethod(){
        BookingRequestDTO bookingRequest = new BookingRequestDTO();

        bookingRequest.setStartTime(LocalTime.of(9, 0));
        bookingRequest.setEndTime(LocalTime.of(11, 0));
        bookingRequest.setHallId(5);
        bookingRequest.setItemsLoans(new ArrayList<>());

        assertNotNull(bookingRequest.hashCode());
    }

    @Test
    public void bookingRequestDTOToStringMethod(){
        BookingRequestDTO bookingRequest = new BookingRequestDTO();

        bookingRequest.setDate(LocalDate.of(2023, 10, 15));
        bookingRequest.setStartTime(LocalTime.of(9, 0));
        bookingRequest.setEndTime(LocalTime.of(11, 0));
        bookingRequest.setHallId(5);
        bookingRequest.setItemsLoans(new ArrayList<>());

        assertNotNull(bookingRequest.toString());
    }
    @Test
    public void bookingRequestDTOEqualsWithNull() {
        BookingRequestDTO bookingRequest = new BookingRequestDTO();
        assertNotEquals(null, bookingRequest);
    }

    @Test
    public void bookingRequestDTOEqualsWithDifferentClass() {
        BookingRequestDTO bookingRequest = new BookingRequestDTO();
        String someString = "I am not a BookingRequestDTO";
        assertNotEquals(bookingRequest, someString);
    }

    @Test
    public void bookingRequestDTOEqualsWithSelf() {
        BookingRequestDTO bookingRequest = new BookingRequestDTO();
        assertEquals(bookingRequest, bookingRequest);
    }

}
