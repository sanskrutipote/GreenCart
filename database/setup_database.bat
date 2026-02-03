@echo off
REM Database Setup Script for Agriculture Website
REM This script helps set up the MySQL database

echo ========================================
echo Agriculture Website Database Setup
echo ========================================
echo.

REM Check if MySQL is in PATH
where mysql >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: MySQL is not found in PATH
    echo Please ensure MySQL is installed and added to your system PATH
    echo.
    echo You can manually run the SQL script using:
    echo   mysql -u root -p ^< create_database.sql
    echo.
    pause
    exit /b 1
)

echo MySQL found!
echo.
echo This script will create the database and tables.
echo You will be prompted for your MySQL root password.
echo.
pause

REM Run the SQL script
echo Creating database and tables...
mysql -u root -p < create_database.sql

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo Database setup completed successfully!
    echo ========================================
    echo.
    echo Database: finalproject
    echo Tables created:
    echo   - membership
    echo   - contactus
    echo   - posts
    echo   - cropsales
    echo.
) else (
    echo.
    echo ========================================
    echo ERROR: Database setup failed!
    echo ========================================
    echo.
    echo Please check:
    echo   1. MySQL is running
    echo   2. Root password is correct
    echo   3. You have CREATE DATABASE privileges
    echo.
)

pause
