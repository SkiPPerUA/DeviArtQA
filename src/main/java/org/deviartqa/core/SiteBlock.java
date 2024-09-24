package org.deviartqa.core;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public abstract class SiteBlock {

    protected Page page = Session.getPage();

    protected void checkBlock(Locator locator){
        assertThat(locator).isVisible();
    }
}
