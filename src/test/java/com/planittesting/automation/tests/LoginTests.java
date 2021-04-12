package com.planittesting.automation.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.planittesting.automation.model.data.ContactData;
import com.planittesting.automation.model.data.LoginData;
import com.planittesting.automation.model.pages.HomePage;
import com.planittesting.automation.tests.dataProviders.CsvToLoginData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class LoginTests extends BaseTests{

    @Test
    void validateSuccessfullogin(){

        var userName = open(HomePage.class).clickContactMenu()
                            .clickLoginButton()
                            .setUserName("Best")
                            .setPassword("letmein")
                            .clickAccept()
                            .clickLogin()
                            .getUserName();

        assertEquals("Best", userName);


    }
    @ParameterizedTest
    @CsvSource({"hozefa,letmein"})
    void validateSuccessfulloginDataDriven(@CsvToLoginData LoginData loginData ){

        var userName = open(HomePage.class).clickContactMenu()
                            .clickLoginButton()
                            .setLoginData(loginData)
                            .clickLogin()
                            .getUserName();

        assertEquals("hozefa", userName);


    }


    @Test
    void validateSuccessfullogout(){

        //var userName =
        var userName = open(HomePage.class).clickContactMenu()
                            .clickLoginButton()
                            .setUserName("Best")
                            .setPassword("letmein")
                            .clickAccept()
                            .clickLogin()
                            .clickLogoutButton()
                            .clickLogout()
                            .getUserName();
                            
                    

        assertEquals("", userName);


    }
    
    
    
}
