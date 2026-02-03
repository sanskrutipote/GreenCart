# Agriculture Website (GreenCart) - Full Working Project

A complete agriculture marketplace website connecting farmers and agricultural businesses. Built with Java Servlets backend and HTML/CSS/JavaScript frontend.

## 🌾 Features

- **User Management**: Registration, Login, Logout
- **Crop Marketplace**: Buy and sell crops
- **Machine Rental**: Post and browse farming machinery
- **Contact System**: Multiple contact form types (General, Support, Partnership, Feedback)
- **Shopping Cart**: Add items to cart and checkout
- **Order Management**: Place and track orders

## 🛠️ Technology Stack

### Backend
- **Java 17+**
- **Jakarta Servlets** (Jakarta EE 9+)
- **MySQL 8.0+**
- **Maven** (Build tool)

### Frontend
- **HTML5**
- **CSS3**
- **JavaScript**
- **Font Awesome** (Icons)

## 📋 Prerequisites

Before you begin, ensure you have:

1. **Java Development Kit (JDK) 17 or higher**
   - Download: https://www.oracle.com/java/technologies/downloads/
   - Verify: `java -version`

2. **Apache Maven**
   - Download: https://maven.apache.org/download.cgi
   - Verify: `mvn -version`

3. **MySQL Server 8.0+**
   - Download: https://dev.mysql.com/downloads/mysql/
   - Verify: `mysql --version`

4. **Apache Tomcat 10+** (or compatible servlet container)
   - Download: https://tomcat.apache.org/download-10.cgi

5. **IDE** (Optional but recommended)
   - IntelliJ IDEA
   - Eclipse
   - VS Code

## 🚀 Quick Start Guide

### Step 1: Database Setup

1. **Start MySQL Server**
   ```bash
   # Windows: Start MySQL service from Services
   # Or use XAMPP/WAMP MySQL
   ```

2. **Create Database**
   
   **Option A: Using Batch File (Easiest)**
   - Double-click: `database\setup_separate_database.bat`
   - Enter MySQL root password when prompted
   
   **Option B: Using Command Line**
   ```bash
   cd database
   mysql -u root -p < create_database_separate.sql
   ```
   
   **Option C: Using MySQL Workbench**
   - Open MySQL Workbench
   - File → Open SQL Script
   - Select `database\create_database_separate.sql`
   - Click Execute (⚡)

3. **Verify Database**
   ```sql
   mysql -u root -p
   USE agriculture_website;
   SHOW TABLES;
   ```

### Step 2: Configure Database Connection

Edit `src\main\java\com\agzone\util\DatabaseConnection.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/agriculture_website";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password_here";  // Update this!
```

### Step 3: Build the Project

```bash
# Navigate to project root
cd "d:\Agriculture Website (2)\Agriculture Website"

# Clean and build
mvn clean package
```

This creates: `target\agriculture-website.war`

### Step 4: Deploy to Tomcat

**Option A: Manual Deployment**
1. Copy `target\agriculture-website.war` to `{TOMCAT_HOME}\webapps\`
2. Start Tomcat
3. Access: `http://localhost:8080/agriculture-website/`

**Option B: Using IDE**
1. Configure Tomcat in your IDE
2. Deploy the WAR file
3. Run on server

**Option C: Embedded Tomcat (Maven)**
```bash
mvn tomcat7:run
```

### Step 5: Access the Application

Open your browser and navigate to:
```
http://localhost:8080/agriculture-website/
```

Or if deployed as ROOT:
```
http://localhost:8080/
```

## 📁 Project Structure

```
Agriculture Website/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── agzone/
│       │           ├── controller/      # Servlets (API endpoints)
│       │           ├── model/           # Data models
│       │           └── util/            # Utilities (DB connection)
│       └── webapp/
│           ├── WEB-INF/
│           │   └── web.xml             # Web configuration
│           ├── *.html                   # Frontend pages
│           ├── *.css                    # Stylesheets
│           └── *.js                     # JavaScript files
├── database/
│   ├── create_database_separate.sql    # Database setup
│   ├── setup_separate_database.bat      # Automated setup
│   └── view_separate_database.bat      # View data script
├── pom.xml                              # Maven configuration
└── README.md                            # This file
```

## 🔌 API Endpoints

| Endpoint | Method | Description | Auth Required |
|----------|--------|-------------|---------------|
| `/registration` | POST | User registration | No |
| `/login` | POST | User login | No |
| `/logout` | GET/POST | User logout | Yes |
| `/saveContact` | POST | Submit contact form | No |
| `/post` | GET | Get all posts | No |
| `/post` | POST | Create new post | Yes |
| `/sales` | POST | Submit crop sale | Yes |
| `/order` | POST | Place order | Yes |

See `BACKEND_API.md` for detailed API documentation.

## 🗄️ Database Schema

**Database:** `agriculture_website`

