# Database Setup Guide

## Separate Database Setup

This project now uses a **separate database** called `agriculture_website` instead of `agriculture_website`.

---

## Quick Setup

### Option 1: Using Batch File (Easiest)

1. **Double-click** `setup_separate_database.bat`
2. Enter your MySQL root password when prompted
3. Done! Database is created.

### Option 2: Using Command Line

1. **Open Command Prompt**
2. **Navigate to database folder:**
   ```bash
   cd "d:\Agriculture Website (2)\Agriculture Website\database"
   ```
3. **Run the SQL script:**
   ```bash
   mysql -u root -p < create_database_separate.sql
   ```
4. Enter your MySQL password when prompted

### Option 3: Using MySQL Workbench

1. **Open MySQL Workbench**
2. **Connect to MySQL**
3. **File → Open SQL Script**
4. **Select** `create_database_separate.sql`
5. **Click Execute** (⚡ button)

---

## Database Information

- **Database Name:** `agriculture_website`
- **Host:** `localhost`
- **Port:** `3306`
- **Username:** `root`
- **Password:** `pass@1234` (update in DatabaseConnection.java if different)

---

## Tables Created

1. **membership** - User accounts
2. **contactus** - Contact form submissions
3. **posts** - Machine/product listings
4. **cropsales** - Crop sale listings
5. **orders** - Order records
6. **order_items** - Order line items

---

## Verify Database Creation

### Using MySQL Command Line:
```sql
SHOW DATABASES;
USE agriculture_website;
SHOW TABLES;
```

### Using MySQL Workbench:
1. Connect to MySQL
2. In left sidebar, expand "Schemas"
3. Look for "agriculture_website"
4. Expand it to see tables

---

## Update Java Code

The `DatabaseConnection.java` file has been updated to use the new database:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/agriculture_website";
```

**No other code changes needed!** All controllers will automatically use the new database.

---

## Switching Between Databases

If you want to switch back to `agriculture_website` database:

1. Edit `DatabaseConnection.java`:
   ```java
   private static final String DB_URL = "jdbc:mysql://localhost:3306/agriculture_website";
   ```

2. Rebuild your project:
   ```bash
   mvn clean package
   ```

---

## Multiple Databases

You can have both databases:
- `agriculture_website` - Old database (if you want to keep it)
- `agriculture_website` - New separate database

They won't interfere with each other.

---

## Viewing the New Database

After setup, view your data:

```bash
mysql -u root -p
USE agriculture_website;
SELECT * FROM membership;
```

Or use MySQL Workbench and navigate to `agriculture_website` database.

---

## Troubleshooting

### Problem: "Database already exists"
**Solution:** The database already exists. You can:
- Use it as is, or
- Drop it first: `DROP DATABASE agriculture_website;` then run setup again

### Problem: "Access denied"
**Solution:** Check your MySQL password matches `pass@1234` or update it in:
- `DatabaseConnection.java`
- Batch file (if using)

### Problem: "Table doesn't exist"
**Solution:** Make sure you ran the complete SQL script. All tables should be created automatically.

---

## Next Steps

1. ✅ Run database setup script
2. ✅ Verify database and tables exist
3. ✅ Rebuild Java project (if needed)
4. ✅ Test your application
5. ✅ Start using the new database!

---

## Database Connection String Reference

For your Java application:
```
jdbc:mysql://localhost:3306/agriculture_website
```

For MySQL command line:
```bash
mysql -u root -p agriculture_website
```

For MySQL Workbench:
- Host: `localhost`
- Port: `3306`
- Schema: `agriculture_website`
- Username: `root`
- Password: `pass@1234`

---

Happy coding! 🎉
