package deviartTests.pageTests.accounting;

import com.microsoft.playwright.TimeoutError;
import deviartTests.BaseTest;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.helper.TextLocalization;
import org.deviartqa.pages.accounting.GeneralBalancesPage;
import org.deviartqa.pages.accounting.TransactionPage;
import org.deviartqa.pages.accounting.payment.CreatePaymentPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

@Test
public class TransactionTest extends BaseTest {

    TransactionPage transactionPage = new TransactionPage();
    GeneralBalancesPage generalBalancesPage = new GeneralBalancesPage();
    ResultSet res;

    public void test_searchFields() throws SQLException {
        String resultLoc = "//tbody/tr[@class]";
        ResultSet sqlRes;
        Widget result;

        //Search by awaiting
        transactionPage.open().readyPage()
                .clickAwaitingButton().clickShowResultButton();
        sqlRes = getDB().select("SELECT count(*) FROM terraleads.accounting_balance_transactions where payment_status in (5,9)");
        sqlRes.next();
        Assert.assertTrue(new Widget(Locators.page.locator("//div[@class='summary']")).textContent().contains(String.valueOf(sqlRes.getInt(1))));

        //Search by ID
        transactionPage.open().readyPage()
                .setID("3").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        Assert.assertTrue(result.textContent().substring(0, 2).contains("3"));
        sqlRes = getDB().select("SELECT count(*) FROM terraleads.accounting_payment where id = 3");
        sqlRes.next();
        Assert.assertEquals(result.element.count(), sqlRes.getInt(1));

        //Search by PaymentType = in
        transactionPage.open().readyPage()
                .setPaymentType("In").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i < result.element.count(); i++) {
            Assert.assertTrue(result.element.nth(i).textContent().contains("In"));
        }
        sqlRes = getDB().select("SELECT count(*) FROM terraleads.accounting_balance_transactions where linked_model_id in (SELECT id FROM terraleads.accounting_payment where payment_allocation = 'in')");
        sqlRes.next();
        Assert.assertTrue(new Widget(Locators.page.locator("//div[@class='summary']")).textContent().contains(String.valueOf(sqlRes.getInt(1))));

        //Search by PaymentType = out
        transactionPage.open().readyPage()
                .setPaymentType("Out").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i < result.element.count(); i++) {
            Assert.assertTrue(result.element.nth(i).textContent().contains("Out"));
        }
        sqlRes = getDB().select("SELECT count(*) FROM terraleads.accounting_balance_transactions where linked_model_id in (SELECT id FROM terraleads.accounting_payment where payment_allocation = 'out')");
        sqlRes.next();
        Assert.assertTrue(new Widget(Locators.page.locator("//div[@class='summary']")).textContent().contains(String.valueOf(sqlRes.getInt(1))));

        //Search by PaymentType = between
        transactionPage.open().readyPage()
                .setPaymentType("Between").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i < result.element.count(); i++) {
            Assert.assertTrue(result.element.nth(i).textContent().contains("Between"));
        }
        sqlRes = getDB().select("SELECT count(*) FROM terraleads.accounting_balance_transactions where payment_type = 0");
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
        sqlRes = getDB().select("SELECT count(*) FROM terraleads.accounting_balance_transactions where t_created between '" + DataHelper.getTime("yyyy-MM-dd", -1) + " 00:00:00.001' and '" + DataHelper.getTime("yyyy-MM-dd", -1) + " 23:59:59.999'");
        sqlRes.next();
        if (sqlRes.getInt(1) > 0) {
            Assert.assertTrue(new Widget(Locators.page.locator("//div[@class='summary']")).textContent().contains(String.valueOf(sqlRes.getInt(1))));
        }

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

