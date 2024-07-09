package deviartTests.pageTests.accounting;

import deviartTests.BaseTest;
import org.deviartqa.core.DBconnector;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.pages.paymentType.PaymentTypePage;
import org.opentest4j.AssertionFailedError;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.sql.ResultSet;
import java.sql.SQLException;

@Test
public class PaymentTypeTest extends BaseTest {

    PaymentTypePage paymentTypePage = new PaymentTypePage();
    String name;

    public void create_paymentType(){
        name = "PaymentType"+ DataHelper.getUuid();
        paymentTypePage.open().readyPage()
                .clickAddPaymentTypeButton()
                .setName(name)
                .clickSaveButton()
                .readyPage();
        Assert.assertTrue(new Widget(Locators.page.locator("//td[text()='"+name+"']")).element.isVisible());
    }

    public void delete_paymentType(){
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
        Assert.assertEquals(new Widget(Locators.page.locator("//tbody/*")).element.count(),res.getInt(1));
    }
}
