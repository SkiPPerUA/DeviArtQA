package org.deviartqa.pages.accounting.systemRequisites;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
import org.deviartqa.helper.TextLocalization;

public class ViewSystemRequisitesPage extends CabinetPage {

    public ViewSystemRequisitesPage(){
        pagePoint = "accounting/systemRequisites";
    }

    public ViewSystemRequisitesPage readyPage() {
        checkPage(Locators.page.locator("//a[contains(@href,'/"+pagePoint+"/modify')]"));
        return this;
    }

    public ViewSystemRequisitesPage open(int id) {
        openPage("/acp/accounting/systemRequisites/view?id="+id);
        return this;
    }

    public String getParametersValue(String name){
        return super.getParametersValue(name);
    }

    public CreateSystemRequisitesPage clickCreateButton(){
        super.clickCreate();
        return new CreateSystemRequisitesPage();
    }

    public UpdateSystemRequisitesPage clickModifyButton(){
        new Widget(Locators.page.locator("//a[contains(@href,'/"+pagePoint+"/modify')]")).click();
        return new UpdateSystemRequisitesPage();
    }

    public SystemRequisitesPage clickListButton(){
        new Widget(Locators.page.locator("//a[text()='"+ TextLocalization.get("list") +"']")).click();
        return new SystemRequisitesPage();
    }

}
