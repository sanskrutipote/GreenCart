package com.agzone.model;

/**
 * User model class
 */
public class User {
    private String userName;
    private String email;
    private String mobileNo;
    private String password;
    
    public User() {
    }
    
    public User(String userName, String email, String mobileNo, String password) {
        this.userName = userName;
        this.email = email;
        this.mobileNo = mobileNo;
        this.password = password;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getMobileNo() {
        return mobileNo;
    }
    
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
