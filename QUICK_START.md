# Quick Start Guide - Get Running in 5 Minutes! ⚡

Follow these steps to get your Agriculture Website up and running quickly.

## 🎯 Prerequisites Check

Before starting, make sure you have:
- ✅ Java 17+ installed (`java -version`)
- ✅ Maven installed (`mvn -version`)
- ✅ MySQL running (`mysql --version`)
- ✅ Tomcat installed (or use embedded)

## 🚀 5-Minute Setup

### Step 1: Setup Database (1 minute)

**Windows:**
```bash
# Double-click this file:
database\setup_separate_database.bat
# Enter MySQL password when prompted
```

**Linux/Mac:**
```bash
cd database
mysql -u root -p < create_database_separate.sql
```

### Step 2: Configure Database (30 seconds)

Edit: `src\main\java\com\agzone\util\DatabaseConnection.java`

Change line 14:
```java
private static final String DB_PASSWORD = "your_mysql_password";  // Update this!
```

### Step 3: Build Project (1 minute)

```bash
# In project root directory
mvn clean package
```

Wait for "BUILD SUCCESS" message.

### Step 4: Deploy to Tomcat (1 minute)

**Option A: Copy WAR file**
```bash
# Copy WAR to Tomcat
cp target/agriculture-website.war /path/to/tomcat/webapps/

# Start Tomcat
/path/to/tomcat/bin/startup.sh  # or startup.bat on Windows
```

**Option B: Use IDE**
- Right-click project → Run on Server
- Select Tomcat
- Wait for deployment

### Step 5: Test (1 minute)

1. **Open browser:** `http://localhost:8080/agriculture-website/`
2. **Test registration:**
   - Click "Login" → "Register here"
   - Fill form and submit
3. **Test login:**
   - Use registered credentials
   - Should redirect after login

## ✅ Verification Checklist

- [ ] Database `agriculture_website` exists
- [ ] All 6 tables created (membership, contactus, posts, cropsales, orders, order_items)
- [ ] WAR file built successfully
- [ ] Application accessible in browser
- [ ] Registration works
- [ ] Login works
- [ ] Contact form works
- [ ] Cart functionality works

## 🐛 Quick Troubleshooting

### "Cannot connect to database"
→ Check MySQL is running and password is correct

### "404 Not Found"
→ Check Tomcat is running and WAR is deployed

### "500 Internal Server Error"
→ Check database connection and logs

### Forms not submitting
→ Check browser console for errors

## 📚 Next Steps

1. Read `README.md` for full documentation
2. Check `BACKEND_API.md` for API details
3. Review `DEPLOYMENT_GUIDE.md` for production setup
4. Test all features
5. Customize as needed

## 🎉 You're Done!

Your Agriculture Website is now running! 

**Default Test User:**
- Username: `admin`
- Password: `admin123`

**Access Points:**
- Home: `http://localhost:8080/agriculture-website/`
- Login: `http://localhost:8080/agriculture-website/login.html`
- Registration: `http://localhost:8080/agriculture-website/registration.html`

Happy coding! 🌾
