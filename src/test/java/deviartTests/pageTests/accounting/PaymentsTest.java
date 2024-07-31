package deviartTests.pageTests.accounting;

import deviartTests.BaseTest;
import org.deviartqa.TestScenario;
import org.deviartqa.api.accounting.PaymentAPI;
import org.deviartqa.core.DBconnector;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.helper.TextLocalization;
import org.deviartqa.pages.accounting.payment.CreatePaymentPage;
import org.deviartqa.pages.accounting.payment.PaymentPage;
import org.deviartqa.pages.accounting.payment.ViewPaymentPage;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Test
public class PaymentsTest extends BaseTest {

    private CreatePaymentPage createPaymentPage = new CreatePaymentPage();
    private PaymentPage paymentPage = new PaymentPage();
    private ViewPaymentPage viewPaymentPage = new ViewPaymentPage();

    public void test_searchFields() throws SQLException {
        String resultLoc = "//tbody/tr";
        //Search by ID
        paymentPage.open().readyPage()
                .setID("3").clickShowResultButton();
        Widget result = new Widget(Locators.page.locator(resultLoc));
        Assert.assertTrue(result.textContent().substring(0,2).contains("3"));
        ResultSet sqlRes = getBD_by("id=3",true);
        sqlRes.next();
        Assert.assertEquals(result.element.count(),sqlRes.getInt(1));

        //Search by Advertiser
        paymentPage.open().readyPage()
                .setAdvertiser("25563").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i<result.element.count();i++){
            Assert.assertTrue(result.element.nth(i).textContent().contains("25563"));
        }
        sqlRes = getBD_by("user_id = 25563",true);
        sqlRes.next();
        Assert.assertEquals(result.element.count(),sqlRes.getInt(1));

