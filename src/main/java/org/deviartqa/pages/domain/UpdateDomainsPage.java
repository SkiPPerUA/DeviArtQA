package org.deviartqa.pages.domain;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;

public class UpdateDomainsPage extends CabinetPage {

    public UpdateDomainsPage(){
        pageLoc = "AcpSysDomain";
        pagePoint = "sysDomain";
    }
    public UpdateDomainsPage open(int id){
        openPage("/acp/sysDomain/modify?id="+id);
        return this;
    }
    public UpdateDomainsPage open(){
        throw new RuntimeException("Необходимо указать ID домена");
    }
    public UpdateDomainsPage readyPage() {
        checkPage(Locators.save);
        return this;
    }

    public UpdateDomainsPage choseStatus(String data) {
        super.choseStatus(data);
        return this;
    }
    public UpdateDomainsPage setDomain_type(String data) {
        super.setDomain_type(data);
        return this;
    }

    public UpdateDomainsPage setDomain(String data) {
        super.setDomain(data);
        return this;
    }

    public UpdateDomainsPage setNotation(String data) {
        super.setNotation(data);
        return this;
    }

    public UpdateDomainsPage setUser_id(String data) {
        super.setUser_id(data);
        return this;
    }

    public UpdateDomainsPage setProduct(String data) {
        super.setProduct(data);
        return this;
    }
    public UpdateDomainsPage setModel_ids(String data) {
        super.setModel_ids(data);
        return this;
    }

    public DomainPage clickSaveButton(){
        super.clickSaveButton();
        return new DomainPage();
    }
}
