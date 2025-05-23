package com.booking.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.entity.BookingEntity;
import com.booking.entity.HallEntity;
import com.booking.entity.ItemEntity;
import com.booking.entity.LoanEntity;
import com.booking.entity.dto.BookingRequestDTO;
import com.booking.entity.dto.LoanRequestDTO;
import com.booking.exception.BookingException;
import com.booking.exception.ItemException;
import com.booking.repository.BookingRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private LoanService loanService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private HallService hallService;

    /**
     * Retrieves a list of all booking entities from the repository.
     *
     * @return a list of {@link BookingEntity} objects representing all bookings.
     */
    public List<BookingEntity> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Checks if a booking is available based on the provided booking request details.
     *
     * @param bookingRequest the booking request containing details such as hall ID, date, 
     *                       start time, and end time.
     * @return {@code true} if the booking is available; {@code false} otherwise.
     *
     * The method performs the following checks:
     * - Iterates through all existing bookings retrieved from the repository.
     * - Compares the hall ID of the existing booking with the requested hall ID.
     * - Ensures the booking status is not "Cancelada" (Cancelled).
     * - Checks if the date of the existing booking matches the requested date.
     * - Verifies that the requested time range does not overlap with the existing booking's time range.
     */
    private boolean isBookingAvailable(BookingRequestDTO bookingRequest) {
        List<BookingEntity> bookings = bookingRepository.findAll();
        for (BookingEntity booking : bookings) {
            if (booking.getHallId().getId() == bookingRequest.getHallId()) {
                if(booking.getStatus() != "Cancelada") {
                    if (booking.getDate().equals(bookingRequest.getDate())) {
                        if ((booking.getTimeStartBooking().isBefore(bookingRequest.getEndTime())
                                && booking.getTimeEndBooking().isAfter(bookingRequest.getStartTime()))) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Saves a booking request and creates associated loans for the requested items.
     *
     * @param bookingRequest the booking request containing details such as hall ID, user information,
     *                       booking date, time, and items to be loaned.
     * @return the saved BookingEntity object representing the booking.
     * @throws BookingException if the hall is not found, the booking is not available for the requested time,
     *                          or there is insufficient quantity for any requested item.
     * @throws ItemException if an item in the booking request is not found.
     */
    public BookingEntity save(BookingRequestDTO bookingRequest, String userId, String userRole, String nameUser) {
        HallEntity hall = hallService.getHallById(bookingRequest.getHallId());
        if (hall == null) {
            throw new BookingException("No se encontró la sala");
        }

        if (!isBookingAvailable(bookingRequest)) {
            throw new BookingException("La reserva no está disponible para ese horario");
        }

        List<ItemEntity> validatedItems = bookingRequest.getItemsLoans().stream().map(item -> {
            ItemEntity itemEntity = itemService.getItemById(item.getIdItem());
            if (itemEntity == null) {
                throw new ItemException("No se encontró el item con ID: " + item.getIdItem());
            }
            if (itemEntity.getQuantityAvailable() < item.getQuantity()) {
                throw new BookingException("Cantidad insuficiente para el item: " + itemEntity.getName());
            }

            // Update stock
            itemEntity.setQuantityAvailable(itemEntity.getQuantity() - item.getQuantity());
            itemService.saveItem(itemEntity);
            return itemEntity;
        }).toList();

        // Crear la reserva
        BookingEntity booking = new BookingEntity();
        booking.setHallId(hall);
        booking.setIdUser(userId);
        booking.setNameUser(nameUser);
        booking.setDate(bookingRequest.getDate());
        booking.setTimeStartBooking(bookingRequest.getStartTime());
        booking.setTimeEndBooking(bookingRequest.getEndTime());
        booking.setStatus("Confirmada");

        BookingEntity savedBooking = bookingRepository.save(booking);

        // Crear los préstamos
        for (ItemEntity item : validatedItems) {
            LoanEntity loan = new LoanEntity();
            loan.setItemId(item);
            loan.setBookingId(savedBooking);
            loan.setLoanDate(bookingRequest.getDate());
            loan.setReturnDate(bookingRequest.getDate());
            loan.setReturnStatus("Activo");
            loanService.save(loan);
        }

        return savedBooking;
    }

    /**
     * Retrieves a booking entity by its unique identifier.
     *
     * @param id the unique identifier of the booking to retrieve
     * @return the BookingEntity associated with the given id
     * @throws BookingException if no booking is found with the specified id
     */
    public BookingEntity getBookingById(int id) {
        return bookingRepository.findById(id).orElseThrow(() -> new BookingException("No se encontro la reserva"));
    }

    /**
     * Retrieves a list of loan entities associated with a specific booking ID.
     *
     * @param id the unique identifier of the booking
     * @return a list of LoanEntity objects associated with the specified booking
     * @throws IllegalArgumentException if the booking ID is invalid or not found
     */
    public List<LoanEntity> getLoansByBookingId(int id) {
        BookingEntity booking = getBookingById(id);
        return booking.getItemsLoans();
    }

    /**
     * Retrieves the first booking associated with a specific user ID.
     *
     * @param id the ID of the user whose bookings are to be retrieved.
     * @return the first {@link BookingEntity} associated with the given user ID.
     * @throws BookingException if no bookings are found for the specified user ID.
     */
    public BookingEntity getBookingsByUserId(int id) {
        List<BookingEntity> bookings = bookingRepository.findByIdUser(id);
        if (bookings.isEmpty()) {
            throw new BookingException("No se encontraron reservas para el usuario");
        }
        return bookings.get(0);
    }

    /**
     * Cancels a booking by its ID. This method updates the booking status to "Cancelada",
     * updates the return status of associated loans to "Cancelado", and adjusts the 
     * quantity available for the items associated with the loans.
     *
     * @param idBooking the ID of the booking to be canceled
     * @return a confirmation message indicating the booking has been canceled
     * @throws BookingException if the booking with the given ID is not found
     */
    public String cancelBooking(int idBooking) {
        BookingEntity booking = getBookingById(idBooking);
        if (booking == null) {
            throw new BookingException("No se encontró la reserva");
        }
        booking.setStatus("Cancelada");
        bookingRepository.save(booking);
        List<LoanEntity> loans = booking.getItemsLoans();
        for (LoanEntity loan : loans) {
            loan.setReturnStatus("Cancelado");
            loanService.save(loan);
        }
        List<ItemEntity> items = new ArrayList<>();
        for (LoanEntity loan : loans) {
            ItemEntity item = loan.getItemId();
            item.setQuantityAvailable(item.getQuantityAvailable() + loan.getQuantity());
            items.add(item);
        }
        for (ItemEntity item : items) {
            itemService.saveItem(item);
        }
        return "Reserva cancelada";
    }

    /**
     * Terminates a booking by its ID. This method updates the booking status to "Finalizada",
     * updates the return status of associated loans to "Finalizado", and adjusts the 
     * quantity available for the items associated with the loans.
     *
     * @param idBooking the ID of the booking to be terminated
     * @return a confirmation message indicating the booking has been terminated
     * @throws BookingException if the booking with the given ID is not found
     */
    public String terminateBooking(int idBooking) {
        BookingEntity booking = getBookingById(idBooking);
        if (booking == null) {
            throw new BookingException("No se encontró la reserva");
        }
        booking.setStatus("Finalizada");
        bookingRepository.save(booking);
        List<LoanEntity> loans = booking.getItemsLoans();
        for (LoanEntity loan : loans) {
            loan.setReturnStatus("Finalizado");
            loanService.save(loan);
        }
        return "Reserva finalizada";
    }
    
}
