package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class Dummy extends BaseClass {

    @Test
    public void dummyTest(){
//        ExtentManager.startTest("Validate Dummy page"); -- This has been implemented in TestListener
        String title = getDriver().getTitle();
        ExtentManager.logStep("Verifying the title ");
        assert title.equals("OrangeHRM"):"Test Failed - Title is Not Matching";
        System.out.println( "Test Passed - Title is Matching");
//        ExtentManager.logSkip("This case is skipped");
        throw new SkipException("Skipping the test as part of this Testing");
    }
}


