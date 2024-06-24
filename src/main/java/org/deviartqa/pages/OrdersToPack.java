package org.deviartqa.pages;

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
        openPage("/acp/shipping/order/listPack");
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
        return readyPage();
    }

    public OrdersToPack setCompany(String data){
        super.setCompany_id(data);
        return this;
    }

    public OrdersToPack setProduct(String data){
        super.setProduct_base_id(data);
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
}
