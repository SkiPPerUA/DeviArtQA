package org.deviartqa.pages.shipping.production;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Widget;

public class Production extends CabinetPage {

    public Production readyPage() {
        checkPage(page.locator("//a[contains(@href,'/acp/shipping/production/listAdvertiser')]"));
        return this;
    }

    public Production open() {
        openPage("/acp/shipping/production");
        return this;
    }

    public Production clickActiveButton(){
        new Widget(page.locator("//a[@data-status='1']")).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public Production clickDisableButton(){
        new Widget(page.locator("//a[@data-status='2']")).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public Production selectedProduct(int storeId, int productId){
        new Widget(page.locator("//tr[@data-store='"+storeId+"'][@data-product='"+productId+"']//input")).click();
        return this;
    }

    public Production setStatus(String data){
        new Widget(page.locator("//button[@data-id='ShippingProduction_status']")).click();
        choseDrop(data);
        return this;
    }
}
