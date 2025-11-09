package deviartTests.functionalTests;

import deviartTests.BaseTest;
import org.deviartqa.core.Session;
import org.deviartqa.core.Widget;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

@Test
public class CheckActiveFilters extends BaseTest {

    public void test() throws InterruptedException, SQLException {
        Session.getPage().navigate("https://sandbox.terraleads.com/acp/statistic/web");
        Thread.sleep(3000);
        Widget filters = new Widget(Session.getPage().locator("//button[@data-id=\"offer_id\"]/..//li//span[1]"));
        for (int i = 0; i<filters.element.count(); i++){
            String text = filters.element.nth(i).textContent();

            // Ищем число после символа #
            String regex = "#(\\d+)";

            // Ищем число
            //String regex = "(\\d+)";


            java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(regex).matcher(text);
            matcher.find();
            String number = matcher.group(1);



            ResultSet res = getDB().select("SELECT * FROM terraleads.offer \n" +
                    "WHERE id = "+number+" and status = 'active' and (SELECT count(*) FROM terraleads.lead \n" +
                    "WHERE offer_id  = "+number+") > 0");
            Assert.assertTrue(res.next());

        }
    }
}
