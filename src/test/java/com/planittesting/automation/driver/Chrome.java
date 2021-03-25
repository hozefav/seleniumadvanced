package com.planittesting.automation.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Chrome extends RemoteDriver {

    @Override
    public ChromeOptions getCapabilities() {
        var options = new ChromeOptions();
        options.setHeadless(isHeadless);
        options.addArguments("--disable-gpu",
                "--window-size=1920,1200",
                "--ignore-certificate-errors")
                .setExperimentalOption("w3c", true);
        return options;
    }

    @Override
    public WebDriver getDriver() {
        return new ChromeDriver(getCapabilities());
    }

}