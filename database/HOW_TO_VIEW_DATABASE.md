# How to View Your Database

This guide shows you how to view and explore your `finalproject` database.

## Quick Start - View All Data

### Method 1: MySQL Command Line (Fastest)

1. **Open Command Prompt or PowerShell**

2. **Connect to MySQL:**
   ```bash
   mysql -u root -p
   ```
   Enter password: `pass@1234` (or your MySQL password)

3. **Select the database:**
   ```sql
   USE finalproject;
   ```

4. **View all tables:**
   ```sql
   SHOW TABLES;
   ```

5. **View data from any table:**
   ```sql
   -- View all users
   SELECT * FROM membership;
   
   -- View all contact submissions
   SELECT * FROM contactus;
   
   -- View all posts
   SELECT * FROM posts;
   
   -- View all crop sales
   SELECT * FROM cropsales;
   ```

---

## Method 2: MySQL Workbench (Easiest - GUI)

### Step 1: Open MySQL Workbench
- Search "MySQL Workbench" in Windows Start Menu
- Click to open

### Step 2: Connect
- Double-click your connection (or create one if needed)
- Enter password: `pass@1234`

### Step 3: View Database
1. In the left sidebar, expand **"Schemas"**
2. Expand **"finalproject"**
3. Expand **"Tables"**
4. Right-click any table → **"Select Rows - Limit 1000"**

---

## Method 3: phpMyAdmin (If using XAMPP)

1. **Start XAMPP** and ensure MySQL is running
2. **Open browser** and go to: `http://localhost/phpmyadmin`
3. **Click** on **"finalproject"** database in left sidebar
4. **Click** on any table name to view data

---

## Useful SQL Queries to View Data

### View All Users
```sql
USE finalproject;
SELECT * FROM membership;
```

### View Users with Formatted Output
```sql
SELECT 
    UserName AS 'Username',
    Email AS 'Email',
    MobileNo AS 'Mobile',
    CreatedAt AS 'Registered On'
FROM membership
ORDER BY CreatedAt DESC;
```

### View Contact Form Submissions
```sql
SELECT * FROM contactus ORDER BY CreatedAt DESC;
```

### View Contact Submissions with Details
```sql
SELECT 
    Id,
    Name,
    Email,
    Type AS 'Inquiry Type',
    LEFT(Message, 50) AS 'Message Preview',
    CreatedAt AS 'Submitted On'
FROM contactus
ORDER BY CreatedAt DESC;
```

### View All Machine/Product Posts
```sql
SELECT * FROM posts ORDER BY CreatedAt DESC;
```

### View Posts with Formatted Price
```sql
SELECT 
    Id,
    Name AS 'Product Name',
    CONCAT('₹', FORMAT(Price, 2)) AS 'Price',
    location AS 'Location',
    Owner AS 'Posted By',
    CreatedAt AS 'Posted On'
FROM posts
ORDER BY CreatedAt DESC;
```

### View All Crop Sales
```sql
SELECT * FROM cropsales ORDER BY CreatedAt DESC;
```

### View Crop Sales Summary
```sql
SELECT 
    Name AS 'Crop Name',
    Quantity,
    address AS 'Location',
    mobileNo AS 'Contact',
    CreatedAt AS 'Posted On'
FROM cropsales
ORDER BY CreatedAt DESC;
```

### Count Records in Each Table
```sql
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
```

### View Recent Activity (Last 10 Records)
```sql
-- Recent users
SELECT 'User Registration' AS 'Activity', UserName AS 'Details', CreatedAt 
FROM membership 
ORDER BY CreatedAt DESC 
LIMIT 10;

-- Recent contacts
SELECT 'Contact Form' AS 'Activity', CONCAT(Name, ' - ', Type) AS 'Details', CreatedAt 
FROM contactus 
ORDER BY CreatedAt DESC 
LIMIT 10;

-- Recent posts
SELECT 'New Post' AS 'Activity', Name AS 'Details', CreatedAt 
FROM posts 
ORDER BY CreatedAt DESC 
LIMIT 10;

-- Recent crop sales
SELECT 'Crop Sale' AS 'Activity', CONCAT(Name, ' - Qty: ', Quantity) AS 'Details', CreatedAt 
FROM cropsales 
ORDER BY CreatedAt DESC 
LIMIT 10;
```

