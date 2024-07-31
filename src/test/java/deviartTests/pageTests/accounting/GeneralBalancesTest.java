package deviartTests.pageTests.accounting;

import deviartTests.BaseTest;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.pages.accounting.GeneralBalancesPage;
import org.deviartqa.pages.accounting.payment.CreatePaymentPage;
import org.deviartqa.pages.accounting.payment.PaymentPage;
import org.testng.Assert;
import org.testng.annotations.Test;

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

    public void test_transfers(){
        generalBalancesPage.open().readyPage()
                .clickMakeTransferButton()
                .setCompanyFrom("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setCompanyTo("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setAmount("12")
                .setCommission("2")
                .clickSaveTransferButton().readyPage();


        generalBalancesPage.open().readyPage().clickMakeTransferButton();
        new CreatePaymentPage()
                .setRoutePayment("out")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPayment_system("paxum")
                .setPayment_type("Commission")
                .setPurpose_of_payment("testVlad")
                .setCurrency("USD")
                .setAmount("12")
                .setPeriod_from("2024-07-05")
                .clickSaveBatton();

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
