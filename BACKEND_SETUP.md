# Backend Setup Guide

## Quick Start

### 1. Database Setup

Run the SQL script to create the database:
```bash
mysql -u root -p < database/create_database.sql
```

Or use the enhanced version with orders table:
```bash
mysql -u root -p < database/create_database_enhanced.sql
```

### 2. Configure Database Connection

Edit `src/main/java/com/agzone/util/DatabaseConnection.java`:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/agriculture_website";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password_here";
```

### 3. Build the Project

```bash
mvn clean package
```

### 4. Deploy to Tomcat

Copy the generated WAR file from `target/agriculture-website.war` to your Tomcat `webapps` directory.

### 5. Update HTML Forms

Update the following HTML files to use correct servlet endpoints:

#### registration.html
- Form action should be: `action="/registration"` (already correct if using relative path)

#### login.html  
- Form action should be: `action="/login"` (update from `login.php`)

#### contactUs.html
- Form actions should be: `action="/saveContact"` (already correct)

#### f1.html (Crop Sales)
- Update sell form actions from `psales.php` to `/sales`

#### rent_machine.html
- If adding posts, use endpoint: `/post`

#### checkout.html
- Update order placement to use: `/order`

### 6. Update Logout Links

Replace all instances of `logout.php` with `/logout` in:
- `f1.html`
- `contactUs.html`
- `rent_machine.html`
- `index.html` (if applicable)

---

## Endpoint Summary

| Feature | Endpoint | Method | Auth Required |
|---------|----------|--------|----------------|
| Registration | `/registration` | POST | No |
| Login | `/login` | POST | No |
| Logout | `/logout` | GET/POST | Yes |
| Contact Form | `/saveContact` | POST | No |
| Get Posts | `/post` | GET | No |
| Create Post | `/post` | POST | Yes |
| Crop Sales | `/sales` | POST | Yes |
| Place Order | `/order` | POST | Yes |

---

## Testing Checklist

- [ ] Database created and tables exist
- [ ] Database connection configured correctly
- [ ] User registration works
- [ ] User login works
- [ ] Session persists after login
- [ ] Logout works
- [ ] Contact form submissions work
- [ ] Crop sales form works (requires login)
- [ ] Order placement works (requires login)

---

## Troubleshooting

### Database Connection Error
- Check MySQL is running
- Verify database name, username, password
- Check MySQL port (default: 3306)

### 404 Errors
- Verify servlet annotations (@WebServlet)
- Check web.xml configuration
- Ensure WAR file deployed correctly

### Session Issues
- Check session timeout in web.xml
- Verify session attributes are set correctly
- Clear browser cookies if needed

### Form Submission Not Working
- Check form action URLs
- Verify servlet mapping
- Check browser console for errors
- Verify all required fields are present

---

## File Structure

```
src/main/java/com/agzone/
├── controller/
│   ├── ContactUsController.java    # Contact form handling
│   ├── LoginController.java        # User authentication
│   ├── LogoutController.java       # Session termination
│   ├── OrderController.java        # Order processing
│   ├── PostController.java         # Machine/product posts
│   ├── RegistrationController.java # User registration
│   └── SalesController.java        # Crop sales
├── model/
│   ├── Contact.java                # Contact model
│   ├── CropSale.java              # Crop sale model
│   ├── Post.java                   # Post model
│   └── User.java                   # User model
└── util/
    └── DatabaseConnection.java     # DB connection utility
```

---

## Next Steps

1. Update HTML forms to use correct endpoints
2. Test all functionality
3. Add password hashing (BCrypt recommended)
4. Implement file upload for images
5. Add input validation and sanitization
6. Set up error pages
7. Configure logging
8. Add unit tests
