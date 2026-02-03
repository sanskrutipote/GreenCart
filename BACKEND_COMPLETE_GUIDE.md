# Backend Setup & Implementation Guide

## Project: GreenCart Agricultural Platform

---

## Table of Contents
1. [Database Setup](#database-setup)
2. [Backend Architecture](#backend-architecture)
3. [API Endpoints](#api-endpoints)
4. [Sample Data](#sample-data)
5. [Build & Deployment](#build--deployment)
6. [Testing](#testing)

---

## Database Setup

### Step 1: Create Database and Tables

Run the complete database creation script:
```sql
-- File: database/create_complete_database.sql
```

This creates:
- `membership` - User accounts (farmers, buyers, admin)
- `crops` - Crop listings for buying/selling
- `posts` - Machine rental/sale listings
- `contacts` - Contact form submissions
- `orders` - Customer orders
- `cart_items` - Shopping cart storage

### Step 2: Insert Sample Data

Run the sample data script:
```sql
-- File: database/insert_sample_data.sql
```

This inserts:
- **10 Sample Users**: 5 farmers + 4 buyers + 1 admin
- **15 Crop Listings**: Wheat, rice, vegetables, fruits, oil seeds
- **15 Machinery Posts**: Tractors, combines, tillers, pumps, etc.
- **6 Contact Messages**: Various inquiry types
- **6 Sample Orders**: Different order statuses
- **8 Cart Items**: Pre-populated shopping carts

### Database Credentials
```
Host: localhost:3306
Username: root
Password: pass@1234
Database: agriculture_website
```

Change these in: [DatabaseConnection.java](src/main/java/com/agzone/util/DatabaseConnection.java)

---

## Backend Architecture

### Models (Entity Classes)

**1. User.java**
```java
- userName (String) - Unique username
- email (String) - Email address
- mobileNo (String) - Phone number
- password (String) - User password
```

**2. Crop.java**
```java
- id (int) - Crop ID
- cropName (String) - Name of crop
- category (String) - Category (Cereals, Vegetables, Fruits, etc.)
- price (double) - Price per unit
- quantity (int) - Available quantity
- unit (String) - Unit (kg, tons, etc.)
- description (String) - Crop details
- image (String) - Image filename
- seller_id (int) - Seller user ID
- location (String) - Location/State
- harvestDate (String) - Harvest date
- available (boolean) - Availability status
```

**3. Post.java** (Machinery)
```java
- id (int) - Post ID
- name (String) - Machine name
- email (String) - Contact email
- mobile (String) - Contact phone
- price (double) - Price (selling or rent rate)
- location (String) - Location
- image (String) - Image filename
- owner (String) - Owner name
- postType (String) - Type: "rent", "sell", "buy"
- description (String) - Machine details
- specifications (String) - Tech specs
- condition (String) - Condition level
- userId (int) - Owner user ID
```

**4. Order.java**
```java
- id (int) - Order ID
- orderId (String) - Unique order reference
- userId (int) - Customer user ID
- totalAmount (double) - Order total
- status (String) - Status: "pending", "confirmed", "shipped", "delivered"
- paymentMethod (String) - Payment type
- shippingAddress (String) - Delivery address
- contactNumber (String) - Phone number
```

**5. CartItem.java**
```java
- id (int) - Cart item ID
- userId (int) - User ID
- cropId (Integer) - Crop reference (nullable)
- machineId (Integer) - Machine reference (nullable)
- itemName (String) - Item name
- price (double) - Unit price
- quantity (int) - Quantity
- itemType (String) - "crop" or "machine"
```

**6. Contact.java**
```java
- id (int) - Contact ID
- name (String) - Sender name
- email (String) - Sender email
- subject (String) - Message subject
- message (String) - Message content
- phoneNumber (String) - Contact phone
- status (String) - Status: "new", "read", "replied"
```

### Controllers

**1. CropController** (`@WebServlet("/api/crops/*")`)
- GET /api/crops/ - Get all available crops
- GET /api/crops/available - Get only available crops
- GET /api/crops/{id} - Get crop by ID
- GET /api/crops/category/{name} - Get crops by category
- GET /api/crops/location/{location} - Get crops by location
- POST /api/crops - Add new crop listing

**2. MachineController** (`@WebServlet("/api/machines/*")`)
- GET /api/machines/ - Get all machinery
- GET /api/machines/{id} - Get machine by ID
- GET /api/machines/type/{rent|sell|buy} - Get by type
- GET /api/machines/location/{location} - Get by location
- POST /api/machines - Add new machinery listing

**3. OrderController** (`@WebServlet({"/order", "/api/orders/*"})`)
- GET /api/orders/ - Get all orders
- GET /api/orders?userId={id} - Get user's orders
- GET /api/orders/{id} - Get order by ID
- POST /order - Legacy order endpoint (from checkout)
- POST /api/orders - Create new order (REST)
- PUT /api/orders/{id} - Update order status

**4. CartController** (`@WebServlet("/api/cart/*")`)
- GET /api/cart?userId={id} - Get user's cart
- POST /api/cart - Add/update item in cart
- PUT /api/cart - Update item quantity
- DELETE /api/cart?itemId={id} - Remove from cart

**5. LoginController** (`@WebServlet("/login")`)
- POST /login - User authentication

**6. RegistrationController** (`@WebServlet("/register")`)
- POST /register - New user registration

**7. ContactUsController** (`@WebServlet("/contactus")`)
- POST /contactus - Submit contact form

---

## API Endpoints

### Crop API

#### Get All Crops
```
GET /api/crops/
Response: [
  {
    "id": 1,
    "cropName": "Premium Wheat",
    "category": "Cereals",
    "price": 2500.00,
    "quantity": 1000,
    "unit": "kg",
    "location": "Punjab",
    "image": "wheat.jpg",
    "seller_id": 1
  }
]
```

#### Get Crop by Category
```
GET /api/crops/category/Vegetables
Response: Array of vegetable crops
```

#### Get Crop by Location
```
GET /api/crops/location/Maharashtra
Response: Array of crops from Maharashtra
```

#### Add New Crop
```
POST /api/crops
Parameters:
  - cropName (required)
  - category (required)
  - price (required)
  - quantity (required)
  - unit (required)
  - description
  - image
  - seller_id (required)
  - location (required)

Response:
{
  "message": "Crop added successfully",
  "success": true
}
```

### Machine API

#### Get All Machines
```
GET /api/machines/
Response: [
  {
    "id": 1,
    "name": "John Deere Tractor",
    "price": 850000.00,
    "location": "Karnataka",
    "postType": "sell",
    "condition": "excellent",
    "owner": "farmer_harsha"
  }
]
```

#### Get Machines for Rent
```
GET /api/machines/type/rent
Response: Array of rental machinery
```

#### Get Machines for Sale
```
GET /api/machines/type/sell
Response: Array of machinery for sale
```

#### Add New Machinery
```
POST /api/machines
Parameters:
  - name (required)
  - email (required)
  - mobile (required)
  - price (required)
  - location (required)
  - postType (required: rent/sell/buy)
  - description
  - image
  - specifications
  - condition
  - userId (required)

Response:
{
  "message": "Machinery listing added successfully",
  "success": true
}
```

### Order API

#### Get All Orders
```
GET /api/orders/
Response: Array of all orders
```

#### Get User Orders
```
GET /api/orders?userId=6
Response: Array of orders for user ID 6
```

#### Create Order
```
POST /api/orders
Parameters:
  - userId (required)
  - totalAmount (required)
  - paymentMethod
  - shippingAddress
  - contactNumber

Response:
{
  "message": "Order created successfully",
  "orderId": "ORD-1672531200000-a1b2c3d4",
  "success": true
}
```

#### Update Order Status
```
PUT /api/orders/1?status=shipped
Response:
{
  "message": "Order status updated successfully",
  "success": true
}
```

### Cart API

#### Get User Cart
```
GET /api/cart?userId=6
Response:
{
  "items": [
    {
      "id": 1,
      "itemName": "Premium Wheat",
      "price": 2500.00,
      "quantity": 5,
      "itemType": "crop",
      "totalPrice": 12500.00
    }
  ],
  "totalAmount": 50000.00
}
```

#### Add Item to Cart
```
POST /api/cart
Parameters:
  - userId (required)
  - itemName (required)
  - price (required)
  - quantity (required)
  - itemType (required: crop/machine)
  - cropId (optional)
  - machineId (optional)

Response:
{
  "message": "Item added to cart",
  "success": true
}
```

#### Update Cart Item Quantity
```
PUT /api/cart?itemId=1&quantity=10
Response:
{
  "message": "Cart item updated",
  "success": true
}
```

#### Remove from Cart
```
DELETE /api/cart?itemId=1
Response:
{
  "message": "Item removed from cart",
  "success": true
}
```

---

## Sample Data

### Sample Users

| Username | Email | Role | Location |
|----------|-------|------|----------|
| farmer_harsha | harsha@agzone.com | Farmer | Karnataka |
| farmer_akshay | akshay@agzone.com | Farmer | Maharashtra |
| farmer_rushabh | rushabh@agzone.com | Farmer | Rajasthan |
| farmer_srivatsan | srivatsan@agzone.com | Farmer | Tamil Nadu |
| farmer_anurag | anurag@agzone.com | Farmer | Uttar Pradesh |
| buyer_john | john@buyer.com | Buyer | Delhi |
| buyer_sarah | sarah@buyer.com | Buyer | Mumbai |
| buyer_amit | amit@buyer.com | Buyer | Bangalore |
| buyer_priya | priya@buyer.com | Buyer | Hyderabad |
| admin_user | admin@agzone.com | Admin | India |

**All passwords**: `pass123` (Change in production!)

### Sample Crops (15 items)

- Wheat (2500/kg, 1000 units)
- Basmati Rice (5000/kg, 500 units)
- Fresh Tomatoes (40/kg, 5000 units)
- Sweet Potatoes (35/kg, 3000 units)
- Onions (30/kg, 8000 units)
- Carrots (45/kg, 2000 units)
- Green Beans (60/kg, 1500 units)
- Mango (120/kg)
- Apple (80/kg, 1500 units)
- Banana (25/kg, 5000 units)
- Grapes (100/kg, 1000 units)
- Corn (3000/kg, 2000 units)
- Mustard Seeds (5500/kg, 800 units)
- Groundnut (6500/kg, 600 units)
- Cotton (8000/kg, 500 units)

### Sample Machinery (15 items)

- John Deere Tractor (₹850,000)
- Bajaj Tractor (₹650,000)
- Combine Harvester (₹1,200,000)
- Power Tiller (₹150,000)
- Rotavator (₹80,000)
- Tractor Rent (₹1,500/day)
- Combine Harvester Rent (₹3,000/day)
- Sprayer Machine (₹25,000)
- Thresher Machine (₹250,000)
- Pump Set (₹45,000)
- Pulverizer (₹120,000)
- Plough (₹12,000)
- Seed Drill (₹35,000)
- Disc Harrow (₹800/day rent)
- Farm Trolley (₹50,000)

---

## Build & Deployment

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+
- Tomcat 10+ (for deployment)

### Build Commands

```bash
# Navigate to project directory
cd "d:\Agriculture Website (2)\Agriculture Website"

# Clean build
mvn clean package

# Create database
mysql -u root -p < database/create_complete_database.sql
mysql -u root -p < database/insert_sample_data.sql

# Deploy to Tomcat
# Copy target/agriculture-website.war to tomcat/webapps/
```

### Run Tests
```bash
mvn test
```

### Project Structure
```
Agriculture Website/
├── src/
│   ├── main/
│   │   ├── java/com/agzone/
│   │   │   ├── controller/  (REST Controllers)
│   │   │   ├── model/       (Entity Classes)
│   │   │   └── util/        (Database Utils)
│   │   └── webapp/
│   │       └── WEB-INF/
│   └── test/
├── database/
│   ├── create_complete_database.sql
│   └── insert_sample_data.sql
├── pom.xml
└── README.md
```

---

## Testing

### Test API Endpoints

#### Using cURL

```bash
# Get all crops
curl http://localhost:8080/agriculture-website/api/crops/

# Get crops by category
curl http://localhost:8080/agriculture-website/api/crops/category/Vegetables

# Get all machinery
curl http://localhost:8080/agriculture-website/api/machines/

# Get machinery for rent
curl http://localhost:8080/agriculture-website/api/machines/type/rent

# Get user cart
curl http://localhost:8080/agriculture-website/api/cart?userId=6

# Add item to cart
curl -X POST http://localhost:8080/agriculture-website/api/cart \
  -d "userId=6&itemName=Premium Wheat&price=2500&quantity=5&itemType=crop"

# Create order
curl -X POST http://localhost:8080/agriculture-website/api/orders \
  -d "userId=6&totalAmount=50000&paymentMethod=UPI&shippingAddress=Delhi&contactNumber=9876543215"

# Get user orders
curl http://localhost:8080/agriculture-website/api/orders?userId=6
```

#### Using Postman

1. Import the Postman collection (create from endpoints above)
2. Set base URL: `http://localhost:8080/agriculture-website`
3. Test each endpoint with sample data

---

## Future Enhancements

1. **Add Payment Gateway Integration**
   - Razorpay / PayPal integration
   - Payment status tracking

2. **Implement Authentication**
   - JWT tokens for API
   - Session management

3. **Add Messaging System**
   - Buyer-Seller communication
   - Notifications

4. **Search & Filtering**
   - Advanced search with multiple filters
   - Price range filtering
   - Date range filtering

5. **User Dashboard**
   - Profile management
   - Order history
   - Listings management
   - Reviews & ratings

6. **Mobile App API**
   - Mobile-specific endpoints
   - Image upload handling
   - Push notifications

7. **Analytics**
   - Sales reports
   - Popular items
   - User activity tracking

---

## Troubleshooting

### Database Connection Error
```
Check database credentials in: src/main/java/com/agzone/util/DatabaseConnection.java
Ensure MySQL is running and database exists
```

### 404 Error on API Calls
```
Ensure Tomcat is running
Check application name: agriculture-website
Verify controller path annotations: @WebServlet("/api/...")
```

### Port Already in Use
```
Change Tomcat port in: $CATALINA_HOME/conf/server.xml
Default: 8080
```

---

## Support

For issues or questions, contact the development team or check the project documentation.

---

**Last Updated**: January 2026
**Version**: 1.0.0
**Status**: Production Ready
