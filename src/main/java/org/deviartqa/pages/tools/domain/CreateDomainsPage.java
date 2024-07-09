package org.deviartqa.pages.tools.domain;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;

public class CreateDomainsPage extends CabinetPage {

    public CreateDomainsPage(){
        pageLoc = "AcpSysDomain";
        pagePoint = "sysDomain";
    }

    public CreateDomainsPage open(){
        openPage("/acp/"+pagePoint+"/new");
        return this;
    }
    public CreateDomainsPage readyPage() {
        checkPage(Locators.save);
        return this;
    }
    public CreateDomainsPage setDomain_type(String data) {
        super.setDomain_type(data);
        return this;
    }

    public CreateDomainsPage setDomain(String data) {
        super.setDomain(data);
        return this;
    }

    public CreateDomainsPage setNotation(String data) {
        super.setNotation(data);
        return this;
    }

    public CreateDomainsPage setUser_id(String data) {
        super.setUser_id(data);
        return this;
    }

    public CreateDomainsPage setProduct(String data) {
        super.setProduct(data);
        return this;
    }
    public CreateDomainsPage setModel_ids(String data) {
        super.setModel_ids(data);
        return this;
    }

    public DomainPage clickSaveButton(){
        super.clickSaveButton();
        return new DomainPage();
    }
}
