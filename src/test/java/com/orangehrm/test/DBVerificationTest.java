package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DBConnection;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

public class DBVerificationTest extends BaseClass {
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage((getDriver()));
        homePage = new HomePage((getDriver()));
    }

    @Test(dataProvider="emplVerification", dataProviderClass = DataProviders.class)
    public void verifyEmployeeNameVerificationFromDB(String emplID,String empName){

        SoftAssert softAssert = getSoftAssert();
        ExtentManager.logStep("Logging with Admin Credentials");
        loginPage.login(properties.getProperty("username"),properties.getProperty("password"));

        ExtentManager.logStep("click on PIM tab");
        homePage.clickOnPIMTab();

        ExtentManager.logStep("search for Employee");
        homePage.employeeSearch(empName);

        ExtentManager.logStep("Get the Employee Name from DB");
        String employee_id = emplID;

        //Fetch the data into a map
        Map<String,String> employeeDetails = DBConnection.getEmployeeDetails(employee_id);
        String employeeFirstName = employeeDetails.get("firstName");
        String employeeMiddleName = employeeDetails.get("middleName");
        String employeeLastName = employeeDetails.get("lastName");

        String emplFirstAndMiddleName=(employeeFirstName+employeeMiddleName).trim();

        ExtentManager.logStep("Verify the employee first and middle name");
        softAssert.assertTrue(homePage.verifyEmployeeFirstAndMiddleName(emplFirstAndMiddleName),"Fist and Middle name are not matching");
        ExtentManager.logStep("Verify the employee last name");
        softAssert.assertTrue(homePage.verifyEmployeeLastName(employeeLastName));
        ExtentManager.logStep("DB Validation Completed");
        softAssert.assertAll();
    }

}
