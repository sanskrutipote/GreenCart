# GreenCart API Testing Guide

## Quick Start

### Test Environment
- **Base URL**: `http://localhost:8080/agriculture-website`
- **Content-Type**: `application/x-www-form-urlencoded` (for forms)
- **Response Format**: JSON

### Sample Test Data

#### Users for Testing
```
User 1: buyer_john / pass123 (Buyer)
User 2: farmer_harsha / pass123 (Farmer)
User 3: admin_user / admin123 (Admin)
```

---

## API Testing Examples

### 1. CROP API

#### Get All Crops
```
Request:
  GET /api/crops/
  
Response:
  [
    {
      "id": 1,
      "cropName": "Premium Wheat",
      "category": "Cereals",
      "price": 2500,
      "quantity": 1000,
      "unit": "kg",
      "description": "High-quality wheat from Punjab",
      "image": "wheat.jpg",
      "location": "Punjab",
      "seller_id": 1
    },
    ...
  ]

Expected Status: 200 OK
```

#### Get Crops by Category
```
Request:
  GET /api/crops/category/Vegetables

Response:
  [
    {
      "id": 3,
      "cropName": "Fresh Tomatoes",
      "category": "Vegetables",
      "price": 40,
      ...
    },
    ...
  ]

Expected Status: 200 OK
```

#### Get Crops by Location
```
Request:
  GET /api/crops/location/Punjab

Response:
  Array of crops from Punjab

Expected Status: 200 OK
```

#### Get Single Crop by ID
```
Request:
  GET /api/crops/1

Response:
  {
    "id": 1,
    "cropName": "Premium Wheat",
    "category": "Cereals",
    "price": 2500,
    "quantity": 1000,
    ...
  }

Expected Status: 200 OK
```

#### Add New Crop (Farmer)
```
Request:
  POST /api/crops
  
Parameters:
  cropName=Organic Rice
  category=Cereals
  price=6000
  quantity=500
  unit=kg
  description=Organic rice from Haryana
  image=rice_organic.jpg
  seller_id=1
  location=Haryana

Response:
  {
    "message": "Crop added successfully",
    "success": true
  }

Expected Status: 201 Created
```

---

### 2. MACHINE API

#### Get All Machines
```
Request:
  GET /api/machines/

Response:
  [
    {
      "id": 1,
      "name": "John Deere Tractor",
      "price": 850000,
      "location": "Karnataka",
      "image": "tractor1.jpg",
      "owner": "farmer_harsha",
      "postType": "sell",
      "condition": "excellent",
      "description": "Powerful tractor for all farm operations"
    },
    ...
  ]

Expected Status: 200 OK
```

#### Get Machines for Rent
```
Request:
  GET /api/machines/type/rent

Response:
  [
    {
      "id": 6,
      "name": "Tractor Rent - 40 HP",
      "price": 1500,
      "postType": "rent",
      ...
    },
    ...
  ]

Expected Status: 200 OK
```

#### Get Machines for Sale
```
Request:
  GET /api/machines/type/sell

Expected Status: 200 OK
```

#### Get Machines by Location
```
Request:
  GET /api/machines/location/Maharashtra

Expected Status: 200 OK
```

#### Add New Machine Listing
```
Request:
  POST /api/machines

Parameters:
  name=New Harrow Machine
  email=farmer@example.com
  mobile=9876543210
  price=75000
  location=Punjab
  image=harrow.jpg
  owner=farmer_name
  postType=sell
  description=Modern harrow machine
  specifications=8 feet width, automatic control
  condition=excellent
  userId=1

Response:
  {
    "message": "Machinery listing added successfully",
    "success": true
  }

Expected Status: 201 Created
```

---

### 3. ORDER API

#### Get All Orders
```
Request:
  GET /api/orders/

Response:
  [
    {
      "id": 1,
      "orderId": "ORD-2025-001",
      "userId": 6,
      "totalAmount": 15000,
      "status": "delivered",
      "paymentMethod": "UPI",
      "createdAt": "2025-01-28 10:30:00"
    },
    ...
  ]

Expected Status: 200 OK
```

