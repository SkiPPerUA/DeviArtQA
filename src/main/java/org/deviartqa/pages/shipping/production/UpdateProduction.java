package org.deviartqa.pages.shipping.production;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class UpdateProduction extends CabinetPage {

    public UpdateProduction readyPage() {
        checkPage(Locators.save);
        return this;
    }

    public UpdateProduction open(int storeId, int productId) {
        openPage("/acp/shipping/production/modifyCount?store_id="+storeId+"&product_base_id="+productId);
        return this;
    }

    public UpdateProduction setStatus(String data){
        new Widget(page.locator("//button[@data-id='ShippingFormProductionChangeCount_status']")).click();
        choseDrop(data);
        return this;
    }

    public Production clickSaveButton(){
        super.clickSaveButton();
        return new Production();
    }
}
