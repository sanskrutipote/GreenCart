package com.agzone.model;

import java.sql.Timestamp;

/**
 * CropSale model class for crop sales
 */
public class CropSale {
    private int id;
    private String cropId;
    private String quantity;
    private String name;
    private String address;
    private String mobileNo;
    private Timestamp createdAt;
    
    public CropSale() {
    }
    
    public CropSale(int id, String cropId, String quantity, String name, 
                    String address, String mobileNo, Timestamp createdAt) {
        this.id = id;
        this.cropId = cropId;
        this.quantity = quantity;
        this.name = name;
        this.address = address;
        this.mobileNo = mobileNo;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getCropId() {
        return cropId;
    }
    
    public void setCropId(String cropId) {
        this.cropId = cropId;
    }
    
    public String getQuantity() {
        return quantity;
    }
    
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getMobileNo() {
        return mobileNo;
    }
    
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
