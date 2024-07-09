package org.deviartqa.pages.clients.behaviorFactors;

import com.microsoft.playwright.Locator;
import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class AddBehaviorFactors extends CabinetPage {

    private final Locator addPropertyButton = page.getByTestId("addFieldValueBtn");

    public AddBehaviorFactors readyPage() {
        checkPage(addPropertyButton);
        return this;
    }

    public AddBehaviorFactors open(int id) {
        openPage("/acp/behaviorFactors/addPropertyValuesBatch?property_id="+id);
        return this;
    }

    public BehaviorFactors clickSaveButton(){
        super.clickSaveButton();
        return new BehaviorFactors();
    }

    public AddBehaviorFactors fillValue(String enValue, String ruValue){
        new Widget(page.locator("//input[@id='ARProductBasePropertyValueLang_1_name']")).fill(enValue);
        new Widget(page.locator("//a[contains(text(),'Русский')]")).click();
        new Widget(page.locator("//input[@id='ARProductBasePropertyValueLang_2_name']")).fill(ruValue);
        return this;
    }
}
