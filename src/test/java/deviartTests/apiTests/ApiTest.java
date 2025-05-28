package deviartTests.apiTests;

import deviartTests.BaseTest;
import org.deviartqa.TestScenario;
import org.deviartqa.api.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.sql.ResultSet;
import java.sql.SQLException;

@Test
public class ApiTest extends BaseTest {

    public void positiveCategory(){
        Category api = new Category();
        api.getCategory(TestScenario.userId);
        Assert.fail();
    }

    public void positiveProduct(){
        new Product().getProduct(TestScenario.userId);
    }

    public void positiveOffer() throws SQLException {
        Offer api = new Offer();
        api.getOffer(TestScenario.userId);
        ResultSet res = getDB().select("SELECT * FROM terraleads.offer");
        while (res.next()){
            Assert.assertTrue(api.getResponse().contains(String.valueOf(res.getInt("product_id"))));
        }
    }

    public void positiveStream() throws SQLException {
        Stream api = new Stream();
        api.getStream(TestScenario.userId);
        ResultSet res = getDB().select("SELECT * FROM terraleads.stream");
        while (res.next()){
            Assert.assertTrue(api.getResponse().contains(String.valueOf(res.getInt("product_id"))));
        }
    }

    public void positiveIp(){
        new Ip().getIp(TestScenario.userId);
        Assert.fail();
    }

    public void positiveLead() throws SQLException {
       String lead_id = new LeadTest().create_lead_allFields_positive();
       LeadsAPI api = new LeadsAPI();
       api.statusLead(TestScenario.userId,Integer.parseInt(lead_id));
       ResultSet res = getDB().select("SELECT * FROM terraleads.lead WHERE id = "+lead_id);
       res.next();
       Assert.assertTrue(api.getResponse().contains("\"status\":\""+res.getString("status")+"\""));
    }

}
