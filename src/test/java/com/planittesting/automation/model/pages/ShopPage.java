package com.planittesting.automation.model.pages;

import java.util.function.Function;

import com.planittesting.automation.model.components.Product;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ShopPage extends BasePage<ShopPage> {

    public ShopPage(WebDriver driver) {
        super(driver);
    }
    
    public Product getProduct(Function<Product, Boolean> comparator) {
        return driver.findElements(By.className("product"))
                     .stream()
                     .map(element -> new Product(element))
                     .filter(product -> comparator.apply(product))
                     //.filter(product -> product.getTitle().equals("Stuffed Frog"))   
                     .findFirst()
                     .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
