package org.deviartqa.blocks;

import org.deviartqa.TestScenario;
import org.deviartqa.core.Locators;
import org.deviartqa.core.SiteBlock;
import org.deviartqa.core.Widget;

public class Header extends SiteBlock {

    public void log_out(){
            new Widget(Locators.log_out).click();
            new Widget(Locators.page.locator("//div[@id='logout-modal']//a")).click();
    }

    public void clickSideMenuButton(){
        new Widget(Locators.page.locator("//button[contains(@class,'show-sidebar')]")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void changeUser(int userID){
        new Widget(Locators.page.locator("//li[@id='change-user-wrapper']/a")).click();
        new Widget(Locators.page.getByTestId("user_id")).fill(String.valueOf(userID));
        new Widget(Locators.page.locator("//ul[@id='change-user']//button[@type='submit']")).click();
    }
}
