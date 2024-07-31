package org.deviartqa.pages.main;

import com.microsoft.playwright.Locator;
import org.deviartqa.core.CabinetPage;

public class WelcomePage extends CabinetPage {

    private final Locator readyLocator = page.locator("//canvas[@id=\"dashboard-char\"]");

    public WelcomePage readyPage() {
        checkPage(readyLocator);
        return this;
    }

    public WelcomePage open(){
        super.openPage("/acp/dashboard/welcome");
        return this;
    }
}
