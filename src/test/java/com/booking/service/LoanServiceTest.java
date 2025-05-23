package com.booking.service;

import com.booking.entity.LoanEntity;
import com.booking.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanRepository loanRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_SuccessfullySavesLoan() {
        LoanEntity loan = new LoanEntity();
        loan.setId(1);
        // Puedes setear más propiedades si las tienes

        when(loanRepository.save(loan)).thenReturn(loan);

        LoanEntity saved = loanService.save(loan);

        assertNotNull(saved);
        assertEquals(1, saved.getId());
        verify(loanRepository, times(1)).save(loan);
    }

}
