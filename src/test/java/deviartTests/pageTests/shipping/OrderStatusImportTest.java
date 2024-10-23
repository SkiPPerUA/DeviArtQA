package deviartTests.pageTests.shipping;

import deviartTests.BaseTest;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.pages.shipping.orderStatusImport.CreateOrderStatusImportPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@Test
public class OrderStatusImportTest extends BaseTest {

    CreateOrderStatusImportPage createOrderStatusImportPage = new CreateOrderStatusImportPage();
    String orders = "307,209";

    public void positive_allParameters() throws SQLException {
        setOrderStatus(orders);
        createOrderStatusImportPage.open().readyPage()
                .setName("VladTest")
                .setDelivery_service_id("40")
                .setOrder_status_type("Confirmed")
                .setOrder_status("Bought")
                .choseAccounting_system_requisites_account_id("testPaymentName 1112222")
                .setCost("44")
                .setInvoice("invoice")
                .setFile("/Users/user/Documents/Deviart/test_orders.csv")
                .clickSaveButton().readyPage()
                .confirm("Found orders with status \"Bought\"");

        ResultSet trans_out = getDB().select("SELECT * FROM terraleads.accounting_balance_transactions where payment_type = 2 ORDER BY id DESC limit 1");
        trans_out.next();
        ResultSet trans_in = getDB().select("SELECT * FROM terraleads.accounting_balance_transactions where payment_type = 1 ORDER BY id DESC limit 1");
        trans_in.next();

        Assert.assertEquals(trans_in.getInt("user_id"), 25695);
        Assert.assertEquals(trans_in.getInt("system_requisites_account_to_id"), 510);
        Assert.assertTrue(trans_in.getString("t_created").contains(DataHelper.getTime("yyyy-MM-dd k:mm:")));
        Assert.assertEquals(trans_in.getInt("payment_status"), 5);
        Assert.assertEquals(trans_in.getInt("payment_type_id"), 24);
        Assert.assertEquals(trans_in.getInt("commission_to"), 0);
        Assert.assertEquals(trans_in.getFloat("amount_system_currency"), 207.07);
        Assert.assertEquals(trans_in.getString("payment_description"), "invoice");

        Assert.assertEquals(trans_out.getInt("user_id"), 25695);
        Assert.assertEquals(trans_out.getInt("system_requisites_account_from_id"), 510);
        Assert.assertTrue(trans_out.getString("t_created").contains(DataHelper.getTime("yyyy-MM-dd k:m:")));
        Assert.assertEquals(trans_out.getInt("payment_status"), 5);
        Assert.assertEquals(trans_out.getInt("payment_type_id"), 24);
        Assert.assertEquals(trans_out.getInt("commission_to"), 0);
        Assert.assertEquals(trans_out.getFloat("amount_system_currency"), 44);
        Assert.assertEquals(trans_out.getString("payment_description"), "invoice");
    }

    public void test_mandatory_parameters(){
        //without account_id
        createOrderStatusImportPage.open().readyPage()
                .setName("VladTest")
                .setDelivery_service_id("40")
                .setOrder_status_type("Confirmed")
                .setOrder_status("Bought")
                .setFile("/Users/user/Documents/Deviart/test_orders.csv")
                .clickSaveButton();
        createOrderStatusImportPage.readyPage();

        //with cost and without invoice
        createOrderStatusImportPage.open().readyPage()
                .setName("VladTest")
                .setDelivery_service_id("40")
                .setOrder_status_type("Confirmed")
                .setOrder_status("Bought")
                .choseAccounting_system_requisites_account_id("testPaymentName 1112222")
                .setCost("44")
                .setFile("/Users/user/Documents/Deviart/test_orders.csv")
                .clickSaveButton();
        createOrderStatusImportPage.readyPage();

        //with invoice and without cost
        createOrderStatusImportPage.open().readyPage()
                .setName("VladTest")
                .setDelivery_service_id("40")
                .setOrder_status_type("Confirmed")
                .setOrder_status("Bought")
                .choseAccounting_system_requisites_account_id("testPaymentName 1112222")
                .setInvoice("invoice")
                .setFile("/Users/user/Documents/Deviart/test_orders.csv")
                .clickSaveButton();
        createOrderStatusImportPage.readyPage();
    }

    public void test_statusesWithoutTrans() {
        String [] statuses = {"Not bought","Transportation to customer","Dispose","Lost","Canceled"};

        //with Confirmed
        Arrays.stream(statuses).forEach(x -> {
            setOrderStatus(orders);
            ResultSet countBefore = getDB().select("SELECT count(*) FROM terraleads.accounting_balance_transactions");
            createOrderStatusImportPage.open().readyPage()
                    .setName("VladTest")
                    .setDelivery_service_id("40")
                    .setOrder_status_type("Confirmed")
                    .setOrder_status(x)
                    .choseAccounting_system_requisites_account_id("testPaymentName 1112222")
                    .setCost("44")
                    .setInvoice("invoice")
                    .setFile("/Users/user/Documents/Deviart/test_orders.csv")
                    .clickSaveButton().readyPage()
                    .confirm(1);
            ResultSet countAfter = getDB().select("SELECT count(*) FROM terraleads.accounting_balance_transactions");
            try {
                countBefore.next();
                countAfter.next();
                Assert.assertEquals(countAfter.getInt(1), countBefore.getInt(1));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        //with Temporary
        Arrays.stream(statuses).forEach(x -> {
            setOrderStatus(orders);
            ResultSet countBefore = getDB().select("SELECT count(*) FROM terraleads.accounting_balance_transactions");
            createOrderStatusImportPage.open().readyPage()
                    .setName("VladTest")
                    .setDelivery_service_id("40")
                    .setOrder_status_type("Temporary")
                    .setOrder_status(x)
                    .choseAccounting_system_requisites_account_id("testPaymentName 1112222")
                    .setCost("44")
                    .setInvoice("invoice")
                    .setFile("/Users/user/Documents/Deviart/test_orders.csv")
                    .clickSaveButton().readyPage()
                    .confirm(1);
            ResultSet countAfter = getDB().select("SELECT count(*) FROM terraleads.accounting_balance_transactions");
            try {
                countBefore.next();
                countAfter.next();
                Assert.assertEquals(countAfter.getInt(1), countBefore.getInt(1));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void test_requisites_account() throws SQLException {
        ResultSet accounts = getDB().select("SELECT * FROM terraleads.accounting_system_requisites_account WHERE `type` = 'sales'");
        while (accounts.next()){
            createOrderStatusImportPage.open().readyPage()
                    .choseAccounting_system_requisites_account_id(accounts.getString("name"));
        }
    }

    private void setOrderStatus(String orders){
        getDB().update("UPDATE terraleads_shipping.`order` set status = 10 where id in ("+orders+")");
    }
}
