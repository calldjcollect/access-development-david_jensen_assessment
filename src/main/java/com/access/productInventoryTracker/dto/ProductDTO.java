package com.access.productInventoryTracker.dto;

public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private String category;
    private boolean available;

    // Constructor, getters and setters
    public ProductDTO(Long id, String name, double price, String category, boolean available) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return available;
    }
}
