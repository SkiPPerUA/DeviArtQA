package org.deviartqa.pages.shipping.call;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;

public class CallTransferPage extends CabinetPage {

    public CallTransferPage readyPage() {
        checkPage(Locators.save);
        return this;
    }

    public CallTransferPage open() {
        openPage("/acp/shipping/call/transfer");
        return this;
    }

    public CallTransferPage setTo_operator(String data) {
        super.setTo_operator(data);
        return this;
    }

    public CallTransferPage setFrom_operator(String data) {
        super.setFrom_operator(data);
        return this;
    }

    public CallTransferPage setCall_sequence(String data) {
        super.setCall_sequence(data);
        return this;
    }

    public CallPage clickSaveButton(){
        super.clickSaveButton();
        return new CallPage();
    }
}
