package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;
import org.testng.annotations.Test;

public class Dummy2 extends BaseClass {

    @Test
    public void dummyTest(){
//        ExtentManager.startTest("DummyTest2 test");-- This has been implemented in TestListener
        String title = getDriver().getTitle();
        ExtentManager.logStep("verifying the title");
        assert title.equals("OrangeHRM"):"Test Failed - Title is Not Matching";
        System.out.println( "Test Passed - Title is Matching");
        ExtentManager.logStep("Validation Successful");
    }
}


