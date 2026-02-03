@echo off
REM Check MySQL Service Status
REM This script checks if MySQL service is running

echo ========================================
echo MySQL Service Status Check
echo ========================================
echo.

REM Check MySQL80 service (MySQL 8.0)
sc query MySQL80 >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo Checking MySQL80 service...
    sc query MySQL80 | findstr "STATE"
    echo.
    echo To start MySQL: sc start MySQL80
    echo To stop MySQL:  sc stop MySQL80
    echo.
) else (
    echo MySQL80 service not found.
    echo.
)

REM Check MySQL service (older versions)
sc query MySQL >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo Checking MySQL service...
    sc query MySQL | findstr "STATE"
    echo.
    echo To start MySQL: sc start MySQL
    echo To stop MySQL:  sc stop MySQL
    echo.
) else (
    echo MySQL service not found.
    echo.
)

REM Check if port 3306 is listening
echo Checking if MySQL port 3306 is listening...
netstat -an | findstr "3306" >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo [OK] Port 3306 is active
    netstat -an | findstr "3306"
) else (
    echo [WARNING] Port 3306 is not listening
    echo MySQL might not be running or configured
)

echo.
echo ========================================
echo To manage MySQL service:
echo   1. Open Services: services.msc
echo   2. Find "MySQL80" or "MySQL"
echo   3. Right-click to Start/Stop/Restart
echo ========================================
echo.

pause
