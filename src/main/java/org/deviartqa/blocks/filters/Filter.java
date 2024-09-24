package org.deviartqa.blocks.filters;

import org.deviartqa.core.Locators;
import org.deviartqa.core.SiteBlock;
import org.deviartqa.core.Widget;

public abstract class Filter extends SiteBlock {

    protected String typePage = "";
    private final String locatorDrop = "//ul[@role=\"listbox\"][@aria-expanded=\"true\"]//span[contains(text(),'%s')]/..";

    public Filter setId(String data){
        new Widget(Locators.page.getByTestId("Acp"+typePage+"[id]")).fill(data);
        waitAction();
        return this;
    }
    protected Filter setArchived(String data){
        String xpathArchived = "//button[@data-id='Acp"+typePage+"_archived']";
        new Widget(Locators.page.locator(xpathArchived)).click();
        new Widget(Locators.page.locator(xpathArchived+"/..//span[contains(text(),'"+data+"')]/..")).click();
        waitAction();
        return this;
    }
    protected Filter setProduct(String data){
        new Widget(Locators.page.locator("//button[@data-id='Acp"+typePage+"_affiliate_title']")).click();
        new Widget(Locators.page.locator(String.format(locatorDrop,data))).focus().click();
        waitAction();
        return this;
    }
    protected Filter setTraffic_source(String data){
        new Widget(Locators.page.locator("//button[@data-id='Acp"+typePage+"_traffic_source_id']")).click().click();
        new Widget(Locators.page.locator(String.format(locatorDrop,data))).click();
        waitAction();
        return this;
    }
    protected Filter setCode(String data){
        new Widget(Locators.page.getByTestId("Acp"+typePage+"[code]")).fill(data);
        waitAction();
        return this;
    }
    protected Filter setName(String data){
        new Widget(Locators.page.getByTestId("Acp"+typePage+"[name]")).fill(data);
        waitAction();
        return this;
    }
    protected Filter setLanding_page(String data){
        new Widget(Locators.page.getByTestId("Acp"+typePage+"[landing_domain]")).fill(data);
        waitAction();
        return this;
    }
    protected Filter setTime_added(String data){
        new Widget(Locators.page.getByTestId("Acp"+typePage+"[t_created]")).fill(data);
        waitAction();
        return this;
    }
    protected Filter setPrelanding_page(String data){
        new Widget(Locators.page.getByTestId("Acp"+typePage+"[prelanding_domain]")).fill(data);
        waitAction();
        return this;
    }
    protected Filter setStatus(String data){
        new Widget(Locators.page.locator("//button[@data-id='Acp"+typePage+"_status']")).click();
        new Widget(Locators.page.locator(String.format(locatorDrop,data))).click();
        waitAction();
        return this;
    }
    protected Filter setType(String data){
        new Widget(Locators.page.locator("//button[@data-id='Acp"+typePage+"_type']")).click();
        new Widget(Locators.page.locator(String.format(locatorDrop,data))).click();
        waitAction();
        return this;
    }
    protected Filter setDomain(String data){
        new Widget(Locators.page.getByTestId("Acp"+typePage+"[domain]")).fill(data);
        waitAction();
        return this;
    }
    protected Filter setNotes(String data){
        new Widget(Locators.page.getByTestId("Acp"+typePage+"[notes]")).fill(data);
        waitAction();
        return this;
    }
    protected Filter setUsers(String data){
        new Widget(Locators.page.getByTestId("Acp"+typePage+"[user_id]")).fill(data);
        waitAction();
        return this;
    }
    protected Filter setModel_ids(String data){
        new Widget(Locators.page.getByTestId("Acp"+typePage+"[model_ids]")).fill(data);
        waitAction();
        return this;
    }
    protected Filter sort(String parameter, Sort sort){
        Widget param = new Widget(Locators.page.locator("//a[contains(@href,'"+typePage+"_sort="+parameter+"')][contains(@class,\"sort-link\")]"));
        String attribute = param.getAttribute("class");
        if((attribute.contains("desc") && sort == Sort.DESC) || (attribute.contains("acs") && sort == Sort.ASC)){
            //Уже установено корректное значение
        }else {
            param.click();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sort(parameter,sort);
        }
        return this;
    }
    private void waitAction(){
        new Widget(Locators.page.locator("//table")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected void choseDrop(String data){
        new Widget(Locators.page.locator(String.format("//ul[@role='listbox'][@aria-expanded='true']//*[contains(text(),'%s')]",data))).click();
    }

    public enum Sort{
        ASC,DESC
    }
}
