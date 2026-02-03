# Deployment Guide

Complete guide to deploy the Agriculture Website to production.

## 📋 Pre-Deployment Checklist

- [ ] Database created and configured
- [ ] All tests passing
- [ ] Database credentials updated for production
- [ ] WAR file built successfully
- [ ] Tomcat server ready
- [ ] Domain/URL configured (if applicable)

## 🗄️ Database Setup

### 1. Create Production Database

```sql
-- Connect to MySQL
mysql -u root -p

-- Create database
CREATE DATABASE agriculture_website_prod;

-- Run setup script
SOURCE database/create_database_separate.sql;
```

### 2. Update Database Connection

Edit `src\main\java\com\agzone\util\DatabaseConnection.java`:

```java
private static final String DB_URL = "jdbc:mysql://your-production-host:3306/agriculture_website_prod";
private static final String DB_USER = "production_user";
private static final String DB_PASSWORD = "secure_password";
```

### 3. Create Production User (Recommended)

```sql
CREATE USER 'agzone_user'@'localhost' IDENTIFIED BY 'secure_password';
GRANT ALL PRIVILEGES ON agriculture_website_prod.* TO 'agzone_user'@'localhost';
FLUSH PRIVILEGES;
```

## 🔨 Build for Production

### 1. Clean Build

```bash
mvn clean package
```

### 2. Verify WAR File

Check that `target\agriculture-website.war` exists and has reasonable size.

### 3. Test WAR Locally

```bash
# Copy to local Tomcat
cp target/agriculture-website.war /path/to/tomcat/webapps/

# Start Tomcat
/path/to/tomcat/bin/startup.sh  # Linux/Mac
# or
/path/to/tomcat/bin/startup.bat  # Windows

# Test in browser
http://localhost:8080/agriculture-website/
```

## 🚀 Deploy to Tomcat

### Option 1: Manual Deployment

1. **Stop Tomcat**
   ```bash
   /path/to/tomcat/bin/shutdown.sh
   ```

2. **Remove Old Deployment** (if exists)
   ```bash
   rm -rf /path/to/tomcat/webapps/agriculture-website
   rm /path/to/tomcat/webapps/agriculture-website.war
   ```

3. **Copy WAR File**
   ```bash
   cp target/agriculture-website.war /path/to/tomcat/webapps/
   ```

4. **Start Tomcat**
   ```bash
   /path/to/tomcat/bin/startup.sh
   ```

5. **Verify Deployment**
   - Check logs: `tail -f /path/to/tomcat/logs/catalina.out`
   - Access: `http://your-server:8080/agriculture-website/`

### Option 2: Tomcat Manager (Web UI)

1. **Access Manager**
   ```
   http://your-server:8080/manager/html
   ```

2. **Deploy WAR**
   - Scroll to "WAR file to deploy"
   - Choose file: `agriculture-website.war`
   - Click "Deploy"

### Option 3: IDE Deployment

1. Configure Tomcat in IDE
2. Add deployment artifact
3. Deploy to server
4. Start server

## ⚙️ Tomcat Configuration

### 1. Increase Memory (if needed)

Edit `{TOMCAT_HOME}/bin/setenv.sh` (or `setenv.bat` on Windows):

```bash
export CATALINA_OPTS="-Xms512m -Xmx1024m"
```

### 2. Configure Context Path

Edit `{TOMCAT_HOME}/conf/server.xml`:

```xml
<Context path="/" docBase="agriculture-website" />
```

### 3. Enable HTTPS (Production)

1. **Generate SSL Certificate**
   ```bash
   keytool -genkey -alias tomcat -keyalg RSA -keystore /path/to/tomcat/keystore
   ```

2. **Edit server.xml**
   ```xml
   <Connector port="8443" protocol="HTTP/1.1"
              SSLEnabled="true"
              maxThreads="150" scheme="https"
              secure="true"
              keystoreFile="/path/to/tomcat/keystore"
              keystorePass="your_password"
              clientAuth="false" sslProtocol="TLS" />
   ```

## 🔒 Security Configuration

### 1. Update web.xml

Add security constraints:

```xml
<security-constraint>
    <web-resource-collection>
        <web-resource-name>Protected Area</web-resource-name>
        <url-pattern>/admin/*</url-pattern>
    </web-resource-collection>
    <auth-constraint>
        <role-name>admin</role-name>
    </auth-constraint>
</security-constraint>
```

### 2. Implement Password Hashing

Update `RegistrationController.java` and `LoginController.java` to use BCrypt.

### 3. Add Input Validation

Enhance all controllers with comprehensive input validation.

## 📊 Monitoring

### 1. Application Logs

Monitor: `{TOMCAT_HOME}/logs/catalina.out`

### 2. Database Logs

Monitor MySQL error log and slow query log.

### 3. Application Health Check

Create endpoint: `/health`

```java
@WebServlet("/health")
public class HealthController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.getWriter().write("{\"status\":\"ok\"}");
    }
}
```

## 🔄 Backup Strategy

### 1. Database Backup

```bash
# Daily backup script
mysqldump -u root -p agriculture_website_prod > backup_$(date +%Y%m%d).sql
```

### 2. Application Backup

```bash
# Backup WAR file
cp agriculture-website.war agriculture-website.war.backup
```

### 3. Automated Backup

Set up cron job (Linux) or Task Scheduler (Windows) for daily backups.

## 🚨 Rollback Procedure

If deployment fails:

1. **Stop Tomcat**
2. **Restore Previous WAR**
   ```bash
   cp agriculture-website.war.backup agriculture-website.war
   ```
3. **Restore Database** (if needed)
   ```bash
   mysql -u root -p agriculture_website_prod < backup_YYYYMMDD.sql
   ```
4. **Start Tomcat**

## 🌐 Domain Configuration

### 1. DNS Setup

Point domain to server IP:
```
A Record: yourdomain.com -> server_ip
```

### 2. Reverse Proxy (Nginx)

```nginx
server {
    listen 80;
    server_name yourdomain.com;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

### 3. SSL Certificate (Let's Encrypt)

```bash
certbot --nginx -d yourdomain.com
```

## 📈 Performance Optimization

### 1. Database Indexing

Ensure all foreign keys and frequently queried columns are indexed.

### 2. Connection Pooling

Configure connection pooling in `DatabaseConnection.java`:

```java
// Use HikariCP or similar
HikariConfig config = new HikariConfig();
config.setJdbcUrl(DB_URL);
config.setUsername(DB_USER);
config.setPassword(DB_PASSWORD);
config.setMaximumPoolSize(10);
HikariDataSource ds = new HikariDataSource(config);
```

### 3. Caching

Implement caching for frequently accessed data.

## ✅ Post-Deployment Verification

1. **Test Registration**
   - Create new user account
   - Verify in database

2. **Test Login**
   - Login with test account
   - Verify session works

3. **Test All Forms**
   - Contact form
   - Crop sales
   - Order placement

4. **Test Cart & Checkout**
   - Add items to cart
   - Complete checkout
   - Verify order in database

5. **Check Logs**
   - No errors in catalina.out
   - No database connection errors

## 🔧 Maintenance

### Regular Tasks

- [ ] Monitor application logs
- [ ] Check database size
- [ ] Review error logs
- [ ] Update dependencies
- [ ] Backup database
- [ ] Test restore procedure

### Updates

1. Make changes in development
2. Test thoroughly
3. Build new WAR
4. Backup current deployment
5. Deploy new WAR
6. Verify functionality
7. Monitor logs

---

**Deployment Complete! 🎉**
