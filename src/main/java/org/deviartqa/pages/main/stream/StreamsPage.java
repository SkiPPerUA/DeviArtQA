package org.deviartqa.pages.main.stream;

import org.deviartqa.blocks.filters.FilterStreams;
import org.deviartqa.core.CabinetPage;

public class StreamsPage extends CabinetPage {

    public StreamsPage(){
        pagePoint = "stream";
    }
    public final FilterStreams filter = new FilterStreams();

    public StreamsPage open(){
        openPage("/acp/"+pagePoint);
        return this;
    }
    public StreamsPage readyPage() {
        checkPage(page.locator("//a[contains(@href,'/acp/"+pagePoint+"/new')]"));
        return this;
    }

    public CreateStreamsPage clickCreateStream(){
        clickCreate();
        return new CreateStreamsPage();
    }
}
