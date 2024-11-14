package org.deviartqa.pages.main.product;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class CreateProductPage extends CabinetPage {

    public CreateProductPage readyPage() {
        checkPage(Locators.save);
        return this;
    }

    public CreateProductPage open(){
        super.openPage("/acp/product/new");
        return this;
    }

    public CreateProductPage setBaseProduct(String data){
        new Widget(page.locator("//button[@data-id='AcpProduct_base_product_id']")).click();
        choseDrop(data);
        return this;
    }

    public CreateProductPage setStatus(String data){
        new Widget(page.locator("//button[@data-id='AcpProduct_status']")).click();
        choseDrop(data);
        return this;
    }

    public CreateProductPage clickSaveButton(){
        super.clickSaveButton();
        return this;
    }

    public CreateProductPage setName(String en_name, String ru_name){
        new Widget(page.locator("//input[@id='AcpProductLang_1_name']")).fill(en_name);
        new Widget(page.locator("//a[@href='#lang-2']")).click();
        new Widget(page.locator("//input[@id='AcpProductLang_2_name']")).fill(ru_name);
        return this;
    }
}
