package org.deviartqa.pages.noAuth.registrationPage;

import com.microsoft.playwright.Locator;
import org.deviartqa.core.Locators;
import org.deviartqa.core.SitePage;
import org.deviartqa.core.Widget;

public class RegistrationPage1 extends SitePage {

    private final Locator readyLocator = Locators.page.getByTestId("email");

    public RegistrationPage1 open(){
        openPage("/registration");
        return this;
    }

    public RegistrationPage1 readyPage() {
        checkPage(readyLocator);
        return this;
    }

    public RegistrationPage1 setEmail(String data) {
        super.setEmail(data);
        return this;
    }

    public RegistrationPage1 setPassword(String data) {
        super.setPassword(data);
        return this;
    }

    public RegistrationPage1 setPassword_repeat(String data) {
        super.setPassword_repeat(data);
        return this;
    }

    public RegistrationPage1 clickAgreementCheckBox() {
        super.clickAgreementCheckBox();
        return this;
    }

    public RegistrationPage2 clickSendButton(){
        new Widget(page.locator("//div[contains(@class,'auth')]/button")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new RegistrationPage2();
    }

    public RegistrationPage1 clickMessengerIcon(String data){
        new Widget(page.locator("//*[@data-social='#"+data+"']")).click();
        return this;
    }

    public RegistrationPage1 setMessenger(String data, String text){
        new Widget(page.getByTestId(data)).fill(text);
        return this;
    }
}
