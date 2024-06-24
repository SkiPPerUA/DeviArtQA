package org.deviartqa.core;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.deviartqa.TestScenario;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public abstract class SitePage {

    protected Page page = Session.getPage();

    protected void openPage(String url){
        page.navigate(TestScenario.getUrl()+url);
    }

    protected void checkPage(Locator locator){
        assertThat(locator).isVisible();
    }

    public void reloadPage(){
        page.reload();
    }
}