---

## View Specific Data (Filtered Queries)

### Find User by Username
```sql
SELECT * FROM membership WHERE UserName = 'admin';
```

### Find Contacts by Type
```sql
SELECT * FROM contactus WHERE Type = 'General Inquiry';
```

### Find Posts by Location
```sql
SELECT * FROM posts WHERE location = 'Kandy';
```

### Find Posts by Price Range
```sql
SELECT * FROM posts WHERE Price BETWEEN 100000 AND 500000;
```

### Find Crop Sales by Name
```sql
SELECT * FROM cropsales WHERE Name LIKE '%Potato%';
```

---

## View Table Structure

### See Table Schema
```sql
DESCRIBE membership;
DESCRIBE contactus;
DESCRIBE posts;
DESCRIBE cropsales;
```

### See Table Creation Statement
```sql
SHOW CREATE TABLE membership;
SHOW CREATE TABLE contactus;
SHOW CREATE TABLE posts;
SHOW CREATE TABLE cropsales;
```

---

## Export Data to View

### Export to CSV (MySQL Command Line)
```bash
mysql -u root -p -e "SELECT * FROM finalproject.membership" > users.csv
```

### Export All Tables
```bash
mysqldump -u root -p finalproject > database_backup.sql
```

---

## Quick View Scripts

### Create a View Script (view_all_data.sql)

Save this as `view_all_data.sql`:

```sql
USE finalproject;

-- Header
SELECT '=== MEMBERSHIP TABLE ===' AS '';

-- View membership
SELECT * FROM membership;

-- Header
SELECT '=== CONTACT US TABLE ===' AS '';

-- View contactus
SELECT * FROM contactus;

-- Header
SELECT '=== POSTS TABLE ===' AS '';

-- View posts
SELECT * FROM posts;

-- Header
SELECT '=== CROP SALES TABLE ===' AS '';

-- View cropsales
SELECT * FROM cropsales;
```

**Run it:**
```bash
mysql -u root -p < view_all_data.sql
```

---

## View Data in Real-Time (Monitoring)

### Watch New Registrations
```sql
-- Run this query periodically to see new users
SELECT * FROM membership 
WHERE CreatedAt > DATE_SUB(NOW(), INTERVAL 1 HOUR)
ORDER BY CreatedAt DESC;
```

### Watch New Contact Submissions
```sql
SELECT * FROM contactus 
WHERE CreatedAt > DATE_SUB(NOW(), INTERVAL 1 DAY)
ORDER BY CreatedAt DESC;
```

---

## Troubleshooting

### Problem: "Table doesn't exist"
**Solution:** Make sure you've run the database setup script:
```bash
mysql -u root -p < database/create_database.sql
```

### Problem: "Empty result set"
**Solution:** The table exists but has no data. This is normal for a new database.

### Problem: "Access denied"
**Solution:** Check your password in `DatabaseConnection.java` matches your MySQL password.

---

## Quick Reference Commands

```sql
-- Connect and use database
USE finalproject;

-- Show all tables
SHOW TABLES;

-- View all data from a table
SELECT * FROM table_name;

-- View specific columns
SELECT column1, column2 FROM table_name;

-- Count rows
SELECT COUNT(*) FROM table_name;

-- View with limit
SELECT * FROM table_name LIMIT 10;

-- View sorted
SELECT * FROM table_name ORDER BY CreatedAt DESC;

-- Exit MySQL
EXIT;
```

---

## Recommended: Use MySQL Workbench

For the easiest experience:
1. Download MySQL Workbench: https://dev.mysql.com/downloads/workbench/
2. Connect using:
   - Host: `localhost`
   - Port: `3306`
   - Username: `root`
   - Password: `pass@1234`
3. Navigate to `finalproject` database
4. Click any table to view data in a nice table format
5. Use the query editor to run custom SQL queries

---

## Next Steps

1. ✅ Connect to MySQL
2. ✅ View your tables
3. ✅ Check if data exists
4. ✅ Test your application
5. ✅ Monitor new data as it's added

Happy viewing! 🎉
