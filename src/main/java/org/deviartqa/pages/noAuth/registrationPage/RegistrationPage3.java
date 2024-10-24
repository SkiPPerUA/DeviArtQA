package org.deviartqa.pages.noAuth.registrationPage;

import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class RegistrationPage3 extends RegistrationPage2 {


    public RegistrationPage3 readyPage() {
        checkPage(Locators.page.locator("//form[@id='step3'][@style='']"));
        return this;
    }

    public RegistrationPage3 clickSendButton(){
        new Widget(page.locator("//form[@id='step3']//div[contains(@class,'auth')]/button")).click();
        return this;
    }

    public RegistrationPage3 choiceParameter(String data){
        super.choiceParameter(data);
        return this;
    }
}
