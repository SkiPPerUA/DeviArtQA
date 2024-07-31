package org.deviartqa.pages.accounting;

import org.deviartqa.core.CabinetPage;

public class Transaction extends CabinetPage {

    public Transaction() {
        pagePoint = "accounting/transaction";
    }

    public Transaction readyPage() {
        checkPage(page.locator("//div[@class='acp-content-list']"));
        return this;
    }

    public Transaction open() {
        openPage("/acp/" + pagePoint);
        return this;
    }
}
