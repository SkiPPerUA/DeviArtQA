package org.deviartqa.pages.shipping;

import org.deviartqa.blocks.filters.ModalWindow;
import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Session;
import org.deviartqa.core.Widget;
import org.deviartqa.pages.shipping.call.CallPage;

public class ProcessingPage extends CabinetPage {

    public ProcessingPage(){
        pageLoc = "ShippingFormCallRo";
    }

    public ProcessingPage readyPage() {
        checkPage(page.getByTestId("ShippingFormCallRo[full_name]"));
        return this;
    }

    public ProcessingPage open(int id) {
        openPage("/acp/shipping/call/processing?id="+id);
        return this;
    }

    public CallPage clickUnprocessedCalls(){
        new Widget(page.locator("//div[@class='alert alert-danger']//a")).click();
        return new CallPage();
    }

    public ProcessingPage startProcessing(){
        openPage("/acp/shipping/call/searching");
        return this;
    }

    public ModalWindow clickBusyCall(){
        new Widget(page.locator("//a[@data-modal='result-busy-stick-top']")).click();
        return new ModalWindow();
    }

    public ModalWindow setCallResult(){
        return new ModalWindow();
    }

    public String getCallInfo(CallParameters param){
        String find_param = "";
        String text;
        String x_path = "//div[contains(@class,'order-info')]//div[@class='item']";
        new Widget(Session.getPage().locator(x_path+"[3]")).element.waitFor();
        if (param == CallParameters.lead_id){
            text = new Widget(Session.getPage().locator(x_path+"[3]")).textContent();
            java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(": (\\d+)").matcher(text);
            matcher.find();
            find_param = String.valueOf(matcher.group(1));
        }else if (param == CallParameters.call_id){
            text = new Widget(Session.getPage().locator(x_path+"[1]")).textContent();
            java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(": (\\d+)").matcher(text);
            matcher.find();
            find_param = String.valueOf(matcher.group(1));
        }

        return find_param;
    }

    public ProcessingPage setSendTime(String data){
        Widget send_data = new Widget(page.getByTestId("ShippingFormCallRo[send_date]"));
        send_data.element.evaluate("(el, value) => el.setAttribute('value', value)", data);
        return this;
    }

    public ProcessingPage setZipcode(String fillSearsh, String selectCode){
        new Widget(page.locator("//span[contains(@id,'zipcode')]")).click();
        new Widget(page.locator("//input[@type='search']")).fill(fillSearsh);
        new Widget(page.locator("//li[contains(text(),'"+selectCode+"')]")).click();
        return this;
    }

    public ProcessingPage setZipcode(String fillSearsh, int selectCode){
        new Widget(page.locator("//span[contains(@id,'zipcode')]")).click();
        new Widget(page.locator("//input[@type='search']")).fill(fillSearsh);
        new Widget(page.locator("//ul[contains(@id,'zipcode')]/li["+selectCode+"]")).click();
        return this;
    }

    public ProcessingPage setAge(String selectAge){
        new Widget(page.locator("//select[@name='"+pageLoc+"[age]']/../button")).click();
        new Widget(page.locator("//option[@value=\""+selectAge+"\"]")).click();
        return this;
    }

    public ProcessingPage setAge(int selectAge){
        new Widget(page.locator("//select[@name='"+pageLoc+"[age]']/../button")).click();
        new Widget(page.locator("//select[@name='"+pageLoc+"[age]']/..//li["+selectAge+"]")).click();
        return this;
    }

    public ProcessingPage setSex(boolean female){
        new Widget(page.locator("//select[@name='"+pageLoc+"[sex]']/../button")).click();
        if (female){
            new Widget(page.locator("//select[@name='"+pageLoc+"[sex]']/..//li[2]")).click();
        }else {
            new Widget(page.locator("//select[@name='" + pageLoc + "[sex]']/..//li[3]")).click();
        }
        return this;
    }

    public ProcessingPage setStreet(String data){
        super.setStreet(data);
        return this;
    }

    public ProcessingPage setHouse(String data){
        super.setHouse(data);
        return this;
    }

    public ProcessingPage setComment_order(String data){
        super.setComment_order(data);
        return this;
    }

    public enum CallParameters {call_id,lead_id,country}

}
