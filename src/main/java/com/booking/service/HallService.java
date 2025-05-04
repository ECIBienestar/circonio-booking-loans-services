package com.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.entity.HallEntity;
import com.booking.entity.dto.HallEntityRequestDTO;
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

    public HallEntity transformToHallEntity(HallEntityRequestDTO hall) {
        HallEntity hallEntity = new HallEntity();
        hallEntity.setId(0);
        hallEntity.setName(hall.getName());
        hallEntity.setCapacity(hall.getCapacity());
        hallEntity.setLocation(hall.getLocation());
        hallEntity.setStatus(hall.getStatus());
        hallEntity.setDescription(hall.getDescription());
        return hallEntity;
    }

    /**
     * Saves a new hall entity to the repository.
     * 
     * This method first checks if a hall with the same ID already exists. If it does,
     * a {@link HallException.HallExceptionNotFound} is thrown. Otherwise, it validates
     * the hall entity and saves it to the repository.
     * 
     * @param hall the {@link HallEntity} object to be saved
     * @return the saved {@link HallEntity} object
     * @throws HallException.HallExceptionNotFound if a hall with the same ID already exists
     */
    public HallEntity saveHall(HallEntityRequestDTO hall) {
        HallEntity hallEntity = transformToHallEntity(hall);
        validateSaveHall(hallEntity);
        return hallRepository.save(hallEntity);
    }

    /**
     * Validates the provided HallEntity object before saving it.
     * 
     * <p>This method performs the following validations:
     * <ul>
     *   <li>Checks if the hall name is null or empty. If so, throws a 
     *       {@link HallException.HallExceptionNotFound} with an appropriate message.</li>
     *   <li>Checks if the hall name already exists using the {@code validateNameHall} method. 
     *       If the name exists, throws a {@link HallException.HallExceptionNotFound} with an appropriate message.</li>
     *   <li>Checks if the hall capacity is less than or equal to zero. If so, throws a 
     *       {@link HallException.HallExceptionNotFound} with an appropriate message.</li>
     * </ul>
     * 
     * @param hall the {@link HallEntity} object to validate
     * @throws HallException.HallExceptionNotFound if any validation fails
     */
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

    /**
     * Validates the uniqueness of a hall name by checking if the given name
     * already exists in the list of all halls.
     *
     * @param name the name of the hall to validate
     * @return {@code true} if the name is unique and does not exist in the list of halls,
     *         {@code false} otherwise
     */
    private boolean validateNameHall(String name) {
        List<HallEntity> halls = hallRepository.findAll();
        for (HallEntity hall : halls) {
            if (hall.getName().equalsIgnoreCase(name)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Deletes a hall with the specified ID.
     *
     * @param id the ID of the hall to be deleted
     * @return a confirmation message indicating the hall has been deleted
     * @throws HallException.HallExceptionNotFound if no hall with the specified ID exists
     */
    public String deleteHall(int id) {
        HallEntity hall = getHallById(id);
        if (hall == null) {
            throw new HallException.HallExceptionNotFound("El salon con id " + id + " no existe");
        }
        hallRepository.deleteById(id);
        return "El salon con el id " + id + " ha sido eliminado";
    }
    
}