package deviartTests.integration;

import deviartTests.BaseTest;
import org.deviartqa.api.courier.CourierSku;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

@Test
public class IntegrationTest extends BaseTest {

    public void sku_test(){
        CourierSku courierSku = new CourierSku();
        courierSku.getSkus("{\"user_id\":\"20239\",\"courier\":\"skladproeshop\",\"data\":{\"courier\":{\"systemName\":\"skladproeshop\"},\"company\":{\"apiName\":\"swift_goods\",\"countryIsoCode\":\"CZ\"}}}");

        JSONArray jsonObject = new JSONObject(courierSku.getResponse()).getJSONArray("data");
        jsonObject.forEach(x-> new JSONObject(x.toString()).getJSONObject("product").getInt("quantity"));
    }

}
