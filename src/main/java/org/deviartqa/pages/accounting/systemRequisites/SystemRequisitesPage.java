package org.deviartqa.pages.accounting.systemRequisites;

import org.deviartqa.blocks.actionButtons.ActionButtons;
import org.deviartqa.core.CabinetPage;

public class SystemRequisitesPage extends CabinetPage {

    public ActionButtons actionButtons = new ActionButtons();

    public SystemRequisitesPage(){
        pagePoint = "accounting/systemRequisites";
        pageLoc = "AccountingFormSystemRequisites";
    }

    public SystemRequisitesPage readyPage() {
        checkPage(page.locator("//a[contains(@href,'/acp/"+pagePoint+"/new')]"));
        return this;
    }

    public SystemRequisitesPage open() {
        openPage("/acp/"+pagePoint);
        return this;
    }

    public SystemRequisitesPage setID(String data){
        super.setID(data);
        return this;
    }

    public SystemRequisitesPage setCompany_name(String data){
        super.setCompany_name(data);
        return this;
    }

    public SystemRequisitesPage setRegistration_number(String data){
        super.setRegistration_number(data);
        return this;
    }

    public SystemRequisitesPage setLegal_address(String data){
        super.setLegal_address(data);
        return this;
    }

    public SystemRequisitesPage setTax_number(String data){
        super.setTax_number(data);
        return this;
    }

    public SystemRequisitesPage clickShowResultButton(){
        super.clickShowResultButton();
        return readyPage();
    }

    public CreateSystemRequisitesPage clickCreateButton(){
        super.clickCreate();
        return new CreateSystemRequisitesPage();
    }
}
