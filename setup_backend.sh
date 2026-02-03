#!/bin/bash

# GreenCart Backend Setup Script for Linux/Mac
# This script sets up the database and prepares the backend for deployment

echo ""
echo "========================================"
echo "GreenCart - Backend Setup Script"
echo "========================================"
echo ""

# Check if MySQL is installed
echo "Checking MySQL installation..."
if ! command -v mysql &> /dev/null; then
    echo "ERROR: MySQL is not installed or not in PATH"
    echo "Please install MySQL first"
    exit 1
fi

echo "MySQL found. Proceeding with setup..."
echo ""

# Create database and tables
echo "Creating database and tables..."
read -p "Enter MySQL username (default: root): " mysql_user
mysql_user=${mysql_user:-root}

read -sp "Enter MySQL password: " mysql_password
echo ""

mysql -u $mysql_user -p"$mysql_password" < database/create_complete_database.sql
if [ $? -ne 0 ]; then
    echo "ERROR: Failed to create database"
    echo "Please check your MySQL connection"
    exit 1
fi

echo "Database created successfully!"
echo ""

# Insert sample data
echo "Inserting sample data..."
mysql -u $mysql_user -p"$mysql_password" < database/insert_sample_data.sql
if [ $? -ne 0 ]; then
    echo "ERROR: Failed to insert sample data"
    exit 1
fi

echo "Sample data inserted successfully!"
echo ""

# Build the project
echo "Building Maven project..."
mvn clean package

if [ $? -ne 0 ]; then
    echo "ERROR: Maven build failed"
    exit 1
fi

echo ""
echo "========================================"
echo "Setup Complete!"
echo "========================================"
echo ""
echo "Database: agriculture_website"
echo "Username: $mysql_user"
echo "Host: localhost:3306"
echo ""
echo "Next steps:"
echo "1. Copy target/agriculture-website.war to Tomcat webapps folder"
echo "2. Start Tomcat"
echo "3. Access application at: http://localhost:8080/agriculture-website"
echo ""
echo "API Endpoints:"
echo "- Crops: GET http://localhost:8080/agriculture-website/api/crops/"
echo "- Machines: GET http://localhost:8080/agriculture-website/api/machines/"
echo "- Orders: GET http://localhost:8080/agriculture-website/api/orders/"
echo "- Cart: GET http://localhost:8080/agriculture-website/api/cart?userId=1"
echo ""
echo "Sample Users:"
echo "- Username: buyer_john, Password: pass123"
echo "- Username: farmer_harsha, Password: pass123"
echo ""
