package com.planittesting.automation.model.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver){
        this.driver = driver;
    }

    public ContactPage clickContactMenu(){
        driver.findElement(By.id("nav-contact")).click();
        return new ContactPage(driver);
    }


    
    
}
