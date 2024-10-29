package deviartTests.pageTests.shipping;

import com.microsoft.playwright.TimeoutError;
import deviartTests.BaseTest;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.pages.shipping.orderStatusImport.CreateOrderStatusImportPage;
import org.deviartqa.pages.shipping.orderStatusImport.UpdateOrderStatusImportPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@Test
public class OrderStatusImportTest extends BaseTest {

    CreateOrderStatusImportPage createOrderStatusImportPage = new CreateOrderStatusImportPage();
    UpdateOrderStatusImportPage updateOrderStatusImportPage = new UpdateOrderStatusImportPage();
    String orders = "307,209";

    public void positive_createTrans() throws SQLException {
        setOrderStatus(orders,5);

        createOrderStatusImportPage.open().readyPage()
                .setName("VladTest "+DataHelper.getTime("yyyy-MM-dd k:m:ss"))
                .setDelivery_service_id("40")
                .setOrder_status_type("Confirmed")
                .setOrder_status("Bought")
                .choseAccounting_system_requisites_account_id("testPaymentName 1112222")
                .setCod_sum("44")
                .setFile("/Users/user/Documents/Deviart/test_orders.csv")
                .clickSaveButton().readyPage()
                .createTransaction()
                .warning_comment("vlad comment")
                .close_warning(true);

        ResultSet trans_out = getDB().select("SELECT * FROM terraleads.accounting_balance_transactions where payment_type = 2 ORDER BY id DESC limit 1");
        trans_out.next();

        Assert.assertEquals(trans_out.getInt("user_id"), 25695);
        Assert.assertEquals(trans_out.getInt("system_requisites_account_from_id"), 510);
        Assert.assertTrue(trans_out.getString("t_created").contains(DataHelper.getTime("yyyy-MM-dd k:m:")));
        Assert.assertEquals(trans_out.getInt("payment_status"), 5);
        Assert.assertEquals(trans_out.getInt("payment_type_id"), 24);
        Assert.assertEquals(trans_out.getInt("commission_to"), 0);
        Assert.assertEquals(trans_out.getFloat("amount_system_currency"), 44);
        Assert.assertEquals(trans_out.getString("payment_description"), "invoice");
    }

    public void negative_createTrans() {
        setOrderStatus(orders,10);
        createOrderStatusImportPage.open().readyPage()
                .setName("VladTest "+DataHelper.getTime("yyyy-MM-dd k:m:ss"))
                .setDelivery_service_id("40")
                .setOrder_status_type("Confirmed")
                .setOrder_status("Bought")
                .choseAccounting_system_requisites_account_id("testPaymentName 1112222")
                .setCod_sum("44")
                .setFile("/Users/user/Documents/Deviart/test_orders.csv")
                .clickSaveButton().readyPage();
        try {
            updateOrderStatusImportPage.createTransaction();
            Assert.fail("Кнопка создание транки есть");
        }catch (TimeoutError e){
        }
    }

    public void test_modalWindow() throws SQLException {
        setOrderStatus(orders,5);
        ResultSet trans_out;

        //create without modal
        createOrderStatusImportPage.open().readyPage()
                .setName("VladTest "+DataHelper.getTime("yyyy-MM-dd k:m:ss"))
                .setDelivery_service_id("40")
                .setOrder_status_type("Confirmed")
                .setOrder_status("Bought")
                .choseAccounting_system_requisites_account_id("testPaymentName 1112222")
                .setCod_sum("470.50")
                .setFile("/Users/user/Documents/Deviart/test_orders.csv")
                .clickSaveButton().readyPage()
                .createTransaction();

        trans_out = getDB().select("SELECT * FROM terraleads.accounting_balance_transactions where payment_type = 2 ORDER BY id DESC limit 1");
        trans_out.next();

        Assert.assertEquals(trans_out.getInt("user_id"), 25695);
        Assert.assertEquals(trans_out.getInt("system_requisites_account_from_id"), 510);
        Assert.assertTrue(trans_out.getString("t_created").contains(DataHelper.getTime("yyyy-MM-dd k:m:")));
        Assert.assertEquals(trans_out.getInt("payment_status"), 5);
        Assert.assertEquals(trans_out.getInt("payment_type_id"), 24);
        Assert.assertEquals(trans_out.getInt("commission_to"), 0);
        Assert.assertEquals(trans_out.getFloat("amount_system_currency"), 44);
        Assert.assertEquals(trans_out.getString("payment_description"), "invoice");

        //cancel modal
        createOrderStatusImportPage.open().readyPage()
                .setName("VladTest "+DataHelper.getTime("yyyy-MM-dd k:m:ss"))
                .setDelivery_service_id("40")
                .setOrder_status_type("Confirmed")
                .setOrder_status("Bought")
                .choseAccounting_system_requisites_account_id("testPaymentName 1112222")
                .setCod_sum("44")
                .setFile("/Users/user/Documents/Deviart/test_orders.csv")
                .clickSaveButton().readyPage()
                .createTransaction()
                .close_warning(false);
        updateOrderStatusImportPage.readyPage();
    }

