# Quick Start Guide - Database Setup

## Prerequisites
- MySQL or MariaDB installed
- MySQL root access
- Password: `pass@1234` (as configured in DatabaseConnection.java)

## Quick Setup (Windows)

### Option 1: Using Batch Script (Easiest)
1. Double-click `setup_database.bat`
2. Enter your MySQL root password when prompted
3. Done!

### Option 2: Using Command Line
1. Open Command Prompt or PowerShell
2. Navigate to the `database` folder
3. Run:
   ```bash
   mysql -u root -p < create_database.sql
   ```
4. Enter your MySQL root password

### Option 3: Using MySQL Workbench
1. Open MySQL Workbench
2. Connect to your MySQL server
3. File → Open SQL Script → Select `create_database.sql`
4. Click the Execute button (⚡)

## Verify Setup

After setup, verify the database:

```sql
USE finalproject;
SHOW TABLES;
```

You should see:
- membership
- contactus
- posts
- cropsales

## Test Connection

The database is configured with:
- **Host**: localhost
- **Port**: 3306
- **Database**: finalproject
- **User**: root
- **Password**: pass@1234

## Sample Login Credentials

After setup, you can test login with:
- **Username**: admin
- **Password**: admin123

⚠️ **Change this password in production!**

## Troubleshooting

**Error: Access Denied**
- Check MySQL root password
- Ensure MySQL service is running

**Error: Database exists**
- This is fine, the script will recreate tables
- Or use `drop_database.sql` first to start fresh

**Error: Table already exists**
- The script drops existing tables automatically
- If issues persist, run `drop_database.sql` first

## Next Steps

1. ✅ Database created
2. ✅ Tables created
3. ✅ Sample user added
4. 🔄 Update DatabaseConnection.java if password differs
5. 🔄 Build and run the Java application
6. 🔄 Test registration and login
