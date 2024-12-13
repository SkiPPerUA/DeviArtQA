package deviartTests.pageTests.accounting;

import com.microsoft.playwright.TimeoutError;
import deviartTests.BaseTest;
import org.deviartqa.TestScenario;
import org.deviartqa.api.accounting.PaymentAPI;
import org.deviartqa.core.DBconnector;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Session;
import org.deviartqa.core.Widget;
import org.deviartqa.helper.TestCases;
import org.deviartqa.helper.TextLocalization;
import org.deviartqa.pages.accounting.GeneralBalancesPage;
import org.deviartqa.pages.accounting.TransactionPage;
import org.deviartqa.pages.accounting.payment.CreatePaymentPage;
import org.deviartqa.pages.accounting.payment.PaymentPage;
import org.deviartqa.pages.accounting.payment.UpdatePaymentPage;
import org.deviartqa.pages.accounting.payment.ViewPaymentPage;
import org.opentest4j.AssertionFailedError;
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
    private UpdatePaymentPage updatePaymentPage = new UpdatePaymentPage();
    String system_company = "Testd068e119-d1ef-4998-b93f-1bc108dd6d21";
    private float rate;
    //-89.4774	-96.6357
    //-100.589	-108.636

    private void ttt() throws InterruptedException {
        List<String> acc = List.of("PayPal", "Paxum", "Cash", "Payoneer");
        for (int i = 0; i < acc.size(); i++) {
            createPaymentPage.open().readyPage()
                    .setRoutePayment("in")
                    .setPurpose_of_payment("testVlad")
                    .setSystem_company("TestType")
                    .setPayment_system(acc.get(i))
                    .setPayment_typeId("Advertisement")
                    .setCurrency("USD")
                    .setAmount(String.valueOf(i+10))
                    .setPayment_period("2024-08-01")
                    .clickSaveBatton().readyPage();

            createPaymentPage.open().readyPage()
                    .setRoutePayment("out")
                    .setSystem_company("TestType");
            Thread.sleep(2000);
            createPaymentPage
                    .setPayment_system(acc.get(i))
                    .setPayment_type("Advertisement")
                    .setPurpose_of_payment("testVlad")
                    .setCurrency("USD")
                    .setAmount(String.valueOf(i+20))
                    .setPayment_period("2024-08-01")
                    .clickSaveBatton().readyPage();
        }
    }

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
        Assert.assertTrue(new Widget(Locators.page.locator("//div[@class='summary']")).textContent().contains(String.valueOf(sqlRes.getInt(1))));


        //Search by Currency
        paymentPage.open().readyPage()
                .setCurrency("USD").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i<result.element.count();i++){
            Assert.assertTrue(result.element.nth(i).textContent().contains("USD"));
        }

        //Search by Invoice
        paymentPage.open().readyPage()
                .setInvoice("3").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i<result.element.count();i++){
            Assert.assertTrue(result.element.nth(i).textContent().contains("#3 - Invoice"));
        }
        sqlRes = getBD_by("accounting_invoice_id = 3",true);
        sqlRes.next();
        Assert.assertEquals(result.element.count(),sqlRes.getInt(1));

        //Search by PaymentType
        paymentPage.open().readyPage()
                .setPayment_type("Commission").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i<result.element.count();i++){
            Assert.assertTrue(result.element.nth(i).textContent().contains("Commission"));
        }
        sqlRes = getBD_by("payment_type_id = 1",true);
        sqlRes.next();
        Assert.assertTrue(new Widget(Locators.page.locator("//div[@class='summary']")).textContent().contains(String.valueOf(sqlRes.getInt(1))));
    }

    public void create_payment_IN() throws SQLException {
        createPaymentPage.open().readyPage()
                .setRoutePayment("in")
                .setPurpose_of_payment("testVlad")
                .setSystem_company(system_company)
                .setPayment_system("Payoneer")
                .choseSystem_requisites_account("testPaymentName3")
                .setPayment_typeId("Commission")
                .setCurrency("USD")
                .setAmount("10")
                .setPayment_period("2024-08-01")
                .clickSaveBatton().readyPage();
        ResultSet sqlRes = getBD_by("seller_company_name='"+system_company+"' order by id desc limit 1",false);
        sqlRes.next();
        Assert.assertEquals(sqlRes.getInt("currency_id"),1);
        Assert.assertEquals(sqlRes.getInt("amount"),10);
        Assert.assertEquals(sqlRes.getInt("system_currency_amount"),10);
        Assert.assertEquals(sqlRes.getString("payment_allocation"),"in");
        Assert.assertNull(sqlRes.getString("user_id"));
        //Assert.assertEquals(sqlRes.getString("payment_system"),"brocard");
        Assert.assertEquals(sqlRes.getString("purpose_of_payment"),"testVlad");
        Assert.assertEquals(sqlRes.getInt("payment_type_id"),1);
        Assert.assertEquals(sqlRes.getString("payment_period"),"2024-08-01");
    }

    public void create_payment_IN_EUR() throws SQLException {
        createPaymentPage.open().readyPage()
                .setRoutePayment("in")
                .setPurpose_of_payment("testVlad")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPayment_system("Paxum")
                .choseSystem_requisites_account("testPaymentName1")
                .setPayment_typeId("Commission")
                .setCurrency("EUR")
                .setAmount("20")
                .setPayment_period("2024-08-01")
                .clickSaveBatton().readyPage();
        ResultSet sqlRes = getBD_by("seller_company_name='Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de' order by id desc limit 1",false);
        sqlRes.next();
        Assert.assertEquals(sqlRes.getInt("currency_id"),6);
        Assert.assertEquals(sqlRes.getInt("amount"),20);
        Assert.assertEquals(sqlRes.getFloat("system_currency_amount"),20*rate);
        Assert.assertEquals(sqlRes.getString("payment_allocation"),"in");
        Assert.assertNull(sqlRes.getString("user_id"));
//        Assert.assertEquals(sqlRes.getString("payment_system"),"brocard");
        Assert.assertEquals(sqlRes.getString("purpose_of_payment"),"testVlad");
        Assert.assertEquals(sqlRes.getInt("payment_type_id"),1);
        Assert.assertEquals(sqlRes.getString("payment_period"),"2024-08-01");
    }

    public void create_payment_IN_allFields() throws SQLException {
        createPaymentPage.open().readyPage()
                .setRoutePayment("in")
                .setPurpose_of_payment("testVlad")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPayment_system("Paxum")
                .choseSystem_requisites_account("testPaymentName1")
                .clickAddAdvertiser()
                .setAdvertiser("25554")
                //.setAdvertiser_requisite("testAdver")
                .setAccounting_user_requisites_id("testVladCompany")
                .setAccounting_user_requisites_account_id("Название банка")
                .setPayment_typeId("Commission")
                .setCurrency("USD")
                .setAmount("11")
                .setPayment_period("2024-08-01")
                .clickSaveBatton().readyPage();
        ResultSet sqlRes = getBD_by("seller_company_name='Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de' order by id desc limit 1",false);
        sqlRes.next();
        Assert.assertEquals(sqlRes.getInt("currency_id"),1);
        Assert.assertEquals(sqlRes.getInt("amount"),11);
        Assert.assertEquals(sqlRes.getInt("system_currency_amount"),11);
        Assert.assertEquals(sqlRes.getString("payment_allocation"),"in");
        Assert.assertNull(sqlRes.getString("advertiser_requisite"));
        Assert.assertEquals(sqlRes.getInt("user_id"),25554);
//        Assert.assertEquals(sqlRes.getString("payment_system"),"brocard");
        Assert.assertEquals(sqlRes.getString("purpose_of_payment"),"testVlad");
        Assert.assertEquals(sqlRes.getInt("payment_type_id"),1);
        Assert.assertEquals(sqlRes.getString("payment_period"),"2024-08-01");
    }

    public void changeStatus_payment() throws SQLException {
        create_payment_IN();
        ResultSet sqlRes = getBD_by("id > 0 order by id desc",false);
        sqlRes.next();
        int payment_id = sqlRes.getInt("id");
        paymentPage.open().readyPage().actionButtons.paidAction(payment_id);
        String payment_status = new Widget(Locators.page.locator("//td[text()='"+payment_id+"']/..//span")).textContent();
        Assert.assertEquals(payment_status, (TestScenario.local.equals("en") ? "Payment status paid" : "Оплачен"));

        create_payment_OUT();
        sqlRes = getBD_by("id > 0 order by id desc",false);
        sqlRes.next();
        payment_id = sqlRes.getInt("id");
        paymentPage.open().readyPage().actionButtons.paidAction(payment_id);
        payment_status = new Widget(Locators.page.locator("//td[text()='"+payment_id+"']/..//span")).textContent();
        Assert.assertEquals(payment_status, (TestScenario.local.equals("en") ? "Payment status paid" : "Оплачен"));
    }

    public void create_payment_OUT() throws SQLException {
        createPaymentPage.open().readyPage()
                .setRoutePayment("out")
                .setSystem_company(system_company)
                .setPayment_system("Paxum")
                .setPayment_type("Commission")
                .setPurpose_of_payment("testVlad")
                .setCurrency("USD")
                .setAmount("12")
                .setPayment_period("2024-08-01")
                .clickSaveBatton().readyPage();
        ResultSet sqlRes = getBD_by("seller_company_name='"+system_company+"' order by id desc limit 1",false);
        sqlRes.next();
        Assert.assertEquals(sqlRes.getInt("currency_id"),1);
        Assert.assertEquals(sqlRes.getInt("amount"),12);
        Assert.assertEquals(sqlRes.getInt("system_currency_amount"),12);
        Assert.assertEquals(sqlRes.getString("payment_allocation"),"out");
        Assert.assertNull(sqlRes.getString("user_id"));
        Assert.assertEquals(sqlRes.getString("payment_system"),"paxum");
        Assert.assertEquals(sqlRes.getString("purpose_of_payment"),"testVlad");
        Assert.assertEquals(sqlRes.getInt("payment_type_id"),1);
        Assert.assertEquals(sqlRes.getString("payment_period"),"2024-08-01");
    }

    public void create_payment_OUT_EUR() throws SQLException, InterruptedException {
        createPaymentPage.open().readyPage()
                .setRoutePayment("out")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de");
        Thread.sleep(2000);
        createPaymentPage
                .setPayment_system("Paxum")
                .setPayment_type("Commission")
                .setPurpose_of_payment("testVlad")
                .setCurrency("EUR")
                .setAmount("22")
                .setPayment_period("2024-08-01")
                .clickSaveBatton().readyPage();
        ResultSet sqlRes = getBD_by("seller_company_name='Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de' order by id desc limit 1",false);
        sqlRes.next();
        Assert.assertEquals(sqlRes.getInt("currency_id"),6);
        Assert.assertEquals(sqlRes.getInt("amount"),22);
        Assert.assertEquals(sqlRes.getFloat("system_currency_amount"),22*rate);
        Assert.assertEquals(sqlRes.getString("payment_allocation"),"out");
        Assert.assertNull(sqlRes.getString("user_id"));
        Assert.assertEquals(sqlRes.getString("payment_system"),"paxum");
        Assert.assertEquals(sqlRes.getString("purpose_of_payment"),"testVlad");
        Assert.assertEquals(sqlRes.getInt("payment_type_id"),1);
        Assert.assertEquals(sqlRes.getString("payment_period"),"2024-08-01");
    }

    public void create_payment_testAmount(){
        List<String> amount = List.of("","0","-1","dsad");

        //out
        amount.forEach(x -> {
            createPaymentPage.open().readyPage()
                    .setRoutePayment("out")
                    .setSystem_company(system_company)
                    .setPayment_type("Commission")
                    .setPurpose_of_payment("testVlad")
                    .setCurrency("USD")
                    .setAmount(x);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            createPaymentPage.clickSaveBatton();
           try {
               viewPaymentPage.readyPage();
               System.out.println(x + " bags");
           }catch (AssertionFailedError e){
           }
        });

        //in
        amount.forEach(x -> {
            createPaymentPage.open().readyPage()
                    .setRoutePayment("in")
                    .setPurpose_of_payment("testVlad")
                    .setSystem_company(system_company)
                    .setPayment_system("Payoneer")
                    .choseSystem_requisites_account("testPaymentName3")
                    .setPayment_typeId("Commission")
                    .setCurrency("USD")
                    .setAmount(x);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            createPaymentPage.clickSaveBatton();
            try {
                viewPaymentPage.readyPage();
                System.out.println(x + " bags");
            }catch (AssertionFailedError e){
            }
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
                .setPayment_period("2024-08-01")
                .clickSaveBatton();
        createPaymentPage.readyPage();

        //Without Payment_type
        createPaymentPage.open().readyPage()
                .setRoutePayment("out")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPurpose_of_payment("testVlad")
                .setCurrency("USD")
                .setAmount("12")
                .setPayment_period("2024-08-01")
                .clickSaveBatton();
        createPaymentPage.readyPage();

        //Without Purpose_of_payment
        createPaymentPage.open().readyPage()
                .setRoutePayment("out")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPayment_type("Commission")
                .setCurrency("USD")
                .setPayment_period("2024-08-01")
                .setAmount("12");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        createPaymentPage
                .clickSaveBatton().readyPage();

        //Without Currency
        createPaymentPage.open().readyPage()
                .setRoutePayment("out")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPayment_type("Commission")
                .setPurpose_of_payment("testVlad")
                .setAmount("12")
                .setPayment_period("2024-08-01")
                .clickSaveBatton();
        createPaymentPage.readyPage();

        //Without Amount
        createPaymentPage.open().readyPage()
                .setRoutePayment("out")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPayment_type("Commission")
                .setCurrency("USD")
                .setPurpose_of_payment("testVlad")
                .setPayment_period("2024-08-01")
                .clickSaveBatton();
        createPaymentPage.readyPage();

        //Without Payment_period
        createPaymentPage.open().readyPage()
                .setRoutePayment("out")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPayment_type("Commission")
                .setCurrency("USD")
                .setPurpose_of_payment("testVlad")
                .setAmount("12")
                .clickSaveBatton();
        createPaymentPage.readyPage();
    }

    public void create_payment_IN_testFields(){
        //Without Payment_type
        createPaymentPage.open().readyPage()
                .setRoutePayment("in")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setCurrency("USD")
                .setAmount("10")
                .setPayment_period("2024-08-01")
                .clickSaveBatton();
        createPaymentPage.readyPage();

        //Without Currency
        createPaymentPage.open().readyPage()
                .setRoutePayment("in")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPayment_typeId("Commission")
                .setAmount("10")
                .setPayment_period("2024-08-01")
                .clickSaveBatton();
        createPaymentPage.readyPage();

        //Without Amount
        createPaymentPage.open().readyPage()
                .setRoutePayment("in")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPayment_typeId("Commission")
                .setCurrency("USD")
                .setPayment_period("2024-08-01")
                .clickSaveBatton();
        createPaymentPage.readyPage();

        //Without System_company
        createPaymentPage.open().readyPage()
                .setRoutePayment("in")
                .setPayment_typeId("Commission")
                .setCurrency("USD")
                .setAmount("10")
                .setPayment_period("2024-08-01")
                .clickSaveBatton();
        createPaymentPage.readyPage();

        //Without Payment_period
        createPaymentPage.open().readyPage()
                .setRoutePayment("in")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPayment_typeId("Commission")
                .setCurrency("USD")
                .setAmount("10")
                .clickSaveBatton();
        createPaymentPage.readyPage();
    }

    public void viewPayment_test() throws SQLException {
        create_payment_IN();
        ResultSet sqlRes = getBD_by("seller_company_name='"+system_company+"' order by id desc limit 1",false);
        sqlRes.next();
        Assert.assertTrue(viewPaymentPage.getParametersValue("Id").contains(sqlRes.getString("id")));
        Assert.assertTrue(viewPaymentPage.getParametersValue(TextLocalization.get("amount")).contains(sqlRes.getString("amount")));
        Assert.assertTrue(viewPaymentPage.getParametersValue(TextLocalization.get("payment_type")).contains("Commission"));

        create_payment_OUT();
        sqlRes = getBD_by("seller_company_name='"+system_company+"' order by id desc limit 1",false);
        sqlRes.next();
        Assert.assertTrue(viewPaymentPage.getParametersValue("Id").contains(sqlRes.getString("id")));
        Assert.assertTrue(viewPaymentPage.getParametersValue(TextLocalization.get("amount")).contains(sqlRes.getString("amount")));
        Assert.assertTrue(viewPaymentPage.getParametersValue(TextLocalization.get("payment_type")).contains("Commission"));
        Assert.assertEquals(sqlRes.getInt("payment_type_id"),1);
    }

    public void correct_payment_IN_EUR() throws SQLException {
        create_payment_IN();
        StringBuffer url = new StringBuffer(Session.getPage().url());
        int old_id = Integer.parseInt(String.valueOf(url.delete(0,url.indexOf("id=")+3)));
        updatePaymentPage.open(old_id).readyPage()
                .setPurpose_of_payment("testVlad")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPayment_typeId("Commission")
                .setCurrency("EUR")
                .setAmount("11")
                .setPayment_period("2024-03-01")
                .clickSaveBatton().readyPage();
        url = new StringBuffer(Session.getPage().url());
        int new_id = Integer.parseInt(String.valueOf(url.delete(0,url.indexOf("id=")+3)));
        ResultSet sqlRes = getBD_by("id = "+new_id,false);
        sqlRes.next();
        Assert.assertEquals(sqlRes.getInt("currency_id"),6);
        Assert.assertEquals(sqlRes.getInt("amount"),11);
        Assert.assertEquals(sqlRes.getFloat("system_currency_amount"),11*rate);
        Assert.assertEquals(sqlRes.getInt("status"),1);
        Assert.assertEquals(sqlRes.getString("payment_allocation"),"in");
        Assert.assertEquals(sqlRes.getString("payment_period"),"2024-03-01");

        sqlRes = getBD_by("id = "+old_id,false);
        sqlRes.next();
        Assert.assertEquals(sqlRes.getInt("status"),4);
    }

    public void correct_payment_OUT_EUR() throws SQLException, InterruptedException {
        create_payment_OUT();
        StringBuffer url = new StringBuffer(Session.getPage().url());
        int old_id = Integer.parseInt(String.valueOf(url.delete(0,url.indexOf("id=")+3)));
        updatePaymentPage.open(old_id).readyPage()
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de");
        Thread.sleep(2000);
        createPaymentPage
                .setPayment_system("Paxum")
                .setPayment_type("Commission")
                .setPurpose_of_payment("testVlad")
                .setCurrency("EUR")
                .setAmount("15")
                .setPayment_period("2024-03-01")
                .clickSaveBatton().readyPage();
        url = new StringBuffer(Session.getPage().url());
        int new_id = Integer.parseInt(String.valueOf(url.delete(0,url.indexOf("id=")+3)));
        ResultSet sqlRes = getBD_by("id = "+new_id,false);
        sqlRes.next();
        Assert.assertEquals(sqlRes.getInt("currency_id"),6);
        Assert.assertEquals(sqlRes.getInt("amount"),15);
        Assert.assertEquals(sqlRes.getFloat("system_currency_amount"),15*rate);
        Assert.assertEquals(sqlRes.getInt("status"),1);
        Assert.assertEquals(sqlRes.getString("payment_allocation"),"out");
        Assert.assertEquals(sqlRes.getString("payment_period"),"2024-03-01");

        sqlRes = getBD_by("id = "+old_id,false);
        sqlRes.next();
        Assert.assertEquals(sqlRes.getInt("status"),4);
    }

    public void correct_payment_IN_USD() throws SQLException {
        create_payment_IN();
        StringBuffer url = new StringBuffer(Session.getPage().url());
        int old_id = Integer.parseInt(String.valueOf(url.delete(0,url.indexOf("id=")+3)));
        updatePaymentPage.open(old_id).readyPage()
                .setPurpose_of_payment("testVlad")
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPayment_typeId("Commission")
                .setCurrency("USD")
                .setAmount("11")
                .setPayment_period("2024-03-01")
                .clickSaveBatton().readyPage();
        url = new StringBuffer(Session.getPage().url());
        int new_id = Integer.parseInt(String.valueOf(url.delete(0,url.indexOf("id=")+3)));
        ResultSet sqlRes = getBD_by("id = "+new_id,false);
        sqlRes.next();
        Assert.assertEquals(sqlRes.getInt("currency_id"),1);
        Assert.assertEquals(sqlRes.getInt("amount"),11);
        Assert.assertEquals(sqlRes.getInt("system_currency_amount"),11);
        Assert.assertEquals(sqlRes.getInt("status"),1);
        Assert.assertEquals(sqlRes.getString("payment_allocation"),"in");
        Assert.assertEquals(sqlRes.getString("payment_period"),"2024-03-01");

        sqlRes = getBD_by("id = "+old_id,false);
        sqlRes.next();
        Assert.assertEquals(sqlRes.getInt("status"),4);
    }

    public void correct_payment_OUT_USD() throws SQLException {
        create_payment_OUT();
        StringBuffer url = new StringBuffer(Session.getPage().url());
        int old_id = Integer.parseInt(String.valueOf(url.delete(0,url.indexOf("id=")+3)));
        updatePaymentPage.open(old_id).readyPage()
                .setSystem_company("Test9dc364f6-c1ce-4a20-bea5-2402b5b4e9de")
                .setPayment_system("Paxum")
                .setPayment_type("Commission")
                .setPurpose_of_payment("testVlad")
                .setCurrency("USD")
                .setAmount("15")
                .setPayment_period("2024-03-01")
                .clickSaveBatton().readyPage();
        url = new StringBuffer(Session.getPage().url());
        int new_id = Integer.parseInt(String.valueOf(url.delete(0,url.indexOf("id=")+3)));
        ResultSet sqlRes = getBD_by("id = "+new_id,false);
        sqlRes.next();
        Assert.assertEquals(sqlRes.getInt("currency_id"),1);
        Assert.assertEquals(sqlRes.getInt("amount"),15);
        Assert.assertEquals(sqlRes.getInt("system_currency_amount"),15);
        Assert.assertEquals(sqlRes.getInt("status"),1);
        Assert.assertEquals(sqlRes.getString("payment_allocation"),"out");
        Assert.assertEquals(sqlRes.getString("payment_period"),"2024-03-01");

        sqlRes = getBD_by("id = "+old_id,false);
        sqlRes.next();
        Assert.assertEquals(sqlRes.getInt("status"),4);
    }

    public void test_LinkToAdv() throws SQLException {
        TransactionPage transactionPage = new TransactionPage();
        ResultSet res;
        //in
        create_payment_IN();
        transactionPage.open().readyPage();
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        int id = res.getInt("id");
        transactionPage.actionButtons.paidAction(id);
        transactionPage.actionButtons.confirmAction(id);
        paymentPage.open().readyPage();
        res = getDB().select("SELECT x.* FROM terraleads.accounting_payment x ORDER BY x.id DESC");
        res.next();
        id = res.getInt("id");
        paymentPage.actionButtons.linkToAdv(id,"25563");
        res = getDB().select("SELECT user_id FROM terraleads.accounting_payment where id = "+id);
        res.next();
        Assert.assertEquals(res.getInt(1),25563);

        //out
        create_payment_OUT();
        transactionPage.open().readyPage();
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        id = res.getInt("id");
        transactionPage.actionButtons.paidAction(id);
        transactionPage.actionButtons.confirmAction(id);
        paymentPage.open().readyPage();
        res = getDB().select("SELECT x.* FROM terraleads.accounting_payment x ORDER BY x.id DESC");
        res.next();
        id = res.getInt("id");
        try {
            paymentPage.actionButtons.linkToAdv(id,"25563");
            Assert.fail("Кнопка есть");
        }catch (TimeoutError e){
            res = getDB().select("SELECT user_id FROM terraleads.accounting_payment where id = "+id);
            res.next();
            Assert.assertEquals(res.getInt(1),0);
        }

        //in without possible to link
        createPaymentPage.open().readyPage()
                .setRoutePayment("in")
                .setPurpose_of_payment("testVlad")
                .setSystem_company(system_company)
                .setPayment_system("Payoneer")
                .choseSystem_requisites_account("testPaymentName3")
                .setPayment_typeId("PaymentTypef5fa533a-3622-4e12-871b-08c876b69328")
                .setCurrency("USD")
                .setAmount("10")
                .setPayment_period("2024-03-01")
                .clickSaveBatton().readyPage();
        transactionPage.open().readyPage();
        res = getDB().select("SELECT x.* FROM terraleads.accounting_balance_transactions x ORDER BY x.id DESC");
        res.next();
        id = res.getInt("id");
        transactionPage.actionButtons.paidAction(id);
        transactionPage.actionButtons.confirmAction(id);
        paymentPage.open().readyPage();
        res = getDB().select("SELECT x.* FROM terraleads.accounting_payment x ORDER BY x.id DESC");
        res.next();
        id = res.getInt("id");
        try {
            paymentPage.actionButtons.linkToAdv(id,"25563");
            Assert.fail("Кнопка есть");
        }catch (TimeoutError e){
            res = getDB().select("SELECT user_id FROM terraleads.accounting_payment where id = "+id);
            res.next();
            Assert.assertEquals(res.getInt(1),0);
        }
    }

    @Test(enabled = TestScenario.enable)
    public void checkAccess(){
        TestCases.checkAccessToPage(()-> paymentPage.open().readyPage());
    }

    DBconnector dBconnector;
    @BeforeTest
    public void openBD(){
        dBconnector = new DBconnector();
        rate = new GeneralBalancesPage().open().readyPage().getRate();
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
