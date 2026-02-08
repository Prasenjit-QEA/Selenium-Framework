package com.orangehrm.pages;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BaseClass {
    private ActionDriver actionDriver;

    // Define locator using By class
    private By adminTab = By.cssSelector("span.oxd-main-menu-item--name");
    private By useIDButton = By.className("oxd-userdropdown-name");
    private By logoutButton = By.xpath("//a[text()='Logout']");
    private By orangeHRMlogo = By.xpath("//div[@class='oxd-brand-banner']//img");
    private By pimTab = By.xpath("//span[text()='PIM']");
    private By employeeSearch = By
            .xpath("//label[text()='Employee Name']/parent::div/following-sibling::div/div/div/input");
    private By searchButton = By.xpath("//button[@type='submit']");
    private By emplFirstAndMiddleName = By.xpath("//div[@class='oxd-table-card']/div/div[3]");
    private By emplLastName = By.xpath("//div[@class='oxd-table-card']/div/div[4]");

    // Initialize the ActionDriver object by passing WebDriver instance
    public HomePage(WebDriver driver) {
        this.actionDriver = BaseClass.getActionDriver();
    }

    // Method to verify if admin tab is visible
    public boolean isAdminTabVisible() {

        return actionDriver.isDisplayed(adminTab);
    }

    // Verify Orange HRM logo
    public boolean verifyOrangeHRMlogo() {
        return actionDriver.isDisplayed(orangeHRMlogo);
    }

    // Method to Navigate to PIM tab
    public void clickOnPIMTab() {
        actionDriver.click(pimTab);
    }

    // Employee Search
    public void employeeSearch(String value) {
        actionDriver.enterText(employeeSearch, value);
        actionDriver.click(searchButton);
        actionDriver.scrollElement(emplFirstAndMiddleName);
    }

    // Verify Employee First and Middle Name
    public boolean verifyEmployeeFirstAndMiddleName(String emplFirstAndMiddleNameFromDB) {
        return actionDriver.compareText(emplFirstAndMiddleName, emplFirstAndMiddleNameFromDB);
    }

    // Verify employee Last Name
    public boolean verifyEmployeeLastName(String emplLastNameFromDB) {
        return actionDriver.compareText(emplLastName, emplLastNameFromDB);
    }

    // Method to perform logout operation
    public void logout() {
        actionDriver.click(useIDButton);
        actionDriver.click(logoutButton);
    }
}
