package org.deviartqa.pages.shipping;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;

public class UpdateOrderPage extends CabinetPage {

    public UpdateOrderPage(){
        pageLoc = "ShippingFormOrderIt";
    }

    public UpdateOrderPage readyPage() {
        checkPage(Locators.save);
        return this;
    }

    public UpdateOrderPage open(int id) {
        openPage("/acp/shipping/order/modify?id="+id);
        return this;
    }

    public UpdateOrderPage setExtra_price(String data){
        super.setExtra_price(data);
        return this;
    }

    public UpdateOrderPage setSend_date(String data){
        super.setSend_date(data);
        return this;
    }

    public UpdateOrderPage clickSaveButton(){
        super.clickSaveButton();
        return this;
    }
}
