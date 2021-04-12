package com.planittesting.automation.tests;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class AfterEachTestProcessor implements AfterEachCallback {

    private WebDriver driver;

    public void setDriver(WebDriver driver){
        this.driver = driver;
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        if(context.getExecutionException().isPresent()) {
           ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            //Do something
        }
        driver.quit();
    }
    
}
