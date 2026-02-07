package com.orangehrm.test;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.SQLOutput;

public class LoginPageTest extends BaseClass {
    private LoginPage loginPage;
    private HomePage homePage;
    private ActionDriver actionDriver;

    @BeforeMethod
    public void setupPages(){
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        actionDriver = new ActionDriver(getDriver());
    }

    @Test(dataProvider="validLoginData", dataProviderClass = DataProviders.class)
    public void verifyValidLoginTest(String userName, String password){
//        ExtentManager.startTest("Valid Login Test");-- This has been implemented in TestListener
        System.out.println("Running testMethod1 on thread: "+ Thread.currentThread().getId());
        ExtentManager.logStep("Navigating to Login Page entering username and password");
        loginPage.login(userName,password);
        ExtentManager.logStep("Verifying admin tab is visible or not");
        Assert.assertTrue(homePage.isAdminTabVisible(),"Admin tab should be visible after successful login ");
        ExtentManager.logStep("Validation Successfully!");
        homePage.logout();
        ExtentManager.logStep("Logged out Successfully!");
        staticWait(2);
    }

    @Test(dataProvider="inValidLoginData", dataProviderClass = DataProviders.class)
    public void invalidloginTest(String userName, String password){
//        ExtentManager.startTest("Invalid Login Test");-- This has been implemented in TestListener
        System.out.println("Running testMethod2 on thread" + Thread.currentThread().getId());
        ExtentManager.logStep("Navigating to Login Page entering username and password");
        loginPage.login(userName,password);
        String expectedErrMes = "Invalid credentials";
        ExtentManager.logStep("Validation Successfully!");
        Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrMes),"Test failed : Invalid Error message");
    }
}
