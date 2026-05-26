package org.deviartqa.pages.shipping;

import com.microsoft.playwright.Locator;
import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class OrdersToPack extends CabinetPage {

    private final Locator getTrackerButton = page.locator("//a[@data-action='get_tracker']");

    public OrdersToPack(){
        pageLoc = "ShippingFormOrderSearchPack";
    }

    public OrdersToPack readyPage() {
        checkPage(getTrackerButton);
        return this;
    }

    public OrdersToPack open() {
        openPage("/acp/shipping/newOrders/listPack");
        return this;
    }

    public OrdersToPack setId(String data){
        super.setID(data);
        return this;
    }

    public OrdersToPack setFull_name(String data){
        super.setFull_name(data);
        return this;
    }

    public OrdersToPack setPhone(String data){
        super.setPhone(data);
        return this;
    }

    public OrdersToPack setLead(String data){
        super.setLead_id(data);
        return this;
    }

    public OrdersToPack setType(String data){
        super.setType(data);
        return this;
    }

    public OrdersToPack setDelivery_service(String data){
        super.setDelivery_service_id(data);
        return this;
    }

    public OrdersToPack setDelivery_type(String data){
        super.setDelivery_type(data);
        return this;
    }

    public OrdersToPack setTracker(String data){
        super.setTracker(data);
        return this;
    }

    public OrdersToPack setCountry(String data){
        super.setCountry(data);
        return this;
    }

    public OrdersToPack setStatus(String data){
        super.setStatus(data);
        return this;
    }

    public OrdersToPack setAdvertiser(String data){
        super.setAdvertiser_id(data);
        return this;
    }

    public OrdersToPack setDate(String date_from, String date_to){
        super.setDate_from(date_from);
        super.setDate_to(date_to);
        return this;
    }

    public OrdersToPack clickShowResultButton(){
        super.clickShowResultButton();
        return this;
    }

    public OrdersToPack choiceOrder(int order_id){
        new Widget(page.locator("//td[text()='"+order_id+"']/..//input")).click();
        return this;
    }

    public OrdersToPack setCompany(String data){
        super.setCompany_id(data);
        return this;
    }

    public OrdersToPack setProduct(String data){
        super.setProduct_base_id(data);
        return this;
    }

    public OrdersToPack changeDate(int date){
        super.changeDate(date);
        return this;
    }

    public OrdersToPack setCount_type_product(String data){
        super.setCount_type_product(data);
        return this;
    }

    public OrdersToPack setProducts_in_order(String data){
        super.setProducts_in_order(data);
        return this;
    }

    public OrdersToPack setDelivery_service_region(String data){
        super.setDelivery_service_region_id(data);
        return this;
    }

    @Override
    public OrdersToPack choseStatus(String data) {
        new Widget(Locators.page.locator("//button[@data-id='status']")).click();
        choseDrop(data);
        return this;
    }

    public OrdersToPack setPayment_type(String data){
        super.setPayment_type(data);
        return this;
    }

    public OrdersToPack setPayment_status(String data){
        super.setPayment_status(data);
        return this;
    }

    public OrdersToPack getTracker(String company, String currency) throws InterruptedException {
        new Widget(page.locator("//a[@data-action='get_tracker']")).click();
        new Widget(page.locator("//button[@data-id='get_tracker_company_account_id']")).click();
        new Widget(page.locator("//ul[@role='listbox'][@aria-expanded='true']//*[contains(text(),'"+company+"') and contains(text(),'"+currency+"')]")).click();
        new Widget(page.locator("//button[@data-action='get_tracker']")).click();
        Thread.sleep(3000);
        return this;
    }

    public OrdersToPack getProforma(String company, String currency, String store) throws InterruptedException {
        new Widget(page.locator("//a[@data-action='get_proforma']")).click();
        new Widget(page.locator("//button[@data-id='proforma_company_account_id']")).click();
        new Widget(page.locator("//ul[@role='listbox'][@aria-expanded='true']//*[contains(text(),'"+company+"') and contains(text(),'"+currency+"')]")).click();
        new Widget(page.locator("//button[@data-id='proforma_store_id']")).click();
        choseDrop(store);
        new Widget(page.locator("//a[@id='send-form-proforma']")).click();
        Thread.sleep(3000);
        return this;
    }

    public OrdersToPack prepareForShipping(boolean confirm) throws InterruptedException {
        new Widget(page.locator("//a[@data-action='prepare_for_shipping']")).click();
        if (confirm){
            new Widget(page.locator("//button[@id='btn-prepare-for-shipping']")).click();
        }else {
            new Widget(page.locator("//button[@id='btn-prepare-for-shipping']/../a")).click();
        }
        Thread.sleep(3000);
        return this;
    }

    public OrdersToPack shipped(boolean confirm) throws InterruptedException {
        new Widget(page.locator("//a[@data-action='shipped']")).click();
        if (confirm){
            new Widget(page.locator("//button[@id='btn-form-set-shipped']")).click();
        }else {
            new Widget(page.locator("//button[@id='btn-form-set-shipped']/../a")).click();
        }
        Thread.sleep(10000);
        return this;
    }
}
