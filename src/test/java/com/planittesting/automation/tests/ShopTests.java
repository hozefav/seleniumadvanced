package com.planittesting.automation.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.planittesting.automation.model.pages.HomePage;
import org.junit.jupiter.api.Test;

class ShopTests extends BaseTests {

    @Test
    void validateProductPrice() {
        var price = open(HomePage.class)
            .clickShopMenu()
            .getProduct(p -> p.getTitle().equals("Stuffed Frog"))
            .getPrice();
        assertEquals(10.99, price);
    }

    @Test
    void validateProductTitle() {
        var title = open(HomePage.class)
            .clickShopMenu()
            .getProduct(p -> p.getPrice() == 13.99)
            .getTitle();
        assertEquals("Smiley Bear", title);
    }

    @Test
    void validateRatingForProduct() {
        var rating = open(HomePage.class)
            .clickShopMenu()
            .getProduct(p -> p.getTitle().equals("Funny Cow"))
            .getStars();
        assertEquals(0,rating);
    }
    
}
