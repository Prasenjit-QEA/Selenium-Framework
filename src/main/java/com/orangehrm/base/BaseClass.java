package com.orangehrm.base;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.LoggerManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

public class BaseClass {
    protected static Properties properties;
    // protected static WebDriver driver;
    // private static ActionDriver actionDriver;
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();
    protected ThreadLocal<SoftAssert> softAssert = ThreadLocal.withInitial(SoftAssert::new);
    public static final Logger logger = LoggerManager.getLogger(BaseClass.class);

    // Getter method for soft assert
    public SoftAssert getSoftAssert() {
        return softAssert.get();
    }

    @BeforeMethod
    @Parameters("browser")
    public synchronized void setup(String browser) throws IOException {
        System.out.println("Setting up WebDriver for : " + getClass().getSimpleName());
        launchBrowser(browser);
        configureBrowser();
        staticWait(2);
        logger.info("WebDriver Initialized and Browser Maximized");
        logger.trace("Trace message");
        logger.error("error message");
        logger.debug("This is a debug message");
        logger.fatal("This is a fetal message");
        // Initialize the actionDriver only once
        // if(actionDriver==null){
        // actionDriver=new ActionDriver(driver);
        // logger.info("ActionDriver instance is
        // created."+Thread.currentThread().getId());
        // }
        // Initialize ActionDriver for the current Thread
        actionDriver.set(new ActionDriver(getDriver()));
        logger.info("ActionDriver initialized for thread" + Thread.currentThread().getId());
    }

    @BeforeSuite
    public void loadConfig() throws IOException {
        // Load the configuration file
        properties = new Properties();
        FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "/src/main/resources/config.properties");
        properties.load(fis);
        logger.info("config.properties file loaded");
        // Start the Extent Report
        // ExtentManager.getReporter();// This has been implemented in TestListener
    }

    private synchronized void launchBrowser(String browser) {
        boolean seleniumGrid = Boolean.parseBoolean(properties.getProperty("seleniumGrid"));
        String gridURL = properties.getProperty("gridURL");

        if (seleniumGrid) {
            try {
                if (browser.equalsIgnoreCase("chrome")) {
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080", "--disable-notifications", "--no-sandbox", "--disable-dev-shm-usage");
                    driver.set(new RemoteWebDriver(new URL(gridURL), options));
                    logger.info("Remote WebDriver instance created for Chrome on Selenium Grid.");
                } else if (browser.equalsIgnoreCase("firefox")) {
                    FirefoxOptions options = new FirefoxOptions();
                    options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080", "--disable-notifications");
                    driver.set(new RemoteWebDriver(new URL(gridURL), options));
                    logger.info("Remote WebDriver instance created for Firefox on Selenium Grid.");
                } else if (browser.equalsIgnoreCase("edge")) {
                    EdgeOptions options = new EdgeOptions();
                    // Use the new headless flag supported by Chromium-based Edge
                    options.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080", "--disable-notifications", "--no-sandbox", "--disable-dev-shm-usage");
                    driver.set(new RemoteWebDriver(new URL(gridURL), options));
                    logger.info("Remote WebDriver instance created for Edge on Selenium Grid.");
                } else {
                    throw new IllegalArgumentException("Browser not supported for Selenium Grid: " + browser);
                }
                logger.info("Remote WebDriver instance created for " + browser + " on Selenium Grid at " + gridURL);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Invalid Selenium Grid URL: " + gridURL);
            }
        } else {
            if (browser.equalsIgnoreCase("chrome")) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080", "--disable-notifications");
                driver.set(new ChromeDriver(options));
                ExtentManager.registerDriver(getDriver());
                logger.info("ChromeDriver Instance is created.");
            } else if (browser.equalsIgnoreCase("firefox")) {
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080", "--disable-notifications");
                driver.set(new FirefoxDriver(options));
                ExtentManager.registerDriver(getDriver());
                logger.info("FirefoxDriver Instance is created.");
            } else if (browser.equalsIgnoreCase("edge")) {
                EdgeOptions options = new EdgeOptions();
                // Prefer the new headless mode for Chromium-based Edge
                options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080", "--disable-notifications");
                driver.set(new EdgeDriver(options));
                ExtentManager.registerDriver(getDriver());
                logger.info("EdgeDriver Instance is created.");
            } else {
                throw new IllegalArgumentException("Browser not supported : " + browser);
            }
        }
    }

    /*
     * Configure browser settings such as implicit wait, maximize the
     * browser and navigate to the URL
     */
    private void configureBrowser() {
        // Implicit Wait
        int implicitWait = Integer.parseInt(properties.getProperty("implicitWait"));
        boolean seleniumGrid = Boolean.parseBoolean(properties.getProperty("seleniumGrid",properties.getProperty("seleniumGrid")));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        // Maximize the browser (skip in headless mode as it overrides --window-size)
        String browser = properties.getProperty("browser");
        boolean isHeadless = true; // Currently headless is always enabled
        if (!isHeadless) {
            getDriver().manage().window().maximize();
        } else {
            // In headless mode, explicitly set the window size to match --window-size
            // argument
            org.openqa.selenium.Dimension targetSize = new org.openqa.selenium.Dimension(1920, 1080);
            getDriver().manage().window().setSize(targetSize);
            logger.info("Headless mode: Window size set to 1920x1080");
        }

        // Navigate to URL
//        try {
//            getDriver().get(properties.getProperty("url"));
//        } catch (Exception e) {
//            System.out.println("Failed to Navigate to the URL " + e.getMessage());
//        }
        if(seleniumGrid){
            getDriver().get(properties.getProperty("url_grid"));
        }
        else{
            getDriver().get(properties.getProperty("url_local"));
        }

    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                getDriver().quit();
            } catch (Exception e) {
                System.out.println("unable to quit the drive " + e.getMessage());
            }
            logger.info("WebDriver instance is closed.");
            driver.remove();
            actionDriver.remove();
            // driver = null;
            // actionDriver = null;
            // ExtentManager.endTest();//implemented in TestListener class
        }
    }

    // Static wait for pause
    public void staticWait(int second) {
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(second));
    }

    public static Properties getProperties() {
        return properties;
    }

    // //Driver getter method
    // public WebDriver getDriver(){
    // return driver;
    // }
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            System.out.println("WebDriver is not initialized");
            throw new IllegalArgumentException("WebDriver is not initialized");
        }
        return driver.get();
    }

    public static ActionDriver getActionDriver() {
        if (actionDriver.get() == null) {
            System.out.println("actionDriver is not initialized");
            throw new IllegalArgumentException("actionDriver is not initialized");
        }
        return actionDriver.get();
    }

    // Driver setter method
    public void setDriver(ThreadLocal<WebDriver> driver) {
        this.driver = driver;
    }
}
