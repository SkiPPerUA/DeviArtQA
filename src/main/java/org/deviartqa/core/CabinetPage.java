package org.deviartqa.core;

import org.deviartqa.blocks.Header;

public abstract class CabinetPage extends SitePage{
    public Header header = new Header();
    protected String pageLoc = "";
    protected String pagePoint = "";

    protected void choseDrop(String data){
        new Widget(Locators.page.locator(String.format("//ul[@role='listbox'][@aria-expanded='true']//*[contains(text(),'%s')]",data))).click();
    }

    protected void checkBox(Widget widget, boolean activate){
        String attribute = widget.getAttribute("class");
       if((attribute.contains("checked") && !activate) || (!attribute.contains("checked") && activate)){
            widget.click();
        }
    }

    protected String getParametersValue(String name){
        return new Widget(Locators.page.locator("//strong[text()='"+name+"']/../..")).textContent();
    }

    protected CabinetPage clickCreate(){
        new Widget(Locators.page.locator("//a[contains(@href,'/acp/"+pagePoint+"/new')]")).click();
        return this;
    }

    protected CabinetPage clickShowResultButton(){
        new Widget(Locators.page.locator("//button[@type='submit'][not(@name)]")).click();
        return this;
    }

    protected CabinetPage clickDelete(int id){
        new Widget(Locators.page.locator("//a[@href=\"/acp/"+pagePoint+"/delete?id="+id+"\"]")).click();
        return this;
    }

    protected CabinetPage setDomain_type(String data) {
        new Widget(Locators.page.locator("//button[@data-id='domain_type']")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage setPayment_type(String data) {
        new Widget(Locators.page.locator("//button[@data-id='payment_type']")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage setPayment_system(String data) {
        new Widget(Locators.page.locator("//button[@data-id='payment_system']")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage setPayment_status(String data) {
        new Widget(Locators.page.locator("//button[@data-id='payment_status']")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage setDomain(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[domain]")).fill(data);
        return this;
    }

    protected CabinetPage setAdvertiser_requisite(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[advertiser_requisite]")).fill(data);
        return this;
    }
    protected CabinetPage setID(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[id]")).fill(data);
        return this;
    }

    protected CabinetPage setCompany_name(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[company_name]")).fill(data);
        return this;
    }

    protected CabinetPage setLegal_address(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[legal_address]")).fill(data);
        return this;
    }

    protected CabinetPage setTax_number(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[tax_number]")).fill(data);
        return this;
    }

    protected CabinetPage setRegistration_number(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[registration_number]")).fill(data);
        return this;
    }

    protected CabinetPage setFull_name(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[full_name]")).fill(data);
        return this;
    }

    protected CabinetPage setPhone(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[phone]")).fill(data);
        return this;
    }

    protected CabinetPage setEmail(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[email]")).fill(data);
        return this;
    }

    protected CabinetPage setInvoice_number_template(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[invoice_number_template]")).fill(data);
        return this;
    }

    protected CabinetPage setInvoice_corrective_number_template(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[invoice_corrective_number_template]")).fill(data);
        return this;
    }

    protected CabinetPage setLead_id(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[lead_id]")).fill(data);
        return this;
    }

    protected CabinetPage setNotation(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[notes]")).fill(data);
        return this;
    }

    protected CabinetPage setUnit_amount(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[unit_amount]")).fill(data);
        return this;
    }

    protected CabinetPage setAmount(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[amount]")).fill(data);
        return this;
    }

    protected CabinetPage setQuantity(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[quantity]")).fill(data);
        return this;
    }

    protected CabinetPage choseUser_id(String data) {
        new Widget(Locators.page.locator("//button[@data-id=\"user_id\"]")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage choseBalance_settings(String data) {
        new Widget(Locators.page.locator("//button[@data-id=\"balance_settings\"]")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage setAccounting_invoice_id(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[accounting_invoice_id]")).fill(data);
        return this;
    }

    protected CabinetPage setUser_id(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[user_id]")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage choseAccounting_system_requisites_id(String data) {
        new Widget(Locators.page.locator("//button[@data-id=\"accounting_system_requisites_id\"]")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage choseAccounting_system_requisites_account_id(String data) {
        new Widget(Locators.page.locator("//button[@data-id=\"accounting_system_requisites_account_id\"]")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage setProduct(String data) {
        new Widget(Locators.page.locator("//button[@data-id='product_id']")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage setProduct_base_id(String data) {
        new Widget(Locators.page.locator("//button[@data-id='product_base_id']")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage setProducts_in_order(String data) {
        new Widget(Locators.page.locator("//button[@data-id='products_in_order']")).click();
        choseDrop(data);
        return this;
    }
    protected CabinetPage setModel_ids(String data) {
        new Widget(Locators.page.locator("//button[@data-id='model_ids']")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage clickSaveButton(){
        new Widget(Locators.save).click();
        return this;
    }

    protected CabinetPage choseStatus(String data) {
        new Widget(Locators.page.locator("//button[@data-id='"+pageLoc+"_status']")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage choseCurrency_id(String data) {
        new Widget(Locators.page.locator("//button[@data-id='currency_id']")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage setStatus(String data) {
        new Widget(Locators.page.locator("//button[@data-id='status']")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage setName(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[name]")).fill(data);
        return this;
    }

    protected CabinetPage setCount_type_product(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[count_type_product]")).fill(data);
        return this;
    }

    protected CabinetPage setType(String data) {
        new Widget(Locators.page.locator("//button[@data-id='type']")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage setDelivery_service_id(String data) {
        new Widget(Locators.page.locator("//button[@data-id='delivery_service_id']")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage setDelivery_type(String data) {
        new Widget(Locators.page.locator("//button[@data-id='delivery_type']")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage setDelivery_service_region_id(String data) {
        new Widget(Locators.page.locator("//button[@data-id='delivery_service_region_id']")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage setAffiliate_title(String data) {
        new Widget(Locators.page.locator("//button[@data-id='affiliate_title']")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage setCountry(String data) {
        new Widget(Locators.page.locator("//button[@data-id='country_id']")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage setCompany_id(String data) {
        new Widget(Locators.page.locator("//button[@data-id='company_id']")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage setAdvertiser_id(String data) {
        new Widget(Locators.page.locator("//button[@data-id='advertiser_id']")).click();
        choseDrop(data);
        return this;
    }

    protected CabinetPage setURL_Postback(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[postback_url]")).fill(data);
        return this;
    }

    protected CabinetPage setDate_from(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[date_from]")).fill(data);
        return this;
    }

    protected CabinetPage setDate_to(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[date_to]")).fill(data);
        return this;
    }

    protected CabinetPage setTracker(String data) {
        new Widget(Locators.page.getByTestId(pageLoc+"[tracker]")).fill(data);
        return this;
    }

    protected CabinetPage setPostbackType(String data) {
        new Widget(Locators.page.locator("//button[@data-id='"+pageLoc+"_postback_type']")).click();
        choseDrop(data);
        return this;
    }
}
