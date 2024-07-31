package org.deviartqa.pages.accounting.payment;

import org.deviartqa.core.CabinetPage;

public class ViewPaymentPage extends CabinetPage {

    public ViewPaymentPage readyPage() {
        checkPage(page.locator("//a[contains(@href,'/acp/accounting/payment')][@class='btn btn-primary']"));
        return this;
    }

    public ViewPaymentPage open(int id) {
        openPage("/acp/"+pagePoint+"view?id="+id);
        return this;
    }

    public String getParametersValue(String name){
        return super.getParametersValue(name);
    }

}
