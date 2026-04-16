package org.example.btth.service;

import org.example.btth.model.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final List<Item> items = new ArrayList<>();

    public ItemService() {
        // Sample data for demo
        items.add(new Item(1L, "Màn hình rời 24 inch", "https://via.placeholder.com/200", 5, Item.Type.EQUIPMENT));
        items.add(new Item(2L, "Cáp HDMI 2m", "https://via.placeholder.com/200", 10, Item.Type.EQUIPMENT));
        items.add(new Item(3L, "Phòng Lab 101", "https://via.placeholder.com/200", 1, Item.Type.LAB));
        items.add(new Item(4L, "Phòng Lab 202", "https://via.placeholder.com/200", 1, Item.Type.LAB));
    }

    public List<Item> findAll() {
        return Collections.unmodifiableList(items);
    }

    public Optional<Item> findById(Long id) {
        return items.stream().filter(i -> i.getId().equals(id)).findFirst();
    }
}

