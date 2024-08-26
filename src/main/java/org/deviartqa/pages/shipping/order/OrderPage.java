package org.deviartqa.pages.shipping.order;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class OrderPage extends CabinetPage {

    public OrderPage readyPage() {
        checkPage(page.locator("//a[@data-action='check_status']"));
        return this;
    }

    public OrderPage open() {
        openPage("/acp/shipping/order");
        return this;
    }
}
