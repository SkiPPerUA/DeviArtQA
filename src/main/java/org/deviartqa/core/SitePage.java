package org.deviartqa.core;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.deviartqa.TestScenario;
import org.deviartqa.blocks.Header;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public abstract class SitePage {

    protected Page page = Session.getPage();

    protected void openPage(String url){
        page.navigate(TestScenario.getUrl()+url);
        if (!page.url().equals(TestScenario.getUrl()+url)){
            new Header().log_out();
        }
    }

    protected void checkPage(Locator locator){
        assertThat(locator).isVisible();
    }

    public void reloadPage(){
        page.reload();
    }

    protected SitePage setEmail(String data) {
        new Widget(Locators.page.getByTestId("email")).fill(data);
        return this;
    }

    protected SitePage setPassword(String data) {
        new Widget(Locators.page.getByTestId("password")).fill(data);
        return this;
    }

    protected SitePage setPassword_repeat(String data) {
        new Widget(Locators.page.getByTestId("password_repeat")).fill(data);
        return this;
    }

    protected SitePage clickAgreementCheckBox() {
        new Widget(Locators.page.locator("//*[@name=\"agreement\"]/..")).click();
        return this;
    }
}
