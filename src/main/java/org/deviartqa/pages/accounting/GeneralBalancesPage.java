package org.deviartqa.pages.accounting;

import com.microsoft.playwright.Locator;
import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
import org.deviartqa.helper.TextLocalization;

public class GeneralBalancesPage extends CabinetPage {

    private final Locator transactionButton = page.locator("//a[@href='/acp/accounting/transaction']");
    private final Locator totalInfo = page.locator("//div[@class='list-group-item']/p");

    public GeneralBalancesPage(){
        pagePoint = "accounting/balances/balances";
        pageLoc = "AccountingFormTransactionCreate";
    }

    public GeneralBalancesPage readyPage() {
        checkPage(transactionButton);
        return this;
    }

    public GeneralBalancesPage open() {
        openPage("/acp/"+pagePoint);
        return this;
    }

    public Double getTotalBalance(){
        String total = new Widget(totalInfo).element.nth(0).textContent().replace(" ","");
        return Double.parseDouble(total);
    }

    public Double getTotalRequirement(){
        String total = new Widget(totalInfo).element.nth(1).textContent().replace(" ","");
        return Double.parseDouble(total);
    }

    public Double getTotalExpected(){
        String total = new Widget(totalInfo).element.nth(2).textContent().replace(" ","");
        return Double.parseDouble(total);
    }

    public Double getTotalImbalance(){
        String total = new Widget(totalInfo).element.nth(3).textContent().replace(" ","");
        return Double.parseDouble(total);
    }

    public Double getTotalToPayment(){
        String total = new Widget(totalInfo).element.nth(4).textContent().replace(" ","");
        return Double.parseDouble(total);
    }

    public GeneralBalancesPage changeRate(String rate){
        new Widget(Locators.page.locator("//input[@id='exchange_rate_input']")).fill(rate);
        new Widget(Locators.page.locator("//button[@id='exchange_rate']")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    public GeneralBalancesPage clickMakeTransferButton(){
        new Widget(Locators.page.locator("//button[@data-target='#exampleModal']")).click();
        return this;
    }

    public GeneralBalancesPage setCompanyFrom(String data){
        new Widget(Locators.page.locator("//button[@data-id='accounting_system_requisites_id_from']")).click();
        choseDrop(data);
        return this;
    }

    public GeneralBalancesPage setPaymentSystemFrom(String data){
        new Widget(Locators.page.locator("//button[@data-id='payment_system_from']")).click();
        choseDrop(data);
        return this;
    }

    public GeneralBalancesPage setPaymentSystemTo(String data){
        new Widget(Locators.page.locator("//button[@data-id='payment_system_to']")).click();
        choseDrop(data);
        return this;
    }

    public GeneralBalancesPage setAccountFrom(String data){
        new Widget(Locators.page.locator("//select[@name='accounting_system_requisites_account_id_from']")).click();
        choseDrop(data);
        return this;
    }

    public GeneralBalancesPage setCompanyTo(String data){
        new Widget(Locators.page.locator("//button[@data-id='accounting_system_requisites_id_to']")).click();
        choseDrop(data);
        return this;
    }

    public GeneralBalancesPage setAccountTo(String data){
        new Widget(Locators.page.locator("//select[@name='accounting_system_requisites_account_id_to']")).click();
        choseDrop(data);
        return this;
    }

    public GeneralBalancesPage setAmount(String data){
        super.setOriginal_amount(data);
        return this;
    }

    public GeneralBalancesPage setCommissionFrom(String type, String data){
        new Widget(page.locator("//input[@name='"+pageLoc+"[fee_from_variant]']/..//button")).click();
        new Widget(page.locator("//input[@name='"+pageLoc+"[fee_from_variant]']/..//a[@data-type='"+type+"']")).click();
        if (!type.equals("disabled")) {
            super.setFee_from(data);
        }
        return this;
    }

    public GeneralBalancesPage setCommissionTo(String type, String data){
        new Widget(page.locator("//input[@name='"+pageLoc+"[fee_to_variant]']/..//button")).click();
        new Widget(page.locator("//input[@name='"+pageLoc+"[fee_to_variant]']/..//a[@data-type='"+type+"']")).click();
        if (!type.equals("disabled")) {
            super.setFee_to(data);
        }
        return this;
    }

    public TransactionPage clickSaveTransferButton(){
        new Widget(Locators.page.locator("//button[text()='"+ TextLocalization.get("save_changes") +"']")).click();
        return new TransactionPage();
    }

    public TransactionPage clickTransferButton(){
        new Widget(Locators.page.locator("//a[contains(@href,'/acp/accounting/transaction')]")).click();
        return new TransactionPage();
    }

}
