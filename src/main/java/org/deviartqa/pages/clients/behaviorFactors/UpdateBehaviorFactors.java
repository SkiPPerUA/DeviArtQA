package org.deviartqa.pages.clients.behaviorFactors;

import com.microsoft.playwright.Locator;
import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class UpdateBehaviorFactors extends CabinetPage {

    public UpdateBehaviorFactors readyPage() {
        checkPage(Locators.save);
        return this;
    }

    public UpdateBehaviorFactors open(int id) {
        openPage("/acp/behaviorFactors/updatePropertyValuesBatch?property_id="+id);
        return this;
    }

    public BehaviorFactors clickSaveButton(){
        super.clickSaveButton();
        return new BehaviorFactors();
    }

    public UpdateBehaviorFactors fillValue(int index, String enValue, String ruValue){
        new Widget(page.locator("//input[contains(@name,'ARProductBasePropertyValueLang[1]')]")).element.nth(index).fill(enValue);
        new Widget(page.locator("//a[contains(text(),'Русский')]")).click();
        new Widget(page.locator("//input[contains(@name,'ARProductBasePropertyValueLang[2]')]")).element.nth(index).fill(ruValue);
        return this;
    }
}
