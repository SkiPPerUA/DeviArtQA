package deviartTests.pageTests.shipping;

import deviartTests.BaseTest;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.helper.TextLocalization;
import org.deviartqa.pages.shipping.order.CreateOrderPage;
import org.deviartqa.pages.shipping.order.UpdateOrderPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Test
public class OrdersTest extends BaseTest {

    UpdateOrderPage updateOrderPage = new UpdateOrderPage();
    CreateOrderPage createOrderPage = new CreateOrderPage();

    public void extra_price_test() throws SQLException {
        ResultSet orders = getDB().select("SELECT x.* FROM terraleads_shipping.`order` x WHERE extra_price is NULL and status = 1 limit 1");
        orders.next();
        int order_id = orders.getInt("id");

        String sql_product_extra_price = "SELECT x.* FROM terraleads_shipping.product_extra_price x WHERE order_id = "+order_id;

        ResultSet product_extra_price = getDB().select(sql_product_extra_price);
        if (product_extra_price.next()){
            Assert.fail("В таблице product_extra_price есть order_id = "+order_id);
        }


        //add
        String extra_price = "20";
        updateOrderPage.open(order_id).readyPage()
                .setExtra_price(extra_price)
                .setSend_date(DataHelper.getTime("yyyy-MM-dd",1))
                .clickSaveButton();
        ResultSet check_result = getDB().select("SELECT x.* FROM terraleads_shipping.product_extra_price x WHERE order_id = "+order_id);
        while (check_result.next()){
            Assert.assertEquals(String.valueOf(check_result.getDouble("product_extra_price")), String.format("%.2f", (float) Integer.parseInt(extra_price)/orders.getInt("quantity")));
        }
        ResultSet check_result_1 = getDB().select("SELECT sum(product_extra_price) FROM terraleads_shipping.product_extra_price WHERE order_id = "+order_id);
        check_result_1.next();
        Assert.assertEquals(check_result_1.getFloat(1),Float.parseFloat(extra_price));
        ResultSet check_result_2 = getDB().select("SELECT x.* FROM terraleads_shipping.`order` x WHERE id ="+order_id);
        check_result_2.next();
        Assert.assertEquals(check_result_2.getFloat("extra_price"),Float.parseFloat(extra_price));

        //update
        extra_price = "25";
        updateOrderPage.open(order_id).readyPage()
                .setExtra_price(extra_price)
                .setSend_date(DataHelper.getTime("yyyy-MM-dd",1))
                .clickSaveButton();
        check_result = getDB().select("SELECT x.* FROM terraleads_shipping.product_extra_price x WHERE order_id = "+order_id);
        while (check_result.next()){
            Assert.assertEquals(String.valueOf(check_result.getDouble("product_extra_price")), String.format("%.2f", (float) Integer.parseInt(extra_price)/orders.getInt("quantity")));
        }
        check_result_1 = getDB().select("SELECT sum(product_extra_price) FROM terraleads_shipping.product_extra_price WHERE order_id = "+order_id);
        check_result_1.next();
        Assert.assertEquals(check_result_1.getFloat(1),Float.parseFloat(extra_price));
        check_result_2 = getDB().select("SELECT x.* FROM terraleads_shipping.`order` x WHERE id ="+order_id);
        check_result_2.next();
        Assert.assertEquals(check_result_2.getFloat("extra_price"),Float.parseFloat(extra_price));

        //delete
        extra_price = "0";
        updateOrderPage.open(order_id).readyPage()
                .setExtra_price(extra_price)
                .setSend_date(DataHelper.getTime("yyyy-MM-dd",1))
                .clickSaveButton();
        check_result = getDB().select(sql_product_extra_price);
        Assert.assertFalse(check_result.next(),"Extra Prices - не удалены из таблицы product_extra_price");
        check_result_2 = getDB().select("SELECT x.* FROM terraleads_shipping.`order` x WHERE id ="+order_id);
        check_result_2.next();
        Assert.assertEquals(check_result_2.getFloat("extra_price"),Float.parseFloat(extra_price));
    }

    public void type_order_positive(){
        createOrderPage.open(157).readyPage().header.changeUser(25554);

        Map<String , Integer> expected_id = new HashMap<>();
        expected_id.put("selling",1);
        expected_id.put("remainder",2);
        expected_id.put("reason_for_non_payment_of_the_order_rehabilitation_of_the_order",3);
        expected_id.put("additional_selling",4);
        expected_id.put("manual",5);
        expected_id.put("extra_control_from_trash_calls_(Selling)",6);
        expected_id.put("extra_control_from_reject_calls_(Selling)",7);
        expected_id.put("straight_sale_on_a_landing",8);
        expected_id.put("straight_sale_on_a_landing_with_handling_(paid_order)",9);
        expected_id.put("straight_sale_on_a_landing_with_handling_(not_paid_order)",10);
        expected_id.put("order_type_MLM",11);

        List.of("selling","remainder","additional_selling","reason_for_non_payment_of_the_order_rehabilitation_of_the_order",
                "extra_control_from_trash_calls_(Selling)","extra_control_from_reject_calls_(Selling)", "straight_sale_on_a_landing",
                "straight_sale_on_a_landing_with_handling_(paid_order)","straight_sale_on_a_landing_with_handling_(not_paid_order)",
                "manual", "order_type_MLM")
                .forEach(x->{
            createOrderPage.open(157).readyPage()
                    .setFull_name("Vlad Sava")
                    .setPhone("0999999999")
                    .setZipCode("031113")
                    .setCity("Bucuresti (Sector 3)")
                    .setStreet("street 22")
                    .setHouse("3333")
                    .setSend_date(DataHelper.getTime("yyyy-MM-dd",1))
                    .setType(TextLocalization.get(x))
                    .changeProductGroup("crossale")
                    .addProduct("crossale","Steam cleaner - Steam Mop X12")
                    .clickSaveButton().readyPage();

            ResultSet res = getDB().select("SELECT * FROM terraleads_shipping.`order` ORDER BY id DESC limit 1");
            int act = -1;
            try {
                res.next();
                act = res.getInt("call_sequence_type");
                Assert.assertEquals(res.getInt("call_sequence_type"),expected_id.get(x));
            } catch (Throwable e) {
                System.out.println(x + " НЕ корректный, ждал = "+expected_id.get(x)+"; а получил = "+ act);
            }
        });

    }
}
