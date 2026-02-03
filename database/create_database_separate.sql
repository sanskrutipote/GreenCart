-- Agriculture Website Database - Separate Database Setup
-- Database: agriculture_website
-- Created for Java Servlet Application
-- This creates a separate database from finalproject

-- Create new database
CREATE DATABASE IF NOT EXISTS agriculture_website;
USE agriculture_website;

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cropsales;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS contactus;
DROP TABLE IF EXISTS membership;

-- Table: membership
-- Stores user registration and login information
CREATE TABLE membership (
    UserName VARCHAR(50) PRIMARY KEY,
    Email VARCHAR(100) NOT NULL,
    MobileNo VARCHAR(15) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_email (Email),
    INDEX idx_mobile (MobileNo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table: contactus
-- Stores contact form submissions
CREATE TABLE contactus (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    MobileNo VARCHAR(15) NOT NULL,
    Type VARCHAR(50) NOT NULL,
    Message TEXT NOT NULL,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_email (Email),
    INDEX idx_created (CreatedAt)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table: posts
-- Stores product/machine posts for sale
CREATE TABLE posts (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    Mobile VARCHAR(15) NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    location VARCHAR(255) NOT NULL,
    image VARCHAR(255),
    Owner VARCHAR(50) NOT NULL,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_owner (Owner),
    INDEX idx_email (Email),
    INDEX idx_location (location),
    FOREIGN KEY (Owner) REFERENCES membership(UserName) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table: cropsales
-- Stores crop sales information
CREATE TABLE cropsales (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    cropid VARCHAR(50) NOT NULL,
    Quantity VARCHAR(50) NOT NULL,
    Name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    mobileNo VARCHAR(15) NOT NULL,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_cropid (cropid),
    INDEX idx_name (Name),
    INDEX idx_mobile (mobileNo),
    INDEX idx_created (CreatedAt)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table: orders
-- Stores order information
CREATE TABLE orders (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    UserName VARCHAR(50) NOT NULL,
    TotalAmount DECIMAL(10, 2) NOT NULL,
    Address TEXT NOT NULL,
    Status VARCHAR(20) DEFAULT 'Pending',
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user (UserName),
    INDEX idx_status (Status),
    INDEX idx_created (CreatedAt),
    FOREIGN KEY (UserName) REFERENCES membership(UserName) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table: order_items
-- Stores individual items in an order
CREATE TABLE order_items (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    OrderId INT NOT NULL,
    ItemName VARCHAR(255) NOT NULL,
    Quantity INT NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_order (OrderId),
    FOREIGN KEY (OrderId) REFERENCES orders(Id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert sample data (optional)
-- Sample user for testing
INSERT INTO membership (UserName, Email, MobileNo, Password) 
VALUES ('admin', 'admin@greencart.com', '1234567890', 'admin123');

-- Display table structures
SHOW TABLES;

-- Verify database creation
SELECT 'Database agriculture_website created successfully!' AS 'Status';
SELECT DATABASE() AS 'Current Database';
