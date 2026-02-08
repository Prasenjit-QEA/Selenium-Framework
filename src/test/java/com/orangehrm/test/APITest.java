package com.orangehrm.test;

import com.orangehrm.utilities.APIUtility;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.RetryAnalyzer;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class APITest {

    @Test
    public void verifyGetUserAPI(){
        SoftAssert softAssert = new SoftAssert();
        //Step1: Define API Endpoint
        String endPoint = "https://api.genderize.io/?name=luc";
        ExtentManager.logStep("API Endpoint: "+endPoint);

        //Step2: Send GET Request
        ExtentManager.logStep("Sending GET Request to the API");
        Response response = APIUtility.sendGetRequest(endPoint);

        //Step3: validate status code
        ExtentManager.logStep("Validating API Response status code");
        boolean isStatusCodeValid = APIUtility.validateStatusCode(response, 200);

        Assert.assertTrue(isStatusCodeValid,"Status code is not as Expected");
        if(isStatusCodeValid){
            ExtentManager.logStep("Status Code Validation Passed!");
        }else {
            ExtentManager.logFailure("Status Code Validation Failed!");
        }

        //Step 4 : validate user name
        ExtentManager.logStep("Validating response body for username");
        String userName=APIUtility.getJsonValue(response,"name");
        boolean isUserNameValid="luc".equals(userName);
        softAssert.assertTrue(isUserNameValid,"Username is not valid");
        if (isStatusCodeValid){
            ExtentManager.logStep("Username Validation Passed !");
        }
        else {
            ExtentManager.logFailure("Username Validation Failed!");
        }

        //Step 5 : validate email
        ExtentManager.logStep("Validating response body for Gender");
        String email=APIUtility.getJsonValue(response,"gender");
        boolean gender="male".equals(email);
        softAssert.assertTrue(gender,"Gender is not valid");
        if (gender){
            ExtentManager.logStep("Gender Validation Passed !");
        }
        else {
            ExtentManager.logFailure("Gender Validation Failed!");
        }
        softAssert.assertAll();
    }
}
