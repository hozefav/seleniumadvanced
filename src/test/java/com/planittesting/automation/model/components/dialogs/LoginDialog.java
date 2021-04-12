package com.planittesting.automation.model.components.dialogs;

import com.planittesting.automation.model.data.LoginData;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginDialog<T> {
    private WebElement rootElement;
    protected T parent;

    public LoginDialog(WebElement rootlement, T parent){
        this.rootElement = rootlement;
        this.parent = parent;

    }

    public LoginDialog<T> setUserName(String userName){
        rootElement.findElement(By.id("loginUserName")).sendKeys(userName);
        return this;
    }

    public LoginDialog<T> setPassword(String password){
        rootElement.findElement(By.id("loginPassword")).sendKeys(password);
        return this;
    }

    public LoginDialog<T> clickAccept() {
        rootElement.findElement(By.id("agree")).click();
        return this;
    }

    public T clickLogin(){
        rootElement.findElement(By.className("btn-primary")).click();
        return parent;
    }

    public LoginDialog<T> setLoginData(LoginData loginData){
        return setPassword(loginData.password())
                .setUserName(loginData.username())
                .clickAccept();

    }
    
}
