package deviartTests.pageTests.accounting;

import deviartTests.BaseTest;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.pages.accounting.TransactionPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

@Test
public class TransactionTest extends BaseTest {

    TransactionPage transactionPage = new TransactionPage();

    public void test_searchFields() throws SQLException {
        String resultLoc = "//tbody/tr[@class]";
        ResultSet sqlRes;
        Widget result;

        //Search by ID
        transactionPage.open().readyPage()
                .setID("3").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        Assert.assertTrue(result.textContent().substring(0,2).contains("3"));
        sqlRes = getDB().select("SELECT count(*) FROM terraleads.accounting_payment where id = 3");
        sqlRes.next();
        Assert.assertEquals(result.element.count(),sqlRes.getInt(1));

        //Search by PaymentType = in
        transactionPage.open().readyPage()
                .setPaymentType("In").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i<result.element.count();i++){
            Assert.assertTrue(result.element.nth(i).textContent().contains("In"));
        }
        sqlRes = getDB().select("SELECT count(*) FROM terraleads.accounting_balance_transactions where linked_model_id in (SELECT id FROM terraleads.accounting_payment where payment_allocation = 'in')");
        sqlRes.next();
        Assert.assertTrue(new Widget(Locators.page.locator("//div[@class='summary']")).textContent().contains(String.valueOf(sqlRes.getInt(1))));

        //Search by PaymentType = out
        transactionPage.open().readyPage()
                .setPaymentType("Out").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i<result.element.count();i++){
            Assert.assertTrue(result.element.nth(i).textContent().contains("Out"));
        }
        sqlRes = getDB().select("SELECT count(*) FROM terraleads.accounting_balance_transactions where linked_model_id in (SELECT id FROM terraleads.accounting_payment where payment_allocation = 'out')");
        sqlRes.next();
        Assert.assertTrue(new Widget(Locators.page.locator("//div[@class='summary']")).textContent().contains(String.valueOf(sqlRes.getInt(1))));

        //Search by PaymentType = between
        transactionPage.open().readyPage()
                .setPaymentType("Between").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i<result.element.count();i++){
            Assert.assertTrue(result.element.nth(i).textContent().contains("Between"));
        }
        sqlRes = getDB().select("SELECT count(*) FROM terraleads.accounting_balance_transactions where linked_model_id is NULL");
        sqlRes.next();
        Assert.assertTrue(new Widget(Locators.page.locator("//div[@class='summary']")).textContent().contains(String.valueOf(sqlRes.getInt(1))));

        //Search by Date from
        transactionPage.open().readyPage()
                .setDate_from("2024-07-23").clickShowResultButton();
        sqlRes = getDB().select("SELECT count(*) FROM terraleads.accounting_balance_transactions where t_created > '2024-07-23 00:00:00.001'");
        sqlRes.next();
        Assert.assertTrue(new Widget(Locators.page.locator("//div[@class='summary']")).textContent().contains(String.valueOf(sqlRes.getInt(1))));

        //Search by Date to
        transactionPage.open().readyPage()
                .setDate_to("2024-08-01").clickShowResultButton();
        sqlRes = getDB().select("SELECT count(*) FROM terraleads.accounting_balance_transactions where t_created < '2024-08-01 23:59:59.999'");
        sqlRes.next();
        Assert.assertTrue(new Widget(Locators.page.locator("//div[@class='summary']")).textContent().contains(String.valueOf(sqlRes.getInt(1))));

        //Search by between date
        transactionPage.open().readyPage()
                .setDate_from("2024-08-01").setDate_to("2024-08-01").clickShowResultButton();
        sqlRes = getDB().select("SELECT count(*) FROM terraleads.accounting_balance_transactions where t_created between '2024-08-01 00:00:00.001' and '2024-08-01 23:59:59.999'");
        sqlRes.next();
        Assert.assertTrue(new Widget(Locators.page.locator("//div[@class='summary']")).textContent().contains(String.valueOf(sqlRes.getInt(1))));

        //Search by yesterday button
        transactionPage.open().readyPage()
                .clickYesterdayPaymentsButton().clickShowResultButton();
        sqlRes = getDB().select("SELECT count(*) FROM terraleads.accounting_balance_transactions where t_created between '"+ DataHelper.getTime("yyyy-MM-dd",-1)+ " 00:00:00.001' and '"+DataHelper.getTime("yyyy-MM-dd",-1)+" 23:59:59.999'");
        sqlRes.next();
        System.out.println(sqlRes.getInt(1));
        Assert.assertTrue(new Widget(Locators.page.locator("//div[@class='summary']")).textContent().contains(String.valueOf(sqlRes.getInt(1))));

        //Search by RequisiteType
        transactionPage.open().readyPage()
                .setRequisiteType("PayPal").clickShowResultButton();
        sqlRes = getDB().select("SELECT count(*) FROM terraleads.accounting_balance_transactions where \n" +
                "system_requisites_account_to_id in (SELECT id from terraleads.accounting_system_requisites_account where payment_system_id = 'paypal') \n" +
                "or system_requisites_account_from_id in (SELECT id from terraleads.accounting_system_requisites_account where payment_system_id = 'paypal')");
        sqlRes.next();
        Assert.assertTrue(new Widget(Locators.page.locator("//div[@class='summary']")).textContent().contains(String.valueOf(sqlRes.getInt(1))));

        //Search by Company_id - only sender
        transactionPage.open().readyPage()
                .setCompany_id("Test063ddc57-574d-473c-a400-67c3c754f157").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i<result.element.count();i++){
            Assert.assertTrue(result.element.nth(i).textContent().contains("Test063ddc57-574d-473c-a400-67c3c754f157"));
        }

        //Search by Company_id - only recipient
        transactionPage.open().readyPage()
                .setCompany_id("Testb527b237-ae38-460e-9e49-9d1d7f015f8e").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i<result.element.count();i++){
            Assert.assertTrue(result.element.nth(i).textContent().contains("Testb527b237-ae38-460e-9e49-9d1d7f015f8e"));
        }

        //Search by Company_id - recipient and sender
        transactionPage.open().readyPage()
                .setCompany_id("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i<result.element.count();i++){
            Assert.assertTrue(result.element.nth(i).textContent().contains("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de"));
        }

        //Search by User ID
        transactionPage.open().readyPage()
                .setUserID("#31 - admin@terraleads.com").clickShowResultButton();
        sqlRes = getDB().select("SELECT count(*) FROM terraleads.accounting_balance_transactions where user_id = 31");
        sqlRes.next();
        Assert.assertTrue(new Widget(Locators.page.locator("//div[@class='summary']")).textContent().contains(String.valueOf(sqlRes.getInt(1))));

    }
}
