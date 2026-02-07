package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTest extends BaseClass {
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupPages() {

        loginPage = new LoginPage((getDriver()));
        homePage = new HomePage((getDriver()));
    }

    @Test(dataProvider="validLoginData", dataProviderClass = DataProviders.class)
    public void verifyOrangeHRMlogo(String userName, String password) {
//        ExtentManager.startTest("Validate Home Page Logo");-- This has been implemented in TestListener
        ExtentManager.logStep("Navigating to Home Page entering username and password");
        loginPage.login(userName,password);
        ExtentManager.logStep("Verifying logo is visible or not ");
        Assert.assertTrue(homePage.verifyOrangeHRMlogo(), "Logo is not visible");
        ExtentManager.logStep("Validation Successfully!");
    }
}
