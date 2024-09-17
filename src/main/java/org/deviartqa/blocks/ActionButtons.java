package org.deviartqa.blocks;

import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public interface ActionButtons {

    String locatorActions = "//td[text()='%s']/..//a[@data-toggle='tooltip'][contains(@href,'status=%s')]";

    default void paid(int id){
        new Widget(Locators.page.locator(String.format(locatorActions,id,9))).click();
    }

    default void cancel(int id){
        new Widget(Locators.page.locator(String.format(locatorActions,id,0))).click();
    }

    default void confirm(int id){
        new Widget(Locators.page.locator(String.format(locatorActions,id,10))).click();
    }

    default void corrective(int id){
        new Widget(Locators.page.locator("//a[contains(@href,'corrective?id="+id+"')]")).click();
    }

    default void view(int id){
        new Widget(Locators.page.locator("//a[contains(@href,'view?id="+id+"')]")).click();
    }

}
