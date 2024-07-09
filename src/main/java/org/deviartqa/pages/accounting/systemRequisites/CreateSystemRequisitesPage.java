package org.deviartqa.pages.accounting.systemRequisites;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
import org.deviartqa.helper.TextLocalization;

public class CreateSystemRequisitesPage extends CabinetPage {

    public CreateSystemRequisitesPage(){
        pageLoc = "AccountingFormSystemRequisites";
        pagePoint = "accounting/systemRequisites";
    }

    public CreateSystemRequisitesPage readyPage() {
        checkPage(Locators.save);
        return this;
    }

    public CreateSystemRequisitesPage open() {
        openPage("/acp/"+pagePoint+"/new");
        return this;
    }

    public CreateSystemRequisitesPage setCompany_name(String data){
        super.setCompany_name(data);
        return this;
    }

    public CreateSystemRequisitesPage setRegistration_number(String data){
        super.setRegistration_number(data);
        return this;
    }

    public CreateSystemRequisitesPage setLegal_address(String data){
        super.setLegal_address(data);
        return this;
    }

    public CreateSystemRequisitesPage setTax_number(String data){
        super.setTax_number(data);
        return this;
    }

    public CreateSystemRequisitesPage setEmail(String data){
        super.setEmail(data);
        return this;
    }

    public CreateSystemRequisitesPage setInvoice_number_template(String data){
        super.setInvoice_number_template(data);
        return this;
    }

    public CreateSystemRequisitesPage setInvoice_corrective_number_template(String data){
        super.setInvoice_corrective_number_template(data);
        return this;
    }

    public CreateSystemRequisitesPage clickAddAccountButton(){
        new Widget(Locators.page.locator("//a[@href='/acp/"+pagePoint+"/formAccount']")).click();
        return this;
    }

    public CreateSystemRequisitesPage setPaymentSystemId(String data){
        new Widget(Locators.page.locator("//button[@title='"+ TextLocalization.get("select_payment_type") +"']")).click();
        choseDrop(data);
        return this;
    }

    public CreateSystemRequisitesPage setCurrency(String data){
        new Widget(Locators.page.locator("//button[contains(@data-id,'currency_id')]")).click();
        choseDrop(data);
        return this;
    }

    public SystemRequisitesPage clickSaveButton(){
        new Widget(Locators.save).click();
        return new SystemRequisitesPage();
    }

    public SystemRequisitesPage clickCancelButton(){
        new Widget(Locators.page.locator("//a[contains(@href,'/acp/"+pagePoint+"/list')]")).click();
        return new SystemRequisitesPage();
    }

}
