package org.deviartqa.pages.shipping.call;

import org.deviartqa.core.CabinetPage;

public class CallPage extends CabinetPage {

    public CallPage readyPage() {
        checkPage(page.locator("//a[contains(@href,'/acp/shipping/call/transfer')]"));
        return this;
    }

    public CallPage open() {
        openPage("/acp/shipping/call");
        return this;
    }
}
