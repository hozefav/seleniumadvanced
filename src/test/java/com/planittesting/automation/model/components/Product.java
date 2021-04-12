package com.planittesting.automation.model.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Product {
    private WebElement rootElement;

    public Product(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public String getTitle() {
        return rootElement.findElement(By.className("product-title")).getText();
    }

    public double getPrice() {
        return Double.parseDouble(
            rootElement.findElement(By.className("product-price")).getText().replaceAll("[^0-9\\.]+", "")
        );
    }

    public int getStars() {
        return Integer.parseInt(
            rootElement.findElement(By.className("star-level")).getText()
        );
    }
}
