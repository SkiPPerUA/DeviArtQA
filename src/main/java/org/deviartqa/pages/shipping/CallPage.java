package org.deviartqa.pages.shipping;

import org.deviartqa.core.CabinetPage;

public class CallPage extends CabinetPage {

    public CallPage readyPage() {
        checkPage(page.locator("//form[@id='call-form']"));
        return this;
    }

    public CallPage open(int id) {
        openPage("/acp/shipping/call");
        return this;
    }
}
