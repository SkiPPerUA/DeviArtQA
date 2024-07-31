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
        String total = new Widget(totalInfo).element.nth(0).textContent();
        return Double.parseDouble(total);
    }

    public Double getTotalRequirement(){
        String total = new Widget(totalInfo).element.nth(1).textContent();
        return Double.parseDouble(total);
    }

    public Double getTotalExpected(){
        String total = new Widget(totalInfo).element.nth(2).textContent();
        return Double.parseDouble(total);
    }

    public Double getTotalImbalance(){
        String total = new Widget(totalInfo).element.nth(3).textContent();
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
        new Widget(Locators.page.locator("//select[@name='system_requisite_1']")).click();
        choseDrop(data);
        return this;
    }

    public GeneralBalancesPage setAccountFrom(String data){
        new Widget(Locators.page.locator("//select[@name='system_requisites_account_from_id']")).click();
        choseDrop(data);
        return this;
    }

    public GeneralBalancesPage setCompanyTo(String data){
        new Widget(Locators.page.locator("//select[@name='system_requisite_2']")).click();
        choseDrop(data);
        return this;
    }

    public GeneralBalancesPage setAccountTo(String data){
        new Widget(Locators.page.locator("//select[@name='system_requisites_account_to_id']")).click();
        choseDrop(data);
        return this;
    }

    public GeneralBalancesPage setAmount(String data){
        super.setOriginal_amount(data);
        return this;
    }

    public GeneralBalancesPage setCommission(String data){
        super.setCommission(data);
        return this;
    }

    public Transaction clickSaveTransferButton(){
        new Widget(Locators.page.locator("//button[text()='"+ TextLocalization.get("save_changes") +"']")).click();
        return new Transaction();
    }

}
