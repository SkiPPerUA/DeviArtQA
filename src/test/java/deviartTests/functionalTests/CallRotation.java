package deviartTests.functionalTests;

import deviartTests.BaseTest;
import org.deviartqa.TestScenario;
import org.deviartqa.api.main.LeadsAPI;
import org.deviartqa.core.Session;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.pages.main.WelcomePage;
import org.deviartqa.pages.shipping.ProcessingPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Test
public class CallRotation extends BaseTest {

    LeadsAPI leads = new LeadsAPI();
    ProcessingPage processingPage = new ProcessingPage();
    WelcomePage welcomePage = new WelcomePage();
    ResultSet res;


    private void makeCall() throws InterruptedException {
        leads.createLead("{\n" +
                "\"user_id\": "+ TestScenario.userId+",\n" +
                "    \"data\":{\n" +
                "        \"offer_id\": \"8104\",\n" +
                "        \"name\": \"callRotation "+ DataHelper.getUuid()+"\",\n" +
                "        \"country\": \"IT\",\n" +
                "        \"phone\": \""+new Date().getTime()+"\"\n" +
                "        }\n" +
                "}\n");
        //wait when call will create
        Thread.sleep(5000);
    }

    public void positive_autoCallQueue_cpaGarant() throws SQLException, InterruptedException {
        //change status old calls
        getDB().update("UPDATE terraleads_shipping.`call` SET status = 3, `type` = 1 WHERE status = 2 and call_center_id = 2");

        makeCall();
        getDB().update("UPDATE terraleads_shipping.`call` SET offer_type = 'cpa_garant' WHERE lead_id = "+leads.getLead_id());

        //check that manualQueue is working
        welcomePage.open().header.changeUser(25572);
        processingPage.startProcessing().clickBusyCall().clickStatus_operator_processing().clickSaveButton();

        //wait processing
        Thread.sleep(10000);

        res = getDB().select("SELECT count(*) FROM terraleads_shipping.`call` WHERE lead_id = "+leads.getLead_id());
        res.next();
        Assert.assertEquals(res.getInt(1),2);

        //check that autoQueue is not working
        welcomePage.header.changeUser(25794);
        processingPage.startProcessing();

        //UPDATE t_scheduled
        getDB().update("UPDATE terraleads_shipping.`call` SET t_scheduled = '2024-12-05 13:46:59.000' WHERE lead_id = "+leads.getLead_id()+" order by id DESC limit 1");

        //wait processing
        Thread.sleep(10000);

        getDB().update("UPDATE terraleads_shipping.`call` SET number_call = 3 WHERE lead_id = "+leads.getLead_id()+" order by id DESC limit 1");
        //wait processing
        Thread.sleep(10000);

        res = getDB().select("SELECT count(*) FROM terraleads_shipping.`call` WHERE lead_id = "+leads.getLead_id());
        res.next();
        Assert.assertEquals(res.getInt(1),2);

        //update number_call > than in rotation settings
        getDB().update("UPDATE terraleads_shipping.`call` SET number_call = 4 WHERE lead_id = "+leads.getLead_id()+" order by id DESC limit 1");

        //wait processing
        Thread.sleep(10000);

        //check that autoQueue is working
        res = getDB().select("SELECT count(*) FROM terraleads_shipping.`call` WHERE lead_id = "+leads.getLead_id());
        res.next();
        Assert.assertEquals(res.getInt(1),3);
        res = getDB().select("SELECT * FROM terraleads_shipping.`call` WHERE number_call = 4 and lead_id = "+leads.getLead_id());
        res.next();
        Assert.assertEquals(res.getString("dialing_mode"),"automatically");
    }

    public void negative_autoCallQueue_notCpaGarant() throws SQLException, InterruptedException {
        //change status old calls
        getDB().update("UPDATE terraleads_shipping.`call` SET status = 3, `type` = 1 WHERE status = 2 and call_center_id = 2");

        makeCall();

        //check that manualQueue is working
        welcomePage.open().header.changeUser(25572);
        processingPage.startProcessing().clickBusyCall().clickStatus_operator_processing().clickSaveButton();

        //wait processing
        Thread.sleep(10000);

        res = getDB().select("SELECT count(*) FROM terraleads_shipping.`call` WHERE lead_id = "+leads.getLead_id());
        res.next();
        Assert.assertEquals(res.getInt(1),2);

        //check that autoQueue is working
        welcomePage.header.changeUser(25794);
        processingPage.startProcessing();

        //UPDATE t_scheduled
        getDB().update("UPDATE terraleads_shipping.`call` SET t_scheduled = '2024-12-05 13:46:59.000' WHERE lead_id = "+leads.getLead_id()+" order by id DESC limit 1");

        //wait processing
        Thread.sleep(10000);

        //check that autoQueue is working
        res = getDB().select("SELECT count(*) FROM terraleads_shipping.`call` WHERE lead_id = "+leads.getLead_id());
        res.next();
        Assert.assertEquals(res.getInt(1),3);
        res = getDB().select("SELECT * FROM terraleads_shipping.`call` WHERE number_call = 2 and lead_id = "+leads.getLead_id());
        res.next();
        Assert.assertEquals(res.getString("dialing_mode"),"automatically");
    }
}
