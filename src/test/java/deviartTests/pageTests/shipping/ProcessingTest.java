package deviartTests.pageTests.shipping;

import com.microsoft.playwright.TimeoutError;
import deviartTests.BaseTest;
import org.deviartqa.pages.shipping.ProcessingPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

@Test
public class ProcessingTest extends BaseTest {

    ProcessingPage processingPage = new ProcessingPage();

    public void positive_checkRemiderMessage() throws SQLException {
        String where = "status = 1 and `type` = 3 and parent_id = 45969 and t_max_assignment is not null";
        ResultSet res = getDB().select("SELECT * from terraleads_shipping.`call` where "+where);
        getDB().update("UPDATE terraleads_shipping.call_queue set missed_exact_call = 1 where id = 19");
        if (!res.next()){
            Assert.fail("Необрабтанных звонков нету, тест НЕ возможен");
        }
        //processingPage.header.changeUser(25559);
        processingPage.startProcessing().readyPage()
                .clickUnprocessedCalls();
    }

    public void negative_checkRemiderMessage() throws SQLException {
        String where = "status = 1 and `type` = 3 and parent_id = 45969 and t_max_assignment is not null";
        ResultSet res = getDB().select("SELECT * from terraleads_shipping.`call` where "+where);
        if (!res.next()){
            Assert.fail("Необрабтанных звонков нету, тест НЕ возможен");
        }
        getDB().update("UPDATE terraleads_shipping.call_queue set missed_exact_call = 0 where id = 19");
        //processingPage.header.changeUser(25559);
        processingPage.startProcessing().readyPage();
        try {
            processingPage.clickUnprocessedCalls();
            Assert.fail("Блок напоминание показан");
        }catch (TimeoutError e){
        }
    }
}
