package com.booking.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingEntityTest {
    @Test
    public void shouldTestId(){
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setId(1);
        assertEquals(1, bookingEntity.getId());
    }
    @Test
    public void shouldSetAndGetNameUser() {
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setNameUser("John Doe");
        assertEquals("John Doe", bookingEntity.getNameUser());
    }




    @Test
    public void shouldSetAndGetDate() {
        BookingEntity bookingEntity = new BookingEntity();
        LocalDate date = LocalDate.of(2023, 10, 1);
        bookingEntity.setDate(date);
        assertEquals(date, bookingEntity.getDate());
    }

    @Test
    public void shouldSetAndGetTimeStartBooking() {
        BookingEntity bookingEntity = new BookingEntity();
        LocalTime timeStart = LocalTime.of(9, 0);
        bookingEntity.setTimeStartBooking(timeStart);
        assertEquals(timeStart, bookingEntity.getTimeStartBooking());
    }

    @Test
    public void shouldSetAndGetTimeEndBooking() {
        BookingEntity bookingEntity = new BookingEntity();
        LocalTime timeEnd = LocalTime.of(10, 0);
        bookingEntity.setTimeEndBooking(timeEnd);
        assertEquals(timeEnd, bookingEntity.getTimeEndBooking());
    }

    @Test
    public void shouldSetAndGetHallId() {
        BookingEntity bookingEntity = new BookingEntity();
        HallEntity hallEntity = new HallEntity();
        bookingEntity.setHallId(hallEntity);
        assertEquals(hallEntity, bookingEntity.getHallId());
    }

    @Test
    public void shouldSetAndGetStatus() {
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setStatus("Confirmed");
        assertEquals("Confirmed", bookingEntity.getStatus());
    }

    @Test
    public void shouldSetAndGetItemsLoans() {
        BookingEntity bookingEntity = new BookingEntity();
        LoanEntity loanEntity = new LoanEntity();
        List<LoanEntity> loans = new ArrayList<>();
        loans.add(loanEntity);
        bookingEntity.setItemsLoans(loans);
        assertEquals(loans, bookingEntity.getItemsLoans());
    }
    @Test
    public void shouldAllArgumentsConstructor() {
        BookingEntity bookingEntity = new BookingEntity(1, "John Doe", "123", LocalDate.of(2023, 10, 1), LocalTime.of(9, 0), LocalTime.of(10, 0), new HallEntity(), "Confirmed", new ArrayList<>());
        assertEquals(1, bookingEntity.getId());
        assertEquals("John Doe", bookingEntity.getNameUser());
        assertEquals("123", bookingEntity.getIdUser());
        assertEquals(LocalDate.of(2023, 10, 1), bookingEntity.getDate());
        assertEquals(LocalTime.of(9, 0), bookingEntity.getTimeStartBooking());
        assertEquals(LocalTime.of(10, 0), bookingEntity.getTimeEndBooking());
        assertEquals("Confirmed", bookingEntity.getStatus());
        assertEquals(new ArrayList<>(), bookingEntity.getItemsLoans());
    }
}
