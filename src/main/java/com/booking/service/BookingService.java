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

    public List<BookingEntity> getAllBookings() {
        return bookingRepository.findAll();
    }

    private boolean isBookingAvailable(BookingRequestDTO bookingRequest) {
        List<BookingEntity> bookings = bookingRepository.findAll();
        for (BookingEntity booking : bookings) {
            if (booking.getHallId().getId() == bookingRequest.getHallId()) {
                if (booking.getDate().equals(bookingRequest.getDate())) {
                    if ((booking.getTimeStartBooking().isBefore(bookingRequest.getEndTime())
                            && booking.getTimeEndBooking().isAfter(bookingRequest.getStartTime()))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    
    public BookingEntity save(BookingRequestDTO bookingRequest){
        BookingEntity booking = new BookingEntity();
        HallEntity hall = hallService.getHallById(bookingRequest.getHallId());
        if (hall == null) {
            throw new BookingException("No se encontro la sala");
        }
        if (!isBookingAvailable(bookingRequest)) {
            throw new BookingException("La reserva no esta disponible para ese horario");
        }

        List<ItemEntity> loansPre = new ArrayList<>();

        for (LoanRequestDTO item : bookingRequest.getItemsLoan()) {
            ItemEntity itemEntity = itemService.getItemById(item.getIdItem());
            if (itemEntity == null) {
                throw new BookingException("No se encontro el item");
            }
            if (itemEntity.getQuantity() < item.getQuantity()) {
                throw new BookingException("No hay suficiente cantidad de items");
            }
            itemEntity.setQuantity(itemEntity.getQuantity() - item.getQuantity());
            itemService.saveItem(itemEntity);
            loansPre.add(itemEntity);
        }

        booking.setHallId(hall);
        booking.setIdUser(bookingRequest.getIdUser());
        booking.setNameUser(bookingRequest.getNameUser());
        booking.setRolUser(bookingRequest.getRolUser());
        booking.setDate(bookingRequest.getDate());
        booking.setTimeStartBooking(bookingRequest.getStartTime());
        booking.setTimeEndBooking(bookingRequest.getEndTime());
        booking.setStatus("Reservado");

        BookingEntity bookingSave = bookingRepository.save(booking);

        for (ItemEntity item : loansPre) {
            LoanEntity loan = new LoanEntity();
            loan.setItemId(item);
            loan.setBookingId(bookingSave);
            loan.setLoanDate(bookingRequest.getDate());
            loan.setQuantity(0);
            loan.setReturnDate(bookingRequest.getDate());
            loan.setReturnStatus("Prestado");
            loanService.save(loan);
        }

        return bookingSave;
    }

}
