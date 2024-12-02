package org.deviartqa.pages.accounting;

import org.deviartqa.blocks.ActionButtons;
import org.deviartqa.blocks.PageData;
import org.deviartqa.blocks.Paginator;
import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class TransactionPage extends CabinetPage {

    public final Paginator paginator = new Paginator();
    public final ActionButtons actionButtons = new ActionButtons();
    public final PageData pageData = new PageData();

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
        super.setRequisite_type(data);
        return this;
    }

    public TransactionPage setDate_from(String data) {
        super.setDate_from(data);
        return this;
    }

    public TransactionPage setCompany_id(String data) {
        Widget button = new Widget(Locators.page.locator("//button[@data-id='company_id']")).click();
        choseDrop(data);
        button.click();
        return this;
    }

    public TransactionPage setUserID(String data){
        Widget select = new Widget(Locators.page.locator("//button[@data-id='user_id']")).click();
        choseDrop(data);
        select.click();
        return this;
    }

    public TransactionPage setDate_to(String data) {
        super.setDate_to(data);
        return this;
    }

    public TransactionPage setPayment_period(String data) {
        super.setPayment_period(data);
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

    public TransactionPage setPayment_status(String data){
        super.setPayment_status(data);
        return this;
    }

    public String allSumInfo(){
        return new Widget(page.locator("//div[@class='acp-content-list']//div[contains(@class,'accounting-content-toolbar')]")).textContent();
    }

    public TransactionPage clickStatusesButton(String status){
        new Widget(page.locator("//a[text()='"+status.substring(0, 1).toUpperCase() + status.substring(1)+"']")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
}
