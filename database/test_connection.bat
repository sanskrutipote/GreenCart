@echo off
REM Test MySQL Connection Script
REM This script helps you test if you can connect to MySQL

echo ========================================
echo MySQL Connection Test
echo ========================================
echo.

REM Check if MySQL is in PATH
where mysql >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] MySQL is not found in PATH
    echo.
    echo Please try one of these:
    echo   1. Add MySQL to your PATH (see HOW_TO_CONNECT_MYSQL.md)
    echo   2. Use full path: "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p
    echo   3. Use MySQL Workbench GUI
    echo.
    pause
    exit /b 1
)

echo [OK] MySQL found in PATH
echo.

REM Test connection
echo Testing MySQL connection...
echo You will be prompted for your MySQL root password.
echo.

mysql -u root -p -e "SELECT VERSION() AS 'MySQL Version', DATABASE() AS 'Current Database';" 2>nul

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo [SUCCESS] MySQL connection successful!
    echo ========================================
    echo.
    echo You can now:
    echo   1. Run: setup_database.bat (to create database)
    echo   2. Or manually: mysql -u root -p ^< create_database.sql
    echo.
) else (
    echo.
    echo ========================================
    echo [ERROR] Connection failed!
    echo ========================================
    echo.
    echo Possible issues:
    echo   1. Wrong password
    echo   2. MySQL service not running
    echo   3. MySQL not installed
    echo.
    echo Troubleshooting:
    echo   - Check if MySQL service is running (services.msc)
    echo   - Verify MySQL is installed
    echo   - Check your root password
    echo.
)

pause