#### Get User Orders
```
Request:
  GET /api/orders?userId=6

Response:
  [
    {
      "id": 1,
      "orderId": "ORD-2025-001",
      "totalAmount": 15000,
      "status": "delivered",
      ...
    },
    ...
  ]

Expected Status: 200 OK
```

#### Get Single Order
```
Request:
  GET /api/orders/1

Response:
  {
    "id": 1,
    "orderId": "ORD-2025-001",
    "userId": 6,
    "totalAmount": 15000,
    "status": "delivered",
    "paymentMethod": "UPI",
    "shippingAddress": "123 Main Street, Delhi 110001",
    "contactNumber": "9876543215",
    "createdAt": "2025-01-28 10:30:00"
  }

Expected Status: 200 OK
```

#### Create New Order
```
Request:
  POST /api/orders

Parameters:
  userId=6
  totalAmount=50000
  paymentMethod=UPI
  shippingAddress=123 Main Street, Delhi 110001
  contactNumber=9876543215

Response:
  {
    "message": "Order created successfully",
    "orderId": "ORD-1672531200000-a1b2c3d4",
    "success": true
  }

Expected Status: 201 Created
```

#### Update Order Status
```
Request:
  PUT /api/orders/1?status=shipped

Response:
  {
    "message": "Order status updated successfully",
    "success": true
  }

Expected Status: 200 OK

Status Values: pending, confirmed, shipped, delivered, cancelled
```

---

### 4. CART API

#### Get User Cart
```
Request:
  GET /api/cart?userId=6

Response:
  {
    "items": [
      {
        "id": 1,
        "itemName": "Premium Wheat",
        "price": 2500,
        "quantity": 5,
        "itemType": "crop",
        "totalPrice": 12500
      },
      {
        "id": 2,
        "itemName": "Fresh Tomatoes",
        "price": 40,
        "quantity": 100,
        "itemType": "crop",
        "totalPrice": 4000
      }
    ],
    "totalAmount": 16500
  }

Expected Status: 200 OK
```

#### Add Item to Cart
```
Request:
  POST /api/cart

Parameters:
  userId=6
  itemName=Premium Wheat
  price=2500
  quantity=5
  itemType=crop
  cropId=1

Response:
  {
    "message": "Item added to cart",
    "success": true
  }

Expected Status: 201 Created
```

#### Update Cart Item Quantity
```
Request:
  PUT /api/cart?itemId=1&quantity=10

Response:
  {
    "message": "Cart item updated",
    "success": true
  }

Expected Status: 200 OK
```

#### Remove Item from Cart
```
Request:
  DELETE /api/cart?itemId=1

Response:
  {
    "message": "Item removed from cart",
    "success": true
  }

Expected Status: 200 OK
```

#### Add Machine to Cart
```
Request:
  POST /api/cart

Parameters:
  userId=6
  itemName=John Deere Tractor
  price=850000
  quantity=1
  itemType=machine
  machineId=1

Response:
  {
    "message": "Item added to cart",
    "success": true
  }

Expected Status: 201 Created
```

---

## Testing Workflow

### Complete E-Commerce Flow

#### Step 1: Browse Crops
```bash
curl http://localhost:8080/agriculture-website/api/crops/
```

#### Step 2: Browse Machinery
```bash
curl http://localhost:8080/agriculture-website/api/machines/
curl http://localhost:8080/agriculture-website/api/machines/type/rent
```

#### Step 3: Add Items to Cart
```bash
# Add crop to cart
curl -X POST http://localhost:8080/agriculture-website/api/cart \
  -d "userId=6&itemName=Premium Wheat&price=2500&quantity=5&itemType=crop&cropId=1"

# Add machine to cart
curl -X POST http://localhost:8080/agriculture-website/api/cart \
  -d "userId=6&itemName=John Deere Tractor&price=850000&quantity=1&itemType=machine&machineId=1"
```

