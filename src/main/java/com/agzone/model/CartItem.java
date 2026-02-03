package com.agzone.model;

import java.sql.Timestamp;

/**
 * CartItem model class
 */
public class CartItem {
    private int id;
    private int userId;
    private Integer cropId;
    private Integer machineId;
    private String itemName;
    private double price;
    private int quantity;
    private String itemType; // crop or machine
    private Timestamp addedAt;
    
    public CartItem() {
    }
    
    public CartItem(int userId, String itemName, double price, int quantity, String itemType) {
        this.userId = userId;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.itemType = itemType;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public Integer getCropId() {
        return cropId;
    }
    
    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }
    
    public Integer getMachineId() {
        return machineId;
    }
    
    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
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
    
    public String getItemType() {
        return itemType;
    }
    
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    
    public Timestamp getAddedAt() {
        return addedAt;
    }
    
    public void setAddedAt(Timestamp addedAt) {
        this.addedAt = addedAt;
    }
    
    public double getTotalPrice() {
        return price * quantity;
    }
}
