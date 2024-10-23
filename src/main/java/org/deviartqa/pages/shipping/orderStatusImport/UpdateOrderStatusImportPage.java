package org.deviartqa.pages.shipping.orderStatusImport;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class UpdateOrderStatusImportPage extends CabinetPage {

    public UpdateOrderStatusImportPage readyPage() {
        checkPage(Locators.cancel);
        return this;
    }

    public UpdateOrderStatusImportPage open(int id) {
        openPage("/acp/shipping/orderStatusImport/completion?id="+id);
        return this;
    }

    public UpdateOrderStatusImportPage confirm(String name){
        new Widget(page.locator("//strong[contains(text(),'"+name+"')]/../..//a[text()='Confirm']")).click();
        return this;
    }

    public UpdateOrderStatusImportPage confirm(int nth){
        new Widget(page.locator("//a[text()='Confirm']")).element.nth(nth).click();
        return this;
    }
}
