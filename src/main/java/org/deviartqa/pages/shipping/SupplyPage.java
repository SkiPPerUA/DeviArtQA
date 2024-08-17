package org.deviartqa.pages.shipping;

import com.microsoft.playwright.Locator;
import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Widget;
import org.deviartqa.helper.TextLocalization;

public class SupplyPage extends CabinetPage {


    public SupplyPage readyPage() {
        checkPage(page.locator("//*[text()='"+ TextLocalization.get("create") +"']"));
        return this;
    }

    public SupplyPage open() {
        openPage("/acp/shipping/supply");
        return this;
    }

    public ImportSupplyPage clickImportButton(){
        new Widget(page.locator("//a[contains(@href,'/acp/shipping/supply/import')]")).click();
        return new ImportSupplyPage();
    }

}
