package org.deviartqa.blocks;

import org.deviartqa.core.Locators;
import org.deviartqa.core.SiteBlock;
import org.deviartqa.core.Widget;

public class Paginator extends SiteBlock {

    public void clickLastPage(){
        new Widget(Locators.page.locator("//ul[@class='pagination']/li[@class='last']/a")).click();
        waiting();
    }

    public void clickNextPage(){
        new Widget(Locators.page.locator("//ul[@class='pagination']/li[@class='next']/a")).click();
        waiting();
    }

    private void waiting(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
