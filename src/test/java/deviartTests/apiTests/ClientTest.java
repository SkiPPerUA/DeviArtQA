package deviartTests.apiTests;

import deviartTests.BaseTest;
import org.deviartqa.api.Client;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@Test
public class ClientTest extends BaseTest {

    Client client = new Client();
    JSONObject json;
    JSONArray array;

    public void getClient(){
        //page_size
        List.of(20,40).forEach(x -> {
            client.getClient("page_size="+x);
            Assert.assertEquals(new JSONObject(client.getResponse()).getJSONObject("result").getJSONArray("collection").length(),x);
        });

        //page
        client.getClient("page_size=20&page=2&sort=id.asc");
        array = new JSONObject(client.getResponse()).getJSONObject("result").getJSONArray("collection");
        array.forEach(x -> {
            json = new JSONObject(x.toString());
            Assert.assertTrue(json.getInt("id") > 20);
            Assert.assertTrue(json.getInt("id") < 31);
        });

    }

    public void getCountries(){
        client.getCountry();
    }

    public void getCard(){
        client.getCard(1);
    }

    public void getOrders(){
        client.getOrders("1?page_size=20&page=1");
    }

    public void getCalls(){
        client.getCalls("1?page_size=20&page=1");
    }

}
