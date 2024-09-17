package deviartTests.pageTests.accounting;

import deviartTests.BaseTest;
import org.deviartqa.core.DBconnector;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Session;
import org.deviartqa.core.Widget;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.pages.accounting.paymentType.PaymentTypePage;
import org.opentest4j.AssertionFailedError;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.sql.ResultSet;
import java.sql.SQLException;

@Test
public class PaymentTypeTest extends BaseTest {

    PaymentTypePage paymentTypePage = new PaymentTypePage();
    String name;

    public void create_paymentType() {
        name = "PaymentType"+ DataHelper.getUuid();
        paymentTypePage.open().readyPage()
                .clickAddPaymentTypeButton()
                .setName(name)
                .clickSendToPayments()
                .clickSaveButton()
                .readyPage();
        paymentTypePage.paginator.clickLastPage();
        Assert.assertTrue(new Widget(Locators.page.locator("//td[text()='"+name+"']")).element.isVisible());
        ResultSet res = getDB().select("SELECT * FROM terraleads.accounting_payment_types where name = '"+name+"'");
        try {
            res.next();
            Assert.assertEquals(res.getInt("send_to_payments"), 1);
        }catch (Throwable e){
            throw new RuntimeException(e);
        }
    }

    public void create_paymentType_notSendToPayments() throws SQLException {
        name = "PaymentType"+ DataHelper.getUuid();
        paymentTypePage.open().readyPage()
                .clickAddPaymentTypeButton()
                .setName(name)
                .clickSaveButton()
                .readyPage();
        paymentTypePage.paginator.clickLastPage();
        Assert.assertTrue(new Widget(Locators.page.locator("//td[text()='"+name+"']")).element.isVisible());
        ResultSet res = getDB().select("SELECT * FROM terraleads.accounting_payment_types where name = '"+name+"'");
        res.next();
        Assert.assertEquals(res.getInt("send_to_payments"), 0);
    }

    @Test(enabled = false)
    public void delete_paymentType() {
        create_paymentType();

        paymentTypePage.deleteType(name).readyPage();
        Assert.assertFalse(new Widget(Locators.page.locator("//td[text()='"+name+"']")).element.isVisible());
    }

    public void update_paymentType(){
        create_paymentType();

        String new_name = "UpdateType"+DataHelper.getUuid();
        paymentTypePage.updateType(name)
                .setName(new_name)
                .clickSaveButton()
                .readyPage();
        paymentTypePage.paginator.clickLastPage();
        Assert.assertFalse(new Widget(Locators.page.locator("//td[text()='"+name+"']")).element.isVisible());
        Assert.assertTrue(new Widget(Locators.page.locator("//td[text()='"+new_name+"']")).element.isVisible());
    }

    public void create_doubleTest(){
        create_paymentType();
        paymentTypePage.open().readyPage()
                .clickAddPaymentTypeButton()
                .setName(name)
                .clickSaveButton();
        try {
            paymentTypePage.readyPage();
            Assert.fail("Дубль создан");
        }catch (AssertionFailedError e){}
    }

    public void test_search_count() throws SQLException {
        paymentTypePage.open().readyPage();
        ResultSet res = new DBconnector().select("SELECT count(*) FROM terraleads.accounting_payment_types");
        res.next();
        Assert.assertTrue(new Widget(Locators.page.locator("//div[@class='summary']")).textContent().contains(String.valueOf(res.getInt(1))));
    }

    public void test_buttons(){
        paymentTypePage.open().readyPage()
                .clickAddPaymentTypeButton();
        Assert.assertTrue(Session.getPage().url().contains("AddPaymentType"));

        paymentTypePage.open().readyPage()
                .clickAddPaymentTypeButton().clickCancelButton().readyPage();
    }
}