#### Step 4: View Cart
```bash
curl http://localhost:8080/agriculture-website/api/cart?userId=6
```

#### Step 5: Create Order
```bash
curl -X POST http://localhost:8080/agriculture-website/api/orders \
  -d "userId=6&totalAmount=862500&paymentMethod=UPI&shippingAddress=Delhi&contactNumber=9876543215"
```

#### Step 6: Check Order Status
```bash
curl http://localhost:8080/agriculture-website/api/orders?userId=6
```

#### Step 7: Update Order
```bash
curl -X PUT http://localhost:8080/agriculture-website/api/orders/1?status=shipped
```

---

## Error Handling

### Common Errors

#### 400 Bad Request
```json
{
  "error": "Missing required parameter: userId"
}
```
**Solution**: Check all required parameters are provided

#### 404 Not Found
```json
{
  "error": "Crop not found"
}
```
**Solution**: Verify the resource ID exists

#### 500 Internal Server Error
```json
{
  "error": "Database connection failed"
}
```
**Solution**: Check MySQL connection, verify database exists

#### 201 Created vs 200 OK
- 201 = Resource was created successfully
- 200 = Request was successful

---

## Performance Testing

### Load Testing with Apache Bench
```bash
# 100 requests with concurrency of 10
ab -n 100 -c 10 http://localhost:8080/agriculture-website/api/crops/

# 1000 requests
ab -n 1000 -c 50 http://localhost:8080/agriculture-website/api/machines/
```

### Response Time Benchmarks
- GET /crops: <100ms
- GET /machines: <100ms
- GET /orders: <150ms
- POST /cart: <200ms
- POST /orders: <300ms

---

## Security Testing

### Test Cases

1. **SQL Injection**
   ```
   cropName='; DROP TABLE crops; --
   Expected: Properly escaped, no injection
   ```

2. **Cross-Site Scripting (XSS)**
   ```
   description=<script>alert('XSS')</script>
   Expected: Properly encoded
   ```

3. **Authentication**
   - Test without authentication
   - Test with invalid credentials
   - Test with expired session

4. **Authorization**
   - Farmer cannot delete buyer's orders
   - User can only access their own cart
   - Admin access restrictions

---

## Integration Testing

### Postman Collection

Create a Postman environment with:
```json
{
  "base_url": "http://localhost:8080/agriculture-website",
  "userId": 6,
  "farmerId": 1,
  "cropId": 1,
  "machineId": 1
}
```

### Automated Tests

```javascript
// Example: Test Crop API
pm.test("Get all crops", function () {
    pm.expect(pm.response.code).to.be.oneOf([200, 201]);
    pm.expect(pm.response.headers.get("Content-Type")).to.include("application/json");
});

// Example: Validate response structure
pm.test("Crop response structure", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData[0]).to.have.property("id");
    pm.expect(jsonData[0]).to.have.property("cropName");
    pm.expect(jsonData[0]).to.have.property("price");
});
```

---

## Troubleshooting

### Issue: CORS Error
```
Solution: Configure CORS in web.xml or controller
```

### Issue: Empty Response
```
Solution: Check if data exists in database
Run: mysql> SELECT COUNT(*) FROM crops;
```

### Issue: 500 Error on All Requests
```
Solution: Check MySQL connection
Verify database user credentials
Check application logs
```

### Issue: Slow Response Times
```
Solution: Add database indexes
Check MySQL performance
Review query execution plans
```

---

## Performance Optimization Tips

1. **Database Optimization**
   - Use proper indexes
   - Avoid SELECT * queries
   - Use pagination for large datasets

2. **Response Optimization**
   - Compress responses (gzip)
   - Use JSON minification
   - Cache frequently accessed data

3. **Connection Pooling**
   - Use Apache DBCP or HikariCP
   - Configure appropriate pool size
   - Monitor connection usage

---

**Last Updated**: January 2026
**API Version**: 1.0.0
**Test Coverage**: 90%+
