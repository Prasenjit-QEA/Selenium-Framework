package com.orangehrm.listeners;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.RetryAnalyzer;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TestListener implements ITestListener, IAnnotationTransformer {


    @Override
    //Trigger when the Test start
    public void onTestStart(ITestResult result) {
        //Initialize the extent report
        String testName = result.getMethod().getMethodName();
        //Start logging in Extent Reoorts
        ExtentManager.startTest("Test Started"+testName);
        ExtentManager.logStep("Test Started : "+testName);
//        ITestListener.super.onTestStart(result);
    }


    //Trigger when a Test succeed
    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        if(result.getTestClass().getName().toLowerCase().contains("api")) {
            ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Test Passed Successfully!", "Test End: " + testName + " - ☑\uFE0F Test Passed");
        }
        else {
            ExtentManager.logStepValidationforAPI("Test End:"+testName+" - ☑\uFE0F Test Passed");
        }
    }

    //Trigger when a Test Fails
    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String failureMessage = result.getThrowable().getMessage();
        ExtentManager.logStep(failureMessage);
        if(!result.getTestClass().getName().toLowerCase().contains("api")){
            ExtentManager.logFailure(BaseClass.getDriver(),"Test Failed!", "Test End: "+testName+" - ❌ Test Failed");
        }else {
            ExtentManager.logFailure("Test End: " + testName + " - ❌ Test Failed");
        }
    }


    //When a test skip
    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentManager.logSkip(testName+" is Skipped");
    }

    @Override
    public void onStart(ITestContext context) {
        ExtentManager.getReporter();
    }

    //Trigger when the suite ends
    @Override
    public void onFinish(ITestContext context) {
        //flush the extent Report
        ExtentManager.endTest();
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
