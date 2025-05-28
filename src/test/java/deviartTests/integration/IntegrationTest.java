package deviartTests.integration;

import deviartTests.BaseTest;
import org.deviartqa.api.Courier;
import org.deviartqa.helper.ApiHelper;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class IntegrationTest extends BaseTest {
    Courier courier = new Courier();

    public void sku_test(){
        courier.getSkus("{\"user_id\":\"20239\",\"courier\":\"skladproeshop\",\"data\":{\"courier\":{\"systemName\":\"skladproeshop\"},\"company\":{\"apiName\":\"swift_goods\",\"countryIsoCode\":\"CZ\"}}}");

        new JSONObject(courier.getResponse()).getJSONArray("data")
                .forEach(x-> new JSONObject(x.toString()).getJSONObject("product").getInt("quantity"));
    }

    public void tracker_test(){
        courier.getTracker("{\"user_id\":\"15552\",\"courier\":\"altexpress\",\"data\":{\"client\":{\"name\":\"Ciobanu Adela\",\"phone\":\"0752145681\",\"email\":null,\"identification_number\":null,\"vat_number\":null},\"order\":{\"id\":\"15329039\",\"create_date\":\"2024-10-15 21:22:42\",\"currency\":\"RON\",\"quantity\":\"5\",\"discount_price\":\"143.00\",\"total_price\":716,\"paid_sum\":\"0.00\",\"comment\":\"apel\"},\"payment\":{\"type\":\"payment_on_delivery\",\"system\":\"cash\",\"status\":\"not_paid\"},\"delivery\":{\"type\":\"address\",\"country\":{\"name\":\"Romania\",\"iso_code\":\"RO\"},\"company\":{\"name\":\"EcoFormula Labs Ltd\",\"address\":\"Unit 255 Moat House Business Centre, 54 Bloomfield Avenue, Belfast, Northern Ireland, BT5 5AD\",\"email\":\"ecoformula@altexpress.rou00a0u2060\",\"phone\":\"0\",\"vat_number\":\"NI713972\",\"currency\":\"EUR\",\"api_name\":\"ecoformula\",\"account\":{\"country\":{\"name\":\"Romania\",\"iso_code\":\"RO\"},\"currency\":{\"name\":\"Romanian leu\",\"iso_code\":\"RON\"},\"tax\":\"0.00\"}},\"warehouse\":{\"id\":null,\"name\":\"\",\"phone\":\"\",\"city\":\"\",\"address\":\"\",\"zip_code\":\"\"},\"region\":\"Bacau\",\"region_short_name\":\"\",\"region_eng\":null,\"parent_region\":null,\"parent_region_short_name\":null,\"parent_region_eng\":null,\"city\":\"Onesti\",\"city_eng\":null,\"zip_code\":\"601112\",\"township\":\"Bacau\",\"street\":\"Strada Daciei\",\"building\":\"bl5\",\"apartment\":\"ap14\",\"send_date\":\"2024-10-16\",\"customer_price\":\"32.00\",\"customer_tax\":0,\"shipping_price\":\"29.39\",\"return_price\":\"10.71\",\"cod_price\":\"0.00\"},\"products\":[{\"system_id\":\"2596\",\"third_party_system_name\":\"Osteflex Premium\",\"name\":\"Osteflex Premium\",\"quantity\":\"4\",\"item_price\":\"179.00\",\"item_vat\":\"0.00\",\"discount_price\":\"143.00\",\"discount_tax\":0,\"total_price\":716,\"total_tax\":0,\"currency\":\"RON\",\"item_weight\":{\"value\":\"1.00\",\"dimension\":\"g\"},\"size\":{\"item_length\":{\"value\":\"1.00\",\"dimension\":\"cm\"},\"item_width\":{\"value\":\"1.00\",\"dimension\":\"cm\"},\"item_height\":{\"value\":\"1.00\",\"dimension\":\"cm\"}}},{\"system_id\":\"3255\",\"third_party_system_name\":\"Leaflets\",\"name\":\"Leaflets\",\"quantity\":\"1\",\"item_price\":\"0.00\",\"item_vat\":\"0.00\",\"discount_price\":\"0.00\",\"discount_tax\":0,\"total_price\":0,\"total_tax\":0,\"currency\":\"RON\",\"item_weight\":{\"value\":\"1.00\",\"dimension\":\"g\"},\"size\":{\"item_length\":{\"value\":\"1.00\",\"dimension\":\"cm\"},\"item_width\":{\"value\":\"1.00\",\"dimension\":\"cm\"},\"item_height\":{\"value\":\"1.00\",\"dimension\":\"cm\"}}}]}}");

        Assert.assertNotNull(new JSONObject(courier.getResponse()).getJSONObject("data").getString("tracker_id"));
    }



    public void sum(){
        String body = "{\"user_id\":\"15552\",\"courier\":\"altexpress\",\"data\":{\"client\":{\"name\":\"Ciobanu Adela\",\"phone\":\"0752145681\",\"email\":null,\"identification_number\":null,\"vat_number\":null},\"order\":{\"id\":\"15329039\",\"create_date\":\"2024-10-15 21:22:42\",\"currency\":\"RON\",\"quantity\":\"5\",\"discount_price\":\"143.00\",\"total_price\":716,\"paid_sum\":\"0.00\",\"comment\":\"apel\"},\"payment\":{\"type\":\"payment_on_delivery\",\"system\":\"cash\",\"status\":\"not_paid\"},\"delivery\":{\"type\":\"address\",\"country\":{\"name\":\"Romania\",\"iso_code\":\"RO\"},\"company\":{\"name\":\"EcoFormula Labs Ltd\",\"address\":\"Unit 255 Moat House Business Centre, 54 Bloomfield Avenue, Belfast, Northern Ireland, BT5 5AD\",\"email\":\"ecoformula@altexpress.rou00a0u2060\",\"phone\":\"0\",\"vat_number\":\"NI713972\",\"currency\":\"EUR\",\"api_name\":\"ecoformula\",\"account\":{\"country\":{\"name\":\"Romania\",\"iso_code\":\"RO\"},\"currency\":{\"name\":\"Romanian leu\",\"iso_code\":\"RON\"},\"tax\":\"0.00\"}},\"warehouse\":{\"id\":null,\"name\":\"\",\"phone\":\"\",\"city\":\"\",\"address\":\"\",\"zip_code\":\"\"},\"region\":\"Bacau\",\"region_short_name\":\"\",\"region_eng\":null,\"parent_region\":null,\"parent_region_short_name\":null,\"parent_region_eng\":null,\"city\":\"Onesti\",\"city_eng\":null,\"zip_code\":\"601112\",\"township\":\"Bacau\",\"street\":\"Strada Daciei\",\"building\":\"bl5\",\"apartment\":\"ap14\",\"send_date\":\"2024-10-16\",\"customer_price\":\"32.00\",\"customer_tax\":0,\"shipping_price\":\"29.39\",\"return_price\":\"10.71\",\"cod_price\":\"0.00\"},\"products\":[{\"system_id\":\"2596\",\"third_party_system_name\":\"Osteflex Premium\",\"name\":\"Osteflex Premium\",\"quantity\":\"4\",\"item_price\":\"179.00\",\"item_vat\":\"0.00\",\"discount_price\":\"143.00\",\"discount_tax\":0,\"total_price\":716,\"total_tax\":0,\"currency\":\"RON\",\"item_weight\":{\"value\":\"1.00\",\"dimension\":\"g\"},\"size\":{\"item_length\":{\"value\":\"1.00\",\"dimension\":\"cm\"},\"item_width\":{\"value\":\"1.00\",\"dimension\":\"cm\"},\"item_height\":{\"value\":\"1.00\",\"dimension\":\"cm\"}}},{\"system_id\":\"3255\",\"third_party_system_name\":\"Leaflets\",\"name\":\"Leaflets\",\"quantity\":\"1\",\"item_price\":\"0.00\",\"item_vat\":\"0.00\",\"discount_price\":\"0.00\",\"discount_tax\":0,\"total_price\":0,\"total_tax\":0,\"currency\":\"RON\",\"item_weight\":{\"value\":\"1.00\",\"dimension\":\"g\"},\"size\":{\"item_length\":{\"value\":\"1.00\",\"dimension\":\"cm\"},\"item_width\":{\"value\":\"1.00\",\"dimension\":\"cm\"},\"item_height\":{\"value\":\"1.00\",\"dimension\":\"cm\"}}}]}}";
        String apiKey = "8c5510d820b8ce554f8870e797253423";
        System.out.println(ApiHelper.getCheckSum(body,apiKey));
    }

}
