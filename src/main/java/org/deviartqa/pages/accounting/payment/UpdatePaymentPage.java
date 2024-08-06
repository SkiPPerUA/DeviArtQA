package org.deviartqa.pages.accounting.payment;

import org.deviartqa.core.Locators;

public class UpdatePaymentPage extends CreatePaymentPage {

    public UpdatePaymentPage readyPage() {
        checkPage(Locators.save);
        return this;
    }

    public UpdatePaymentPage open(int id) {
        openPage("/acp/accounting/payment/corrective?id="+id);
        return this;
    }

}
