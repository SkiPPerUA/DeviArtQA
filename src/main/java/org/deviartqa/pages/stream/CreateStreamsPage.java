package org.deviartqa.pages.stream;

import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class CreateStreamsPage extends CabinetPage {

    public CreateStreamsPage(){
        pagePoint = "stream";
        pageLoc = "AcpStream";
    }

    public CreateStreamsPage open(){
        openPage("/acp/"+pagePoint+"/new");
        return this;
    }
    public CreateStreamsPage readyPage() {
        checkPage(page.locator("//form[@action=\"/acp/"+pagePoint+"/new\"]"));
        return this;
    }

    public ViewStreamsPage clickSave(){
        clickSaveButton();
        return new ViewStreamsPage();
    }

    public CreateStreamsPage setName(String data) {
        super.setName(data);
        return this;
    }

    public CreateStreamsPage setTraffic_source(String data) {
        setType(data);
        return this;
    }

    public CreateStreamsPage setProduct(String data) {
        setAffiliate_title(data);
        return this;
    }

    public CreateStreamsPage setLandingPre_pages(String data) {
        new Widget(Locators.page.locator("//label[contains(text(),'"+data+"')]/../div[@class=\"icheckbox_minimal-grey\"]")).click();
        return this;
    }

    public CreateStreamsPage setCountry(String data) {
        super.setCountry(data);
        return this;
    }
    public CreateStreamsPage setPartner_landing_pages(String data) {
        new Widget(Locators.page.locator("//button[@data-id='status_campaing_landing']")).click();
        choseDrop(data);
        return this;
    }
    public CreateStreamsPage setURL_Postback(String data) {
        super.setURL_Postback(data);
        return this;
    }
    public CreateStreamsPage setPostbackType(String data) {
        super.setPostbackType(data);
        return this;
    }

    public CreateStreamsPage setRedirectURL(String data) {
        new Widget(Locators.page.getByTestId("AcpStream[unactive_redirect_url]")).fill(data);
        return this;
    }

    public CreateStreamsPage setParameters(String name, String data) {
        new Widget(Locators.page.getByTestId("AcpStream["+name+"]")).fill(data);
        return this;
    }

    public CreateStreamsPage setFB_pixelId(String name, String data) {
        new Widget(Locators.page.getByTestId("AcpStreamRefTag["+name+"][0][value]")).fill(data);
        return this;
    }

    public CreateStreamsPage setInformLeadsStatus(String data, boolean activate) {
        Widget box = new Widget(Locators.page
                .locator("//input[contains(@name,'"+data.toLowerCase()+"]')][@type=\"checkbox\"]/.."));
        checkBox(box,activate);
        return this;
    }
}
