package org.deviartqa.pages.noAuth.registrationPage;

import org.deviartqa.core.Locators;
import org.deviartqa.core.SitePage;
import org.deviartqa.core.Widget;

public class RegistrationPage2 extends SitePage {


    public RegistrationPage2 readyPage() {
        checkPage(Locators.page.locator("//form[@id='step2'][not(@style)]"));
        return this;
    }

    public final RegistrationPage3 clickNextButton(){
        new Widget(page.locator("//form[@id='step2']//div[contains(@class,'auth')]/button")).click();
        return new RegistrationPage3();
    }

    public RegistrationPage2 choiceParameter(String data){
        new Widget(page.locator("//div[@class='auth__row']//span[text()='"+data+"']")).click();
        return this;
    }
}
