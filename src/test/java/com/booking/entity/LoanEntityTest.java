package com.booking.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoanEntityTest {
    @Test
    public void shouldTestId(){
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setId(1);
        assertEquals(1, loanEntity.getId());
    }
    @Test
    public void shouldTestBookingId(){
        LoanEntity loanEntity = new LoanEntity();
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setId(1);
        loanEntity.setBookingId(bookingEntity);
        assertEquals(bookingEntity, loanEntity.getBookingId());
    }
    @Test
    public void shouldTestItemId(){
        LoanEntity loanEntity = new LoanEntity();
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(1);
        loanEntity.setItemId(itemEntity);
        assertEquals(itemEntity, loanEntity.getItemId());
    }
    @Test
    public void shouldTestQuantity(){
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setQuantity(5);
        assertEquals(5, loanEntity.getQuantity());
    }
    @Test
    public void shouldTestLoanDate(){
        LoanEntity loanEntity = new LoanEntity();
        LocalDate loanDate = LocalDate.of(2023, 10, 1);
        loanEntity.setLoanDate(loanDate);
        assertEquals(loanDate, loanEntity.getLoanDate());
    }
    @Test
    public void shouldTestReturnDate(){
        LoanEntity loanEntity = new LoanEntity();
        LocalDate returnDate = LocalDate.of(2023, 10, 2);
        loanEntity.setReturnDate(returnDate);
        assertEquals(returnDate, loanEntity.getReturnDate());
    }
    @Test
    public void shouldTestReturnStatus(){
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setReturnStatus("Returned");
        assertEquals("Returned", loanEntity.getReturnStatus());
    }
    @Test
    public void shouldAllArgumentsConstructor() {
        BookingEntity bookingEntity = new BookingEntity();
        ItemEntity itemEntity = new ItemEntity();
        LocalDate loanDate = LocalDate.of(2023, 10, 1);
        LocalDate returnDate = LocalDate.of(2023, 10, 2);
        LoanEntity loanEntity = new LoanEntity(1, bookingEntity, itemEntity, 5, loanDate, returnDate, "Returned");
        assertEquals(1, loanEntity.getId());
        assertEquals(bookingEntity, loanEntity.getBookingId());
        assertEquals(itemEntity, loanEntity.getItemId());
        assertEquals(5, loanEntity.getQuantity());
        assertEquals(loanDate, loanEntity.getLoanDate());
        assertEquals(returnDate, loanEntity.getReturnDate());
        assertEquals("Returned", loanEntity.getReturnStatus());
    }

}
