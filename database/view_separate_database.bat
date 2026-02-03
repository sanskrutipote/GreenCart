@echo off
echo ========================================
echo   Viewing Agriculture Website Database
echo   Database: agriculture_website
echo ========================================
echo.

REM Check if MySQL is accessible
where mysql >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: MySQL command not found!
    echo Please add MySQL to your PATH or use full path.
    echo.
    echo Trying with common MySQL path...
    set MYSQL_PATH="C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"
    if exist %MYSQL_PATH% (
        set MYSQL_CMD=%MYSQL_PATH%
    ) else (
        set MYSQL_PATH="C:\Program Files\MySQL\MySQL Server 8.1\bin\mysql.exe"
        if exist %MYSQL_PATH% (
            set MYSQL_CMD=%MYSQL_PATH%
        ) else (
            echo MySQL not found. Please install MySQL or update the path in this script.
            pause
            exit /b 1
        )
    )
) else (
    set MYSQL_CMD=mysql
)

echo Connecting to MySQL...
echo Database: agriculture_website
echo Enter your MySQL root password when prompted.
echo.

REM Connect and view data
%MYSQL_CMD% -u root -p -e "USE agriculture_website; SELECT '=== MEMBERSHIP TABLE ===' AS ''; SELECT * FROM membership; SELECT '' AS ''; SELECT '=== CONTACT US TABLE ===' AS ''; SELECT * FROM contactus; SELECT '' AS ''; SELECT '=== POSTS TABLE ===' AS ''; SELECT * FROM posts; SELECT '' AS ''; SELECT '=== CROP SALES TABLE ===' AS ''; SELECT * FROM cropsales; SELECT '' AS ''; SELECT '=== ORDERS TABLE ===' AS ''; SELECT * FROM orders; SELECT '' AS ''; SELECT '=== ORDER ITEMS TABLE ===' AS ''; SELECT * FROM order_items;"

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo   Data displayed successfully!
    echo ========================================
) else (
    echo.
    echo ========================================
    echo   Error viewing database!
    echo   Check your MySQL connection and password.
    echo   Make sure database agriculture_website exists.
    echo ========================================
)

echo.
pause
