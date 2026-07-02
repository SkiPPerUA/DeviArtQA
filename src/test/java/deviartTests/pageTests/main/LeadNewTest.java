package deviartTests.pageTests.main;

import deviartTests.BaseTest;
import org.deviartqa.pages.main.lead.LeadNewPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Date;

@Test(groups = "regress")
public class LeadNewTest extends BaseTest {

    LeadNewPage page;

    public void positive_addNewLead(){
        String phone = String.valueOf(new Date().getTime());
        page.open().readyPage()
                .setName("Vlad_"+phone)
                .setPhone(phone)
                .setOffer_id(region.getOffer_id())
                .setUser_id(region.getWebmaster_id())
                .clickSaveButton()
                .readyPage();

    }

    @BeforeMethod
    void start(){
        page = new LeadNewPage();
        getDB().update("update terraleads.users set status = 1 where id = "+region.getWebmaster_id());
    }
}
