package org.deviartqa.pages.noAuth;

import org.deviartqa.core.Locators;
import org.deviartqa.core.SitePage;
import org.deviartqa.core.Widget;

public class RegistrationPage2 extends SitePage {


    public RegistrationPage2 readyPage() {
        checkPage(Locators.page.locator("//*[contains(@action,'/acp/startup/registration-additional')]"));
        return this;
    }

    public RegistrationPage2 clickSendButton(){
        new Widget(page.locator("//div[contains(@class,'auth')]/button")).click();
        return this;
    }
}
