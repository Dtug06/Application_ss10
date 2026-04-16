package org.example.btth.model;

import java.util.Objects;

public class Item {

    public enum Type {
        EQUIPMENT, LAB
    }

    private Long id;
    private String name;
    private String imageUrl;
    private int stock;
    private Type type;

    public Item() {
    }

    public Item(Long id, String name, String imageUrl, int stock, Type type) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

