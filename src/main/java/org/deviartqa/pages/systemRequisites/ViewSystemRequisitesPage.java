package org.deviartqa.pages.systemRequisites;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class ViewSystemRequisitesPage extends CabinetPage {

    public ViewSystemRequisitesPage(){
        pagePoint = "accounting/systemRequisites";
    }

    public ViewSystemRequisitesPage readyPage() {
        checkPage(Locators.page.locator("//table[@class='table items table-hover table-condensed']"));
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
        new Widget(Locators.page.locator("//a[contains(@href,'/"+pagePoint+"/list')]")).click();
        return new SystemRequisitesPage();
    }

}
