package org.deviartqa.pages.main.offer;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Widget;

public class OffersPage extends CabinetPage {

    private final Locator newOfferButton = page.locator("//a[contains(@href,'/offer/new')]");

    public OffersPage(){
        pageLoc = "Offer";
    }

    public OffersPage readyPage() {
        checkPage(newOfferButton);
        return this;
    }

    public OffersPage open(){
        super.openPage("/acp/offer");
        return this;
    }

    public OffersPage choiceOffer(int offer_id){
        new Widget(page.locator("//td[text()='"+offer_id+"']/..//input")).click();
        return this;
    }

    public OffersPage clickGroupActionButton(){
        new Widget(page.locator("//span[@id='group_action']")).click();
        return this;
    }

    public OffersPage choiceGroupAction(String action){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(1111111);

        new Widget(page.locator("//span[@id='group_action']//a[text()='"+action+"']")).element.scrollIntoViewIfNeeded();
        new Widget(page.locator("//span[@id='group_action']//a[text()='"+action+"']")).element.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(22222222);
        page.onDialog(dialog -> dialog.accept("dd"));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        return this;
    }

}
