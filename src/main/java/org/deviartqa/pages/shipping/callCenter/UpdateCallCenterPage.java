package org.deviartqa.pages.shipping.callCenter;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;


public class UpdateCallCenterPage extends CabinetPage {

    public UpdateCallCenterPage(){
        pageLoc = "ShippingFormCallCenter";
    }

    public UpdateCallCenterPage readyPage() {
        checkPage(Locators.save);
        return this;
    }

    public UpdateCallCenterPage open(int id) {
        openPage("/acp/shipping/callCenter/modify?id="+id);
        return this;
    }

    public UpdateCallCenterPage setDelivery(String days){
        new Widget(page.getByTestId(pageLoc+"[send_date]")).fill(days);
        return this;
    }

    public CallCenterPage clickSaveButton(){
        super.clickSaveButton();
        return new CallCenterPage();
    }

}
