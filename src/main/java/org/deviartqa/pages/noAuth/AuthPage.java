package org.deviartqa.pages.noAuth;

import com.microsoft.playwright.Locator;
import org.deviartqa.core.Locators;
import org.deviartqa.core.SitePage;
import org.deviartqa.core.Widget;
import org.deviartqa.pages.main.WelcomePage;

public class AuthPage extends SitePage {

    private final Locator readyLocator = Locators.page.locator("//a[@href=\"/forgot-password\"]");

    public AuthPage open(){
        openPage("/login");
        return this;
    }

    public AuthPage readyPage() {
        checkPage(readyLocator);
        return this;
    }

    public WelcomePage makeAuth(String login, String password){
        new Widget(Locators.email).fill(login);
        new Widget(Locators.password).fill(password);
        new Widget(Locators.submit).click();
        return new WelcomePage();
    }

    public RegistrationPage clickRegistrationButton(){
        new Widget(Locators.page.locator("//a[@href=\"/registration\"]")).click();
        return new RegistrationPage();
    }

    public ForgotPasswordPage clickForgotPasswordButton(){
        new Widget(Locators.page.locator("//a[@href=\"/forgot-password\"]")).click();
        return new ForgotPasswordPage();
    }
}
