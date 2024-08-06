package org.deviartqa.pages.shipping;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;

public class CallCenterPage extends CabinetPage {

    public CallCenterPage readyPage() {
        checkPage(Locators.page.locator("//a[contains(@href,'/acp/shipping/callCenter/new')]"));
        return this;
    }

    public CallCenterPage open(int id) {
        openPage("/acp/shipping/callCenter");
        return this;
    }
}
