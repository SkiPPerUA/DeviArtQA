package deviartTests.pageTests.accounting;

import deviartTests.BaseTest;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
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

    public void test_totalImbalance(){
        generalBalancesPage.open().readyPage();
        Assert.assertEquals((generalBalancesPage.getTotalExpected()- generalBalancesPage.getTotalRequirement()+ generalBalancesPage.getTotalBalance()),generalBalancesPage.getTotalImbalance());
    }

    public void test_changeRate(){
        List<Double> expectAmountsEUR = new ArrayList<>();
        generalBalancesPage.open().readyPage();
        generalBalancesPage.changeRate("1");
        List<Double> beforeChangeRateEUR = findAllAmount("EUR");
        List<Double> beforeChangeRateUSD = findAllAmount("USD");

        generalBalancesPage.changeRate("3");
        List<Double> afterChangeRateEUR = findAllAmount("EUR");
        List<Double> afterChangeRateUSD = findAllAmount("USD");

        beforeChangeRateEUR.forEach(x -> expectAmountsEUR.add(x*3));
        Assert.assertEquals(expectAmountsEUR,afterChangeRateEUR);
        Assert.assertEquals(beforeChangeRateUSD,afterChangeRateUSD);
    }

    public void test_transfers_positive() throws InterruptedException, SQLException {
        ResultSet res;
        //between - all fields
        generalBalancesPage.open().readyPage()
                .clickMakeTransferButton()
                .setCompanyFrom("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPaymentSystemFrom("payoneer")
                .setCompanyTo("Testb527b237-ae38-460e-9e49-9d1d7f015f8e")
                .setPaymentSystemTo("brocard")
                .setAmount("15")
                .setCommissionFrom("currency","2")
                .setCommissionTo("percent","25")
                .clickSaveTransferButton().readyPage();
        res = getDB().select("SELECT * FROM terraleads.accounting_payment where seller_company_name='Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de' order by id desc limit 1");
        res.next();
        Assert.assertEquals(res.getInt("status"),555);
        Assert.assertEquals(res.getInt("amount"),15);
        Assert.assertEquals(res.getString("payment_allocation"),"between");

        //between - without commission
        generalBalancesPage.open().readyPage()
                .clickMakeTransferButton()
                .setCompanyFrom("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPaymentSystemFrom("payoneer")
                .setCompanyTo("Testb527b237-ae38-460e-9e49-9d1d7f015f8e")
                .setPaymentSystemTo("brocard")
                .setAmount("15")
                .setCommissionFrom("disabled","2")
                .setCommissionTo("disabled","25")
                .clickSaveTransferButton().readyPage();

        //between - commission float
        generalBalancesPage.open().readyPage()
                .clickMakeTransferButton()
                .setCompanyFrom("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPaymentSystemFrom("payoneer")
                .setCompanyTo("Testb527b237-ae38-460e-9e49-9d1d7f015f8e")
                .setPaymentSystemTo("brocard")
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
                .choseSystem_requisites_account("testPaymentName0")
                .setCurrency("USD")
                .setAmount("10")
                .clickSaveBatton().readyPage();

        //out
        generalBalancesPage.open().readyPage().clickMakeTransferButton();
        new CreatePaymentPage()
                .setRoutePayment("out")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de");
        Thread.sleep(2000);
        new CreatePaymentPage()
                .setPayment_system("paxum")
                .setPayment_type("Commission")
                .setPurpose_of_payment("testVlad")
                .setCurrency("USD")
                .setAmount("12")
                .clickSaveBatton();
        new TransactionPage().readyPage();
    }

    public void test_transfers_same_account_negative() {
        generalBalancesPage.open().readyPage()
                .clickMakeTransferButton()
                .setCompanyFrom("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPaymentSystemFrom("payoneer")
                .setCompanyTo("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPaymentSystemTo("payoneer")
                .setAmount("15")
                .setCommissionFrom("currency","2")
                .setCommissionTo("percent","25")
                .clickSaveTransferButton();
        generalBalancesPage.readyPage();
    }

    private List<Double> findAllAmount(String currency){
        List<Double> amounts = new ArrayList<>();
        Widget www = new Widget(Locators.page.locator("//td[contains(text(),'"+currency+"')]/../td[text() != '0.00']"));
        for (int i = 0; i < www.element.count(); i++){
            try {
                Double d = Double.valueOf(www.element.nth(i).textContent());
                amounts.add(d);
            }catch (Throwable e){
            }
        }
        return amounts;
    }
}
