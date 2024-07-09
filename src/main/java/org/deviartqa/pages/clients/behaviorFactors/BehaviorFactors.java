package org.deviartqa.pages.clients.behaviorFactors;

import com.microsoft.playwright.Locator;
import org.deviartqa.core.CabinetPage;

public class BehaviorFactors extends CabinetPage {

    private final Locator historyButton = page.locator("//a[contains(@href,'/acp/behaviorFactors/history')]");

    public BehaviorFactors readyPage() {
        checkPage(historyButton);
        return this;
    }

    public BehaviorFactors open() {
        openPage("/acp/behaviorFactors");
        return this;
    }
}
