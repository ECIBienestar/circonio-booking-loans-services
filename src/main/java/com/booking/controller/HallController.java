package com.booking.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.booking.entity.HallEntity;
import com.booking.service.HallService;


@RestController
@RequestMapping("/api/halls")
public class HallController {
    
    @Autowired
    private HallService hallService;

    /**
     * Retrieves a HallEntity by its unique identifier.
     *
     * @param id the unique identifier of the hall to retrieve
     * @return the HallEntity if found, or {@code null} if no hall exists with the given id
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllHalls() {
        return ResponseEntity.ok(hallService.getAllHalls());
    }    

    @GetMapping("/{id}")
    public ResponseEntity<?> getHallById(@PathVariable int id) {
        return ResponseEntity.ok(hallService.getHallById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHall(@PathVariable int id) {
        hallService.deleteHall(id);
        return ResponseEntity.ok("Hall deleted successfully");
    }

    @PostMapping("/add")
    public ResponseEntity<?> addHall(@RequestBody HallEntity hall) {
        try {
            hall.toString();
            HallEntity savedHall = hallService.saveHall(hall);
            return ResponseEntity.status(201).body(savedHall);
        } catch (Exception e) {
            return ResponseEntity.status(Response.SC_INTERNAL_SERVER_ERROR).body("Error saving hall: " + e.getMessage());
        }
    }
}
