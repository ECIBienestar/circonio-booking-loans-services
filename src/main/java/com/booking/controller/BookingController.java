package com.booking.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.entity.BookingEntity;
import com.booking.entity.dto.BookingRequestDTO;
import com.booking.exception.BookingException;
import com.booking.exception.ItemException;
import com.booking.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    @Operation(summary = "Get all bookings", description = "Retrieve all bookings", tags = { "Booking Management" })
    public ResponseEntity<?> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking by ID", description = "Retrieve a booking by its ID", tags = {
            "Booking Management" })
    public ResponseEntity<?> getBookingById(@PathVariable int id) {
        try {
            BookingEntity booking = bookingService.getBookingById(id);
            return ResponseEntity.status(
                    200).body(
                            Map.of("status", "success", "data", booking));

        } catch (BookingException e) {
            return ResponseEntity.status(
                    400).body(
                            Map.of("status", "error", "message", e.getMessage()));
        } catch (ItemException e) {
            return ResponseEntity.status(
                    404).body(
                            Map.of("status", "error", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(
                    500).body(
                            Map.of("status", "error", "message", e.getMessage()));
        }
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "Get bookings by user ID", description = "Retrieve all bookings associated with a specific user ID", tags = {
            "Booking Management" })
    public ResponseEntity<?> getBookingsByUserId(@PathVariable int id) {
        try {
            return ResponseEntity.status(
                    200).body(
                            Map.of("status", "success", "data", bookingService.getBookingsByUserId(id)));
        } catch (BookingException e) {
            return ResponseEntity.status(
                    400).body(
                            Map.of("status", "error", "message", e.getMessage()));
        } catch (ItemException e) {
            return ResponseEntity.status(
                    404).body(
                            Map.of("status", "error", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(
                    500).body(
                            Map.of("status", "error", "message", e.getMessage()));
        }
    }

    @GetMapping("/{id}/loans")
    @Operation(summary = "Get loans by booking ID", description = "Retrieve all loans associated with a specific booking ID", tags = {
            "Booking Management" })
    public ResponseEntity<?> getLoansByBookingId(@PathVariable int id) {
        try {
            return ResponseEntity.status(
                    200).body(
                            Map.of("status", "success", "data", bookingService.getLoansByBookingId(id)));
        } catch (BookingException e) {
            return ResponseEntity.status(
                    400).body(
                            Map.of("status", "error", "message", e.getMessage()));
        } catch (ItemException e) {
            return ResponseEntity.status(
                    404).body(
                            Map.of("status", "error", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(
                    500).body(
                            Map.of("status", "error", "message", e.getMessage()));
        }
    }

    @GetMapping("/hall/{id}")
    @Operation(summary = "Get bookings by hall ID", description = "Retrieve all bookings associated with a specific hall ID", tags = {
            "Booking Management" })
    public ResponseEntity<?> getBookingsByHallId(@PathVariable int id) {
        try {
            return ResponseEntity.status(
                    200).body(
                            Map.of("status", "success", "data", bookingService.getBookingsByHallId(id)));
        } catch (BookingException e) {
            return ResponseEntity.status(
                    400).body(
                            Map.of("status", "error", "message", e.getMessage()));
        } catch (ItemException e) {
            return ResponseEntity.status(
                    404).body(
                            Map.of("status", "error", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(
                    500).body(
                            Map.of("status", "error", "message", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody BookingRequestDTO booking) {
        try {
            BookingEntity bookingSave = bookingService.save(booking);
            return ResponseEntity.status(
                    201).body(
                            Map.of("status", "success", "data", bookingSave));
        } catch (BookingException e) {
            return ResponseEntity.status(
                    400).body(
                            Map.of("status", "error", "message", e.getMessage()));
        } catch (ItemException e) {
            return ResponseEntity.status(
                    404).body(
                            Map.of("status", "error", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(
                    500).body(
                            Map.of("status", "error", "message", e.getMessage()));
        }
    }
    
}