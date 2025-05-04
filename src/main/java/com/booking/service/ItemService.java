package com.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.booking.repository.ItemRepository;
import com.booking.entity.HallEntity;
import com.booking.entity.ItemEntity;
import com.booking.entity.dto.ItemEntityRequest;
import com.booking.exception.ItemException;

import java.util.List;

@Service
public class ItemService {
    /**
     * The repository for managing item entities.
     */
    @Autowired
    private ItemRepository itemRepository;

    /**
     * The service for managing hall entities.
     */
    @Autowired
    private HallService hallService;

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
     * Transforms an {@link ItemEntityRequest} object into an {@link ItemEntity} object.
     * 
     * @param item the {@link ItemEntityRequest} containing the data to be transformed.
     * @return the transformed {@link ItemEntity} object.
     * @throws ItemException if the hall associated with the given hall ID is not found.
     */
    private ItemEntity transformToEntity(ItemEntityRequest item) {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setName(item.getName());
        itemEntity.setStatus(item.getStatus());
        itemEntity.setDescription(item.getDescription());
        itemEntity.setCategory(item.getCategory());
        itemEntity.setQuantity(item.getQuantity());
        itemEntity.setQuantityAvailable(item.getQuantity());
        itemEntity.setAvailable(item.isAvailable());
        HallEntity hallEntity = hallService.getHallById(item.getHall());
        if (hallEntity == null) {
            throw new ItemException("Hall not found with id: " + item.getHall());
        }
        itemEntity.setHall(hallEntity);
        return itemEntity;
    }

    /**
     * Validates the provided ItemEntityRequest object to ensure it meets the required criteria.
     *
     * @param item the ItemEntityRequest object to validate
     * @throws ItemException if:
     *         - the item name is null or empty
     *         - the item description is null or empty
     *         - the item category is null or empty
     *         - the item quantity is less than or equal to zero
     *         - an item with the same name already exists in the repository
     */
    private void validateItem(ItemEntityRequest item) {
        if (item.getName() == null || item.getName().isEmpty()) {
            throw new ItemException("Item name cannot be null or empty");
        }
        if (item.getDescription() == null || item.getDescription().isEmpty()) {
            throw new ItemException("Item description cannot be null or empty");
        }
        if (item.getCategory() == null || item.getCategory().isEmpty()) {
            throw new ItemException("Item category cannot be null or empty");
        }
        if (item.getQuantity() <= 0) {
            throw new ItemException("Item quantity must be greater than zero");
        }
        ItemEntity existingItem = itemRepository.findByName(item.getName());
        if (existingItem != null) {
            throw new ItemException("Item with name " + item.getName() + " already exists");
        }
    }

    /**
     * Saves the given item entity to the repository.
     *
     * @param item the item entity to be saved
     * @return the saved item entity
     */
    public ItemEntity saveItem(ItemEntityRequest item) {
        ItemEntity itemEntity = transformToEntity(item);
        validateItem(item);
        return itemRepository.save(itemEntity);
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

    /**
     * Updates an existing item in the repository with the given details.
     *
     * @param id   The ID of the item to be updated.
     * @param item The updated item details.
     * @return The updated item entity if the item exists, or {@code null} if the item does not exist.
     */
    public ItemEntity updateItem(int id, ItemEntity item) {
        if (itemRepository.existsById(id)) {
            item.setId(id);
            return itemRepository.save(item);
        } else {
            return null;
        }
    }

    /**
     * Searches for items whose names contain the specified substring, ignoring case.
     *
     * @param name the substring to search for in item names
     * @return a list of {@link ItemEntity} objects that match the search criteria
     */
    public List<ItemEntity> searchItemsByName(String name) {
        return itemRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Searches for items based on the specified category.
     *
     * @param category the category to search for items
     * @return a list of ItemEntity objects that belong to the specified category
     */
    public List<ItemEntity> searchItemsByCategory(String category) {
        return itemRepository.findByCategory(category);
    }
}


