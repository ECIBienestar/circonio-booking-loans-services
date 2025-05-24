package com.booking.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.booking.entity.HallEntity;
import com.booking.entity.ItemEntity;
import com.booking.entity.dto.ItemEntityRequest;
import com.booking.exception.ItemException;
import com.booking.repository.ItemRepository;
import com.booking.service.HallService;
import com.booking.service.ItemService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private HallService hallService;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetItemById_found() {
        ItemEntity item = new ItemEntity();
        item.setId(1);
        when(itemRepository.findById(1)).thenReturn(Optional.of(item));

        ItemEntity result = itemService.getItemById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetItemById_notFound() {
        when(itemRepository.findById(1)).thenReturn(Optional.empty());

        ItemEntity result = itemService.getItemById(1);
        assertNull(result);
    }

    @Test
    void testSaveItem_validRequest() {
        ItemEntityRequest request = new ItemEntityRequest();
        request.setName("Proyector");
        request.setDescription("Proyector HD");
        request.setCategory("Tecnología");
        request.setQuantity(5);
        request.setAvailable(true);
        request.setHall(10);
        request.setStatus("Activo");

        HallEntity hall = new HallEntity();
        hall.setId(10);

        when(hallService.getHallById(10)).thenReturn(hall);
        when(itemRepository.findByName("Proyector")).thenReturn(null);
        when(itemRepository.save(any(ItemEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        ItemEntity savedItem = itemService.saveItem(request);
        assertEquals("Proyector", savedItem.getName());
        assertEquals("Tecnología", savedItem.getCategory());
    }

    @Test
    void testSaveItem_duplicateName_throwsException() {
        ItemEntityRequest request = new ItemEntityRequest();
        request.setName("Duplicado");
        request.setDescription("Desc");
        request.setCategory("Cat");
        request.setQuantity(2);
        request.setAvailable(true);
        request.setHall(1);

        HallEntity hall = new HallEntity();
        hall.setId(1);

        when(hallService.getHallById(1)).thenReturn(hall);
        when(itemRepository.findByName("Duplicado")).thenReturn(new ItemEntity());

        assertThrows(ItemException.class, () -> itemService.saveItem(request));
    }

    @Test
    void testDeleteItem_success() {
        ItemEntity item = new ItemEntity();
        item.setId(1);
        when(itemRepository.findById(1)).thenReturn(Optional.of(item));

        String result = itemService.deleteItem(1);
        assertEquals("Item deleted with id: 1", result);
        verify(itemRepository).deleteById(1);
    }

    @Test
    void testDeleteItem_notFound() {
        when(itemRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ItemException.class, () -> itemService.deleteItem(1));
    }

    @Test
    void testGetAllItems() {
        when(itemRepository.findAll()).thenReturn(List.of(new ItemEntity(), new ItemEntity()));
        List<ItemEntity> items = itemService.getAllItems();
        assertEquals(2, items.size());
    }

    @Test
    void testGetItemsByHallId_success() {
        when(itemRepository.findByHallId(5)).thenReturn(List.of(new ItemEntity()));
        List<ItemEntity> items = itemService.getItemsByHallId(5);
        assertFalse(items.isEmpty());
    }

    @Test
    void testGetItemsByHallId_empty() {
        when(itemRepository.findByHallId(5)).thenReturn(List.of());
        assertThrows(ItemException.class, () -> itemService.getItemsByHallId(5));
    }

    @Test
    void testSearchItemsByCategory_foundAvailableTrue() {
        ItemEntity item1 = new ItemEntity();
        item1.setAvailable(true);
        ItemEntity item2 = new ItemEntity();
        item2.setAvailable(false);

        when(itemRepository.findByCategory("Tecnología")).thenReturn(List.of(item1, item2));

        List<ItemEntity> result = itemService.searchItemsByCategory("Tecnología", true);
        assertEquals(1, result.size());
        assertTrue(result.get(0).isAvailable());
    }

    @Test
    void testSearchItemsByCategory_foundAvailableFalse() {
        ItemEntity item1 = new ItemEntity();
        item1.setAvailable(false);

        when(itemRepository.findByCategory("Tecnología")).thenReturn(List.of(item1));

        List<ItemEntity> result = itemService.searchItemsByCategory("Tecnología", false);
        assertEquals(1, result.size());
        assertFalse(result.get(0).isAvailable());
    }

    @Test
    void testSearchItemsByCategory_nullAvailable() {
        ItemEntity item1 = new ItemEntity();
        item1.setAvailable(true);
        ItemEntity item2 = new ItemEntity();
        item2.setAvailable(false);

        when(itemRepository.findByCategory("Tecnología")).thenReturn(List.of(item1, item2));

        List<ItemEntity> result = itemService.searchItemsByCategory("Tecnología", null);
        assertEquals(1, result.size()); // Solo debe retornar los disponibles
        assertTrue(result.get(0).isAvailable());
    }

    @Test
    void testSearchItemsByCategory_notFound_returnsNull() {
        when(itemRepository.findByCategory("NoExiste")).thenReturn(List.of());
        List<ItemEntity> result = itemService.searchItemsByCategory("NoExiste", true);
        assertNull(result);
    }

    @Test
    void testSearchItemsByName_found() {
        ItemEntity item1 = new ItemEntity();
        item1.setName("Proyector");
        ItemEntity item2 = new ItemEntity();
        item2.setName("Mini Proyector");

        when(itemRepository.findByNameContainingIgnoreCase("proyector")).thenReturn(List.of(item1, item2));

        List<ItemEntity> result = itemService.searchItemsByName("proyector");
        assertEquals(2, result.size());
    }

    @Test
    void testSearchItemsByName_notFound() {
        when(itemRepository.findByNameContainingIgnoreCase("xxx")).thenReturn(List.of());
        List<ItemEntity> result = itemService.searchItemsByName("xxx");
        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdateItem_exists() {
        ItemEntity item = new ItemEntity();
        item.setName("Actualizado");

        when(itemRepository.existsById(1)).thenReturn(true);
        when(itemRepository.save(item)).thenReturn(item);

        ItemEntity result = itemService.updateItem(1, item);
        assertNotNull(result);
        assertEquals("Actualizado", result.getName());
        assertEquals(1, item.getId());
    }

    @Test
    void testUpdateItem_notExists() {
        ItemEntity item = new ItemEntity();
        when(itemRepository.existsById(99)).thenReturn(false);

        ItemEntity result = itemService.updateItem(99, item);
        assertNull(result);
    }

}
