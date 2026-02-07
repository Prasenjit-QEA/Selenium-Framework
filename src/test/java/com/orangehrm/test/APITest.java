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
        String endPoint = "https://jsonplaceholder.typicode.com/user/1";
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
        String userName=APIUtility.getJsonValue(response,"username");
        boolean isUserNameValid="Bret".equals(userName);
        softAssert.assertTrue(isUserNameValid,"Username is not valid");
        if (isStatusCodeValid){
            ExtentManager.logStep("Username Validation Passed !");
        }
        else {
            ExtentManager.logFailure("Username Validation Failed!");
        }

        //Step 5 : validate email
        ExtentManager.logStep("Validating response body for username");
        String email=APIUtility.getJsonValue(response,"email");
        boolean isUseremailValid="Sincere@april.biz".equals(email);
        softAssert.assertTrue(isUseremailValid,"Email is not valid");
        if (isUseremailValid){
            ExtentManager.logStep("Email Validation Passed !");
        }
        else {
            ExtentManager.logFailure("Email Validation Failed!");
        }
        softAssert.assertAll();
    }
}
