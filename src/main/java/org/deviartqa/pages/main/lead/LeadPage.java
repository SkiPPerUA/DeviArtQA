package org.deviartqa.pages.main.lead;

import com.microsoft.playwright.Locator;
import org.deviartqa.core.CabinetPage;

public class LeadPage extends CabinetPage {

    private final Locator showButton = page.locator("//a[contains(@href,'/acp/lead/new')]");

    public LeadPage(){
        pageLoc = "AcpLead";
    }

    public LeadPage readyPage() {
        checkPage(showButton);
        return this;
    }

    public LeadPage open(){
        super.openPage("/acp/lead");
        return this;
    }
}
