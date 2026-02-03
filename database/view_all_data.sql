-- Quick View Script for Agriculture Website Database
-- Run this to see all data in all tables
-- Usage: mysql -u root -p < view_all_data.sql

USE finalproject;

-- View Membership Table
SELECT '=== MEMBERSHIP TABLE (Users) ===' AS '';
SELECT 
    UserName AS 'Username',
    Email AS 'Email',
    MobileNo AS 'Mobile',
    CreatedAt AS 'Registered On'
FROM membership
ORDER BY CreatedAt DESC;

-- View Contact Us Table
SELECT '' AS '';
SELECT '=== CONTACT US TABLE (Contact Submissions) ===' AS '';
SELECT 
    Id,
    Name,
    Email,
    MobileNo AS 'Mobile',
    Type AS 'Inquiry Type',
    LEFT(Message, 100) AS 'Message Preview',
    CreatedAt AS 'Submitted On'
FROM contactus
ORDER BY CreatedAt DESC;

-- View Posts Table
SELECT '' AS '';
SELECT '=== POSTS TABLE (Machine/Product Listings) ===' AS '';
SELECT 
    Id,
    Name AS 'Product Name',
    Email AS 'Contact Email',
    Mobile AS 'Contact Mobile',
    CONCAT('₹', FORMAT(Price, 2)) AS 'Price',
    location AS 'Location',
    Owner AS 'Posted By',
    CreatedAt AS 'Posted On'
FROM posts
ORDER BY CreatedAt DESC;

-- View Crop Sales Table
SELECT '' AS '';
SELECT '=== CROP SALES TABLE (Crop Listings) ===' AS '';
SELECT 
    Id,
    cropid AS 'Crop ID',
    Name AS 'Crop Name',
    Quantity,
    address AS 'Location',
    mobileNo AS 'Contact',
    CreatedAt AS 'Posted On'
FROM cropsales
ORDER BY CreatedAt DESC;

-- Summary Count
SELECT '' AS '';
SELECT '=== SUMMARY (Record Counts) ===' AS '';
SELECT 
    'membership' AS 'Table',
    COUNT(*) AS 'Total Records'
FROM membership
UNION ALL
SELECT 
    'contactus',
    COUNT(*)
FROM contactus
UNION ALL
SELECT 
    'posts',
    COUNT(*)
FROM posts
UNION ALL
SELECT 
    'cropsales',
    COUNT(*)
FROM cropsales;
