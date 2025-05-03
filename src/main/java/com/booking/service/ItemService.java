package com.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.booking.repository.ItemRepository;
import com.booking.entity.ItemEntity;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public ItemEntity getItemById(int id) {
        return itemRepository.findById(id).orElse(null);
    }

    public ItemEntity saveItem(ItemEntity item) {
        return itemRepository.save(item);
    }

    public void deleteItem(int id) {
        itemRepository.deleteById(id);
    }

}



