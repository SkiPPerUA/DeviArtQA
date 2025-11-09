package org.deviartqa.pages.noAuth;

import com.microsoft.playwright.Locator;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Session;
import org.deviartqa.core.SitePage;
import org.deviartqa.core.Widget;
import org.deviartqa.pages.main.WelcomePage;
import org.deviartqa.pages.noAuth.registrationPage.RegistrationPage1;

public class AuthPage extends SitePage {

    private final Locator readyLocator = Locators.page.locator("//a[@href=\"/forgot-password\"]");

    public AuthPage open(){
        //openPage("/login");
        openPage("/acp/startup/login");
        return this;
    }

    public AuthPage readyPage() {
        if (Session.getPage().url().contains("/startup/login")) {
            checkPage(Session.getPage().locator("//a[contains(@href,'/restorePassword')]"));
        }else {
            checkPage(readyLocator);
        }
        return this;
    }

    public WelcomePage makeAuth(String login, String password){
        new Widget(Locators.password).fill(password);

        if (Session.getPage().url().contains("/startup/login")){
            new Widget(Session.getPage().locator("//input[@name='login']")).fill(login);
            new Widget(Session.getPage().locator("//button[@type='submit']")).click();
        }else {
            new Widget(Locators.email).fill(login);
            new Widget(Locators.submit).click();
        }

        return new WelcomePage();
    }

    public RegistrationPage1 clickRegistrationButton(){
        new Widget(Locators.page.locator("//a[@href=\"/registration\"]")).click();
        return new RegistrationPage1();
    }

    public ForgotPasswordPage clickForgotPasswordButton(){
        new Widget(Locators.page.locator("//a[@href=\"/forgot-password\"]")).click();
        return new ForgotPasswordPage();
    }
}