    public void test_mandatoryParameters(){
        //without account_id
        createOrderStatusImportPage.open().readyPage()
                .setName("VladTest "+DataHelper.getTime("yyyy-MM-dd k:m:ss"))
                .setDelivery_service_id("40")
                .setOrder_status_type("Confirmed")
                .setOrder_status("Bought")
                .setCod_sum("44")
                .setFile("/Users/user/Documents/Deviart/test_orders.csv")
                .clickSaveButton();
        createOrderStatusImportPage.readyPage();

        //with cod_sum and without invoice
        createOrderStatusImportPage.open().readyPage()
                .setName("VladTest "+DataHelper.getTime("yyyy-MM-dd k:m:ss"))
                .setDelivery_service_id("40")
                .setOrder_status_type("Confirmed")
                .setOrder_status("Bought")
                .choseAccounting_system_requisites_account_id("testPaymentName 1112222")
                .setFile("/Users/user/Documents/Deviart/test_orders.csv")
                .clickSaveButton();
        createOrderStatusImportPage.readyPage();
    }

    public void test_mandatoryParametersNotConfirmedAndBought() {
        String [] statuses = {"Not bought","Transportation to customer","Dispose","Lost","Canceled"};

        //with Confirmed
        Arrays.stream(statuses).forEach(x -> {
            createOrderStatusImportPage.open().readyPage()
                    .setName("VladTest "+DataHelper.getTime("yyyy-MM-dd k:m:ss"))
                    .setDelivery_service_id("40")
                    .setOrder_status_type("Confirmed")
                    .setOrder_status(x)
                    .setFile("/Users/user/Documents/Deviart/test_orders.csv")
                    .clickSaveButton().readyPage();
        });

        //with Temporary
        Arrays.stream(statuses).forEach(x -> {
            createOrderStatusImportPage.open().readyPage()
                    .setName("VladTest "+DataHelper.getTime("yyyy-MM-dd k:m:ss"))
                    .setDelivery_service_id("40")
                    .setOrder_status_type("Temporary")
                    .setOrder_status("Bought")
                    .setFile("/Users/user/Documents/Deviart/test_orders.csv")
                    .clickSaveButton().readyPage();

            createOrderStatusImportPage.open().readyPage()
                    .setName("VladTest "+DataHelper.getTime("yyyy-MM-dd k:m:ss"))
                    .setDelivery_service_id("40")
                    .setOrder_status_type("Temporary")
                    .setOrder_status(x)
                    .setFile("/Users/user/Documents/Deviart/test_orders.csv")
                    .clickSaveButton().readyPage();
        });

    }

    public void test_requisites_account() throws SQLException {
        ResultSet accounts = getDB().select("SELECT * FROM terraleads.accounting_system_requisites_account WHERE `type` = 'sales'");
        while (accounts.next()){
            createOrderStatusImportPage.open().readyPage()
                    .choseAccounting_system_requisites_account_id(accounts.getString("name"));
        }
    }

    private void setOrderStatus(String orders, int status){
        getDB().update("UPDATE terraleads_shipping.`order` set status = "+status+" where id in ("+orders+")");
    }
}
