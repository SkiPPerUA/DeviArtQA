package org.deviartqa.core;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public abstract class SiteBlock {

    protected Page page = Session.getPage();
    protected String pageLoc;

    protected SiteBlock checkBlock(Locator locator){
        assertThat(locator).isVisible();
        return this;
    }

    protected SiteBlock clickStatus_operator_processing() {
        new Widget(page.locator("//input[contains(@name,'status_operator_processing')]/..")).click();
        return this;
    }

    protected SiteBlock clickSaveButton() {
        new Widget(page.locator("//button[@id='yw1']")).click();
        return this;
    }

}
