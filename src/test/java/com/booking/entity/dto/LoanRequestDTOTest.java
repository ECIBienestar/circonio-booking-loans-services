package com.booking.entity.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LoanRequestDTOTest {

    @Test
    public void loanRequestDTOHandlesValidDataCorrectly() {
        LoanRequestDTO loanRequest = new LoanRequestDTO();
        loanRequest.setIdItem(1);
        loanRequest.setQuantity(5);

        assertEquals(1, loanRequest.getIdItem());
        assertEquals(5, loanRequest.getQuantity());
    }

    @Test
    public void loanRequestDTOequalsMethod() {
        LoanRequestDTO loanRequest1 = new LoanRequestDTO();
        loanRequest1.setIdItem(1);
        loanRequest1.setQuantity(5);

        LoanRequestDTO loanRequest2 = new LoanRequestDTO();
        loanRequest2.setIdItem(1);
        loanRequest2.setQuantity(5);

        LoanRequestDTO loanRequest3 = new LoanRequestDTO();
        loanRequest3.setIdItem(2);
        loanRequest3.setQuantity(10);

        assertEquals(loanRequest1, loanRequest2);
        assertNotEquals(loanRequest1, loanRequest3);
    }

    @Test
    public void loanRequestDTOhashCodeMethod() {
        LoanRequestDTO loanRequest1 = new LoanRequestDTO();
        loanRequest1.setIdItem(1);
        loanRequest1.setQuantity(5);

        LoanRequestDTO loanRequest2 = new LoanRequestDTO();
        loanRequest2.setIdItem(1);
        loanRequest2.setQuantity(5);

        LoanRequestDTO loanRequest3 = new LoanRequestDTO();
        loanRequest3.setIdItem(2);
        loanRequest3.setQuantity(10);

        assertEquals(loanRequest1.hashCode(), loanRequest2.hashCode());
        assertNotEquals(loanRequest1.hashCode(), loanRequest3.hashCode());
    }

    @Test
    public void loanRequestDTOtoStringMethod() {
        LoanRequestDTO loanRequest = new LoanRequestDTO();
        loanRequest.setIdItem(1);
        loanRequest.setQuantity(5);

        String expectedString = "LoanRequestDTO(idItem=1, quantity=5)";
        assertEquals(expectedString, loanRequest.toString());
    }
}
