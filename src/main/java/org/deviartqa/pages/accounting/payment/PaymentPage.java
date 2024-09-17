package org.deviartqa.pages.accounting.payment;

import org.deviartqa.core.CabinetPage;

public class PaymentPage extends CabinetPage {

    public PaymentPage(){
        pagePoint = "accounting/payment";
        pageLoc = "AccountingFormPayment";
    }

    public PaymentPage readyPage() {
        checkPage(page.locator("//a[contains(@href,'/acp/"+pagePoint+"/create')]"));
        return this;
    }

    public PaymentPage open() {
        openPage("/acp/"+pagePoint);
        return this;
    }

    public PaymentPage setID(String data){
        super.setID(data);
        return this;
    }

    public PaymentPage setAdvertiser(String data){
        super.choseUser_id(data);
        return this;
    }

    public PaymentPage setPayment_type(String data){
        super.setPayment_type(data);
        return this;
    }

    public PaymentPage setSystem_requisites(String data){
        super.choseAccounting_system_requisites_id(data);
        return this;
    }

    public PaymentPage setInvoice(String data){
        super.setAccounting_invoice_id(data);
        return this;
    }

    public PaymentPage setStatus(String data){
        super.setStatus(data);
        return this;
    }

    public PaymentPage setCurrency(String data){
        super.choseCurrency_id(data);
        return this;
    }

    public PaymentPage clickShowResultButton(){
        super.clickShowResultButton();
        return readyPage();
    }

}
