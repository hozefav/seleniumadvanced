package com.planittesting.automation.model.pages;

import com.planittesting.automation.model.components.dialogs.LoginDialog;
import com.planittesting.automation.model.components.dialogs.LogoutDialog;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BasePage<T> {

    protected WebDriver driver;   

    public BasePage(WebDriver driver){
        this.driver = driver;        
    }

    public ContactPage clickContactMenu(){
        driver.findElement(By.id("nav-contact")).click();
        return new ContactPage(driver);
    }

    public ShopPage clickShopMenu(){
        driver.findElement(By.id("nav-shop")).click();
        return new ShopPage(driver);
    }
    
    @SuppressWarnings("unchecked")
    public LoginDialog<T> clickLoginButton(){
        driver.findElement(By.cssSelector("#nav-login a")).click();
        return new LoginDialog<T>(driver.findElement(By.className("popup")),(T)this);

    }

    public String getUserName(){
        var elements =  driver.findElements(By.className("user"));
        return elements.isEmpty() ? "" : elements.get(0).getText();
    }

    public LogoutDialog<T> clickLogoutButton(){
        driver.findElement(By.cssSelector("#nav-logout a")).click();
        //return new LogoutDialog<T>(driver.findElement(By.cssSelector("popup.modal.hide.ng-scope.in")),(T)this);
        return new LogoutDialog<T>(driver.findElement(By.cssSelector(".popup")),(T)this);
    }


    
    
}
