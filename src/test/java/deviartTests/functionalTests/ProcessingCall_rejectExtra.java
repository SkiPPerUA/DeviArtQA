package deviartTests.functionalTests;

import deviartTests.BaseTest;
import deviartTests.apiTests.LeadTest;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.pages.main.WelcomePage;
import org.deviartqa.pages.shipping.ProcessingPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

@Test
public class ProcessingCall_rejectExtra extends BaseTest {

    ProcessingCall_test processingCall_test = new ProcessingCall_test();
    ProcessingPage processingPage = new ProcessingPage();
    ResultSet res;
    String call_id = "";
    String call_lead_id = "";

    public void approve_test() throws SQLException {
        processingCall_test.approve_call();
        res = getDB().select("SELECT * FROM terraleads.lead where id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getString("status"),"confirm");
        Assert.assertEquals(res.getString("status_web"),"confirm");

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where id = "+call_id);
        res.next();
        Assert.assertEquals(res.getInt("call_sequence_type"),6);
        Assert.assertEquals(res.getInt("status"),3);

        res = getDB().select("SELECT count(*) FROM terraleads_shipping.`call` where lead_id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getInt(1),2);
    }

    public void busy_test() throws SQLException {
        processingCall_test.busy_call();
        res = getDB().select("SELECT * FROM terraleads.lead where id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getString("status"),"reject");
        Assert.assertEquals(res.getString("status_web"),"expect");

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where id = "+call_id);
        res.next();
        Assert.assertEquals(res.getInt("call_sequence_type"),6);
        Assert.assertEquals(res.getInt("status"),3);

        res = getDB().select("SELECT count(*) FROM terraleads_shipping.`call` where lead_id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getInt(1),3);

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where lead_id = "+call_lead_id+ " order by id DESC");
        res.next();
        Assert.assertEquals(res.getInt("parent_id"),Integer.parseInt(call_id));
        Assert.assertEquals(res.getInt("call_sequence_type"),6);
        Assert.assertEquals(res.getInt("status"),1);
        Assert.assertEquals(res.getInt("type"),2);
        Assert.assertNull(res.getObject("operator_id"));
    }

    public void busy_newTime_test() throws SQLException {
        processingCall_test.busy_call_selectTime(DataHelper.getTime("yyyy-MM-dd",3)+" 22:00");
        res = getDB().select("SELECT * FROM terraleads.lead where id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getString("status"),"reject");
        Assert.assertEquals(res.getString("status_web"),"expect");

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where id = "+call_id);
        res.next();
        Assert.assertEquals(res.getInt("call_sequence_type"),6);
        Assert.assertEquals(res.getInt("status"),3);

        res = getDB().select("SELECT count(*) FROM terraleads_shipping.`call` where lead_id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getInt(1),3);

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where lead_id = "+call_lead_id+ " order by id DESC");
        res.next();
        Assert.assertEquals(res.getInt("parent_id"),Integer.parseInt(call_id));
        Assert.assertEquals(res.getInt("call_sequence_type"),6);
        Assert.assertEquals(res.getInt("status"),1);
        Assert.assertEquals(res.getInt("type"),3);
        Assert.assertEquals(res.getInt("operator_id"),25561);
        Assert.assertEquals(res.getString("t_scheduled"), DataHelper.getTime("yyyy-MM-dd",3)+" 22:00"+":00");
    }

    public void reject_test() throws SQLException {
        processingCall_test.reject_call();
        res = getDB().select("SELECT * FROM terraleads.lead where id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getString("status"),"reject");
        Assert.assertEquals(res.getString("status_web"),"reject");

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where id = "+call_id);
        res.next();
        Assert.assertEquals(res.getInt("call_sequence_type"),6);
        Assert.assertEquals(res.getInt("status"),3);

        res = getDB().select("SELECT count(*) FROM terraleads_shipping.`call` where lead_id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getInt(1),2);

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where lead_id = "+call_lead_id+ " order by id DESC");
        res.next();
        Assert.assertNull(res.getObject("parent_id"));
        Assert.assertEquals(res.getInt("call_sequence_type"),6);
        Assert.assertEquals(res.getInt("status"),3);
        Assert.assertEquals(res.getInt("type"),1);
        Assert.assertEquals(res.getInt("operator_id"),25561);
    }

    public void trash_test() throws SQLException {
        processingCall_test.trash_call();
        res = getDB().select("SELECT * FROM terraleads.lead where id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getString("status"),"reject");
        Assert.assertEquals(res.getString("status_web"),"reject");

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where id = "+call_id);
        res.next();
        Assert.assertEquals(res.getInt("call_sequence_type"),6);
        Assert.assertEquals(res.getInt("status"),3);

        res = getDB().select("SELECT count(*) FROM terraleads_shipping.`call` where lead_id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getInt(1),2);

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where lead_id = "+call_lead_id+ " order by id DESC");
        res.next();
        Assert.assertNull(res.getObject("parent_id"));
        Assert.assertEquals(res.getInt("call_sequence_type"),6);
        Assert.assertEquals(res.getInt("status"),3);
        Assert.assertEquals(res.getInt("type"),1);
        Assert.assertEquals(res.getInt("operator_id"),25561);
    }

    public void techProblem_test() throws SQLException {
        processingCall_test.techProblem_call();
        res = getDB().select("SELECT * FROM terraleads.lead where id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getString("status"),"reject");
        Assert.assertEquals(res.getString("status_web"),"expect");

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where id = "+call_id);
        res.next();
        Assert.assertEquals(res.getInt("call_sequence_type"),6);
        Assert.assertEquals(res.getInt("status"),3);

        res = getDB().select("SELECT count(*) FROM terraleads_shipping.`call` where lead_id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getInt(1),3);

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where lead_id = "+call_lead_id+ " order by id DESC");
        res.next();
        Assert.assertEquals(res.getInt("parent_id"),Integer.parseInt(call_id));
        Assert.assertEquals(res.getInt("call_sequence_type"),6);
        Assert.assertEquals(res.getInt("status"),1);
        Assert.assertEquals(res.getInt("type"),2);
        Assert.assertNull(res.getObject("operator_id"));
    }

    @BeforeClass
    void auth() throws SQLException, InterruptedException {
        //make test calls
        new WelcomePage().open().header.changeUser(25559);
        for (int i = 0; i<5; i++) {
            new LeadTest().create_lead_positive();
            processingPage.startProcessing();
            new ProcessingCall_test().reject_call();
            getDB().update("update terraleads_shipping.`call` set t_scheduled = '2025-11-10 16:09:16.000', `type`= 1 ORDER BY id DESC limit 1");
        }
        new WelcomePage().open().header.changeUser(25561);
    }

    @BeforeMethod
    void startProcc() {
        processingPage.startProcessing();
        call_lead_id = processingPage.getCallInfo(ProcessingPage.CallParameters.lead_id);
        call_id = processingPage.getCallInfo(ProcessingPage.CallParameters.call_id);
    }


}
