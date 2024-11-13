package org.deviartqa.pages.shipping;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Widget;
import org.deviartqa.pages.shipping.call.CallPage;

public class ProcessingPage extends CabinetPage {

    public ProcessingPage readyPage() {
        checkPage(page.locator("//div[@class='col-sm-12']//a[contains(@class,'btn-success')]"));
        return this;
    }

    public ProcessingPage open(int id) {
        openPage("/acp/shipping/call/processing?id="+id);
        return this;
    }

    public CallPage clickUnprocessedCalls(){
        new Widget(page.locator("//div[@class='alert alert-danger']//a")).click();
        return new CallPage();
    }

    public ProcessingPage startProcessing(){
        openPage("/acp/shipping/call/searching");
        return this;
    }


}
