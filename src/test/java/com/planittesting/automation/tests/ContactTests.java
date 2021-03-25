package com.planittesting.automation.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.planittesting.automation.model.data.ContactData;
import com.planittesting.automation.model.pages.HomePage;
import com.planittesting.automation.tests.dataProviders.CsvToContactData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ContactTests extends BaseTests {

    @Test
    void validateMandatoryErrors() {
        var contactPage = open(HomePage.class)
            .clickContactMenu()
            .clickSubmitButton();
        assertEquals("Forename is required", contactPage.getForenameError());
        assertEquals("Email is required", contactPage.getEmailError());
        assertEquals("Message is required", contactPage.getMessageError());
    }

    @Test

    void validateErrorsFix() {
        var contactPage = open(HomePage.class)
        .clickContactMenu()
        .enterForename("Consultant")
        .enterEmail("Consultant@planittesting.com")
        .enterMessage("Consultant is here");

        assertEquals("", contactPage.getForenameError());
        assertEquals("", contactPage.getEmailError());
        assertEquals("", contactPage.getMessageError());
    }  

    @Test
    void validateSuccessfulSubmission() {
        var message = open(HomePage.class)
            .clickContactMenu()
            .setForename("juan")
            .setEmail("hsdgs@hdgd.com")
            .setMessage("Hello")
            .clickSubmitButton()
            .getSuccessMessage();
        assertEquals("Thanks juan, we appreciate your feedback.", message);
    }

    @ParameterizedTest
    @CsvSource({
        "juan,,a@b.com,0415415415,hello",
        "florez,teacher,c@d.com,0415415415,world"
    })
    void validateSuccessfulSubmissionDataDrivenInline(@CsvToContactData ContactData contactData) {
        var message = open(HomePage.class)
            .clickContactMenu()
            .setContactData(contactData)
            .clickSubmitButton()
            .getSuccessMessage();
        assertEquals("Thanks "+contactData.forename()+", we appreciate your feedback.", message);
    }


    

    
}
