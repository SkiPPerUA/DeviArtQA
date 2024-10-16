package deviartTests.pageTests.accounting;

import deviartTests.BaseTest;
import org.deviartqa.TestScenario;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
import org.deviartqa.helper.TestCases;
import org.deviartqa.pages.accounting.GeneralBalancesPage;
import org.deviartqa.pages.accounting.TransactionPage;
import org.deviartqa.pages.accounting.payment.CreatePaymentPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Test
public class GeneralBalancesTest extends BaseTest {

    GeneralBalancesPage generalBalancesPage = new GeneralBalancesPage();

    public void test_totalImbalance() throws SQLException {
        generalBalancesPage.open().readyPage();
        Assert.assertEquals((generalBalancesPage.getTotalExpected()- generalBalancesPage.getTotalRequirement()+ generalBalancesPage.getTotalBalance()),generalBalancesPage.getTotalImbalance());
        ResultSet res = getDB().select("SELECT sum(sum) FROM terraleads.payouts where status = 'wait'");
        res.next();
        Assert.assertEquals(generalBalancesPage.getTotalToPayment(), res.getDouble(1));
    }

    public void test_transfers_positive() throws InterruptedException, SQLException {
        ResultSet res;
        //between - all fields
        generalBalancesPage.open().readyPage()
                .clickMakeTransferButton()
                .setCompanyFrom("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPaymentSystemFrom("Payoneer")
                .setCompanyTo("Testb527b237-ae38-460e-9e49-9d1d7f015f8e")
                .setPaymentSystemTo("Brocard");
        Thread.sleep(1000);
        generalBalancesPage
                .setAmount("15")
                .setCommissionFrom("currency","2")
                .setCommissionTo("percent","25")
                .clickSaveTransferButton().readyPage();
        res = getDB().select("SELECT * FROM terraleads.accounting_balance_transactions order by id desc limit 1");
        res.next();
        Assert.assertEquals(res.getInt("payment_status"),10);
        Assert.assertEquals(res.getInt("original_amount"),15);
        Assert.assertEquals(res.getInt("commission"),2);
        Assert.assertEquals(res.getFloat("commission_to"),3.75);

        //between - without commission
        generalBalancesPage.open().readyPage()
                .clickMakeTransferButton()
                .setCompanyFrom("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPaymentSystemFrom("Payoneer")
                .setCompanyTo("Testb527b237-ae38-460e-9e49-9d1d7f015f8e")
                .setPaymentSystemTo("Brocard");
        Thread.sleep(1000);
        generalBalancesPage
                .setAmount("15")
                .setCommissionFrom("disabled","2")
                .setCommissionTo("disabled","25")
                .clickSaveTransferButton().readyPage();

        //between - commission float
        generalBalancesPage.open().readyPage()
                .clickMakeTransferButton()
                .setCompanyFrom("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPaymentSystemFrom("Payoneer")
                .setCompanyTo("Testb527b237-ae38-460e-9e49-9d1d7f015f8e")
                .setPaymentSystemTo("Brocard");
        Thread.sleep(1000);
        generalBalancesPage
                .setAmount("15")
                .setCommissionFrom("currency","2.34")
                .setCommissionTo("percent","25.54")
                .clickSaveTransferButton().readyPage();

        //in
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
        new TransactionPage().readyPage();

        //out
        generalBalancesPage.open().readyPage().clickMakeTransferButton();
        new CreatePaymentPage()
                .setRoutePayment("out")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de");
        Thread.sleep(2000);
        new CreatePaymentPage()
                .setPayment_system("Paxum")
                .setPayment_type("Commission")
                .setPurpose_of_payment("testVlad")
                .setCurrency("USD")
                .setAmount("-12")
                .clickSaveBatton();
        new TransactionPage().readyPage();
    }

    public void test_transfers_same_account_negative() {
        generalBalancesPage.open().readyPage()
                .clickMakeTransferButton()
                .setCompanyFrom("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPaymentSystemFrom("Payoneer")
                .setCompanyTo("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPaymentSystemTo("Payoneer")
                .setAmount("15")
                .setCommissionFrom("currency","2")
                .setCommissionTo("percent","25")
                .clickSaveTransferButton();
        generalBalancesPage.readyPage();
    }

    public void test_buttons(){
        generalBalancesPage.open().readyPage()
                .clickTransferButton().readyPage();
    }

    @Test(enabled = TestScenario.enable)
    public void checkAccess(){
        TestCases.checkAccessToPage(()-> generalBalancesPage.open().readyPage());
    }
}
