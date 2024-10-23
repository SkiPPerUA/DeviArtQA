package org.deviartqa.pages.shipping.order;

import com.microsoft.playwright.FileChooser;
import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Session;
import org.deviartqa.core.Widget;

import java.nio.file.Paths;

public class CreateOrderPage extends CabinetPage {

    public CreateOrderPage(){
        pageLoc = "ShippingFormOrderRo";
    }

    public CreateOrderPage readyPage() {
        checkPage(Locators.save);
        return this;
    }

    public CreateOrderPage open(int country_id) {
        openPage("/acp/shipping/order/new?country_id="+country_id);
        return this;
    }

    public CreateOrderPage setFull_name(String data){
        super.setFull_name(data);
        return this;
    }

    public CreateOrderPage setPhone(String data){
        super.setPhone(data);
        return this;
    }

    public CreateOrderPage setZipCode(String data){
        super.setZipCode(data);
        return this;
    }

    public CreateOrderPage setCity(String data){
        super.setCity_id(data);
        return this;
    }

    public CreateOrderPage setStreet(String data){
        super.setStreet(data);
        return this;
    }

    public CreateOrderPage setHouse(String data){
        super.setHouse(data);
        return this;
    }

    public CreateOrderPage setSend_date(String data){
        super.setSend_date(data);
        return this;
    }

    public CreateOrderPage setType(String data){
        new Widget(Locators.page.locator("//button[@data-id='type']")).click();
        new Widget(page.locator("//ul[@role='listbox'][@aria-expanded='true']//*[text()='"+data+"']")).click();
        return this;
    }

    public OrderPage clickSaveButton(){
        super.clickSaveButton();
        return new OrderPage();
    }

    public CreateOrderPage addProduct(String group, String name_product){
        new Widget(page.locator("//div[@id='"+group+"']//span[@data-name='"+name_product+"']")).click();
        return this;
    }

    public CreateOrderPage changeProductGroup(String data){
        new Widget(page.locator("//a[@href='#"+data+"']")).click();
        return this;
    }
}
