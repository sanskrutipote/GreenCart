# Backend API Documentation

## Agriculture Website (GreenCart) - Java Servlet Backend

### Base URL
All endpoints are relative to your application context root.

---

## Authentication Endpoints

### 1. User Registration
- **Endpoint:** `POST /registration`
- **Description:** Register a new user
- **Parameters:**
  - `UserName` (required): Username for the account
  - `Email` (required): Valid email address
  - `MobileNo` (required): 10-digit mobile number
  - `Npassword` (required): New password
  - `Cpassword` (required): Confirm password (must match Npassword)
- **Response:** JavaScript alert with success/error message
- **Example:**
  ```html
  <form action="/registration" method="post">
    <input name="UserName" value="john_doe">
    <input name="Email" value="john@example.com">
    <input name="MobileNo" value="1234567890">
    <input name="Npassword" type="password" value="password123">
    <input name="Cpassword" type="password" value="password123">
  </form>
  ```

### 2. User Login
- **Endpoint:** `POST /login`
- **Description:** Authenticate user and create session
- **Parameters:**
  - `UserName` (required): Username or email
  - `Password` (required): User password
- **Response:** Redirects to `index.jsp` on success, or JavaScript alert on failure
- **Session Attributes:**
  - `login`: Boolean indicating logged in status
  - `name`: Username
  - `Pass`: Username (for compatibility)

### 3. User Logout
- **Endpoint:** `GET /logout` or `POST /logout`
- **Description:** Destroy user session
- **Response:** Redirects to `login.html`

---

## Contact Form Endpoint

### 4. Submit Contact Form
- **Endpoint:** `POST /saveContact`
- **Description:** Submit contact form from any tab (General, Support, Partnership, Feedback)
- **Parameters:**
  - `name` (required): Full name
  - `email` (required): Valid email address
  - `phone` (required): Phone number (10-15 digits)
  - `subject` (optional): Type of inquiry (defaults to "General Inquiry")
  - `message` (required): Message content
- **Response:** JavaScript alert with success/error message
- **Example:**
  ```html
  <form action="/saveContact" method="post">
    <input name="name" value="John Doe">
    <input name="email" value="john@example.com">
    <input name="phone" value="1234567890">
    <input name="subject" value="General Question">
    <textarea name="message">Your message here</textarea>
  </form>
  ```

---

## Post/Machine Management Endpoints

### 5. Get All Posts
- **Endpoint:** `GET /post`
- **Description:** Retrieve all machine/product posts
- **Response:** JSON array of posts
- **Example Response:**
  ```json
  [
    {
      "id": 1,
      "name": "Garden Tractor",
      "email": "seller@example.com",
      "mobile": "1234567890",
      "price": 150000.00,
      "location": "Kandy",
      "image": "tractor.jpg",
      "owner": "john_doe",
      "createdAt": "2026-01-25 10:30:00"
    }
  ]
  ```

### 6. Create Post
- **Endpoint:** `POST /post`
- **Description:** Create a new machine/product post (requires authentication)
- **Parameters:**
  - `name` (required): Product/machine name
  - `email` (required): Contact email
  - `mobile` (required): Contact mobile number
  - `price` (required): Price (decimal)
  - `location` (required): Location
  - `image` (optional): Image path/URL
- **Response:** JavaScript alert with success/error message
- **Note:** Owner is automatically set from session

---

## Crop Sales Endpoints

### 7. Submit Crop Sale
- **Endpoint:** `POST /sales`
- **Description:** Submit crop for sale (requires authentication)
- **Parameters:**
  - `cid` (required): Crop ID
  - `item` (required): Crop name
  - `quantity` (required): Quantity available
  - `sell-location` (required): Location
- **Response:** JavaScript alert with success/error message
- **Example:**
  ```html
  <form action="/sales" method="post">
    <input name="cid" value="1">
    <input name="item" value="Sweet Potato">
    <input name="quantity" value="100">
    <input name="sell-location" value="Kandy">
  </form>
  ```

---

## Order Management Endpoints

### 8. Place Order
- **Endpoint:** `POST /order`
- **Description:** Place an order from checkout (requires authentication)
- **Parameters:**
  - `cart` (required): Cart data in format "item1:qty1,item2:qty2,..."
  - `address` (optional): Delivery address
  - `total` (optional): Total amount
- **Response:** JSON response
  ```json
  {
    "success": true,
    "message": "Order placed successfully!"
  }
  ```
- **Example:**
  ```javascript
  fetch('/order', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: 'cart=Sweet Potato:2,Corn:1&address=123 Main St&total=140'
  })
  ```

---

## Database Schema

### Tables:
1. **membership** - User accounts
2. **contactus** - Contact form submissions
3. **posts** - Machine/product listings
4. **cropsales** - Crop sale listings
5. **orders** - Order records (if using enhanced schema)
6. **order_items** - Order line items (if using enhanced schema)

---

## Error Handling

All endpoints return appropriate error messages:
- **Validation errors:** JavaScript alert with specific error message
- **Database errors:** JavaScript alert with generic error message
- **Authentication errors:** Redirect to login page or JSON error response

---

## Session Management

- Sessions are created on successful login
- Session timeout: 30 minutes (configured in web.xml)
- Session attributes:
  - `login`: Boolean
  - `name`: Username
  - `Pass`: Username

---

## Notes

1. All password comparisons are currently plain text (consider implementing password hashing for production)
2. Database connection details are in `DatabaseConnection.java` - update for your environment
3. CORS headers may need to be added if frontend is on different domain
4. Consider adding input sanitization and SQL injection prevention (PreparedStatements are used)
5. File uploads for images are not yet implemented - image field accepts URL/path string

---

## Configuration

### Database Connection
Edit `src/main/java/com/agzone/util/DatabaseConnection.java`:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/agriculture_website";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password";
```

### Web Application
- Servlet container: Tomcat 10+ (Jakarta EE 9+)
- Java version: 17+
- Database: MySQL 8.0+

---

## Testing

1. **Registration:**
   - Navigate to `registration.html`
   - Fill form and submit
   - Check database for new user

2. **Login:**
   - Navigate to `login.html`
   - Use registered credentials
   - Should redirect to `index.jsp`

3. **Contact Form:**
   - Navigate to `contactUs.html`
   - Fill any tab form and submit
   - Check `contactus` table

4. **Crop Sales:**
   - Login first
   - Navigate to `f1.html`
   - Click "Sell" on any crop
   - Fill form and submit
   - Check `cropsales` table

---

## Future Enhancements

- [ ] Password hashing (BCrypt)
- [ ] Email verification
- [ ] File upload for images
- [ ] Order status tracking
- [ ] Payment integration
- [ ] Admin dashboard
- [ ] Search and filter functionality
- [ ] Pagination for listings
- [ ] Rate limiting
- [ ] API authentication tokens
