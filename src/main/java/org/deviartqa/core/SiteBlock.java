package org.deviartqa.core;

import com.microsoft.playwright.Locator;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public abstract class SiteBlock {

    protected void checkBlock(Locator locator){
        assertThat(locator).isVisible();
    }
}
