package org.deviartqa.pages.main.lead;

import com.microsoft.playwright.Locator;
import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class LeadNewPage extends CabinetPage {

    private final Locator showButton = page.locator("//input[@id='AcpLead_name']");

    public LeadNewPage(){
        pageLoc = "AcpLead";
    }

    public LeadNewPage readyPage() {
        checkPage(showButton);
        return this;
    }

    public LeadNewPage open(){
        super.openPage("/acp/lead/new");
        return this;
    }

    public LeadNewPage setName(String name){
        super.setName(name);
        return this;
    }

    public LeadNewPage setPhone(String phone){
        super.setPhone(phone);
        return this;
    }

    public LeadNewPage setOffer_id(int offerId){
        super.setOffer_id(String.valueOf(offerId));
        return this;
    }

    public LeadNewPage setUser_id(int offerId){
        new Widget(Locators.page.locator("//button[@data-id='"+pageLoc+"_user_id']")).click();
        choseDrop(String.valueOf(offerId));
        return this;
    }

    public LeadNewPage setUserAdv_id(int offerId){
        super.setUserAdv_id(String.valueOf(offerId));
        return this;
    }

    public LeadPage clickSaveButton(){
        new Widget(page.locator("//*[@id='yw0']")).click();
        return new LeadPage();
    }
}