        //Search by System requisites
        paymentPage.open().readyPage()
                .setSystem_requisites("Testbb975fc9-83c7-4f0a-ae38-391e0e1bb2a4").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i<result.element.count();i++){
            Assert.assertTrue(result.element.nth(i).textContent().contains("Testbb975fc9-83c7-4f0a-ae38-391e0e1bb2a4"));
        }
        sqlRes = getBD_by("accounting_system_requisites_id = 41",true);
        sqlRes.next();
        Assert.assertEquals(result.element.count(),sqlRes.getInt(1));

        //Search by Status
        paymentPage.open().readyPage()
                .setStatus("Payment status cancel").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i<result.element.count();i++){
            Assert.assertTrue(result.element.nth(i).textContent().contains("Payment status cancel"));
        }
        sqlRes = getBD_by("status = 4",true);
        sqlRes.next();
        Assert.assertEquals(result.element.count(),sqlRes.getInt(1));

        //Search by Currency
        paymentPage.open().readyPage()
                .setCurrency("USD").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i<result.element.count();i++){
            Assert.assertTrue(result.element.nth(i).textContent().contains("USD"));
        }

        //Search by Advertiser
        paymentPage.open().readyPage()
                .setInvoice("3").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i<result.element.count();i++){
            Assert.assertTrue(result.element.nth(i).textContent().contains("#3 - Invoice"));
        }
        sqlRes = getBD_by("accounting_invoice_id = 3",true);
        sqlRes.next();
        Assert.assertEquals(result.element.count(),sqlRes.getInt(1));
    }

    public void create_payment_IN() throws SQLException {
        createPaymentPage.open().readyPage()
                .setRoutePayment("in")
                .setAdvertiser("25554")
                .setPayment_type("Payment type other")
                .setPayment_system("Paxum")
                .setAdvertiser_requisite("Test"+ DataHelper.getUuid())
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .choseSystem_requisites_account("testPaymentName0")
                .setCurrency("USD")
                .setAmount("10")
                .clickSaveBatton().readyPage();
        ResultSet sqlRes = getBD_by("seller_company_name='Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de' order by id desc limit 1",false);
        sqlRes.next();
        Assert.assertEquals(sqlRes.getString("user_id"),"25554");
        Assert.assertEquals(sqlRes.getInt("currency_id"),1);
        Assert.assertEquals(sqlRes.getInt("amount"),10);
        Assert.assertEquals(sqlRes.getString("payment_system"),"paxum");
        Assert.assertEquals(sqlRes.getString("payment_allocation"),"in");
    }

    public void changeStatus_payment() throws SQLException {
        create_payment_IN();
        ResultSet sqlRes = getBD_by("id > 0 order by id desc",false);
        sqlRes.next();
        PaymentAPI api = new PaymentAPI();
        int payment_id = sqlRes.getInt("id");
        api.changeStatus(payment_id, PaymentAPI.PaymentStatus.Paid);
        paymentPage.open().readyPage();
        String payment_status = new Widget(Locators.page.locator("//td[text()='"+payment_id+"']/..//span")).textContent();
        Assert.assertEquals(payment_status, (TestScenario.local.equals("en") ? "Payment status paid" : ""));
    }

    public void create_payment_OUT() throws SQLException, InterruptedException {
        createPaymentPage.open().readyPage()
                .setRoutePayment("out")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de");
        Thread.sleep(2000);
        createPaymentPage
                .setPayment_system("paxum")
                .setPayment_type("Commission")
                .setPurpose_of_payment("testVlad")
                .setCurrency("USD")
                .setAmount("12")
                .setPeriod_from("2024-07-05")
                .clickSaveBatton().readyPage();
        ResultSet sqlRes = getBD_by("seller_company_name='Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de' order by id desc limit 1",false);
        sqlRes.next();
        Assert.assertEquals(sqlRes.getInt("currency_id"),1);
        Assert.assertEquals(sqlRes.getInt("amount"),12);
        Assert.assertEquals(sqlRes.getString("payment_system"),"paxum");
        Assert.assertEquals(sqlRes.getString("payment_allocation"),"out");
    }

    public void create_payment_OUT_testAmount(){
        createPaymentPage.open().readyPage()
                .setRoutePayment("out")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPayment_type("Commission")
                .setPurpose_of_payment("testVlad")
                .setCurrency("USD");
        List.of("","0","-1","dsad","12.12","12,13").forEach(x -> {
            createPaymentPage.setAmount(x).clickSaveBatton();
            createPaymentPage.readyPage();
        });
    }

    public void create_payment_OUT_testFields(){
        //Without System_company
        createPaymentPage.open().readyPage()
                .setRoutePayment("out")
                .setPayment_type("Commission")
                .setPurpose_of_payment("testVlad")
                .setCurrency("USD")
                .setAmount("12")
                .setPeriod_from("2024-07-05")
                .clickSaveBatton();
        createPaymentPage.readyPage();

        //Without Payment_type
        createPaymentPage.open().readyPage()
                .setRoutePayment("out")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .choseSystem_requisites_account("testPaymentName0")
                .setPurpose_of_payment("testVlad")
                .setCurrency("USD")
                .setAmount("12")
                .setPeriod_from("2024-07-05")
                .clickSaveBatton();
        createPaymentPage.readyPage();

        //Without Purpose_of_payment
        createPaymentPage.open().readyPage()
                .setRoutePayment("out")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .choseSystem_requisites_account("testPaymentName0")
                .setPayment_type("Commission")
                .setCurrency("USD")
                .setAmount("12")
                .setPeriod_from("2024-07-05")
                .clickSaveBatton().readyPage();

        //Without Amount
        createPaymentPage.open().readyPage()
                .setRoutePayment("out")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .choseSystem_requisites_account("testPaymentName0")
                .setPayment_type("Commission")
                .setCurrency("USD")
                .setPurpose_of_payment("testVlad")
                .setPeriod_from("2024-07-05")
                .clickSaveBatton();
        createPaymentPage.readyPage();

        //Without Period_from
        createPaymentPage.open().readyPage()
                .setRoutePayment("out")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .choseSystem_requisites_account("testPaymentName0")
                .setPayment_type("Commission")
                .setCurrency("USD")
                .setPurpose_of_payment("testVlad")
                .setAmount("12")
                .clickSaveBatton();
        createPaymentPage.readyPage();
    }

    public void viewPayment_test() throws SQLException, InterruptedException {
        create_payment_IN();
        ResultSet sqlRes = getBD_by("seller_company_name='Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de' order by id desc limit 1",false);
        sqlRes.next();
        Assert.assertTrue(viewPaymentPage.getParametersValue("Id").contains(sqlRes.getString("id")));
        Assert.assertTrue(viewPaymentPage.getParametersValue(TextLocalization.get("amount")).contains(sqlRes.getString("amount")));
        Assert.assertTrue(viewPaymentPage.getParametersValue(TextLocalization.get("payment_type")).contains(sqlRes.getString("payment_type")));

        create_payment_OUT();
        sqlRes = getBD_by("seller_company_name='Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de' order by id desc limit 1",false);
        sqlRes.next();
        Assert.assertTrue(viewPaymentPage.getParametersValue("Id").contains(sqlRes.getString("id")));
        Assert.assertTrue(viewPaymentPage.getParametersValue(TextLocalization.get("amount")).contains(sqlRes.getString("amount")));
        Assert.assertTrue(viewPaymentPage.getParametersValue(TextLocalization.get("payment_type")).contains(sqlRes.getString("payment_type")));
    }

    DBconnector dBconnector;
    @BeforeTest
    public void openBD(){
        dBconnector = new DBconnector();
    }

    private ResultSet getBD_by(String condition,boolean count){
        String sql = "";
        if(count){
            sql = "SELECT count(*) FROM terraleads.accounting_payment where ";
        }else {
            sql = "SELECT * FROM terraleads.accounting_payment where ";
        }
        return dBconnector.select(sql+condition);
    }

}
