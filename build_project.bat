@echo off
echo ========================================
echo   Building Agriculture Website Project
echo ========================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH!
    echo.
    echo Please install Java JDK 17 or higher from:
    echo https://www.oracle.com/java/technologies/downloads/
    echo.
    pause
    exit /b 1
)

echo Java found!
java -version
echo.

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ========================================
    echo   Maven is not installed!
    echo ========================================
    echo.
    echo Please choose an option:
    echo.
    echo Option 1: Install Maven (Recommended)
    echo   1. Download from: https://maven.apache.org/download.cgi
    echo   2. Extract to: C:\Program Files\Apache\maven
    echo   3. Add to PATH: C:\Program Files\Apache\maven\bin
    echo   4. Restart PowerShell and run this script again
    echo.
    echo Option 2: Use IDE to build
    echo   - IntelliJ IDEA: Right-click pom.xml ^> Maven ^> package
    echo   - Eclipse: Right-click project ^> Run As ^> Maven build
    echo.
    echo See BUILD_WITHOUT_MAVEN.md for detailed instructions.
    echo.
    pause
    exit /b 1
)

echo Maven found!
mvn -version
echo.

echo ========================================
echo   Starting build process...
echo ========================================
echo.

REM Build the project
mvn clean package

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo   BUILD SUCCESS!
    echo ========================================
    echo.
    echo WAR file created at:
    echo target\agriculture-website.war
    echo.
    echo Next steps:
    echo 1. Copy WAR file to Tomcat webapps folder
    echo 2. Start Tomcat
    echo 3. Access: http://localhost:8080/agriculture-website/
    echo.
) else (
    echo.
    echo ========================================
    echo   BUILD FAILED!
    echo ========================================
    echo.
    echo Check the error messages above.
    echo Common issues:
    echo - Database connection error (check DatabaseConnection.java)
    echo - Missing dependencies
    echo - Java version mismatch
    echo.
)

pause