//        //Search by Payment Type
//        transactionPage.open().readyPage()
//                .setUserID("#31 - admin@terraleads.com").clickShowResultButton();
//        sqlRes = getDB().select("SELECT count(*) FROM terraleads.accounting_balance_transactions where user_id = 31");
//        sqlRes.next();
//        Assert.assertTrue(new Widget(Locators.page.locator("//div[@class='summary']")).textContent().contains(String.valueOf(sqlRes.getInt(1))));

    }

    public void change_status_paidAndConfirm() throws SQLException {
        //in
        createIn();
        transactionPage.readyPage();
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        int id = res.getInt("id");
        transactionPage.actionButtons.paidAction(id);
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        Assert.assertEquals(res.getInt("payment_status"),9);
        transactionPage.actionButtons.confirmAction(id);
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        Assert.assertEquals(res.getInt("payment_status"),10);

        //out
        createOut();
        transactionPage.readyPage();
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        id = res.getInt("id");
        transactionPage.actionButtons.paidAction(id);
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        Assert.assertEquals(res.getInt("payment_status"),9);
        transactionPage.actionButtons.confirmAction(id);
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        Assert.assertEquals(res.getInt("payment_status"),10);
    }

    public void test_LinkToAdv() throws SQLException {
        //in
        createIn();
        transactionPage.readyPage();
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        int id = res.getInt("id");
        transactionPage.actionButtons.paidAction(id);
        transactionPage.actionButtons.confirmAction(id);
        transactionPage.actionButtons.linkToAdv(id,"25563");
        res = getDB().select("SELECT user_id FROM terraleads.accounting_payment where id = (SELECT linked_model_id FROM terraleads.accounting_balance_transactions where id = "+id+")");
        res.next();
        Assert.assertEquals(res.getInt(1),25563);

        //out
        createOut();
        transactionPage.readyPage();
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        id = res.getInt("id");
        transactionPage.actionButtons.paidAction(id);
        transactionPage.actionButtons.confirmAction(id);
        try {
            transactionPage.actionButtons.linkToAdv(id,"25563");
            Assert.fail("Кнопка есть");
        }catch (TimeoutError e){
            res = getDB().select("SELECT user_id FROM terraleads.accounting_payment where id = (SELECT linked_model_id FROM terraleads.accounting_balance_transactions where id = "+id+")");
            res.next();
            Assert.assertEquals(res.getInt(1),0);
        }

        //in without possible to link
        generalBalancesPage.open().readyPage().clickMakeTransferButton();
        new CreatePaymentPage()
                .setRoutePayment("in")
                .setPurpose_of_payment("testVlad")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .choseSystem_requisites_account("testPaymentName6")
                .setPayment_typeId("PaymentTypef5fa533a-3622-4e12-871b-08c876b69328")
                .setCurrency("USD")
                .setAmount("10")
                .clickSaveBatton();
        transactionPage.readyPage();
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        id = res.getInt("id");
        transactionPage.actionButtons.paidAction(id);
        transactionPage.actionButtons.confirmAction(id);
        try {
            transactionPage.actionButtons.linkToAdv(id,"25563");
            Assert.fail("Кнопка есть");
        }catch (TimeoutError e){
            res = getDB().select("SELECT user_id FROM terraleads.accounting_payment where id = (SELECT linked_model_id FROM terraleads.accounting_balance_transactions where id = "+id+")");
            res.next();
            Assert.assertEquals(res.getInt(1),0);
        }
    }

    public void change_status_paidAndManualApprove() throws SQLException, InterruptedException {
        //in
        createIn();
        transactionPage.readyPage();
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        int id = res.getInt("id");
        transactionPage.actionButtons.paidAction(id);
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        Assert.assertEquals(res.getInt("payment_status"),9);
        transactionPage.actionButtons.manualApprove(id,2.2f);
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        Assert.assertEquals(res.getInt("payment_status"),10);

        //out
        createOut();
        transactionPage.readyPage();
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        id = res.getInt("id");
        transactionPage.actionButtons.paidAction(id);
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        Assert.assertEquals(res.getInt("payment_status"),9);
        transactionPage.actionButtons.manualApprove(id,2.2f);
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        Assert.assertEquals(res.getInt("payment_status"),10);
    }

    public void change_status_cancel() throws SQLException {
        //in
        createIn();
        transactionPage.readyPage();
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        int id = res.getInt("id");
        transactionPage.actionButtons.cancelAction(id);
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        Assert.assertEquals(res.getInt("payment_status"),0);

        //out
        createOut();
        transactionPage.readyPage();
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        id = res.getInt("id");
        transactionPage.actionButtons.cancelAction(id);
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        Assert.assertEquals(res.getInt("payment_status"),0);
    }

    public void changeBalance_USD_true() throws SQLException {
        ResultSet res = getDB().select("SELECT * FROM terraleads.users WHERE id = 25554");
        res.next();
        int old_balance = res.getInt("balance_usd");
        new CreatePaymentPage().open().readyPage()
                .setRoutePayment("in")
                .setPurpose_of_payment("testVlad")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPayment_system("Brocard")
                .choseSystem_requisites_account("testPaymentName6")
                .clickAddAdvertiser()
                .setAdvertiser("25554")
                .setAdvertiser_requisite("testAdver")
                .setAccounting_user_requisites_id("testVladCompany")
                .setAccounting_user_requisites_account_id("Название банка")
                .setPayment_typeId("Commission")
                .setBalance_settings(TextLocalization.get("yes"))
                .setCurrency("USD")
                .setAmount("11")
                .clickSaveBatton().readyPage();
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        int id = res.getInt("id");
        transactionPage.open().readyPage().actionButtons.paidAction(id);
        transactionPage.open().readyPage().actionButtons.confirmAction(id);
        res = getDB().select("SELECT * FROM terraleads.users WHERE id = 25554");
        res.next();
        int new_balance = res.getInt("balance_usd");
        Assert.assertEquals(new_balance, old_balance + 11);
    }

    public void changeBalance_USD_false() throws SQLException {
        ResultSet res = getDB().select("SELECT * FROM terraleads.users WHERE id = 25554");
        res.next();
        int old_balance = res.getInt("balance_usd");
        new CreatePaymentPage().open().readyPage()
                .setRoutePayment("in")
                .setPurpose_of_payment("testVlad")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPayment_system("Brocard")
                .choseSystem_requisites_account("testPaymentName6")
                .clickAddAdvertiser()
                .setAdvertiser("25554")
                //.setAdvertiser_requisite("testAdver")
                .setAccounting_user_requisites_id("testVladCompany")
                .setAccounting_user_requisites_account_id("Название банка")
                .setPayment_typeId("Commission")
                .setBalance_settings(TextLocalization.get("no"))
                .setCurrency("USD")
                .setAmount("11")
                .clickSaveBatton().readyPage();
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        int id = res.getInt("id");
        transactionPage.open().readyPage().actionButtons.paidAction(id);
        transactionPage.open().readyPage().actionButtons.confirmAction(id);
        res = getDB().select("SELECT * FROM terraleads.users WHERE id = 25554");
        res.next();
        int new_balance = res.getInt("balance_usd");
        Assert.assertEquals(new_balance, old_balance);
    }

    public void changeBalance_EUR_true() throws SQLException {
        ResultSet res = getDB().select("SELECT * FROM terraleads.users WHERE id = 25554");
        res.next();
        int old_balance = res.getInt("balance_usd");
        new CreatePaymentPage().open().readyPage()
                .setRoutePayment("in")
                .setPurpose_of_payment("testVlad")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPayment_system("Brocard")
                .choseSystem_requisites_account("testPaymentName6")
                .clickAddAdvertiser()
                .setAdvertiser("25554")
                .setAdvertiser_requisite("testAdver")
                .setAccounting_user_requisites_id("testVladCompany")
                .setAccounting_user_requisites_account_id("Название банка")
                .setPayment_typeId("Commission")
                .setBalance_settings(TextLocalization.get("yes"))
                .setCurrency("EUR")
                .setAmount("11")
                .clickSaveBatton().readyPage();
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        int id = res.getInt("id");
        transactionPage.open().readyPage().actionButtons.paidAction(id);
        transactionPage.open().readyPage().actionButtons.confirmAction(id);
        res = getDB().select("SELECT * FROM terraleads.users WHERE id = 25554");
        res.next();
        int new_balance = res.getInt("balance_usd");
        Assert.assertEquals(new_balance, old_balance + new GeneralBalancesPage().open().readyPage().getRate()*11);
    }

    public void changeBalance_EUR_false() throws SQLException {
        ResultSet res = getDB().select("SELECT * FROM terraleads.users WHERE id = 25554");
        res.next();
        int old_balance = res.getInt("balance_usd");
        new CreatePaymentPage().open().readyPage()
                .setRoutePayment("in")
                .setPurpose_of_payment("testVlad")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPayment_system("Brocard")
                .choseSystem_requisites_account("testPaymentName6")
                .clickAddAdvertiser()
                .setAdvertiser("25554")
                //.setAdvertiser_requisite("testAdver")
                .setAccounting_user_requisites_id("testVladCompany")
                .setAccounting_user_requisites_account_id("Название банка")
                .setPayment_typeId("Commission")
                .setBalance_settings(TextLocalization.get("no"))
                .setCurrency("EUR")
                .setAmount("11")
                .clickSaveBatton().readyPage();
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        int id = res.getInt("id");
        transactionPage.open().readyPage().actionButtons.paidAction(id);
        transactionPage.open().readyPage().actionButtons.confirmAction(id);
        res = getDB().select("SELECT * FROM terraleads.users WHERE id = 25554");
        res.next();
        int new_balance = res.getInt("balance_usd");
        Assert.assertEquals(new_balance, old_balance);
    }

    private void createIn(){
        generalBalancesPage.open().readyPage().clickMakeTransferButton();
        new CreatePaymentPage()
                .setRoutePayment("in")
                .setPurpose_of_payment("testVlad")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .choseSystem_requisites_account("testPaymentName6")
                .setPayment_typeId("Commission")
                .setCurrency("USD")
                .setAmount("10")
                .clickSaveBatton();
    }

    private void createOut(){
        generalBalancesPage.open().readyPage().clickMakeTransferButton();
        new CreatePaymentPage()
                .setRoutePayment("out")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new CreatePaymentPage()
                .setPayment_system("Paxum")
                .setPayment_type("Commission")
                .setPurpose_of_payment("testVlad")
                .setCurrency("USD")
                .setAmount("12")
                .clickSaveBatton();
    }

}
