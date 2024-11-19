package org.deviartqa.pages.analytics;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class OpexPage extends CabinetPage {

    public OpexPage(){
        pageLoc = "AcpFormOpex";
    }

    public OpexPage readyPage() {
        checkPage(page.locator("//div[@class='box-info'][2]"));
        return this;
    }

    public OpexPage open(){
        super.openPage("/acp/opex");
        return this;
    }

    public OpexPage clickSaveButton(int id){
        String atr = "";
        if (page.url().contains("boughtRate")){
            atr = "data-br-offer";
        }else{
            atr = "data-opex-advertiser";
        }
        new Widget(page.locator("//input[contains(@name,'yt')][@"+atr+"='"+id+"']")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public OpexPage setOpex(String data, int id){
        new Widget(page.locator("//input[contains(@id,'opex_"+id+"')]")).fill(data);
        return this;
    }

    public String getOpex(int id){
        return new Widget(page.locator("//input[contains(@id,'opex_"+id+"')]")).element.getAttribute("value").trim();
    }

    public OpexPage setCapa(String data, int id){
        new Widget(page.locator("//input[contains(@id,'capa_"+id+"')]")).fill(data);
        return this;
    }

    public OpexPage setCpApprove(String data, int id){
        new Widget(page.locator("//input[contains(@id,'cpaprove_"+id+"')]")).fill(data);
        return this;
    }

    public String getCpo_value(int id){
        return new Widget(page.locator("//td[@id='opex_cpo_value_"+id+"']")).textContent();
    }

    public String getCurrentCpo_value(int id){
        return new Widget(page.locator("//td[@id='opex_cpo_value_"+id+"']/../td[7]")).textContent().trim();
    }

    public OpexPage setBought_rate(String data, int id){
        new Widget(page.locator("//input[contains(@id,'bought_rate_"+id+"')]")).fill(data);
        return this;
    }

    public OpexPage clickOpexTab(){
        new Widget(page.locator("//a[contains(text(),'OPEX')]")).click();
        return this;
    }

    public OpexPage clickBoughtRateTab(){
        new Widget(page.locator("//a[contains(text(),'Bought Rate')]")).click();
        return this;
    }

    public OpexPage setAdvertiserOrOffer(String data){
        super.setAdvertiser_id(data);
        new Widget(Locators.page.locator("//button[@data-id='advertiser_id']")).click();
        return this;
    }

    public OpexPage setCountry(String data){
        super.setCountry(data);
        return this;
    }

    public OpexPage setDate_from(String data){
        super.setDate_from(data);
        return this;
    }

    public OpexPage setDate_to(String data){
        super.setDate_to(data);
        return this;
    }

    public OpexPage clickShowResultButton(){
        super.clickShowResultButton();
        return this;
    }
}
