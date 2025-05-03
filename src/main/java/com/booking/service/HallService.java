package com.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.entity.HallEntity;
import com.booking.exception.HallException;
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

    public HallEntity saveHall(HallEntity hall) {
        HallEntity existingHall = getHallById(hall.getId());
        if (existingHall != null) {
            throw new HallException.HallExceptionNotFound("El salon con id " + hall.getId() + " ya existe");
        }
        validateSaveHall(hall);
        return hallRepository.save(hall);
    }

    private void validateSaveHall(HallEntity hall) {
        if (hall.getName() == null || hall.getName().isEmpty()) {
            throw new HallException.HallExceptionNotFound("El nombre del salon no puede estar vacio");
        }

        if (!validateNameHall(hall.getName())) {
            throw new HallException.HallExceptionNotFound("El nombre del salon ya existe");
        }

        if (hall.getCapacity() <= 0) {
            throw new HallException.HallExceptionNotFound("La capacidad del salon no puede ser menor o igual a 0");
        }
    }

    private boolean validateNameHall(String name) {
        List<HallEntity> halls = hallRepository.findAll();
        for (HallEntity hall : halls) {
            if (hall.getName().equalsIgnoreCase(name)) {
                return false;
            }
        }
        return true;
    }

    public String deleteHall(int id) {
        HallEntity hall = getHallById(id);
        if (hall == null) {
            throw new HallException.HallExceptionNotFound("El salon con id " + id + " no existe");
        }
        hallRepository.deleteById(id);
        return "El salon con el id " + id + " ha sido eliminado";
    }
    
}