**Tables:**
- `membership` - User accounts
- `contactus` - Contact form submissions
- `posts` - Machine/product listings
- `cropsales` - Crop sale listings
- `orders` - Order records
- `order_items` - Order line items

See `database\create_database_separate.sql` for full schema.

## 🧪 Testing the Application

### 1. Test Registration
1. Navigate to `registration.html`
2. Fill the form
3. Submit
4. Check database: `SELECT * FROM membership;`

### 2. Test Login
1. Navigate to `login.html`
2. Use registered credentials
3. Should redirect to `index.jsp` or `index.html`

### 3. Test Contact Form
1. Navigate to `contactUs.html`
2. Fill any tab form
3. Submit
4. Check database: `SELECT * FROM contactus;`

### 4. Test Crop Sales
1. Login first
2. Navigate to `f1.html`
3. Click "Sell" on any crop
4. Fill form and submit
5. Check database: `SELECT * FROM cropsales;`

### 5. Test Shopping Cart & Checkout
1. Navigate to `f1.html`
2. Click "Buy" on crops (adds to cart)
3. Click cart icon to view
4. Navigate to `checkout.html`
5. Click "Place your order"
6. Check database: `SELECT * FROM orders;`

## 🔧 Configuration

### Database Connection
Edit: `src\main\java\com\agzone\util\DatabaseConnection.java`

### Web Application Context
Edit: `src\main\webapp\WEB-INF\web.xml`

### Maven Settings
Edit: `pom.xml`

## 📝 Default Credentials

After database setup, a test user is created:
- **Username:** `admin`
- **Email:** `admin@greencart.com`
- **Password:** `admin123`

**⚠️ Change this in production!**

## 🐛 Troubleshooting

### Database Connection Error
- **Problem:** Cannot connect to MySQL
- **Solution:**
  1. Check MySQL is running
  2. Verify database exists: `SHOW DATABASES;`
  3. Check credentials in `DatabaseConnection.java`
  4. Verify port 3306 is accessible

### 404 Errors
- **Problem:** Servlets not found
- **Solution:**
  1. Check servlet annotations (`@WebServlet`)
  2. Verify WAR file deployed correctly
  3. Check `web.xml` configuration
  4. Restart Tomcat

### Session Issues
- **Problem:** Login not persisting
- **Solution:**
  1. Check session timeout in `web.xml`
  2. Verify cookies enabled in browser
  3. Check session attributes are set correctly

### Form Submission Not Working
- **Problem:** Forms not submitting
- **Solution:**
  1. Check form action URLs (should start with `/`)
  2. Verify servlet endpoints
  3. Check browser console for errors
  4. Verify all required fields present

## 📚 Documentation

- **API Documentation:** `BACKEND_API.md`
- **Database Setup:** `database\DATABASE_SETUP.md`
- **View Database:** `database\HOW_TO_VIEW_DATABASE.md`
- **Backend Summary:** `BACKEND_SUMMARY.md`

## 🚀 Deployment

### Development
- Use embedded Tomcat or local Tomcat instance
- Hot reload enabled in IDE

### Production
1. Build WAR: `mvn clean package`
2. Deploy to production Tomcat
3. Configure production database
4. Update `DatabaseConnection.java` with production credentials
5. Set up SSL/HTTPS
6. Configure firewall rules

## 🔐 Security Notes

**⚠️ Current Implementation:**
- Passwords stored in plain text (NOT for production!)
- No password hashing
- No CSRF protection
- No input sanitization beyond basic validation

**🔒 Production Recommendations:**
1. Implement password hashing (BCrypt)
2. Add CSRF tokens
3. Sanitize all inputs
4. Use prepared statements (already implemented)
5. Add rate limiting
6. Implement HTTPS
7. Add authentication tokens
8. Input validation on both client and server

## 📦 Dependencies

See `pom.xml` for complete dependency list:
- Jakarta Servlet API 6.0.0
- Jakarta JSP API 3.1.1
- JSTL 3.0.0
- MySQL Connector 8.0.33

## 🤝 Contributing

1. Fork the repository
2. Create feature branch
3. Make changes
4. Test thoroughly
5. Submit pull request

## 📄 License

This project is created for educational purposes.

## 👥 Authors

- Created by: Janarthan ManojKumar, Meshith Ariyawansa, Hizbullah Razik
- Backend Enhancement: [Your Name]

## 🎯 Next Steps

1. ✅ Database setup complete
2. ✅ Backend implementation complete
3. ✅ Frontend connected to backend
4. ⏭️ Add password hashing
5. ⏭️ Implement file upload for images
6. ⏭️ Add admin dashboard
7. ⏭️ Add search and filter functionality
8. ⏭️ Implement payment gateway
9. ⏭️ Add email notifications
10. ⏭️ Add unit tests

## 📞 Support

For issues or questions:
1. Check documentation files
2. Review troubleshooting section
3. Check database connection
4. Verify all prerequisites installed

---

**Happy Coding! 🌾🚜**
