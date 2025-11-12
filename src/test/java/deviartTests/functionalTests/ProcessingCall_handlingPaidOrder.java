package deviartTests.functionalTests;

import deviartTests.BaseTest;
import deviartTests.apiTests.LeadTest;
import org.deviartqa.blocks.filters.ModalWindow;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.pages.main.WelcomePage;
import org.deviartqa.pages.shipping.ProcessingPage;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

@Test
public class ProcessingCall_handlingPaidOrder extends BaseTest {

    ProcessingCall_test processingCall_test = new ProcessingCall_test();
    ProcessingPage processingPage = new ProcessingPage();
    ResultSet res;
    String call_id = "";
    String call_lead_id = "";

    public void approve_test() throws SQLException {
        processingCall_test.approve_call();
        res = getDB().select("SELECT * FROM terraleads.lead where id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getString("status"),"expect");
        Assert.assertEquals(res.getString("status_web"),"expect");

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where id = "+call_id);
        res.next();
        Assert.assertEquals(res.getInt("call_sequence_type"),7);
        Assert.assertEquals(res.getInt("status"),2);

        res = getDB().select("SELECT count(*) FROM terraleads_shipping.`call` where lead_id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getInt(1),1);
    }

    public void busy_test() throws SQLException {
        processingCall_test.busy_call();
        res = getDB().select("SELECT * FROM terraleads.lead where id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getString("status"),"expect");
        Assert.assertEquals(res.getString("status_web"),"expect");

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where id = "+call_id);
        res.next();
        Assert.assertEquals(res.getInt("call_sequence_type"),7);
        Assert.assertEquals(res.getInt("status"),3);

        res = getDB().select("SELECT count(*) FROM terraleads_shipping.`call` where lead_id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getInt(1),2);

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where lead_id = "+call_lead_id+ " order by id DESC");
        res.next();
        Assert.assertEquals(res.getInt("parent_id"),Integer.parseInt(call_id));
        Assert.assertEquals(res.getInt("call_sequence_type"),7);
        Assert.assertEquals(res.getInt("status"),1);
        Assert.assertEquals(res.getInt("type"),2);
        Assert.assertNull(res.getObject("operator_id"));
    }

    public void busy_test_finishAfterSomeCalls() throws SQLException {
        processingCall_test.busy_call();
        for (int i = 1; i <= 3; i++){
            getDB().update("update terraleads_shipping.`call` set type = 1, t_scheduled = '"+DataHelper.getTime("yyyy-MM-dd",-1)+" 00:00:01.000' where lead_id = "+call_lead_id);
            processingPage.startProcessing();
            Assert.assertEquals(processingPage.getCallInfo(ProcessingPage.CallParameters.lead_id),call_lead_id);
            processingCall_test.busy_call();
            res = getDB().select("SELECT * FROM terraleads.lead where id = "+call_lead_id);
            res.next();
            Assert.assertEquals(res.getString("status"),"expect");
            Assert.assertEquals(res.getString("status_web"),"expect");
        }

        res = getDB().select("SELECT count(*) FROM terraleads_shipping.`call` where lead_id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getInt(1),5);

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where lead_id = "+call_lead_id+ " order by id DESC");
        res.next();
        Assert.assertNotNull(res.getObject("parent_id"));
        Assert.assertEquals(res.getInt("call_sequence_type"),7);
        Assert.assertEquals(res.getInt("status"),1);
        Assert.assertEquals(res.getInt("type"),2);
        Assert.assertNull(res.getObject("operator_id"));
    }

    public void busy_newTime_test() throws SQLException {
        processingCall_test.busy_call_selectTime(DataHelper.getTime("yyyy-MM-dd",3)+" 22:00");
        res = getDB().select("SELECT * FROM terraleads.lead where id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getString("status"),"expect");
        Assert.assertEquals(res.getString("status_web"),"expect");

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where id = "+call_id);
        res.next();
        Assert.assertEquals(res.getInt("call_sequence_type"),7);
        Assert.assertEquals(res.getInt("status"),3);

        res = getDB().select("SELECT count(*) FROM terraleads_shipping.`call` where lead_id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getInt(1),2);

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where lead_id = "+call_lead_id+ " order by id DESC");
        res.next();
        Assert.assertEquals(res.getInt("parent_id"),Integer.parseInt(call_id));
        Assert.assertEquals(res.getInt("call_sequence_type"),7);
        Assert.assertEquals(res.getInt("status"),1);
        Assert.assertEquals(res.getInt("type"),3);
        Assert.assertEquals(res.getInt("operator_id"),25562);
        Assert.assertEquals(res.getString("t_scheduled"),DataHelper.getTime("yyyy-MM-dd",3)+" 23:00"+":00");
    }

    public void reject_test() throws SQLException {
        processingCall_test.reject_call();
        res = getDB().select("SELECT * FROM terraleads.lead where id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getString("status"),"expect");
        Assert.assertEquals(res.getString("status_web"),"expect");

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where id = "+call_id);
        res.next();
        Assert.assertEquals(res.getInt("call_sequence_type"),7);
        Assert.assertEquals(res.getInt("status"),3);

        res = getDB().select("SELECT count(*) FROM terraleads_shipping.`call` where lead_id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getInt(1),1);

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where lead_id = "+call_lead_id+ " order by id DESC");
        res.next();
        Assert.assertNull(res.getObject("parent_id"));
        Assert.assertEquals(res.getInt("call_sequence_type"),7);
        Assert.assertEquals(res.getInt("status"),3);
        Assert.assertEquals(res.getInt("type"),1);
        Assert.assertEquals(res.getInt("operator_id"),25562);
    }

    public void trash_test() throws SQLException {
        processingCall_test.trash_call();
        res = getDB().select("SELECT * FROM terraleads.lead where id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getString("status"),"expect");
        Assert.assertEquals(res.getString("status_web"),"expect");

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where id = "+call_id);
        res.next();
        Assert.assertEquals(res.getInt("call_sequence_type"),7);
        Assert.assertEquals(res.getInt("status"),3);

        res = getDB().select("SELECT count(*) FROM terraleads_shipping.`call` where lead_id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getInt(1),1);

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where lead_id = "+call_lead_id+ " order by id DESC");
        res.next();
        Assert.assertNull(res.getObject("parent_id"));
        Assert.assertEquals(res.getInt("call_sequence_type"),7);
        Assert.assertEquals(res.getInt("status"),3);
        Assert.assertEquals(res.getInt("type"),1);
        Assert.assertEquals(res.getInt("operator_id"),25562);
    }

    public void trashTest_test() throws SQLException {
        String call_lead_id = processingPage.getCallInfo(ProcessingPage.CallParameters.lead_id);
        String call_id = processingPage.getCallInfo(ProcessingPage.CallParameters.call_id);
        processingPage
                .setCallResult()
                .callProcess(ModalWindow.CallResult.trash,
                        ModalWindow.ResultAdditional.trash_test,
                        "trash",
                        ModalWindow.OperatorStatus.stopProcessing)
                .save(true);
        res = getDB().select("SELECT * FROM terraleads.lead where id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getString("status"),"expect");
        Assert.assertEquals(res.getString("status_web"),"expect");

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where id = "+call_id);
        res.next();
        Assert.assertEquals(res.getInt("call_sequence_type"),7);
        Assert.assertEquals(res.getInt("status"),3);

        res = getDB().select("SELECT count(*) FROM terraleads_shipping.`call` where lead_id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getInt(1),1);

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where lead_id = "+call_lead_id+ " order by id DESC");
        res.next();
        Assert.assertNull(res.getObject("parent_id"));
        Assert.assertEquals(res.getInt("call_sequence_type"),7);
        Assert.assertEquals(res.getInt("status"),3);
        Assert.assertEquals(res.getInt("type"),1);
        Assert.assertEquals(res.getInt("operator_id"),25562);
    }

    public void techProblem_test() throws SQLException {
        processingCall_test.techProblem_call();
        res = getDB().select("SELECT * FROM terraleads.lead where id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getString("status"),"expect");
        Assert.assertEquals(res.getString("status_web"),"expect");

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where id = "+call_id);
        res.next();
        Assert.assertEquals(res.getInt("call_sequence_type"),7);
        Assert.assertEquals(res.getInt("status"),3);

        res = getDB().select("SELECT count(*) FROM terraleads_shipping.`call` where lead_id = "+call_lead_id);
        res.next();
        Assert.assertEquals(res.getInt(1),2);

        res = getDB().select("SELECT * FROM terraleads_shipping.`call` where lead_id = "+call_lead_id+ " order by id DESC");
        res.next();
        Assert.assertEquals(res.getInt("parent_id"),Integer.parseInt(call_id));
        Assert.assertEquals(res.getInt("call_sequence_type"),7);
        Assert.assertEquals(res.getInt("status"),1);
        Assert.assertEquals(res.getInt("type"),2);
        Assert.assertNull(res.getObject("operator_id"));
    }

    @BeforeClass
    void auth() throws SQLException, InterruptedException {
        for (int i = 0; i<10; i++) {
            LeadTest leadTest = new LeadTest();
            leadTest.create_lead_positive();
            String lead = new JSONObject(leadTest.leads.getResponse()).getJSONObject("data").getString("id");
            Thread.sleep(1000);
            getDB().update("update terraleads_shipping.`call` set call_sequence_type = 7 where lead_id = "+lead);
            getDB().update("update terraleads_shipping.call_sequence set type = 7 where lead_id = "+lead);
        }
        getDB().update("update terraleads_shipping.call_queue_ref_operator set operator_id = 25555 where operator_id = 25562");
        getDB().update("update terraleads_shipping.call_queue_ref_operator set operator_id = 25562 where call_queue_id = 25");
        new WelcomePage().open().header.changeUser(25562);
    }

    @BeforeMethod
    void startProcc() {
        processingPage.startProcessing();
        call_lead_id = processingPage.getCallInfo(ProcessingPage.CallParameters.lead_id);
        call_id = processingPage.getCallInfo(ProcessingPage.CallParameters.call_id);
    }


}
