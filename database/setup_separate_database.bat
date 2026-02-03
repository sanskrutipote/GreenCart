@echo off
echo ========================================
echo   Setting Up Separate Database
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

echo Creating separate database: agriculture_website
echo Enter your MySQL root password when prompted.
echo.

REM Run the SQL script
%MYSQL_CMD% -u root -p < create_database_separate.sql

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo   Database created successfully!
    echo   Database Name: agriculture_website
    echo ========================================
    echo.
    echo Next steps:
    echo 1. DatabaseConnection.java has been updated
    echo 2. Rebuild your Java project
    echo 3. Test the connection
    echo.
) else (
    echo.
    echo ========================================
    echo   Error creating database!
    echo   Check your MySQL connection and password.
    echo ========================================
)

echo.
pause
