package deviartTests.pageTests;

import deviartTests.BaseTest;
import org.deviartqa.core.DBconnector;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
import org.deviartqa.helper.TextLocalization;
import org.deviartqa.pages.OrdersToPack;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

@Test
public class OrdersToPackTest extends BaseTest {

    OrdersToPack ordersToPack = new OrdersToPack();
    public void params_test() throws SQLException {
        //ID
        ordersToPack.open().readyPage()
                .setId("259")
                .clickShowResultButton();
        Assert.assertEquals(getOrdersList().element.count(),1);
        Assert.assertTrue(getOrdersList().textContent().substring(0,4).contains("259"));

        //Status
        ordersToPack.open().readyPage()
                .choseStatus("New")
                .clickShowResultButton();
        int countData = getOrdersList().element.count();
        for (int i = 0; i<countData;i++) {
            Assert.assertTrue(getOrdersList().element.nth(i).textContent().contains(TextLocalization.get("new")));
        }
        ResultSet res = dBconnector.select("SELECT count(*) FROM terraleads_shipping.order where status = 1");
        res.next();
        Assert.assertEquals(countData,res.getInt(1));


        //Payment Type
        ordersToPack.open().readyPage()
                .setPayment_type(TextLocalization.get("payment_on_delivery"))
                .clickShowResultButton();
        countData = getOrdersList().element.count();
        for (int i = 0; i<countData;i++) {
            Assert.assertTrue(getOrdersList().element.nth(i).textContent().contains(TextLocalization.get("payment_on_delivery")));
        }
        assertInDB("payment_type='payment_on_delivery'",countData);

        //Payment status
        ordersToPack.open().readyPage()
                .setPayment_status(TextLocalization.get("not_paid"))
                .clickShowResultButton();
        countData = getOrdersList().element.count();
        for (int i = 0; i < countData; i++) {
            Assert.assertTrue(getOrdersList().element.nth(i).textContent().contains(TextLocalization.get("not_paid")));
        }
        assertInDB("payment_status='not_paid'",countData);

        //Lead
        ordersToPack.open().readyPage()
                .setLead("20270")
                .clickShowResultButton();
        countData = getOrdersList().element.count();
        for (int i = 0; i < countData; i++) {
            Assert.assertTrue(getOrdersList().element.nth(i).textContent().contains("20270"));
        }
        assertInDB("lead_id=20270",countData);

        //Full name
        ordersToPack.open().readyPage()
                .setFull_name("Annunziata Napolitano")
                .clickShowResultButton();
        countData = getOrdersList().element.count();
        for (int i = 0; i < countData; i++) {
            Assert.assertTrue(getOrdersList().element.nth(i).textContent().contains("Annunziata Napolitano"));
        }
        assertInDB("full_name='Annunziata Napolitano'",countData);

        //Phone
        ordersToPack.open().readyPage()
                .setPhone("40732658883")
                .clickShowResultButton();
        countData = getOrdersList().element.count();
        for (int i = 0; i < countData; i++) {
            Assert.assertTrue(getOrdersList().element.nth(i).textContent().contains("4********83"));
        }
        assertInDB("phone='40732658883'",countData);

        //Type
        ordersToPack.open().readyPage()
                .setType(TextLocalization.get("additional_selling"))
                .clickShowResultButton();
        countData = getOrdersList().element.count();
        for (int i = 0; i < countData; i++) {
            Assert.assertTrue(getOrdersList().element.nth(i).textContent().contains(TextLocalization.get("additional_selling")));
        }
        assertInDB("type=4",countData);

        //Delivery service
        ordersToPack.open().readyPage()
                .setDelivery_service("#37 - GLS A.S. E")
                .clickShowResultButton();
        countData = getOrdersList().element.count();
        for (int i = 0; i < countData; i++) {
            Assert.assertTrue(getOrdersList().element.nth(i).textContent().contains("#37 - GLS A.S. E"));
        }
        assertInDB("delivery_service_id=37",countData);

        //Delivery type
        ordersToPack.open().readyPage()
                .setDelivery_type(TextLocalization.get("address"))
                .clickShowResultButton();
        countData = getOrdersList().element.count();
        for (int i = 0; i < countData; i++) {
            Assert.assertTrue(getOrdersList().element.nth(i).textContent().contains(TextLocalization.get("address")));
        }
        assertInDB("delivery_type='address'",countData);

        //Product
        ordersToPack.open().readyPage()
                .setProduct("1418")
                .clickShowResultButton();
        countData = getOrdersList().element.count();
        for (int i = 0; i < countData; i++) {
            Assert.assertTrue(getOrdersList().element.nth(i).textContent().contains("U-shaped Massage Pillow"));
        }
        assertInDB("product_base_id=1418",countData);

        //Products in order
        ordersToPack.open().readyPage()
                .setProducts_in_order("Mini HD Camera")
                .clickShowResultButton();
        countData = getOrdersList().element.count();
        for (int i = 0; i < countData; i++) {
            Assert.assertTrue(getOrdersList().element.nth(i).textContent().contains("Mini HD Camera"));
        }
        assertInDB("_cart like '%\"product_base_id\":\"2591\"%'",countData);


        //Count type products in order
        ordersToPack.open().readyPage()
                .setCount_type_product("3")
                .clickShowResultButton();
        assertInDB("_cart like '[{%},{%},{%}]' ",getOrdersList().element.count());

        //Country
        ordersToPack.open().readyPage()
                .setCountry(TextLocalization.get("italy"))
                .clickShowResultButton();
        Widget country = new Widget(Locators.page.locator("//tbody//*[@data-original-title]"));
        countData = country.element.count();
        for (int i = 0; i < countData; i++) {
            Assert.assertEquals(country.element.nth(i).getAttribute("data-original-title"),TextLocalization.get("italy"));
        }
        assertInDB("country_id=92",countData);

        //Advertiser
        ordersToPack.open().readyPage()
                .setAdvertiser("25563")
                .clickShowResultButton();
        countData = getOrdersList().element.count();
        for (int i = 0; i < countData; i++) {
            Assert.assertTrue(getOrdersList().element.nth(i).textContent().contains("25563"));
        }
        assertInDB("advertiser_id=25563",countData);

        //Date
        ordersToPack.open().readyPage()
                .setDate("2023-12-27","2023-12-27")
                .clickShowResultButton();
        countData = getOrdersList().element.count();
        for (int i = 0; i < countData; i++) {
            Assert.assertTrue(getOrdersList().element.nth(i).textContent().contains("2023-12-27"));
        }
        assertInDB("send_date BETWEEN '2023-12-27' and '2023-12-27'",countData);
    }

    private Widget getOrdersList(){
        return new Widget(Locators.page.locator("//tbody/*"));
    }

    private void assertInDB(String condition, int expect){
        ResultSet res = dBconnector.select("SELECT count(*) FROM terraleads_shipping.order where status = 1 and "+condition);
        try {
            res.next();
            Assert.assertEquals(res.getInt(1),expect);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @BeforeTest
    void openConn(){
        dBconnector = new DBconnector("terraleads_shipping");
    }

    DBconnector dBconnector;

}
