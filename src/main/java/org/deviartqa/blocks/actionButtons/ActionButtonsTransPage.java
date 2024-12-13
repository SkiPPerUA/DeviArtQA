package org.deviartqa.blocks.actionButtons;

import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class ActionButtonsTransPage extends ActionButtons {

    private String locatorActions = "//a[@data-toggle='tooltip'][@data-id='%s'][@data-status='%s']";

    public void paidAction(int id){
        new Widget(Locators.page.locator(String.format(locatorActions,id,9))).click();
        wait(1);
    }

    public void verificationPassed(int id, String comment, boolean save) {
        new Widget(Locators.page.locator(String.format(locatorActions, id, 11))).click();
        if (save){
            new Widget(Locators.page.locator("//button[@id='changeToVerificationStatus']")).click();
        }
        wait(1);
    }

}
