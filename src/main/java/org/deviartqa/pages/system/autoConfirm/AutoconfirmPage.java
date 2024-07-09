package org.deviartqa.pages.system.autoConfirm;

import org.deviartqa.core.CabinetPage;

public class AutoconfirmPage extends CabinetPage {

    public AutoconfirmPage(){
        super.pagePoint = "leadAutoconfirm";
    }

    public AutoconfirmPage readyPage() {
        checkPage(page.locator("//a[contains(@href,'/acp/"+pagePoint+"/new')]"));
        return this;
    }

    public AutoconfirmPage open(){
        super.openPage("/acp/"+pagePoint);
        return this;
    }
}
