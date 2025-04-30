package com.booking.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
