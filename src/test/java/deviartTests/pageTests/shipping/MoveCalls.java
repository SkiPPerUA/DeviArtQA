package deviartTests.pageTests.shipping;

import deviartTests.BaseTest;
import org.deviartqa.pages.shipping.call.CallTransferPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.sql.ResultSet;
import java.util.List;

@Test
public class MoveCalls extends BaseTest {

    CallTransferPage callTransferPage = new CallTransferPage();
    int operator_from = 25573;
    int operator_to = 25572;
    int call_id = 47034;

    public void positive_move() {
        List<String> call_sequence_types = List.of("Selling","Remainder","Reason for non-payment of the order, rehabilitation of the order",
                "Additional selling","Trash calls from sale (Extra control)","Reject calls from sale (Extra control)","Handling paid order",
                "Handling not paid order","MLM");

        getDB().update("UPDATE terraleads_shipping.`call` SET operator_id ="+operator_from+" WHERE id = "+call_id);
        call_sequence_types.forEach(x->{
            getDB().update("UPDATE terraleads_shipping.`call` SET call_sequence_type = "+(call_sequence_types.indexOf(x)+1)+" WHERE id = "+call_id);
            callTransferPage.open().readyPage()
                    .setFrom_operator(String.valueOf(operator_from))
                    .setTo_operator(String.valueOf(operator_to))
                    .setCall_sequence(x)
                    .clickSaveButton();
            ResultSet res = getDB().select("SELECT operator_id FROM terraleads_shipping.`call` WHERE id = "+call_id);
            try {
                res.next();
                Assert.assertEquals(res.getInt(1),operator_to);
            } catch (Throwable e) {
                Assert.fail(x + " - бага");
            }
        });

        //revert
        getDB().update("UPDATE terraleads_shipping.`call` SET operator_id ="+operator_to+" WHERE id = "+call_id);
        call_sequence_types.forEach(x->{
            getDB().update("UPDATE terraleads_shipping.`call` SET call_sequence_type = "+(call_sequence_types.indexOf(x)+1)+" WHERE id = "+call_id);
            callTransferPage.open().readyPage()
                    .setFrom_operator(String.valueOf(operator_to))
                    .setTo_operator(String.valueOf(operator_from))
                    .setCall_sequence(x)
                    .clickSaveButton();
            ResultSet res = getDB().select("SELECT operator_id FROM terraleads_shipping.`call` WHERE id = "+call_id);
            try {
                res.next();
                Assert.assertEquals(res.getInt(1),operator_from);
            } catch (Throwable e) {
                Assert.fail(x + " - бага");
            }
        });
    }

    public void negative_move() {
        getDB().update("UPDATE terraleads_shipping.`call` SET operator_id = 25573 WHERE id = "+call_id);

        //from different call centers
        getDB().update("UPDATE terraleads_shipping.`call` SET call_sequence_type = 4 WHERE id = "+call_id);
        callTransferPage.open().readyPage()
                .setFrom_operator("25573")
                .setTo_operator("25560")
                .setCall_sequence("Additional selling")
                .clickSaveButton();
        callTransferPage.readyPage();

        //calls status = 3
        callTransferPage.open().readyPage()
                .setFrom_operator("25721")
                .setTo_operator("25616")
                .setCall_sequence("Additional selling")
                .clickSaveButton();
        callTransferPage.readyPage();
    }

    public void test_parameters(){
        //without From_operator
        callTransferPage.open().readyPage()
                .setTo_operator(String.valueOf(operator_to))
                .setCall_sequence("Additional selling")
                .clickSaveButton();
        callTransferPage.readyPage();

        //without To_operator
        callTransferPage.open().readyPage()
                .setFrom_operator(String.valueOf(operator_from))
                .setCall_sequence("Additional selling")
                .clickSaveButton();
        callTransferPage.readyPage();

        //without Call_sequence
        callTransferPage.open().readyPage()
                .setFrom_operator(String.valueOf(operator_from))
                .setTo_operator(String.valueOf(operator_to))
                .clickSaveButton();
        callTransferPage.readyPage();
    }
}
