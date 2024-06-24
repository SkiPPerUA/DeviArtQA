package org.deviartqa.blocks;

import org.deviartqa.TestScenario;
import org.deviartqa.core.Locators;
import org.deviartqa.core.SiteBlock;
import org.deviartqa.core.Widget;

public class Header extends SiteBlock {

    public void log_out(){
            new Widget(Locators.log_out).click();
            new Widget(Locators.page.locator("//div[@id=\"logout-modal\"]//a")).click();
    }
}
