package org.deviartqa.pages.accounting.payment;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
import org.deviartqa.helper.TextLocalization;
import org.deviartqa.pages.accounting.TransactionPage;

public class CreatePaymentPage extends CabinetPage {

    public CreatePaymentPage(){
        pagePoint = "accounting/payment";
        pageLoc = "AccountingFormInvoice";
    }

    public CreatePaymentPage readyPage() {
        checkPage(Locators.save);
        return this;
    }

    public CreatePaymentPage setSystem_company(String data){
        super.choseAccounting_system_requisites_id(data);
        return this;
    }

    public CreatePaymentPage open() {
        openPage("/acp/"+pagePoint+"/create");
        return this;
    }

    public CreatePaymentPage setRoutePayment(String type){
        new Widget(Locators.page.locator("//*[contains(@href,'type="+type+"')]")).click();
        return this;
    }

    public CreatePaymentPage setPayment_type(String data){
        super.setPayment_type(data);
        return this;
    }

    public CreatePaymentPage setPayment_typeId(String data){
        super.setPayment_typeId(data);
        return this;
    }

    public CreatePaymentPage setAdvertiser(String data){
        super.choseUser_id(data);
        return this;
    }

    public CreatePaymentPage setAccounting_user_requisites_id(String data){
        super.setAccounting_user_requisites_id(data);
        return this;
    }

    public CreatePaymentPage setAccounting_user_requisites_account_id(String data){
        super.setAccounting_user_requisites_account_id(data);
        return this;
    }

    public CreatePaymentPage setPayment_period(String data){
        super.setPayment_period(data);
        return this;
    }

    public CreatePaymentPage setPayment_system(String data){
        super.setPayment_system(data);
        return this;
    }

    public CreatePaymentPage setAdvertiser_requisite(String data){
        super.setAdvertiser_requisite(data);
        return this;
    }

    public CreatePaymentPage setPurpose_of_payment(String data){
        super.setPurpose_of_payment(data);
        return this;
    }

    public CreatePaymentPage setAmount(String data){
        super.setAmount(data);
        return this;
    }

    public CreatePaymentPage choseSystem_requisites_account(String data){
        super.choseAccounting_system_requisites_account_id(data);
        return this;
    }

    public CreatePaymentPage setCurrency(String data){
        super.choseCurrency_id(data);
        return this;
    }

    public CreatePaymentPage setUnit_amount(String data){
        super.setUnit_amount(data);
        return this;
    }

    public CreatePaymentPage setQuantity(String data){
        super.setQuantity(data);
        return this;
    }

    public CreatePaymentPage setBalance_settings(String data){
        super.choseBalance_settings(data);
        return this;
    }

    public ViewPaymentPage clickSaveBatton(){
        new Widget(Locators.save).click();
        return new ViewPaymentPage();
    }

    public PaymentPage clickCancelBatton(){
        new Widget(Locators.page.locator("//a[contains(@href,\"/acp/accounting/payment\")][@class=\"btn btn-primary btn-block\"]")).click();
        return new PaymentPage();
    }

    public CreatePaymentPage clickAddAdvertiser(){
        new Widget(page.locator("//button[@data-ref=\"#advid\"]")).click();
        return this;
    }
}
