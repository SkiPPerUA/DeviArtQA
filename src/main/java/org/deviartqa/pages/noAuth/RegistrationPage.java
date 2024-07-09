package org.deviartqa.pages.noAuth;

import com.microsoft.playwright.Locator;
import org.deviartqa.core.Locators;
import org.deviartqa.core.SitePage;

public class RegistrationPage extends SitePage {

    private final Locator readyLocator = Locators.page.locator("//a[@href=\"/login\"]");

    public RegistrationPage open(){
        openPage("/registration");
        return this;
    }

    public RegistrationPage readyPage() {
        checkPage(readyLocator);
        return this;
    }
}
