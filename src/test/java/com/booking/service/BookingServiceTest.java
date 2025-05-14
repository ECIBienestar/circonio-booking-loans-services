package com.booking.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.booking.entity.BookingEntity;
import com.booking.entity.HallEntity;
import com.booking.entity.ItemEntity;
import com.booking.entity.LoanEntity;
import com.booking.entity.dto.BookingRequestDTO;
import com.booking.entity.dto.LoanRequestDTO;
import com.booking.exception.BookingException;
import com.booking.exception.ItemException;
import com.booking.repository.BookingRepository;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private LoanService loanService;

    @Mock
    private ItemService itemService;

    @Mock
    private HallService hallService;

    @Test
    void saveBooking_success() {
        // Arrange
        BookingRequestDTO bookingRequest = new BookingRequestDTO();
        bookingRequest.setHallId(1);
        bookingRequest.setIdUser(101);
        bookingRequest.setNameUser("Juan");
        bookingRequest.setRolUser("Estudiante");
        bookingRequest.setDate(LocalDate.of(2025, 5, 12));
        bookingRequest.setStartTime(LocalTime.of(10, 0));
        bookingRequest.setEndTime(LocalTime.of(12, 0));

        // Item de préstamo
        LoanRequestDTO loanRequest = new LoanRequestDTO();
        loanRequest.setIdItem(1);
        loanRequest.setQuantity(2);
        bookingRequest.setItemsLoans(List.of(loanRequest));

        HallEntity hall = new HallEntity();
        hall.setId(1);

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(1);
        itemEntity.setName("Proyector");
        itemEntity.setQuantity(5);
        itemEntity.setQuantityAvailable(5);

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setId(1);
        bookingEntity.setDate(bookingRequest.getDate());
        bookingEntity.setTimeStartBooking(bookingRequest.getStartTime());
        bookingEntity.setTimeEndBooking(bookingRequest.getEndTime());
        bookingEntity.setStatus("Confirmada");
        bookingEntity.setHallId(hall);
        bookingEntity.setIdUser(bookingRequest.getIdUser());

        // Mock behavior
        when(hallService.getHallById(1)).thenReturn(hall);
        when(bookingRepository.findAll()).thenReturn(List.of()); // No hay conflictos
        when(itemService.getItemById(1)).thenReturn(itemEntity);
        when(itemService.saveItem(any(ItemEntity.class))).thenReturn(itemEntity);
        when(bookingRepository.save(any(BookingEntity.class))).thenReturn(bookingEntity);
        LoanEntity loanEntity = new LoanEntity();
        when(loanService.save(any(LoanEntity.class))).thenReturn(loanEntity);

        // Act
        BookingEntity result = bookingService.save(bookingRequest);

        // Assert
        assertNotNull(result);
        assertEquals("Confirmada", result.getStatus());
        verify(bookingRepository, times(1)).save(any(BookingEntity.class));
        verify(loanService, times(1)).save(any(LoanEntity.class));
        verify(itemService, times(1)).saveItem(any(ItemEntity.class));
    }

    @Test
    void saveBooking_shouldThrowException_whenBookingNotAvailable() {
        // Arrange
        BookingRequestDTO bookingRequest = new BookingRequestDTO();
        bookingRequest.setHallId(1);
        bookingRequest.setDate(LocalDate.of(2025, 5, 12));
        bookingRequest.setStartTime(LocalTime.of(10, 0));
        bookingRequest.setEndTime(LocalTime.of(12, 0));

        HallEntity hall = new HallEntity();
        hall.setId(1);

        // Reserva existente que choca en horario
        BookingEntity existingBooking = new BookingEntity();
        existingBooking.setHallId(hall);
        existingBooking.setDate(bookingRequest.getDate());
        existingBooking.setTimeStartBooking(LocalTime.of(9, 0));
        existingBooking.setTimeEndBooking(LocalTime.of(11, 0));
        existingBooking.setStatus("Confirmada");

        when(hallService.getHallById(1)).thenReturn(hall);
        when(bookingRepository.findAll()).thenReturn(List.of(existingBooking));

        // Act & Assert
        BookingException exception = assertThrows(BookingException.class,
                () -> bookingService.save(bookingRequest));

        assertEquals("La reserva no está disponible para ese horario", exception.getMessage());
    }

    @Test
    void saveBooking_shouldThrowException_whenItemNotFound() {
        // Arrange
        BookingRequestDTO bookingRequest = new BookingRequestDTO();
        bookingRequest.setHallId(1);
        bookingRequest.setDate(LocalDate.of(2025, 5, 12));
        bookingRequest.setStartTime(LocalTime.of(10, 0));
        bookingRequest.setEndTime(LocalTime.of(12, 0));

        LoanRequestDTO loanRequest = new LoanRequestDTO();
        loanRequest.setIdItem(1);
        loanRequest.setQuantity(2);
        bookingRequest.setItemsLoans(List.of(loanRequest));

        HallEntity hall = new HallEntity();
        hall.setId(1);

        when(hallService.getHallById(1)).thenReturn(hall);
        when(bookingRepository.findAll()).thenReturn(List.of()); // No conflictos
        when(itemService.getItemById(1)).thenReturn(null); // Simula ítem inexistente

        // Act & Assert
        ItemException exception = assertThrows(ItemException.class,
                () -> bookingService.save(bookingRequest));

        assertEquals("No se encontró el item con ID: 1", exception.getMessage());
    }

    @Test
    void cancelBooking_shouldUpdateStatusAndRestoreItems() {
        // Arrange
        int bookingId = 1;

        ItemEntity item = new ItemEntity();
        item.setId(1);
        item.setName("Proyector");
        item.setQuantityAvailable(3);

        LoanEntity loan = new LoanEntity();
        loan.setItemId(item);
        loan.setQuantity(2); // Cantidad prestada
        loan.setReturnStatus("Activo");

        BookingEntity booking = new BookingEntity();
        booking.setId(bookingId);
        booking.setStatus("Confirmada");
        booking.setItemsLoans(List.of(loan));

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any())).thenReturn(booking);

        // Act
        String response = bookingService.cancelBooking(bookingId);

        // Assert
        assertEquals("Reserva cancelada", response);
        assertEquals("Cancelada", booking.getStatus());
        verify(loanService, times(1)).save(any(LoanEntity.class));
        verify(itemService, times(1)).saveItem(any(ItemEntity.class));
    }

    @Test
    void terminateBooking_shouldUpdateStatusAndLoans() {
        // Arrange
        int bookingId = 1;

        LoanEntity loan = new LoanEntity();
        loan.setReturnStatus("Activo");

        BookingEntity booking = new BookingEntity();
        booking.setId(bookingId);
        booking.setStatus("Confirmada");
        booking.setItemsLoans(List.of(loan));

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any())).thenReturn(booking);

        // Act
        String response = bookingService.terminateBooking(bookingId);

        // Assert
        assertEquals("Reserva finalizada", response);
        assertEquals("Finalizada", booking.getStatus());
        verify(loanService, times(1)).save(any(LoanEntity.class));
    }


}
