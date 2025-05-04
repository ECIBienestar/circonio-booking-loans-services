package com.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.booking.repository.ItemRepository;
import com.booking.entity.ItemEntity;
import com.booking.exception.ItemException;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    /**
     * Retrieves an item by its unique identifier.
     *
     * @param id the unique identifier of the item to retrieve
     * @return the {@link ItemEntity} if found, or {@code null} if no item exists with the given ID
     */
    public ItemEntity getItemById(int id) {
        return itemRepository.findById(id).orElse(null);
    }

    /**
     * Saves the given item entity to the repository.
     *
     * @param item the item entity to be saved
     * @return the saved item entity
     */
    public ItemEntity saveItem(ItemEntity item) {
        return itemRepository.save(item);
    }

    /**
     * Deletes an item with the specified ID from the repository.
     *
     * @param id the ID of the item to be deleted
     * @return a confirmation message indicating the item has been deleted
     * @throws RuntimeException if no item is found with the specified ID
     */
    public String deleteItem(int id) {
        ItemEntity item = getItemById(id);
        if (item == null) {
            throw new ItemException("Item not found with id: " + id);
        }
        itemRepository.deleteById(id);
        return "Item deleted with id: " + id;
    }

    /**
     * Retrieves a list of all items from the repository.
     *
     * @return a list of {@link ItemEntity} objects representing all items.
     */
    public List<ItemEntity> getAllItems() {
        return itemRepository.findAll();
    }

    /**
     * Retrieves a list of items associated with a specific hall ID.
     *
     * @param hallId the ID of the hall for which items are to be retrieved
     * @return a list of {@link ItemEntity} objects associated with the given hall ID
     * @throws ItemException if no items are found for the specified hall ID
     */
    public List<ItemEntity> getItemsByHallId(int hallId) {
        List<ItemEntity> itemListSearch =  itemRepository.findByHallId(hallId);
        if (itemListSearch.isEmpty()) {
            throw new ItemException("No items found for hall ID: " + hallId);
        }
        return itemListSearch;
    }

    public ItemEntity updateItem(int id, ItemEntity item) {
        if (itemRepository.existsById(id)) {
            item.setId(id);
            return itemRepository.save(item);
        } else {
            return null;
        }
    }

    public List<ItemEntity> searchItemsByName(String name) {
        return itemRepository.findByNameContainingIgnoreCase(name);
    }
}