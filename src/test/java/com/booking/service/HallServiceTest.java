package com.booking.service;

import com.booking.entity.HallEntity;
import com.booking.entity.dto.HallEntityRequestDTO;
import com.booking.exception.HallException;
import com.booking.repository.HallRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HallServiceTest {

    @InjectMocks
    private HallService hallService;

    @Mock
    private HallRepository hallRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getHallById_ReturnsHall_WhenFound() {
        HallEntity hall = new HallEntity();
        hall.setId(1);
        when(hallRepository.findById(1)).thenReturn(Optional.of(hall));

        HallEntity result = hallService.getHallById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(hallRepository).findById(1);
    }

    @Test
    void getHallById_ReturnsNull_WhenNotFound() {
        when(hallRepository.findById(1)).thenReturn(Optional.empty());

        HallEntity result = hallService.getHallById(1);
        assertNull(result);
        verify(hallRepository).findById(1);
    }

    @Test
    void getAllHalls_ReturnsListOfHalls() {
        List<HallEntity> halls = new ArrayList<>();
        halls.add(new HallEntity());
        when(hallRepository.findAll()).thenReturn(halls);

        List<HallEntity> result = hallService.getAllHalls();
        assertEquals(1, result.size());
        verify(hallRepository).findAll();
    }

    @Test
    void transformToHallEntity_CopiesProperties() {
        HallEntityRequestDTO dto = new HallEntityRequestDTO();
        dto.setName("Main Hall");
        dto.setCapacity(100);
        dto.setLocation("First Floor");
        dto.setStatus("Available");
        dto.setDescription("Spacious hall");

        HallEntity entity = hallService.transformToHallEntity(dto);
        assertEquals(0, entity.getId()); // Hardcoded in transform
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getCapacity(), entity.getCapacity());
        assertEquals(dto.getLocation(), entity.getLocation());
        assertEquals(dto.getStatus(), entity.getStatus());
        assertEquals(dto.getDescription(), entity.getDescription());
    }

    @Test
    void saveHall_SuccessfulSave() {
        HallEntityRequestDTO dto = new HallEntityRequestDTO();
        dto.setName("New Hall");
        dto.setCapacity(50);
        dto.setLocation("Second Floor");
        dto.setStatus("Available");
        dto.setDescription("Description");

        when(hallRepository.findAll()).thenReturn(new ArrayList<>()); // no halls, so name is unique
        when(hallRepository.save(any(HallEntity.class))).thenAnswer(i -> i.getArgument(0));

        HallEntity saved = hallService.saveHall(dto);

        assertNotNull(saved);
        assertEquals(dto.getName(), saved.getName());
        verify(hallRepository).save(any(HallEntity.class));
    }

    @Test
    void saveHall_ThrowsException_WhenNameIsEmpty() {
        HallEntityRequestDTO dto = new HallEntityRequestDTO();
        dto.setName("");
        dto.setCapacity(10);

        HallException.HallExceptionNotFound exception = assertThrows(
                HallException.HallExceptionNotFound.class,
                () -> hallService.saveHall(dto)
        );

        assertEquals("El nombre del salon no puede estar vacio", exception.getMessage());
    }

    @Test
    void saveHall_ThrowsException_WhenNameAlreadyExists() {
        HallEntityRequestDTO dto = new HallEntityRequestDTO();
        dto.setName("Existing Hall");
        dto.setCapacity(10);

        List<HallEntity> halls = new ArrayList<>();
        HallEntity existing = new HallEntity();
        existing.setName("Existing Hall");
        halls.add(existing);

        when(hallRepository.findAll()).thenReturn(halls);

        HallException.HallExceptionNotFound exception = assertThrows(
                HallException.HallExceptionNotFound.class,
                () -> hallService.saveHall(dto)
        );

        assertEquals("El nombre del salon ya existe", exception.getMessage());
    }

    @Test
    void saveHall_ThrowsException_WhenCapacityIsZeroOrLess() {
        HallEntityRequestDTO dto = new HallEntityRequestDTO();
        dto.setName("New Hall");
        dto.setCapacity(0);

        when(hallRepository.findAll()).thenReturn(new ArrayList<>());

        HallException.HallExceptionNotFound exception = assertThrows(
                HallException.HallExceptionNotFound.class,
                () -> hallService.saveHall(dto)
        );

        assertEquals("La capacidad del salon no puede ser menor o igual a 0", exception.getMessage());
    }

    @Test
    void saveHall_UpdatesExistingHall() {
        HallEntity hall = new HallEntity();
        hall.setId(1);
        hall.setName("Updated Hall");
        hall.setCapacity(20);

        when(hallRepository.save(hall)).thenReturn(hall);

        HallEntity updated = hallService.saveHall(hall);
        assertEquals("Updated Hall", updated.getName());
        verify(hallRepository).save(hall);
    }

    @Test
    void deleteHall_SuccessfulDelete() {
        HallEntity hall = new HallEntity();
        hall.setId(1);
        when(hallRepository.findById(1)).thenReturn(Optional.of(hall));
        doNothing().when(hallRepository).deleteById(1);

        String result = hallService.deleteHall(1);

        assertEquals("El salon con el id 1 ha sido eliminado", result);
        verify(hallRepository).deleteById(1);
    }

    @Test
    void deleteHall_ThrowsException_WhenHallNotFound() {
        when(hallRepository.findById(1)).thenReturn(Optional.empty());

        HallException.HallExceptionNotFound exception = assertThrows(
                HallException.HallExceptionNotFound.class,
                () -> hallService.deleteHall(1)
        );

        assertEquals("El salon con id 1 no existe", exception.getMessage());
    }

}

