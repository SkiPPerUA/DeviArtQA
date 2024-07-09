package deviartTests.pageTests.shipping;

import deviartTests.BaseTest;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.pages.shipping.UpdateOrderPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

@Test
public class OrdersTest extends BaseTest {

    UpdateOrderPage updateOrderPage = new UpdateOrderPage();

    public void extra_price_test() throws SQLException {
        ResultSet orders = getDB("terraleads_shipping").select("SELECT x.* FROM terraleads_shipping.`order` x WHERE extra_price is NULL and status = 1 limit 1");
        orders.next();
        int order_id = orders.getInt("id");

        String sql_product_extra_price = "SELECT x.* FROM terraleads_shipping.product_extra_price x WHERE order_id = "+order_id;

        ResultSet product_extra_price = getDB("terraleads_shipping").select(sql_product_extra_price);
        if (product_extra_price.next()){
            Assert.fail("В таблице product_extra_price есть order_id = "+order_id);
        }


        //add
        String extra_price = "20";
        updateOrderPage.open(order_id).readyPage()
                .setExtra_price(extra_price)
                .setSend_date(DataHelper.getTime("yyyy-MM-dd",1))
                .clickSaveButton();
        ResultSet check_result = getDB("terraleads_shipping").select("SELECT x.* FROM terraleads_shipping.product_extra_price x WHERE order_id = "+order_id);
        while (check_result.next()){
            Assert.assertEquals(String.valueOf(check_result.getDouble("product_extra_price")), String.format("%.2f", (float) Integer.parseInt(extra_price)/orders.getInt("quantity")));
        }
        ResultSet check_result_1 = getDB("terraleads_shipping").select("SELECT sum(product_extra_price) FROM terraleads_shipping.product_extra_price WHERE order_id = "+order_id);
        check_result_1.next();
        Assert.assertEquals(check_result_1.getFloat(1),Float.parseFloat(extra_price));
        ResultSet check_result_2 = getDB("terraleads_shipping").select("SELECT x.* FROM terraleads_shipping.`order` x WHERE id ="+order_id);
        check_result_2.next();
        Assert.assertEquals(check_result_2.getFloat("extra_price"),Float.parseFloat(extra_price));

        //update
        extra_price = "25";
        updateOrderPage.open(order_id).readyPage()
                .setExtra_price(extra_price)
                .setSend_date(DataHelper.getTime("yyyy-MM-dd",1))
                .clickSaveButton();
        check_result = getDB("terraleads_shipping").select("SELECT x.* FROM terraleads_shipping.product_extra_price x WHERE order_id = "+order_id);
        while (check_result.next()){
            Assert.assertEquals(String.valueOf(check_result.getDouble("product_extra_price")), String.format("%.2f", (float) Integer.parseInt(extra_price)/orders.getInt("quantity")));
        }
        check_result_1 = getDB("terraleads_shipping").select("SELECT sum(product_extra_price) FROM terraleads_shipping.product_extra_price WHERE order_id = "+order_id);
        check_result_1.next();
        Assert.assertEquals(check_result_1.getFloat(1),Float.parseFloat(extra_price));
        check_result_2 = getDB("terraleads_shipping").select("SELECT x.* FROM terraleads_shipping.`order` x WHERE id ="+order_id);
        check_result_2.next();
        Assert.assertEquals(check_result_2.getFloat("extra_price"),Float.parseFloat(extra_price));

        //delete
        extra_price = "0";
        updateOrderPage.open(order_id).readyPage()
                .setExtra_price(extra_price)
                .setSend_date(DataHelper.getTime("yyyy-MM-dd",1))
                .clickSaveButton();
        check_result = getDB("terraleads_shipping").select(sql_product_extra_price);
        Assert.assertFalse(check_result.next(),"Extra Prices - не удалены из таблицы product_extra_price");
        check_result_2 = getDB("terraleads_shipping").select("SELECT x.* FROM terraleads_shipping.`order` x WHERE id ="+order_id);
        check_result_2.next();
        Assert.assertEquals(check_result_2.getFloat("extra_price"),Float.parseFloat(extra_price));
    }
}
