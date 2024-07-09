package org.deviartqa.pages.main;

import com.microsoft.playwright.Locator;
import org.deviartqa.core.CabinetPage;

public class WelcomePage extends CabinetPage {

    private final Locator readyLocator = page.locator("//canvas[@id=\"dashboard-char\"]");

    public WelcomePage readyPage() {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        checkPage(readyLocator);
        return this;
    }
}
