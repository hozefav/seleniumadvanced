package com.planittesting.automation.model.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;

import com.planittesting.automation.model.data.ContactData;

public class ContactPage extends BasePage {

    public ContactPage(WebDriver driver) {
        super(driver);
    }

    public ContactPage clickSubmitButton(){
        driver.findElement(By.id("contact-submit-btn")).click();
        return this;
    }

    
    public String getForenameError() {
        return getErrorText(By.id("forename-err"));
    }

    public String getEmailError() {
        return getErrorText(By.id("email-err"));
    }

    public String getMessageError() {
        return getErrorText(By.id("message-err"));
    }

    private String getErrorText(By locator) {
        var errors = driver.findElements(locator);
        if(errors.isEmpty()) {
            return "";
        }
        return errors.get(0).getText();
    }

    public ContactPage setForename(String forename) {
        driver.findElement(By.id("forename")).sendKeys(forename);
        return this;
    }

    public ContactPage setSurname(String Surname) {
        driver.findElement(By.id("surname")).sendKeys(Surname);
        return this;
    }

    public ContactPage setEmail(String email) {
        driver.findElement(By.id("email")).sendKeys(email);
        return this;
    }

    public ContactPage setTelephone(String telephone) {
        driver.findElement(By.id("telephone")).sendKeys(telephone);
        return this;
    }

    public ContactPage setMessage(String message) {
        driver.findElement(By.id("message")).sendKeys(message);
        return this;
    }

    public ContactPage enterForename(String forename) {
        addText(By.id("forename"),forename);
        return this;
    }

    public ContactPage enterEmail(String email) {
        addText(By.id("email"),email);
        return this;
    }

    public ContactPage enterMessage(String message) {
        addText(By.id("message"),message);
        return this;

    }

    private void addText(By locator,String string){
        driver.findElement(locator).sendKeys(string);
    }

    public String getSuccessMessage() {
        return new WebDriverWait(driver, 60)
            .until(d -> d.findElement(By.className("alert-success")))
            .getText();
                
    }

    public void tester(){
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        numbers.add(5);
        numbers.add(9);
        numbers.add(8);
        numbers.add(1);
        numbers.forEach( (n) -> { System.out.println(n); } );
                
    }

    public ContactPage setContactData(ContactData contactData) {
        return setForename(contactData.forename())
            .setSurname(contactData.surname())
            .setEmail(contactData.email())
            .setTelephone(contactData.telephone())
            .setMessage(contactData.message());
    }





}
