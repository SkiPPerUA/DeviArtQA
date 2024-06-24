package org.deviartqa.pages.stream;

import com.microsoft.playwright.Locator;
import org.deviartqa.core.CabinetPage;

public class ViewStreamsPage extends CabinetPage {

    private final Locator readyLocator = page.locator("//div[@class=\"box-info\"]");

    public ViewStreamsPage open(int id){
        openPage("/acp/stream/view?id="+id);
        return this;
    }
    public ViewStreamsPage readyPage() {
        checkPage(readyLocator);
        return this;
    }

    public String getParametersValue(String name){
        return super.getParametersValue(name);
    }
}
