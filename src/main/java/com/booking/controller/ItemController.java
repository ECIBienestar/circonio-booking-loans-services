package com.booking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.booking.service.ItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.booking.entity.ItemEntity;
import com.booking.entity.dto.ItemEntityRequest;
import com.booking.exception.ItemException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'TEACHER', 'STUDENT')")
    @GetMapping("/{id}")
    @Operation(summary = "Get item by ID", description = "Retrieve an item by its ID", tags = { "Item Management" })
    public ResponseEntity<ItemEntity> getItemById(@PathVariable int id) {
        ItemEntity item = itemService.getItemById(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'TEACHER', 'STUDENT')")
    @GetMapping("/all")
    @Operation(summary = "Get all items", description = "Retrieve all items", tags = { "Item Management" })
    public ResponseEntity<?> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER')")
    @PostMapping
    @Operation(summary = "Add new item", description = "Add a new item to the system", tags = { "Item Management" })
    public ResponseEntity<?> addItem(@RequestBody ItemEntityRequest item) {
        try {
            ItemEntity savedItem = itemService.saveItem(item);
            return ResponseEntity.status(201).body(
                    Map.of("message", "Item creado correctamente", "item", savedItem)
            );
            
        } catch (ItemException e) {
            return ResponseEntity.status(400).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Error: " + e.getMessage()));
        }

    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete item by ID", description = "Delete an item by its ID", tags = { "Item Management" })
    public ResponseEntity<?> deleteItem(@PathVariable int id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'TEACHER', 'STUDENT')")
    @GetMapping("/hall/{id}")
    @Operation(summary = "Get items by hall ID", description = "Retrieve all items associated with a specific hall ID", tags = { "Item Management" })
    public ResponseEntity<?> findByIdHall(@PathVariable int id) {
        try {
            List<ItemEntity> item = itemService.getItemsByHallId(id);
            if (item != null) {
                return ResponseEntity.ok(item);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'TEACHER', 'STUDENT')")
    @GetMapping("/search")
    @Operation(summary = "Search items by name", description = "Search for items by their name", tags = { "Item Management" })
    public ResponseEntity<?> searchItemsByName(@RequestParam String name) {
        List<ItemEntity> items = itemService.searchItemsByName(name);
        if (items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(items);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER')")
    @PutMapping("/{id}")
    @Operation(summary = "Update item by ID", description = "Update an existing item by its ID", tags = { "Item Management" })
    public ResponseEntity<?> updateItem(@PathVariable int id, @RequestBody ItemEntity updatedItem) {
        ItemEntity item = itemService.updateItem(id, updatedItem);
        return item != null ? ResponseEntity.ok(item) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'TEACHER', 'STUDENT')")
    @GetMapping("/category/{name}")
    @Operation(summary = "Search items by category", description = "Search for items by their category name", tags = { "Item Management" },
            parameters = {
                    @Parameter(name = "available", description = "Filter by availability", required = false, in = ParameterIn.QUERY)
            })
    public ResponseEntity<?> searchItemsByCategory(@PathVariable String name,
                                                   @RequestParam(value = "available", required = false) Boolean available) {
        List<ItemEntity> items = itemService.searchItemsByCategory(name, available);
        return ResponseEntity.ok(items);
    }
}