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
    String orders = "307,309";

//    public void pp(){
//        setOrderStatus(orders,5);
//    }

    public void positive_createTrans() throws SQLException {
        ResultSet trans_out;
        ResultSet exchange;

        //USD account
        setOrderStatus(orders,5);
        createOrderStatusImportPage.open().readyPage()
                .setName("VladTest "+DataHelper.getTime("yyyy-MM-dd k:m:ss"))
                .setDelivery_service_id("40")
                .setOrder_status_type("Confirmed")
                .setOrder_status("Bought")
                .choseAccounting_system_requisites_account_id("testPaymentName 1112222")
                .setCod_sum("44")
                .setComment("commentCreate")
                .setFile("/Users/user/Documents/Deviart/test_orders.csv")
                .clickSaveButton().readyPage()
                .createTransaction()
                .warning_comment("vlad comment")
                .close_warning(true);

        trans_out = getDB().select("SELECT * FROM terraleads.accounting_balance_transactions where payment_type = 1 ORDER BY id DESC limit 1");
        trans_out.next();
        exchange = getDB().select("SELECT value FROM terraleads.exchange_rate WHERE from_currency_id = 4 and to_currency_id = 1 ORDER BY t_created DESC");
        exchange.next();

        Assert.assertEquals(trans_out.getInt("user_id"), 25695);
        Assert.assertTrue(trans_out.getString("t_created").contains(DataHelper.getTime("yyyy-MM-dd k:mm:")));
        Assert.assertEquals(trans_out.getInt("system_requisites_account_to_id"), 510);
        Assert.assertEquals(trans_out.getInt("payment_status"), 5);
        Assert.assertEquals(trans_out.getInt("payment_type_id"), 24);
        Assert.assertEquals(trans_out.getInt("commission_to"), 0);
        Assert.assertTrue(String.format("%.2f",44 * exchange.getFloat(1)).contains(String.valueOf(trans_out.getFloat("original_amount")).replace(".",",")));
        Assert.assertEquals(trans_out.getFloat("original_amount"),trans_out.getFloat("amount_system_currency"));
        Assert.assertTrue(trans_out.getString("payment_description").contains("commentCreate"));
        Assert.assertEquals(trans_out.getString("comment"),"vlad comment");


        //EUR account
        setOrderStatus(orders,5);
        createOrderStatusImportPage.open().readyPage()
                .setName("VladTest "+DataHelper.getTime("yyyy-MM-dd k:m:ss"))
                .setDelivery_service_id("40")
                .setOrder_status_type("Confirmed")
                .setOrder_status("Bought")
                .choseAccounting_system_requisites_account_id("EUR1111")
                .setCod_sum("44")
                .setComment("commentCreate")
                .setFile("/Users/user/Documents/Deviart/test_orders.csv")
                .clickSaveButton().readyPage()
                .createTransaction()
                .warning_comment("vlad comment")
                .close_warning(true);

        trans_out = getDB().select("SELECT * FROM terraleads.accounting_balance_transactions where payment_type = 1 ORDER BY id DESC limit 1");
        trans_out.next();
        ResultSet exchangeToUSD = getDB().select("SELECT value FROM terraleads.exchange_rate WHERE from_currency_id = 4 and to_currency_id = 1 ORDER BY t_created DESC");
        exchangeToUSD.next();
        ResultSet exchangeToEUR = getDB().select("SELECT value FROM terraleads.exchange_rate WHERE from_currency_id = 1 and to_currency_id = 6 ORDER BY t_created DESC");
        exchangeToEUR.next();
        ResultSet exchangeCustom = getDB().select("SELECT exchange_rate FROM terraleads.accounting_exchange_rates ORDER BY t_created DESC");
        exchangeCustom.next();

        Assert.assertEquals(trans_out.getInt("user_id"), 25695);
        Assert.assertTrue(trans_out.getString("t_created").contains(DataHelper.getTime("yyyy-MM-dd k:mm:")));
        Assert.assertEquals(trans_out.getInt("system_requisites_account_to_id"), 577);
        Assert.assertEquals(trans_out.getInt("payment_status"), 5);
        Assert.assertEquals(trans_out.getInt("payment_type_id"), 24);
        Assert.assertEquals(trans_out.getInt("commission_to"), 0);
        Assert.assertTrue(trans_out.getString("payment_description").contains("commentCreate"));
        Assert.assertEquals(trans_out.getString("comment"),"vlad comment");
        Assert.assertTrue(String.format("%.2f",(44 * exchangeToUSD.getFloat(1) * exchangeToEUR.getFloat(1))).contains(String.valueOf(trans_out.getFloat("original_amount")).replace(".",",")));
        Assert.assertTrue(String.valueOf(trans_out.getFloat("original_amount") * exchangeCustom.getFloat(1)).contains(String.valueOf(trans_out.getFloat("amount_system_currency"))));
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
                .setComment("comment")
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
        ResultSet exchange = getDB().select("SELECT value FROM terraleads.exchange_rate WHERE from_currency_id = 4 and to_currency_id = 1 ORDER BY t_created DESC");
        exchange.next();

        //create without modal
        createOrderStatusImportPage.open().readyPage()
                .setName("VladTest "+DataHelper.getTime("yyyy-MM-dd k:m:ss"))
                .setDelivery_service_id("40")
                .setOrder_status_type("Confirmed")
                .setOrder_status("Bought")
                .choseAccounting_system_requisites_account_id("testPaymentName 1112222")
                .setCod_sum("470.50")
                .setComment("comm")
                .setFile("/Users/user/Documents/Deviart/test_orders.csv")
                .clickSaveButton().readyPage()
                .createTransaction();

        trans_out = getDB().select("SELECT * FROM terraleads.accounting_balance_transactions where payment_type = 1 ORDER BY id DESC limit 1");
        trans_out.next();

        Assert.assertEquals(trans_out.getInt("user_id"), 25695);
        Assert.assertEquals(trans_out.getInt("system_requisites_account_to_id"), 510);
        Assert.assertEquals(trans_out.getInt("payment_status"), 5);
        Assert.assertEquals(trans_out.getInt("payment_type_id"), 24);
        Assert.assertEquals(trans_out.getInt("commission_to"), 0);
        Assert.assertEquals(String.valueOf(trans_out.getFloat("amount_system_currency")).replace(".",","), String.format("%.2f",470.50 * exchange.getFloat(1)));
        Assert.assertTrue(trans_out.getString("payment_description").contains("comm"));
        Assert.assertEquals(trans_out.getObject("comment"),"");

        //cancel modal
        createOrderStatusImportPage.open().readyPage()
                .setName("VladTest "+DataHelper.getTime("yyyy-MM-dd k:m:ss"))
                .setDelivery_service_id("40")
                .setOrder_status_type("Confirmed")
                .setOrder_status("Bought")
                .choseAccounting_system_requisites_account_id("testPaymentName 1112222")
                .setCod_sum("44")
                .setComment("comm")
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

        //without cod_sum
        createOrderStatusImportPage.open().readyPage()
                .setName("VladTest "+DataHelper.getTime("yyyy-MM-dd k:m:ss"))
                .setDelivery_service_id("40")
                .setOrder_status_type("Confirmed")
                .setOrder_status("Bought")
                .choseAccounting_system_requisites_account_id("testPaymentName 1112222")
                .setFile("/Users/user/Documents/Deviart/test_orders.csv")
                .clickSaveButton();
        createOrderStatusImportPage.readyPage();

        //without Comment
        createOrderStatusImportPage.open().readyPage()
                .setName("VladTest "+DataHelper.getTime("yyyy-MM-dd k:m:ss"))
                .setDelivery_service_id("40")
                .setOrder_status_type("Confirmed")
                .setOrder_status("Bought")
                .setCod_sum("44")
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
