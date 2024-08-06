package org.deviartqa.pages.shipping;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;


public class UpdateСallCenterPage extends CabinetPage {

    public UpdateСallCenterPage(){
        pageLoc = "ShippingFormCallCenter";
    }

    public UpdateСallCenterPage readyPage() {
        checkPage(Locators.save);
        return this;
    }

    public UpdateСallCenterPage open(int id) {
        openPage("/acp/shipping/callCenter/modify?id="+id);
        return this;
    }

    public UpdateСallCenterPage setDelivery(String days){
        new Widget(page.getByTestId(pageLoc+"[send_date]")).fill(days);
        return this;
    }

    public CallCenterPage clickSaveButton(){
        super.clickSaveButton();
        return new CallCenterPage();
    }

}
