package org.deviartqa.blocks;

import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class ActionButtons {

    private String locatorActions = "//td[text()='%s']/..//a[@data-toggle='tooltip'][contains(@href,'status=%s')]";
    public int paidStatus = 9;

    public void paidAction(int id){
        new Widget(Locators.page.locator(String.format(locatorActions,id,paidStatus))).click();
    }

    public void cancelAction(int id){
        new Widget(Locators.page.locator(String.format(locatorActions,id,0))).click();
    }

    public void confirmAction(int id){
        new Widget(Locators.page.locator(String.format(locatorActions,id,10))).click();
    }

    public void correctiveAction(int id){
        new Widget(Locators.page.locator("//a[contains(@href,'corrective?id="+id+"')]")).click();
    }

    public void viewAction(int id){
        new Widget(Locators.page.locator("//a[contains(@href,'view?id="+id+"')]")).click();
    }

    public void modifyAction(int id){
        new Widget(Locators.page.locator("//a[contains(@href,'modify?id="+id+"')]")).click();
    }

    public void historyAction(int id){
        new Widget(Locators.page.locator("//a[contains(@href,'history?id="+id+"')]")).click();
    }

    public void manualApprove(int id, float rate){
        new Widget(Locators.page.locator("//a[contains(@href,'manualApprove?id="+id+"')]")).click();
        new Widget(Locators.page.locator("//input[contains(@name,'exchange_rate')]")).fill(String.valueOf(rate));
        new Widget(Locators.page.locator("//div[@id='form-manual-approve-block']//button[@type='submit']")).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void linkToAdv(int id, String adv){
        new Widget(Locators.page.locator("//a[contains(@class,'link-advert')][@data-id='"+id+"']")).click();
        new Widget(Locators.page.locator("//button[@data-id='selected_advertiser_id']")).click();
        new Widget(Locators.page.locator(String.format("//ul[@role='listbox'][@aria-expanded='true']//*[contains(text(),'%s')]",adv))).click();
        new Widget(Locators.page.locator("//button[@id='link_selected_advertiser_id']")).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
