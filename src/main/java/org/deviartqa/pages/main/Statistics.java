package org.deviartqa.pages.main;

import com.microsoft.playwright.Locator;
import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class Statistics extends CabinetPage {

    private final Locator showButton = page.locator("//form[@id='statistic-form']//button[@type='submit']");

    public Statistics(){
        pageLoc = "Statistic";
    }

    public Statistics readyPage() {
        checkPage(showButton);
        return this;
    }

    public Statistics open(String type){
        super.openPage("/acp/statistic/"+type);
        return this;
    }

    public Statistics setDate_from(String data) {
        new Widget(Locators.page.locator("//input[@id='date_from']")).fill(data);
        return this;
    }

    public Statistics setDate_to(String data) {
        new Widget(Locators.page.locator("//input[@id='date_to']")).fill(data);
        return this;
    }

    public Statistics setGroupBy(String data){
        super.setGroup_mode(data);
        return this;
    }

    public Statistics setTimezone(String data){
        super.setTimezone(data);
        return this;
    }

    public Statistics clickShowButton(){
        showButton.click();
        return this;
    }
}
