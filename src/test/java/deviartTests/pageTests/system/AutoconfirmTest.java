package deviartTests.pageTests.system;

import deviartTests.BaseTest;
import org.deviartqa.api.LeadsAPI;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.pages.system.autoConfirm.CreateAutoconfirmPage;
import org.deviartqa.pages.system.autoConfirm.UpdateAutoconfirmPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Test
public class AutoconfirmTest extends BaseTest {

    LeadsAPI leads = new LeadsAPI();
    CreateAutoconfirmPage createAutoconfirmPage = new CreateAutoconfirmPage();
    UpdateAutoconfirmPage updateAutoconfirmPage = new UpdateAutoconfirmPage();
    String offer_id = "8122"; //8104 8122
    int user_id = 72078; //72078 25696

    @Test(invocationCount = 1)
    private void createLead() {
            leads.createLead("{\n" +
                    "\"user_id\": "+user_id+",\n" +
                    "    \"data\":{\n" +
                    "        \"offer_id\": \""+offer_id+"\",\n" +
                    "        \"name\": \""+ DataHelper.getUuid()+"\",\n" +
                    "        \"country\": \"RO\",\n" +
                    "        \"phone\": \""+new Date().getTime()+"\"\n" +
                    "        }\n" +
                    "}\n",user_id);
    }

    public void create_Auto_approval_positive() throws SQLException {
        getDB().update("delete FROM terraleads.lead_autoconfirm where offer_id="+offer_id);
        createAutoconfirmPage.open().readyPage()
                .setOffer(offer_id)
                .setWebmaster("25696")
                .setValid_approve_percent("80")
                .setRate_from("10")
                .setRate_to("50")
                .setDay_process("2")
                .clickSaveButton().readyPage();
        ResultSet res = getDB().select("SELECT x.* FROM terraleads.lead_autoconfirm x WHERE offer_id ="+offer_id);
        res.next();
        Assert.assertEquals(res.getInt("webmaster_id"),25696);
        Assert.assertEquals(res.getInt("valid_approve_percent"),80);
        Assert.assertEquals(res.getInt("rate_from"),10);
        Assert.assertEquals(res.getInt("rate_to"),50);
        Assert.assertEquals(res.getInt("day_process"),2);
    }

    public void create_Auto_approval_checkOffers() throws SQLException {
        ResultSet res = getDB().select("SELECT id FROM terraleads.offer WHERE type = 'cpa_garant'");
        createAutoconfirmPage.open().readyPage();
        while (res.next()) {
            createAutoconfirmPage.setOffer(String.valueOf(res.getInt(1)));
        }
    }

    public void create_Auto_approval_checkWebmasters() throws SQLException {
        ResultSet res = getDB().select("SELECT id FROM terraleads.users WHERE role = 'webmaster' and status = 1");
        createAutoconfirmPage.open().readyPage();
        while (res.next()) {
            createAutoconfirmPage.setWebmaster(String.valueOf(res.getInt(1)));
        }
    }

    public void create_Auto_approval_checkValid_approve_percent_nagative(){
        getDB().update("delete FROM terraleads.lead_autoconfirm where offer_id="+offer_id);
        createAutoconfirmPage.open().readyPage()
                .setOffer(offer_id)
                .setWebmaster("25696")
                .setRate_from("10")
                .setDay_process("2")
                .setRate_to("50");
        List.of("fdfsda","one","44.4","44,43","100.01","101","100,01","0","-10").forEach(x->
                {
                    createAutoconfirmPage.setValid_approve_percent(x).clickSaveButton();
                    createAutoconfirmPage.readyPage();
                }
        );
    }

    public void create_Auto_approval_checkValid_day_process_nagative(){
        getDB().update("delete FROM terraleads.lead_autoconfirm where offer_id="+offer_id);
        createAutoconfirmPage.open().readyPage()
                .setOffer(offer_id)
                .setWebmaster("25696")
                .setValid_approve_percent("80")
                .setRate_from("10")
                .setRate_to("50");
        List.of("0","-10","100").forEach(x->
                {
                    createAutoconfirmPage.setDay_process(x).clickSaveButton();
                    createAutoconfirmPage.readyPage();
                }
        );
    }

    public void create_Auto_approval_checkRate_nagative(){
        getDB().update("delete FROM terraleads.lead_autoconfirm where offer_id="+offer_id);
        List<String> cases = List.of("fdfsda","one","44.4","44,43","100.01","100,01","-10");

        createAutoconfirmPage.open().readyPage()
                .setOffer(offer_id)
                .setWebmaster("25696")
                .setValid_approve_percent("10")
                .setDay_process("2")
                .setRate_to("1000");
        cases.forEach(x->
                {
                    createAutoconfirmPage.setRate_from(x).clickSaveButton();
                    createAutoconfirmPage.readyPage();
                }
        );


        createAutoconfirmPage.open().readyPage()
                .setOffer(offer_id)
                .setWebmaster("25696")
                .setValid_approve_percent("10")
                .setDay_process("2")
                .setRate_from("1");
        cases.forEach(x->
                {
                    createAutoconfirmPage.setRate_to(x).clickSaveButton();
                    createAutoconfirmPage.readyPage();
                }
        );

        createAutoconfirmPage
                .setRate_from("51")
                .setRate_to("50")
                .setDay_process("2")
                .clickSaveButton();
        createAutoconfirmPage.readyPage();

    }

    public void update_Auto_approval_positive() throws SQLException {
        create_Auto_approval_positive();
        ResultSet id = getDB().select("SELECT id FROM terraleads.lead_autoconfirm order by id desc");
        id.next();
        updateAutoconfirmPage.open(id.getInt(1)).readyPage()
                .setValid_approve_percent("85")
                .setRate_from("100")
                .setRate_to("500")
                .setDay_process("5")
                .clickSaveButton().readyPage();
        ResultSet res = getDB().select("SELECT x.* FROM terraleads.lead_autoconfirm x WHERE offer_id ="+offer_id);
        res.next();
        Assert.assertEquals(res.getInt("webmaster_id"),25696);
        Assert.assertEquals(res.getInt("valid_approve_percent"),85);
        Assert.assertEquals(res.getInt("rate_from"),100);
        Assert.assertEquals(res.getInt("rate_to"),500);
        Assert.assertEquals(res.getInt("day_process"),5);
    }

}
