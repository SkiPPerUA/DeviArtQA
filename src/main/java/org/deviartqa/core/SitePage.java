package org.deviartqa.core;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.deviartqa.TestScenario;
import org.deviartqa.pages.AuthPage;

import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public abstract class SitePage {

    protected Page page = Session.getPage();

    protected void openPage(String url){
        page.navigate(TestScenario.getUrl()+url);
        if (page.url().equals(TestScenario.getUrl()+"/")){
            Map<String, String> creeds = new Credentials().getWebCreeds();
            new AuthPage().open().readyPage()
                    .makeAuth(creeds.get("email"),creeds.get("password")).readyPage();
            Session.getContext().cookies().forEach(x -> {
                if (x.name.equals("PHPSESSID")){
                    System.out.println("Добавь куку -> "+ x.value);
                }
            });
            openPage(url);
        }
    }

    protected void checkPage(Locator locator){
        assertThat(locator).isVisible();
    }

    public void reloadPage(){
        page.reload();
    }
}
