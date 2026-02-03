# Database Setup Guide

This directory contains SQL scripts to set up the database for the Agriculture Website Java application.

## Database Information

- **Database Name**: `agriculture_website`
- **Database Type**: MySQL/MariaDB
- **Character Set**: utf8mb4
- **Collation**: utf8mb4_unicode_ci

## Tables

### 1. membership
Stores user registration and authentication information.

**Columns:**
- `UserName` (VARCHAR(50), PRIMARY KEY) - Unique username
- `Email` (VARCHAR(100), NOT NULL) - User email address
- `MobileNo` (VARCHAR(15), NOT NULL) - Mobile phone number
- `Password` (VARCHAR(255), NOT NULL) - User password (currently plain text)
- `CreatedAt` (TIMESTAMP) - Account creation timestamp

**Indexes:**
- Primary key on `UserName`
- Index on `Email`
- Index on `MobileNo`

### 2. contactus
Stores contact form submissions.

**Columns:**
- `Id` (INT, AUTO_INCREMENT, PRIMARY KEY) - Unique identifier
- `Name` (VARCHAR(100), NOT NULL) - Contact person name
- `Email` (VARCHAR(100), NOT NULL) - Contact email
- `MobileNo` (VARCHAR(15), NOT NULL) - Contact mobile number
- `Type` (VARCHAR(50), NOT NULL) - Type of inquiry
- `Message` (TEXT, NOT NULL) - Message content
- `CreatedAt` (TIMESTAMP) - Submission timestamp

**Indexes:**
- Primary key on `Id`
- Index on `Email`
- Index on `CreatedAt`

### 3. posts
Stores product/machine posts for sale.

**Columns:**
- `Id` (INT, AUTO_INCREMENT, PRIMARY KEY) - Unique identifier
- `Name` (VARCHAR(255), NOT NULL) - Product name
- `Email` (VARCHAR(100), NOT NULL) - Seller email
- `Mobile` (VARCHAR(15), NOT NULL) - Seller mobile number
- `Price` (DECIMAL(10, 2), NOT NULL) - Product price
- `location` (VARCHAR(255), NOT NULL) - Product location
- `image` (VARCHAR(255)) - Image filename
- `Owner` (VARCHAR(50), NOT NULL) - Username of the post owner
- `CreatedAt` (TIMESTAMP) - Post creation timestamp

**Indexes:**
- Primary key on `Id`
- Index on `Owner` (Foreign key to membership.UserName)
- Index on `Email`
- Index on `location`

**Foreign Keys:**
- `Owner` references `membership(UserName)` ON DELETE CASCADE

### 4. cropsales
Stores crop sales information.

**Columns:**
- `Id` (INT, AUTO_INCREMENT, PRIMARY KEY) - Unique identifier
- `cropid` (VARCHAR(50), NOT NULL) - Crop identifier
- `Quantity` (VARCHAR(50), NOT NULL) - Quantity available
- `Name` (VARCHAR(100), NOT NULL) - Seller name
- `address` (VARCHAR(255), NOT NULL) - Seller address
- `mobileNo` (VARCHAR(15), NOT NULL) - Seller mobile number
- `CreatedAt` (TIMESTAMP) - Sale creation timestamp

**Indexes:**
- Primary key on `Id`
- Index on `cropid`
- Index on `Name`
- Index on `mobileNo`
- Index on `CreatedAt`

## Setup Instructions

### Method 1: Using MySQL Command Line

1. Open MySQL command line or MySQL Workbench
2. Connect to your MySQL server
3. Run the SQL script:
   ```bash
   mysql -u root -p < create_database.sql
   ```
   Or in MySQL:
   ```sql
   source create_database.sql;
   ```

### Method 2: Using MySQL Workbench

1. Open MySQL Workbench
2. Connect to your MySQL server
3. File → Open SQL Script → Select `create_database.sql`
4. Execute the script (⚡ button)

### Method 3: Manual Execution

1. Connect to MySQL:
   ```bash
   mysql -u root -p
   ```
2. Copy and paste the contents of `create_database.sql`
3. Execute

## Verification

After running the script, verify the database was created:

```sql
USE agriculture_website;
SHOW TABLES;
DESCRIBE membership;
DESCRIBE contactus;
DESCRIBE posts;
DESCRIBE cropsales;
```

## Database Connection

Update the connection details in `DatabaseConnection.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/agriculture_website";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "pass@1234"; // Your MySQL password
```

## Security Notes

1. **Password Storage**: Currently passwords are stored in plain text. For production, implement password hashing (BCrypt recommended).

2. **SQL Injection**: The Java application uses PreparedStatement to prevent SQL injection.

3. **Database Credentials**: Store database credentials in environment variables or configuration files, not in source code.

4. **Backup**: Regularly backup your database:
   ```bash
   mysqldump -u root -p agriculture_website > backup.sql
   ```

## Sample Data

The script includes a sample admin user:
- Username: `admin`
- Email: `admin@agzone.com`
- Mobile: `1234567890`
- Password: `admin123`

**Note**: Change the default password immediately after setup!

## Troubleshooting

### Error: Access Denied
- Ensure MySQL user has CREATE DATABASE privileges
- Check username and password

### Error: Database Already Exists
- The script uses `CREATE DATABASE IF NOT EXISTS`, so it's safe to run multiple times
- If you need a fresh start, manually drop the database first:
  ```sql
  DROP DATABASE IF EXISTS agriculture_website;
  ```

### Error: Table Already Exists
- The script drops existing tables before creating new ones
- Ensure you have DROP privileges

## Next Steps

1. Run the database creation script
2. Update database credentials in `DatabaseConnection.java`
3. Test the connection by running the application
4. Register a new user through the registration form
5. Test login functionality
