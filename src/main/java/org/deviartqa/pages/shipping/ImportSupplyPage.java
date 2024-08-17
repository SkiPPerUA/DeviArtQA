package org.deviartqa.pages.shipping;

import com.microsoft.playwright.Locator;
import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Widget;

public class ImportSupplyPage extends CabinetPage {

    private Locator importButton = page.locator("//button[@id='upload-button']");

    public ImportSupplyPage readyPage() {
        checkPage(importButton);
        return this;
    }

    public ImportSupplyPage open() {
        openPage("/acp/shipping/supply/import");
        return this;
    }

}
