package com.booking.repository;
import com.booking.entity.ItemEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository <ItemEntity, Integer> {
    List<ItemEntity> findByHallId(int hallId);
    List<ItemEntity> findByNameContainingIgnoreCase(String name);
    ItemEntity findByName(String name);
    List<ItemEntity> findByCategory(String category);
}
