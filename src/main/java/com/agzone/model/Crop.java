package com.agzone.model;

import java.sql.Timestamp;

/**
 * Crop model class for crop sales
 */
public class Crop {
    private int id;
    private String cropName;
    private String category;
    private double price;
    private int quantity;
    private String unit;
    private String description;
    private String image;
    private int seller_id;
    private String location;
    private String harvestDate;
    private boolean available;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    public Crop() {
    }
    
    public Crop(String cropName, String category, double price, int quantity, String unit,
                String description, String image, int seller_id, String location) {
        this.cropName = cropName;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
        this.description = description;
        this.image = image;
        this.seller_id = seller_id;
        this.location = location;
        this.available = true;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getCropName() {
        return cropName;
    }
    
    public void setCropName(String cropName) {
        this.cropName = cropName;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public int getSeller_id() {
        return seller_id;
    }
    
    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getHarvestDate() {
        return harvestDate;
    }
    
    public void setHarvestDate(String harvestDate) {
        this.harvestDate = harvestDate;
    }
    
    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
