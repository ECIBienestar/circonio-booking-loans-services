package com.booking.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.entity.BookingEntity;
import com.booking.entity.dto.BookingRequestDTO;
import com.booking.exception.BookingException;
import com.booking.exception.ItemException;
import com.booking.service.BookingService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.booking.security.UserDetailsImpl;



import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping
    @Operation(summary = "Get all bookings", description = "Retrieve all bookings", tags = { "Booking Management" })
    public ResponseEntity<?> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
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

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/user/{id}")
    @Operation(summary = "Get bookings by user ID", description = "Retrieve all bookings associated with a specific user ID", tags = {
            "Booking Management" })
    public ResponseEntity<?> getBookingsByUserId(@PathVariable String idUser) {
        try {
            return ResponseEntity.status(
                    200).body(
                            Map.of("status", "success", "data", bookingService.getBookingsByUserId(idUser)));
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
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'TEACHER', 'STUDENT')")
    @PostMapping
    @Operation(summary = "Create a new booking", description = "Creates a new booking with associated items", tags = {
            "Booking Management" })
    public ResponseEntity<?> save(@RequestBody BookingRequestDTO bookingRequest, 
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            String userId = userDetails.getId();
            String userRole = userDetails.getRole();
            String nameUser = userDetails.getFullName();
            BookingEntity savedBooking = bookingService.save(bookingRequest, userId, userRole, nameUser);
            return ResponseEntity.status(201).body(Map.of("status", "success", "data", savedBooking));
        } catch (BookingException | ItemException e) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", "Unexpected error occurred"));
        }
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel a booking", description = "Cancels a booking and its associated loans", tags = {
            "Booking Management" })
    public ResponseEntity<?> cancelBooking(@PathVariable int id) {
        try {
            String message = bookingService.cancelBooking(id);
            return ResponseEntity.ok(Map.of("status", "success", "message", message));
        } catch (BookingException e) {
            return ResponseEntity.status(400).body(Map.of("status", "error", "message", e.getMessage()));
        } catch (ItemException e) {
            return ResponseEntity.status(404).body(Map.of("status", "error", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'TEACHER', 'STUDENT')")
    @PostMapping("/{id}/return")
    @Operation(summary = "Return a booking", description = "Returns a booking and its associated loans", tags = {
            "Booking Management" })
    public ResponseEntity<?> returnBooking(@PathVariable int id) {
        try {
            String message = bookingService.terminateBooking(id);
            return ResponseEntity.ok(Map.of("status", "success", "message", message));
        } catch (BookingException e) {
            return ResponseEntity.status(400).body(Map.of("status", "error", "message", e.getMessage()));
        } catch (ItemException e) {
            return ResponseEntity.status(404).body(Map.of("status", "error", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

}