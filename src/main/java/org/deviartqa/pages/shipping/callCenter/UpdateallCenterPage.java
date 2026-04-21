package org.deviartqa.pages.shipping.callCenter;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;


public class UpdateallCenterPage extends CabinetPage {

    public UpdateallCenterPage(){
        pageLoc = "ShippingFormCallCenter";
    }

    public UpdateallCenterPage readyPage() {
        checkPage(Locators.save);
        return this;
    }

    public UpdateallCenterPage open(int id) {
        openPage("/acp/shipping/callCenter/modify?id="+id);
        return this;
    }

    public UpdateallCenterPage setDelivery(String days){
        new Widget(page.getByTestId(pageLoc+"[send_date]")).fill(days);
        return this;
    }

    public CallCenterPage clickSaveButton(){
        super.clickSaveButton();
        return new CallCenterPage();
    }

}
