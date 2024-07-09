package org.deviartqa.pages.system.autoConfirm;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class CreateAutoconfirmPage extends CabinetPage {

    public CreateAutoconfirmPage(){
        pagePoint = "leadAutoconfirm";
        pageLoc = "AcpLeadAutoconfirm";
    }

    public CreateAutoconfirmPage readyPage() {
        checkPage(Locators.save);
        return this;
    }

    public CreateAutoconfirmPage open(){
        super.openPage("/acp/"+pagePoint+"/new");
        return this;
    }

    public CreateAutoconfirmPage setOffer(String data){
        new Widget(Locators.page.locator("//label[@for='"+pageLoc+"_offer_id']/..//button[@data-id='type']")).click();
        choseDrop(data);
        return this;
    }

    public CreateAutoconfirmPage setWebmaster(String data){
        super.setTypeContainer(data);
        return this;
    }

    public CreateAutoconfirmPage setValid_approve_percent(String data){
        super.setValid_approve_percent(data);
        return this;
    }

    public CreateAutoconfirmPage setRate_to(String data){
        super.setRate_to(data);
        return this;
    }

    public CreateAutoconfirmPage setRate_from(String data){
        super.setRate_from(data);
        return this;
    }

    public AutoconfirmPage clickSaveButton(){
        super.clickSaveButton();
        return new AutoconfirmPage();
    }
}
