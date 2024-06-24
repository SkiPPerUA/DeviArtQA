package org.deviartqa.pages;

import com.microsoft.playwright.Locator;
import org.deviartqa.core.Locators;
import org.deviartqa.core.SitePage;

public class ForgotPasswordPage extends SitePage {

    private final Locator readyLocator = Locators.page.locator("//div[@class=\"auth__wrapper\"]//a[@href=\"/login\"]");

    public ForgotPasswordPage open(){
        openPage("/forgot-password");
        return this;
    }

    public ForgotPasswordPage readyPage() {
        checkPage(readyLocator);
        return this;
    }
}
