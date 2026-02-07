package com.orangehrm.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExtentManager {
    private static ExtentReports extent;
    public static ThreadLocal<ExtentTest> test= new ThreadLocal<>();
    public static Map<Long, WebDriver> driverMap = new HashMap<>();

    //Initialize the Extent Report
    public static synchronized ExtentReports getReporter(){
       if (extent==null){
           String reportPath =System.getProperty("user.dir")+"/src/test/resources/ExtentReport/ExtentReport.html";
           ExtentSparkReporter spark =new ExtentSparkReporter(reportPath);
           spark.config().setReportName("Automation Test Report");
           spark.config().setDocumentTitle("OrangeHRM Report");
           spark.config().setTheme(Theme.DARK);

           extent = new ExtentReports();
           extent.attachReporter(spark);
           //Adding system information
           extent.setSystemInfo("Operating System", System.getProperty("os.name"));
           extent.setSystemInfo("Java Version",System.getProperty("java.version"));
           extent.setSystemInfo("User Name",System.getProperty("user.name"));

       }
       return extent;
    }
    //Start the Test
    public static synchronized ExtentTest startTest(String testName){
       ExtentTest extentTest = getReporter().createTest(testName);
       test.set(extentTest);
       return extentTest;
    }

    //Get Current Thread's Test
    public static synchronized ExtentTest getTest(){
       return test.get();
    }

    //Method to get the name of the current test
    public static String getTestName(){
       ExtentTest currentTest = getTest();
       if (currentTest!=null){
           return currentTest.getModel().getName();
       }
       else {
           return "No test is currently active for this thread";
       }
    }

    //Log a step
    public static void logStep(String logMessage){
        getTest().info(logMessage);
    }

    //Log a step validation with screeshot
    public static void logStepWithScreenshot(WebDriver driver, String logMessage, String Screenshotmessage){
        getTest().pass(logMessage);
        //Screenshot method
        attachScreenshot(driver,Screenshotmessage);
    }

    //Log a step validation for API
    public static void logStepValidationforAPI(String logMessage){
        getTest().pass(logMessage);
    }

    //Log a Failure
    public static void logFailure(WebDriver driver, String logMessage, String Screenshotmessage){
        String colorMessage = String.format("<span style='color:red;'>%s</span",logMessage);
        getTest().fail(colorMessage);
        //Screenshot method
        attachScreenshot(driver,Screenshotmessage);
    }
    //Log a Failure for API
    public static void logFailure(String logMessage){
        String colorMessage = String.format("<span style='color:red;'>%s</span",logMessage);
        getTest().fail(colorMessage);
    }

    //Log a skip
    public static void logSkip(String logMessage){
        String colorMessage = String.format("<span style='color:grey;'>%s</span",logMessage);
        getTest().skip(colorMessage);
    }
    public synchronized static void endTest(){
        getReporter().flush();
    }


    //Take a screenshot with date and time in the file
    public synchronized static String takeScreenshot(WebDriver driver, String screenshotName){
        TakesScreenshot ts = (TakesScreenshot)driver;
        File src=ts.getScreenshotAs(OutputType.FILE);
        //Format Date and Time for file name
        String TimeStamp=new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        //Saving the Screenshot to a file
        String destPath =System.getProperty("user.dir")+"/src/test/resources/screenshots/"+screenshotName+"_"+TimeStamp+".png";

        File finalPath = new File(destPath);
        try {
            FileUtils.copyFile(src,finalPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Convert screenshot to Base64 fir embedding in the Report
        String base64Format=convertTobase64(src);
        return base64Format;
    }

    //Convert Screenshot to base64
    public synchronized static String convertTobase64(File screenShotFile){
        String base64Format="";
        //Read the file content into a byte array
        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(screenShotFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //convert the byte to Base64 String
        return base64Format = Base64.getEncoder().encodeToString(fileContent);
    }

    //Attach screenshot to report using Base64
    public static void attachScreenshot(WebDriver driver, String message){
        try {
            String screenShotBase64 = takeScreenshot(driver, getTestName());
            getTest().info(message, com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(screenShotBase64).build());
        }catch (Exception e){
            getTest().fail("Failed to attch screenshot"+message);
        }
    }
    //Register WebDriver for current Thread
    public static void registerDriver(WebDriver driver){
        driverMap.put(Thread.currentThread().getId(),driver);
    }
}
