# 🧪 OrangeHRM Test Automation Framework

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium-4.39.0-green.svg)](https://www.selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.11.0-red.svg)](https://testng.org/)
[![Maven](https://img.shields.io/badge/Maven-Build-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> A robust, maintainable, and scalable Selenium test automation framework for OrangeHRM application testing, featuring parallel execution, extent reporting, and headless browser support.

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Setup](#-setup)
- [Configuration](#-configuration)
- [Running Tests](#-running-tests)
- [Test Reports](#-test-reports)
- [Best Practices](#-best-practices)
- [Contributing](#-contributing)

## ✨ Features

- ✅ **Page Object Model (POM)** - Clean separation of test logic and page interactions
- ✅ **Parallel Test Execution** - Run tests concurrently with configurable thread count
- ✅ **Headless Mode Support** - Execute tests without GUI for CI/CD pipelines
- ✅ **Multi-Browser Support** - Chrome, Firefox, and Edge compatibility
- ✅ **Extent Reports** - Beautiful HTML reports with screenshots
- ✅ **Data-Driven Testing** - Excel and properties-based test data management
- ✅ **Custom Listeners** - Enhanced test execution tracking and reporting
- ✅ **Log4j2 Integration** - Comprehensive logging for debugging
- ✅ **Database Verification** - MySQL database validation capabilities
- ✅ **REST API Testing** - REST Assured integration for API tests
- ✅ **ThreadSafe Execution** - Thread-local WebDriver management for parallel tests
- ✅ **Reusable Action Drivers** - Common Selenium operations abstracted

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17 | Programming Language |
| Selenium WebDriver | 4.39.0 | Browser Automation |
| TestNG | 7.11.0 | Test Framework |
| Maven | 3.x | Build & Dependency Management |
| ExtentReports | 5.1.2 | Test Reporting |
| Log4j2 | 2.25.3 | Logging Framework |
| Apache POI | 5.5.1 | Excel Data Handling |
| REST Assured | 5.5.6 | API Testing |
| MySQL Connector | 9.4.0 | Database Testing |

## 🏗️ Architecture

```
Framework Design Pattern: Page Object Model (POM)
│
├── Page Objects (Pages Layer)
│   ├── LoginPage.java
│   └── HomePage.java
│
├── Test Classes (Tests Layer)
│   ├── LoginPageTest.java
│   ├── HomePageTest.java
│   ├── DBVerificationTest.java
│   └── APITest.java
│
├── Base Classes (Base Layer)
│   └── BaseClass.java (WebDriver initialization & configuration)
│
├── Action Drivers (Utilities Layer)
│   └── ActionDriver.java (Reusable Selenium actions)
│
├── Utilities
│   ├── DataProviders.java (Test data management)
│   ├── ExtentManager.java (Report management)
│   └── LoggerManager.java (Logging configuration)
│
└── Listeners
    └── TestListener.java (TestNG listeners for reports)
```

## 📁 Project Structure

```
orangeHRMPrasenjit/
│
├── src/
│   ├── main/
│   │   ├── java/com/orangehrm/
│   │   │   ├── actiondriver/        # Reusable Selenium actions
│   │   │   ├── base/                # Base test configuration
│   │   │   ├── listeners/           # TestNG listeners
│   │   │   ├── pages/               # Page Object classes
│   │   │   └── utilities/           # Helper utilities
│   │   └── resources/
│   │       ├── config.properties    # Application configuration
│   │       └── log4j2.xml          # Logging configuration
│   │
│   └── test/
│       ├── java/com/orangehrm/test/ # Test classes
│       └── resources/
│           └── testng.xml           # TestNG suite configuration
│
├── logs/                            # Application & error logs
├── target/
│   └── surefire-reports/           # Test execution reports
│
└── pom.xml                          # Maven dependencies
```

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- **Java JDK 17** or higher
- **Maven 3.6+**
- **Chrome/Firefox/Edge Browser** (latest version)
- **IDE** (IntelliJ IDEA / Eclipse)
- **Git** (for cloning the repository)

## 🚀 Setup

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/orangeHRMPrasenjit.git
cd orangeHRMPrasenjit
```

### 2. Install Dependencies

```bash
mvn clean install
```

### 3. Configure Test Environment

Edit `src/main/resources/config.properties`:

```properties
# Application URL
url = http://localhost/orangehrm/web/index.php/auth/login

# Browser Configuration
browser = chrome

# Credentials
username = orangehrm_hverma
password = This@test1

# Timeouts (in seconds)
implicitWait = 10
explicitWait = 30
```

## ⚙️ Configuration

### TestNG Configuration

Edit `src/test/resources/testng.xml` to control test execution:

```xml
<suite name="OrangeHRMSuite" parallel="classes" thread-count="5">
    <listeners>
        <listener class-name="com.orangehrm.listeners.TestListener"/>
    </listeners>
    <test name="OrangeHRMTest">
        <classes>
            <class name="com.orangehrm.test.LoginPageTest"/>
            <class name="com.orangehrm.test.HomePageTest"/>
        </classes>
    </test>
</suite>
```

### Headless Mode Configuration

To enable/disable headless mode, modify `BaseClass.java`:

```java
// Line 79 - Comment out to disable headless mode
options.addArguments("--headless");
```

### Parallel Execution

Adjust thread count in `testng.xml`:

```xml
<suite name="OrangeHRMSuite" parallel="classes" thread-count="5">
```

Options:
- `parallel="classes"` - Run test classes in parallel
- `parallel="methods"` - Run test methods in parallel
- `thread-count="5"` - Number of concurrent threads

## 🏃 Running Tests

### Run All Tests

```bash
mvn clean test
```

### Run Specific Test Class

```bash
mvn clean test -Dtest=LoginPageTest
```

### Run Tests with TestNG XML

```bash
mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml
```

### Debug Mode (Non-Headless)

1. Comment out headless mode in `BaseClass.java` (line 79)
2. Run tests from IDE or Maven

### CI/CD Integration

```bash
# For Jenkins/GitHub Actions
mvn clean test -Dheadless=true
```

## 📊 Test Reports

### Extent Reports

After test execution, view the HTML report:

```
target/ExtentReports/ExtentReport.html
```

Features:
- ✅ Pass/Fail status with screenshots
- ✅ Execution time and logs
- ✅ Interactive dashboard
- ✅ Test hierarchy visualization

### TestNG Reports

```
target/surefire-reports/index.html
```

### Logs

- **Application Logs:** `logs/app.log`
- **Error Logs:** `logs/error.log`

## 🎯 Best Practices Implemented

### 1. Page Object Model
Every page has a dedicated class with element locators and actions:

```java
public class LoginPage {
    private By usernameField = By.name("username");
    
    public void login(String username, String password) {
        actionDriver.enterText(usernameField, username);
        // ...
    }
}
```

### 2. ThreadSafe WebDriver
Uses ThreadLocal for parallel execution:

```java
private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
```

### 3. Explicit Waits
All actions use proper wait mechanisms:

```java
actionDriver.waitForElementVisible(element, 30);
```

### 4. Reusable Actions
Common operations abstracted in ActionDriver:

```java
actionDriver.click(element);
actionDriver.enterText(element, text);
actionDriver.isDisplayed(element);
```

### 5. Data-Driven Testing
Test data managed through DataProviders:

```java
@Test(dataProvider="validLoginData")
public void verifyValidLoginTest(String username, String password) {
    // Test implementation
}
```

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Coding Standards

- Follow Java naming conventions
- Maintain POM design pattern
- Add proper comments and documentation
- Write meaningful commit messages
- Include test coverage for new features

## 📝 Test Coverage

Current test modules:

- ✅ **Login Functionality** - Valid/Invalid login scenarios
- ✅ **Home Page** - Navigation and element verification
- ✅ **PIM Module** - Employee search functionality
- ✅ **Database Verification** - Employee data validation
- ✅ **API Testing** - REST API endpoint validation

## 🐛 Troubleshooting

### Common Issues

**Issue:** Tests not running in headless mode
- **Solution:** Ensure `--window-size=1920,1080` is set in ChromeOptions

**Issue:** Element not found errors
- **Solution:** Check explicit waits and locator strategies

**Issue:** Parallel execution failures
- **Solution:** Verify ThreadLocal WebDriver implementation

**Issue:** Screenshot not captured
- **Solution:** Check extent report configuration in TestListener

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👤 Author

**Prasenjit**

- GitHub: [@YOUR_GITHUB_USERNAME](https://https://github.com/Prasenjit-QEA)
- LinkedIn: [Your LinkedIn Profile]([https://linkedin.com/in/YOUR_PROFILE](https://www.linkedin.com/in/prasenjit-paul-b67506211))

## 🙏 Acknowledgments

- OrangeHRM for the demo application
- Selenium WebDriver community
- TestNG framework
- ExtentReports for beautiful reporting

---

⭐ **Star this repository if you find it helpful!** ⭐

For questions or support, please open an issue on GitHub.
