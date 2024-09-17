package org.deviartqa.pages.accounting.paymentType;

import org.deviartqa.blocks.Paginator;
import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class PaymentTypePage extends CabinetPage {

    public final Paginator paginator = new Paginator();
    private final Widget addPaymentTypeButton = new Widget(Locators.page.locator("//a[@href='/acp/accounting/paymentType/AddPaymentType']"));

    public PaymentTypePage(){
            pageLoc = "ARAccountingPaymentTypes";
    }

    public PaymentTypePage readyPage() {
        checkPage(addPaymentTypeButton.element);
        return this;
    }

    public PaymentTypePage open() {
        openPage("/acp/accounting/paymentType/PaymentTypeList");
        return this;
    }

    public PaymentTypePage clickAddPaymentTypeButton(){
        addPaymentTypeButton.click();
        return this;
    }

    public PaymentTypePage clickSaveButton(){
        new Widget(Locators.save).click();
        return this;
    }

    public PaymentTypePage clickCancelButton(){
        new Widget(Locators.cancel).click();
        return this;
    }

    public PaymentTypePage setName(String data){
        super.setName(data);
        return this;
    }

    public PaymentTypePage clickSendToPayments(){
        new Widget(page.locator("//div[@class='icheckbox_minimal-grey']")).click();
        return this;
    }

    public PaymentTypePage deleteType(String nameType){
        new Widget(Locators.page.locator("//td[text()='"+nameType+"']/..//i[contains(@class,'glyphicon-remove')]")).click();
        return this;
    }

    public PaymentTypePage updateType(String nameType){
        new Widget(Locators.page.locator("//td[text()='"+nameType+"']/..//i[contains(@class,'glyphicon-pencil')]")).click();
        return this;
    }

}
