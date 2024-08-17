package org.deviartqa.pages.accounting;

import org.deviartqa.blocks.Paginator;
import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class TransactionPage extends CabinetPage {

    public final Paginator paginator = new Paginator();

    public TransactionPage() {
        pagePoint = "accounting/transaction";
        pageLoc = "AccountingFormTransaction";
    }

    public TransactionPage readyPage() {
        checkPage(page.locator("//div[@class='acp-content-list']"));
        return this;
    }

    public TransactionPage open() {
        openPage("/acp/" + pagePoint);
        return this;
    }

    public TransactionPage setID(String data){
        super.setID(data);
        return this;
    }

    public TransactionPage setPaymentType(String data){
        new Widget(Locators.page.locator("//button[@data-id='payment_type']")).click();
        choseDrop(data);
        return this;
    }

    public TransactionPage setRequisiteType(String data){
        new Widget(Locators.page.locator("//button[@data-id='manager_id']")).click();
        choseDrop(data);
        return this;
    }

    public TransactionPage setDate_from(String data) {
        super.setDate_from(data);
        return this;
    }

    public TransactionPage setCompany_id(String data) {
        new Widget(Locators.page.locator("//button[@data-id='company_id']")).click();
        choseDrop(data);
        return this;
    }

    public TransactionPage setUserID(String data){
        new Widget(Locators.page.locator("//button[@data-id='user_id']")).click();
        choseDrop(data);
        return this;
    }

    public TransactionPage setDate_to(String data) {
        super.setDate_to(data);
        return this;
    }

    public TransactionPage clickYesterdayPaymentsButton(){
        new Widget(page.locator("//button[@id='yesterday']")).click();
        return this;
    }

    public TransactionPage clickShowResultButton(){
        super.clickShowResultButton();
        return this;
    }

    public TransactionPage clickAwaitingButton(){
        new Widget(page.locator("//button[@id='await-checkbox']")).click();
        return this;
    }
}
