-- Create agriculture_website database
CREATE DATABASE IF NOT EXISTS agriculture_website;
USE agriculture_website;

-- Drop existing tables if they exist
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS crops;
DROP TABLE IF EXISTS contacts;
DROP TABLE IF EXISTS membership;

-- Membership/Users Table
CREATE TABLE membership (
    id INT AUTO_INCREMENT PRIMARY KEY,
    UserName VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(120) UNIQUE NOT NULL,
    mobileNo VARCHAR(15) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    userType ENUM('farmer', 'buyer', 'admin') DEFAULT 'buyer',
    location VARCHAR(255),
    profileImage VARCHAR(255),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (UserName),
    INDEX idx_email (email)
);

-- Crops Table
CREATE TABLE crops (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cropName VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    unit VARCHAR(50),
    description TEXT,
    image VARCHAR(255),
    seller_id INT NOT NULL,
    location VARCHAR(255),
    harvestDate DATE,
    available BOOLEAN DEFAULT TRUE,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (seller_id) REFERENCES membership(id),
    INDEX idx_category (category),
    INDEX idx_seller (seller_id),
    INDEX idx_available (available)
);

-- Machinery/Posts Table (For machinery rental/sale)
CREATE TABLE posts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(120) NOT NULL,
    mobile VARCHAR(15) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    location VARCHAR(255) NOT NULL,
    image VARCHAR(255),
    owner VARCHAR(100) NOT NULL,
    postType ENUM('rent', 'sell', 'buy') DEFAULT 'sell',
    description TEXT,
    specifications VARCHAR(500),
    condition ENUM('excellent', 'good', 'average', 'fair') DEFAULT 'good',
    userId INT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES membership(id),
    INDEX idx_postType (postType),
    INDEX idx_owner (owner),
    INDEX idx_location (location)
);

-- Contact Us Table
CREATE TABLE contacts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL,
    subject VARCHAR(255),
    message TEXT NOT NULL,
    phoneNumber VARCHAR(15),
    status ENUM('new', 'read', 'replied') DEFAULT 'new',
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_status (status)
);

-- Orders Table
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    orderId VARCHAR(50) UNIQUE NOT NULL,
    userId INT NOT NULL,
    totalAmount DECIMAL(10, 2) NOT NULL,
    status ENUM('pending', 'confirmed', 'shipped', 'delivered', 'cancelled') DEFAULT 'pending',
    paymentMethod VARCHAR(50),
    shippingAddress TEXT,
    contactNumber VARCHAR(15),
    notes TEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES membership(id),
    INDEX idx_userId (userId),
    INDEX idx_orderId (orderId),
    INDEX idx_status (status)
);

-- Cart Items Table
CREATE TABLE cart_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    cropId INT,
    machineId INT,
    itemName VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    itemType ENUM('crop', 'machine') NOT NULL,
    addedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES membership(id),
    UNIQUE KEY unique_cart (userId, itemType, cropId, machineId),
    INDEX idx_userId (userId)
);

-- Create indexes for better performance
CREATE INDEX idx_orders_userId ON orders(userId);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_crops_sellerId ON crops(seller_id);
CREATE INDEX idx_posts_userId ON posts(userId);

-- Verification message
SELECT 'Database structure created successfully' as Status;
