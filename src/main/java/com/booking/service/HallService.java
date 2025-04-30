package com.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.entity.HallEntity;
import com.booking.repository.HallRepository;

@Service
public class HallService {
   
    @Autowired
    private HallRepository hallRepository;

    /**
     * Retrieves a HallEntity by its unique identifier.
     *
     * @param id the unique identifier of the hall to retrieve
     * @return the HallEntity if found, or {@code null} if no hall exists with the given id
     */
    public HallEntity getHallById(int id) {
        return hallRepository.findById(id).orElse(null);
    }

    /**
     * Retrieves a list of all hall entities from the repository.
     *
     * @return a list of {@link HallEntity} objects representing all halls.
     */
    public List<HallEntity> getAllHalls() {
        return hallRepository.findAll();
    }

}
