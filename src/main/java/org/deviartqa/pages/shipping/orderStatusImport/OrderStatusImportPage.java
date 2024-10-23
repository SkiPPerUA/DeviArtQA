package org.deviartqa.pages.shipping.orderStatusImport;

import org.deviartqa.core.CabinetPage;

public class OrderStatusImportPage extends CabinetPage {

    public OrderStatusImportPage readyPage() {
        checkPage(page.locator("//a[contains(@href,'/acp/shipping/orderStatusImport/new')]"));
        return this;
    }

    public OrderStatusImportPage open() {
        openPage("/acp/shipping/orderStatusImport");
        return this;
    }
}
