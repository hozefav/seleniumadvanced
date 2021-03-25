package com.planittesting.automation.tests;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import com.planittesting.automation.driver.AbstractDriverFactory;
import com.planittesting.automation.driver.DriverFactory;
import com.planittesting.automation.environment.EnvironmentVariables;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public abstract class BaseTests {

    protected WebDriver driver;

    @BeforeEach
    public void setup() throws ReflectiveOperationException{
        // Read configuration from environment
        var browser = EnvironmentVariables.getBrowser();
        var wait = EnvironmentVariables.getImplicitWait();
        var url = EnvironmentVariables.getUrl();
        var headless = EnvironmentVariables.isHeadless();
        
        // Create driver
        driver = new AbstractDriverFactory().withHeadless(headless).getInstance(browser);
        driver.manage().timeouts().implicitlyWait(wait,TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.navigate().to(url);
    }

    @AfterEach
    public void shutdown(){
        
    }

    protected <T> T open(Class<T> clazz)  {
        try {
            return clazz.getConstructor(WebDriver.class).newInstance(driver);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    
}
