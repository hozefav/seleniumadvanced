package com.planittesting.automation.model.components.dialogs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LogoutDialog<T> {
    private WebElement rootElement;
    protected T parent;

    public LogoutDialog(WebElement rootlement, T parent){
        this.rootElement = rootlement;
        this.parent = parent;

    }

    public T clickLogout(){
        rootElement.findElement(By.className("btn-success")).click();
        return parent;
    }
    
}
