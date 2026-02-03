# How to Connect to MySQL Server

This guide shows you different ways to connect to your MySQL server on Windows.

## Prerequisites

1. **MySQL Server must be installed** on your computer
2. **MySQL Server must be running**
3. **Know your MySQL root password** (default is often empty or `root`)

## Method 1: MySQL Command Line Client (Recommended)

### Step 1: Open MySQL Command Line
- Press `Windows Key + R`
- Type: `cmd` and press Enter
- Or search for "Command Prompt" in Start Menu

### Step 2: Connect to MySQL
```bash
mysql -u root -p
```

**What this does:**
- `-u root` = username is "root"
- `-p` = prompt for password

### Step 3: Enter Password
When prompted, enter your MySQL root password:
- If you set it to `pass@1234`, type that
- If no password, just press Enter
- Password won't show as you type (this is normal)

### Step 4: Verify Connection
You should see:
```
Welcome to the MySQL monitor. Commands end with ; or \g.
mysql>
```

**Success!** You're now connected.

### Common Commands:
```sql
-- Show all databases
SHOW DATABASES;

-- Use a specific database
USE agriculture_website;

-- Show tables in current database
SHOW TABLES;

-- Exit MySQL
EXIT;
```

---

## Method 2: MySQL Workbench (GUI - Easiest)

### Step 1: Download MySQL Workbench
- Download from: https://dev.mysql.com/downloads/workbench/
- Install it (if not already installed)

### Step 2: Open MySQL Workbench
- Search for "MySQL Workbench" in Start Menu
- Click to open

### Step 3: Create Connection
1. Click **"MySQL Connections"** (or the + icon)
2. Fill in the connection details:
   - **Connection Name**: `Local MySQL` (or any name)
   - **Hostname**: `localhost` (or `127.0.0.1`)
   - **Port**: `3306` (default)
   - **Username**: `root`
   - **Password**: Click "Store in Vault" and enter your password
3. Click **"Test Connection"**
4. If successful, click **"OK"**

### Step 4: Connect
- Double-click your connection name
- Enter password if prompted
- You're connected!

---

## Method 3: Using Command Prompt with Full Path

If MySQL is not in your PATH:

### Find MySQL Installation
MySQL is usually installed at:
- `C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe`
- `C:\Program Files\MySQL\MySQL Server 8.1\bin\mysql.exe`

### Connect Using Full Path
```bash
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p
```

Or navigate to the bin folder first:
```bash
cd "C:\Program Files\MySQL\MySQL Server 8.0\bin"
mysql -u root -p
```

---

## Method 4: PowerShell

### Step 1: Open PowerShell
- Press `Windows Key + X`
- Select "Windows PowerShell" or "Terminal"

### Step 2: Connect
```powershell
mysql -u root -p
```

Enter password when prompted.

---

## Method 5: Check if MySQL is Running

Before connecting, verify MySQL service is running:

### Using Services
1. Press `Windows Key + R`
2. Type: `services.msc` and press Enter
3. Look for **"MySQL80"** or **"MySQL"**
4. Status should be **"Running"**
5. If not running, right-click → **Start**

### Using Command Line
```bash
sc query MySQL80
```

Or check if MySQL port is listening:
```bash
netstat -an | findstr 3306
```

---

## Troubleshooting

### Problem: "mysql is not recognized"
**Solution**: MySQL is not in your PATH

**Fix Option 1**: Add MySQL to PATH
1. Search "Environment Variables" in Start Menu
2. Click "Edit the system environment variables"
3. Click "Environment Variables"
4. Under "System Variables", find "Path" and click "Edit"
5. Click "New" and add: `C:\Program Files\MySQL\MySQL Server 8.0\bin`
6. Click OK on all windows
7. Restart Command Prompt

**Fix Option 2**: Use full path (see Method 3 above)

### Problem: "Access Denied"
**Solution**: Wrong password or user doesn't exist

**Try:**
```bash
mysql -u root -p
```
Enter the correct password.

**If you forgot password:**
1. Stop MySQL service
2. Start MySQL in safe mode
3. Reset password (advanced - see MySQL documentation)

### Problem: "Can't connect to MySQL server"
**Solution**: MySQL service is not running

**Fix:**
1. Open Services (`services.msc`)
2. Find MySQL service
3. Right-click → Start

### Problem: "Connection refused"
**Solution**: MySQL might not be installed or configured

**Check:**
- Is MySQL installed?
- Is MySQL service running?
- Is port 3306 open?

---

## Quick Connection Test

Test if you can connect:

```bash
mysql -u root -p -e "SELECT VERSION();"
```

If successful, you'll see MySQL version.

---

## Setting Up Your Database

Once connected, run the database setup:

### Option 1: Using SQL Script File
```bash
mysql -u root -p < database/create_database.sql
```

### Option 2: Copy-Paste SQL
1. Connect to MySQL (any method above)
2. Open `database/create_database.sql` in Notepad
3. Copy all content
4. Paste into MySQL command line
5. Press Enter

### Option 3: Using MySQL Workbench
1. Connect using Method 2
2. File → Open SQL Script
3. Select `database/create_database.sql`
4. Click Execute (⚡ button)

---

## Connection Strings Reference

For your Java application, the connection string is:
```
jdbc:mysql://localhost:3306/agriculture_website
```

**Parameters:**
- **Protocol**: `jdbc:mysql://`
- **Host**: `localhost` (or `127.0.0.1`)
- **Port**: `3306`
- **Database**: `agriculture_website`
- **Username**: `root`
- **Password**: `pass@1234` (as configured)

---

## Next Steps

After connecting successfully:

1. ✅ Verify MySQL is running
2. ✅ Connect using one of the methods above
3. ✅ Run the database setup script
4. ✅ Verify tables were created
5. ✅ Test your Java application connection

---

## Need Help?

If you're still having trouble:

1. **Check MySQL Installation**
   - Is MySQL installed? Check Programs and Features
   - Default installation location: `C:\Program Files\MySQL\`

2. **Check MySQL Service**
   - Open Services (`services.msc`)
   - Look for MySQL service
   - Ensure it's running

3. **Check Firewall**
   - Windows Firewall might block MySQL
   - Add MySQL to firewall exceptions if needed

4. **Verify Port**
   - MySQL uses port 3306 by default
   - Check if another application is using it
