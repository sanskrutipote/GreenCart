-- =====================================================
-- GreenCart Consolidated Database Setup Script
-- Optimized for MySQL Workbench
-- Date: January 2026
-- =====================================================

-- 1. Create and Use Database
CREATE DATABASE IF NOT EXISTS agriculture_website;
USE agriculture_website;

-- 2. Clean up existing tables (Optional)
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cropsales;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS contactus;
DROP TABLE IF EXISTS membership;
SET FOREIGN_KEY_CHECKS = 1;

-- 3. Create Tables

-- Users Table
CREATE TABLE membership (
    id INT AUTO_INCREMENT PRIMARY KEY,
    UserName VARCHAR(100) UNIQUE NOT NULL,
    Email VARCHAR(120) UNIQUE NOT NULL,
    MobileNo VARCHAR(15) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Contact Form Table
CREATE TABLE contactus (
    id INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Email VARCHAR(120) NOT NULL,
    MobileNo VARCHAR(15) NOT NULL,
    Type VARCHAR(50),
    Message TEXT NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Machinery/Tools Posts Table
CREATE TABLE posts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Email VARCHAR(120) NOT NULL,
    Mobile VARCHAR(15) NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    location VARCHAR(255) NOT NULL,
    image VARCHAR(255),
    Owner VARCHAR(100) NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crop Sales Table
CREATE TABLE cropsales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cropid VARCHAR(50) NOT NULL,
    Quantity VARCHAR(50) NOT NULL,
    Name VARCHAR(100) NOT NULL,
    address TEXT NOT NULL,
    mobileNo VARCHAR(15) NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Orders Table
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    UserName VARCHAR(100) NOT NULL,
    TotalAmount DECIMAL(10, 2) NOT NULL,
    Address TEXT NOT NULL,
    Status VARCHAR(50) DEFAULT 'Pending',
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Order Items Table
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    OrderId INT NOT NULL,
    ItemName VARCHAR(255) NOT NULL,
    Quantity INT NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (OrderId) REFERENCES orders(id)
);

-- 4. Insert Sample Data

-- Users
INSERT INTO membership (UserName, Email, MobileNo, Password) VALUES
('farmer_raj', 'raj.kumar@gmail.com', '9876543210', 'password123'),
('krishna_farm', 'krishna.reddy@yahoo.com', '9845612378', 'farm2026'),
('priya_green', 'priya.nair@gmail.com', '9012345678', 'green2026'),
('vijay_agri', 'vijay.kumar@hotmail.com', '9321098765', 'vijay2026'),
('meera_agro', 'meera.nair@hotmail.com', '9789012345', 'meera2026');

-- Contacts
INSERT INTO contactus (Name, Email, MobileNo, Type, Message) VALUES
('Rajesh Kumar', 'rajesh.k@gmail.com', '9876543210', 'General', 'Interested in crop pricing.'),
('Ananya Sharma', 'ananya.s@yahoo.com', '9845612378', 'Support', 'Order delay issue #1234.');

-- Posts (Machinery)
INSERT INTO posts (Name, Email, Mobile, Price, location, image, Owner) VALUES
('John Deere Tractor 5050D', 'farmer_raj@gmail.com', '9876543210', 850000.00, 'Bangalore', 'Images/tractor1.jpg', 'farmer_raj'),
('Mahindra 575 DI Tractor', 'krishna.reddy@yahoo.com', '9845612378', 720000.00, 'Hyderabad', 'Images/tractor2.jpg', 'krishna_farm');

-- Crop Sales
INSERT INTO cropsales (cropid, Quantity, Name, address, mobileNo) VALUES
('RICE001', '500 kg', 'Ramesh Gupta', 'Lucknow, UP', '9234567890'),
('WHEAT002', '1000 kg', 'Sunita Verma', 'Indore, MP', '9678901234');

-- Orders
INSERT INTO orders (UserName, TotalAmount, Address, Status) VALUES
('farmer_raj', 2500.00, '123 Farm Road, Bangalore', 'Delivered'),
('krishna_farm', 4200.00, '456 Agri Street, Hyderabad', 'Shipped');

-- Order Items
INSERT INTO order_items (OrderId, ItemName, Quantity, Price) VALUES
(1, 'Sweet Potato', 5, 200.00),
(1, 'Corn', 3, 180.00),
(2, 'Wheat', 10, 160.00);

-- 5. Verification
SELECT 'Database Setup Complete!' AS Message;
SELECT * FROM membership;
USE green_cart;

CREATE TABLE `user` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(120) UNIQUE NOT NULL,
    mobile_no VARCHAR(15) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
select * from user;