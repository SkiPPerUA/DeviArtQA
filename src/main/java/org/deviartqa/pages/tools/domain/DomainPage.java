package org.deviartqa.pages.tools.domain;

import org.deviartqa.blocks.filters.FilterDomain;
import org.deviartqa.core.CabinetPage;

public class DomainPage extends CabinetPage {

    public DomainPage(){
        pageLoc = "AcpSysDomain";
        pagePoint = "sysDomain";
    }

    public final FilterDomain filter = new FilterDomain();
    public DomainPage open(){
        openPage("/acp/"+pagePoint);
        return this;
    }
    public DomainPage readyPage() {
        checkPage(page.locator("//a[contains(@href,'/acp/"+pagePoint+"/new')]"));
        return this;
    }

    public CreateDomainsPage clickCreateDomain(){
        clickCreate();
        return new CreateDomainsPage();
    }
}
