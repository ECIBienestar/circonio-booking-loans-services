package com.booking.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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
class BookingServiceTest {

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

        // Mock behavior
        when(hallService.getHallById(1)).thenReturn(hall);
        when(bookingRepository.findAll()).thenReturn(List.of()); // No hay conflictos
        when(itemService.getItemById(1)).thenReturn(itemEntity);
        when(itemService.saveItem(any(ItemEntity.class))).thenReturn(itemEntity);
        when(bookingRepository.save(any(BookingEntity.class))).thenReturn(bookingEntity);
        LoanEntity loanEntity = new LoanEntity();
        when(loanService.save(any(LoanEntity.class))).thenReturn(loanEntity);

        // Act
        BookingEntity result = bookingService.save(bookingRequest, "Usuario1", "Admin", "Usuario Test");

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
                () -> bookingService.save(bookingRequest, "Usuario1", "Admin", "Usuario Test"));

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
                () -> bookingService.save(bookingRequest, "Usuario1", "Admin", "Usuario Test"));

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

    @Test
    void getAllBookings_shouldReturnList() {
        BookingEntity booking1 = new BookingEntity();
        BookingEntity booking2 = new BookingEntity();
        when(bookingRepository.findAll()).thenReturn(List.of(booking1, booking2));

        List<BookingEntity> result = bookingService.getAllBookings();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void getBookingById_shouldReturnBooking_whenFound() {
        BookingEntity booking = new BookingEntity();
        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

        BookingEntity result = bookingService.getBookingById(1);

        assertNotNull(result);
        verify(bookingRepository, times(1)).findById(1);
    }

    @Test
    void getBookingById_shouldThrowException_whenNotFound() {
        when(bookingRepository.findById(1)).thenReturn(Optional.empty());

        BookingException exception = assertThrows(BookingException.class, () -> bookingService.getBookingById(1));
        assertEquals("No se encontro la reserva", exception.getMessage());
    }

    @Test
    void getLoansByBookingId_shouldReturnLoansList() {
        LoanEntity loan = new LoanEntity();
        BookingEntity booking = new BookingEntity();
        booking.setItemsLoans(List.of(loan));

        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

        List<LoanEntity> result = bookingService.getLoansByBookingId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getLoansByBookingId_shouldThrowException_whenBookingNotFound() {
        when(bookingRepository.findById(1)).thenReturn(Optional.empty());

        BookingException exception = assertThrows(BookingException.class, () -> bookingService.getLoansByBookingId(1));
        assertEquals("No se encontro la reserva", exception.getMessage());
    }

    @Test
    void getBookingsByUserId_shouldReturnFirstBooking() {
        BookingEntity booking = new BookingEntity();
        when(bookingRepository.findByIdUser(1)).thenReturn(List.of(booking));

        BookingEntity result = bookingService.getBookingsByUserId(1);

        assertNotNull(result);
        verify(bookingRepository, times(1)).findByIdUser(1);
    }

    @Test
    void getBookingsByUserId_shouldThrowException_whenNoBookingsFound() {
        when(bookingRepository.findByIdUser(1)).thenReturn(List.of());

        BookingException exception = assertThrows(BookingException.class, () -> bookingService.getBookingsByUserId(1));
        assertEquals("No se encontraron reservas para el usuario", exception.getMessage());
    }

    @Test
    void save_shouldThrowException_whenHallNotFound() {
        BookingRequestDTO bookingRequest = new BookingRequestDTO();
        bookingRequest.setHallId(999);

        when(hallService.getHallById(999)).thenReturn(null);

        BookingException exception = assertThrows(BookingException.class,
            () -> bookingService.save(bookingRequest, "user", "role", "name"));
        
        assertEquals("No se encontró la sala", exception.getMessage());
    }

    @Test
    void cancelBooking_shouldThrowException_whenBookingNotFound() {
        when(bookingRepository.findById(1)).thenReturn(Optional.empty());

        BookingException exception = assertThrows(BookingException.class, () -> bookingService.cancelBooking(1));
        assertEquals("No se encontro la reserva", exception.getMessage());
    }

    @Test
    void terminateBooking_shouldThrowException_whenBookingNotFound() {
        when(bookingRepository.findById(1)).thenReturn(Optional.empty());

        BookingException exception = assertThrows(BookingException.class, () -> bookingService.terminateBooking(1));
        assertEquals("No se encontro la reserva", exception.getMessage());
    }

// Removed redundant test method `testGetLoansByBookingId_ReturnsLoansList`.
    @Test
    void testGetBookingsByUserId_ReturnsFirstBooking() {
        int userId = 1;
        BookingEntity booking1 = new BookingEntity();
        booking1.setId(10);
        BookingEntity booking2 = new BookingEntity();
        List<BookingEntity> bookings = List.of(booking1, booking2);

        when(bookingRepository.findByIdUser(userId)).thenReturn(bookings);

        BookingEntity result = bookingService.getBookingsByUserId(userId);

        assertEquals(booking1, result);
    }

    @Test
    void testGetBookingsByUserId_ThrowsException_WhenNoBookingsFound() {
        int userId = 1;

        when(bookingRepository.findByIdUser(userId)).thenReturn(Collections.emptyList());

        assertThrows(BookingException.class, () -> {
            bookingService.getBookingsByUserId(userId);
        });
    }


}
