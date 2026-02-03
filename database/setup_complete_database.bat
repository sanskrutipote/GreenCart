@echo off
echo ====================================
echo Agriculture Website - Database Setup
echo ====================================
echo.

set /p MYSQL_PWD="Enter MySQL root password: "

echo.
echo Step 1: Creating database and tables...
mysql -u root -p%MYSQL_PWD% < create_database_separate.sql

if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Failed to create database. Please check your MySQL connection.
    pause
    exit /b 1
)

echo Step 2: Inserting sample data...
mysql -u root -p%MYSQL_PWD% < insert_random_data.sql

if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Failed to insert sample data.
    pause
    exit /b 1
)

echo.
echo ====================================
echo Database setup completed successfully!
echo ====================================
echo.
echo Database: agriculture_website
echo Tables created:
echo   - membership (users)
echo   - contactus (contact form submissions)
echo   - posts (machine/product listings)
echo   - cropsales (crop sale listings)
echo   - orders (customer orders)
echo   - order_items (order line items)
echo.
echo Sample data has been inserted into all tables.
echo.
echo Default admin credentials:
echo   Username: admin
echo   Password: admin123
echo.
pause
