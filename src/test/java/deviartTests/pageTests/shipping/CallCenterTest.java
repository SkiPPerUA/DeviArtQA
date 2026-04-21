package deviartTests.pageTests.shipping;

import deviartTests.BaseTest;
import org.deviartqa.pages.shipping.callCenter.UpdateallCenterPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

@Test
public class CallCenterTest extends BaseTest {

    UpdateallCenterPage updateСallCenterPage;

    @BeforeMethod
    public void init() {
        updateСallCenterPage = new UpdateallCenterPage();
    }

    public void positive_deliverySettings_test() throws SQLException {
        ResultSet res;
        updateСallCenterPage.open(2).readyPage()
                .setDelivery("20")
                .clickSaveButton().readyPage();
        res = getDB().select("SELECT send_date FROM terraleads_shipping.call_center WHERE id = 2");
        res.next();
        Assert.assertEquals(res.getInt(1),20);

        updateСallCenterPage.open(2).readyPage()
                .setDelivery("25")
                .clickSaveButton().readyPage();
        res = getDB().select("SELECT send_date FROM terraleads_shipping.call_center WHERE id = 2");
        res.next();
        Assert.assertEquals(res.getInt(1),25);
    }

}
