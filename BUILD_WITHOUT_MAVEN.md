# Build Project Without Maven Installation

Since Maven is not installed, here are alternative ways to build and run your project:

## Option 1: Install Maven (Recommended - 5 minutes)

### Quick Install Steps:

1. **Download Maven:**
   - Go to: https://maven.apache.org/download.cgi
   - Download: `apache-maven-3.9.5-bin.zip` (Windows)
   - Extract to: `C:\Program Files\Apache\maven`

2. **Add to PATH:**
   - Press `Windows Key`, type "Environment Variables"
   - Click "Edit the system environment variables"
   - Click "Environment Variables"
   - Under "System Variables", find "Path" → Click "Edit"
   - Click "New" → Add: `C:\Program Files\Apache\maven\bin`
   - Click OK on all windows

3. **Restart PowerShell:**
   - Close current PowerShell window
   - Open new PowerShell
   - Run: `mvn -version` to verify

4. **Build Project:**
   ```powershell
   cd "D:\Agriculture Website (2)\Agriculture Website"
   mvn clean package
   ```

---

## Option 2: Use IDE to Build (Easiest - No Maven Install)

### Using IntelliJ IDEA:

1. **Download IntelliJ IDEA Community** (Free):
   - https://www.jetbrains.com/idea/download/

2. **Open Project:**
   - File → Open → Select project folder
   - IntelliJ will detect Maven project automatically

3. **Build:**
   - Right-click `pom.xml` → "Maven" → "Reload Project"
   - Right-click project → "Maven" → "Lifecycle" → "package"
   - WAR file will be in `target` folder

### Using Eclipse:

1. **Download Eclipse IDE:**
   - https://www.eclipse.org/downloads/

2. **Import Project:**
   - File → Import → Maven → Existing Maven Projects
   - Select project folder
   - Click Finish

3. **Build:**
   - Right-click project → "Run As" → "Maven build..."
   - Goals: `clean package`
   - Click Run

### Using VS Code:

1. **Install Extensions:**
   - Java Extension Pack
   - Maven for Java

2. **Open Project:**
   - File → Open Folder → Select project

3. **Build:**
   - Open terminal in VS Code (Ctrl + `)
   - Run: `mvn clean package` (if Maven is installed)
   - Or use Maven extension to build

---

## Option 3: Manual WAR Creation (Advanced)

If you can't install Maven or use IDE, you can manually create the WAR file, but it's complex. Better to use Option 1 or 2.

---

## Option 4: Use Pre-built Script (I'll create this)

I can create a batch script that downloads Maven temporarily and builds your project. Would you like me to do that?

---

## Quick Check: Do You Have Java?

Run this in PowerShell:
```powershell
java -version
```

**If Java is installed:** You'll see version info → Proceed with Maven installation (Option 1)

**If Java is NOT installed:** 
1. Download Java JDK 17+: https://www.oracle.com/java/technologies/downloads/
2. Install Java
3. Then install Maven (Option 1)

---

## Recommended: Install Maven

Maven is the standard build tool for Java projects. Installing it will make your life easier:

1. **Download:** https://maven.apache.org/download.cgi
2. **Extract** to `C:\Program Files\Apache\maven`
3. **Add to PATH:** `C:\Program Files\Apache\maven\bin`
4. **Restart PowerShell**
5. **Build:** `mvn clean package`

**Time:** ~5 minutes
**Difficulty:** Easy

---

## Need Help?

If you're stuck, let me know:
- Do you have Java installed? (Run `java -version`)
- Do you have an IDE installed? (IntelliJ, Eclipse, VS Code)
- Would you prefer to install Maven or use an IDE?

I can guide you through whichever option you prefer!
