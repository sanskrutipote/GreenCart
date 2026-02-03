# How to Install Maven on Windows

## Option 1: Install Maven (Recommended)

### Step 1: Download Maven

1. Go to: https://maven.apache.org/download.cgi
2. Download: `apache-maven-3.9.5-bin.zip` (or latest version)
3. Extract to: `C:\Program Files\Apache\maven` (or your preferred location)

### Step 2: Add Maven to PATH

1. **Open System Properties:**
   - Press `Windows Key + X`
   - Select "System"
   - Click "Advanced system settings"
   - Click "Environment Variables"

2. **Edit PATH Variable:**
   - Under "System Variables", find "Path"
   - Click "Edit"
   - Click "New"
   - Add: `C:\Program Files\Apache\maven\bin`
   - Click "OK" on all windows

3. **Verify Installation:**
   - Close and reopen PowerShell/Command Prompt
   - Run: `mvn -version`
   - Should show Maven version

### Step 3: Verify Java

Maven requires Java. Check if Java is installed:
```powershell
java -version
```

If not installed, download from: https://www.oracle.com/java/technologies/downloads/

---

## Option 2: Use Maven Wrapper (No Installation Needed)

If you don't want to install Maven, we can use Maven Wrapper. Let me create that for you.

---

## Option 3: Build Using IDE

### Using IntelliJ IDEA:
1. Open project in IntelliJ
2. Right-click `pom.xml` → "Maven" → "Reload Project"
3. Right-click project → "Maven" → "Lifecycle" → "package"

### Using Eclipse:
1. Import project as "Existing Maven Project"
2. Right-click project → "Run As" → "Maven build..."
3. Goals: `clean package`
4. Click "Run"

### Using VS Code:
1. Install "Java Extension Pack"
2. Open project folder
3. Open terminal in VS Code
4. Run: `mvn clean package` (if Maven is installed)

---

## Option 4: Manual Compilation (Advanced)

If Maven is not available, you can compile manually, but it's complex. Better to install Maven.

---

## Quick Check: Is Java Installed?

Run this in PowerShell:
```powershell
java -version
```

If you see Java version, you're good. If not, install Java first.

---

## After Installing Maven

Once Maven is installed and in PATH:

1. **Close and reopen PowerShell**
2. **Navigate to project:**
   ```powershell
   cd "D:\Agriculture Website (2)\Agriculture Website"
   ```
3. **Build project:**
   ```powershell
   mvn clean package
   ```

---

## Troubleshooting

### "mvn is not recognized"
- Maven not in PATH
- Solution: Add Maven bin folder to PATH (see Step 2 above)

### "JAVA_HOME is not set"
- Set JAVA_HOME environment variable:
  - Variable: `JAVA_HOME`
  - Value: `C:\Program Files\Java\jdk-17` (or your Java path)

### "Java not found"
- Install Java JDK 17 or higher
- Download: https://www.oracle.com/java/technologies/downloads/

---

## Alternative: Use Maven Wrapper

I can create a Maven Wrapper for you that doesn't require Maven installation. Would you like me to do that?
