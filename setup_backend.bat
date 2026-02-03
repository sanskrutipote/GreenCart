@echo off
REM GreenCart Backend Setup Script for Windows
REM This script sets up the database and prepares the backend for deployment

echo.
echo ========================================
echo GreenCart - Backend Setup Script
echo ========================================
echo.

REM Check if MySQL is installed
echo Checking MySQL installation...
mysql --version >nul 2>&1
if errorlevel 1 (
    echo ERROR: MySQL is not installed or not in PATH
    echo Please install MySQL and add it to your system PATH
    pause
    exit /b 1
)

echo MySQL found. Proceeding with setup...
echo.

REM Create database and tables
echo Creating database and tables...
echo Username: root
set /p "password=Enter MySQL password: "

mysql -u root -p%password% < database\create_complete_database.sql
if errorlevel 1 (
    echo ERROR: Failed to create database
    echo Please check your MySQL connection
    pause
    exit /b 1
)

echo Database created successfully!
echo.

REM Insert sample data
echo Inserting sample data...
mysql -u root -p%password% < database\insert_sample_data.sql
if errorlevel 1 (
    echo ERROR: Failed to insert sample data
    pause
    exit /b 1
)

echo Sample data inserted successfully!
echo.

REM Build the project
echo Building Maven project...
call mvn clean package

if errorlevel 1 (
    echo ERROR: Maven build failed
    pause
    exit /b 1
)

echo.
echo ========================================
echo Setup Complete!
echo ========================================
echo.
echo Database: agriculture_website
echo Username: root
echo Host: localhost:3306
echo.
echo Next steps:
echo 1. Copy target\agriculture-website.war to Tomcat webapps folder
echo 2. Start Tomcat
echo 3. Access application at: http://localhost:8080/agriculture-website
echo.
echo API Endpoints:
echo - Crops: GET http://localhost:8080/agriculture-website/api/crops/
echo - Machines: GET http://localhost:8080/agriculture-website/api/machines/
echo - Orders: GET http://localhost:8080/agriculture-website/api/orders/
echo - Cart: GET http://localhost:8080/agriculture-website/api/cart?userId=1
echo.
echo Sample Users:
echo - Username: buyer_john, Password: pass123
echo - Username: farmer_harsha, Password: pass123
echo.
pause
