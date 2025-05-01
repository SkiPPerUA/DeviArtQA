package deviartTests.pageTests.shipping;

import deviartTests.BaseTest;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

@Test
public class StatisticsShipping extends BaseTest {

    public void test() throws SQLException {
        ResultSet res = getDB().select("SELECT id FROM terraleads.states");
        while (res.next()){
            int state_id = res.getInt(1);
            ResultSet res1 = getDB().select("SELECT count(*) FROM terraleads_shipping.`order` WHERE state_id = "+state_id+" and t_lead_created BETWEEN '2024-10-22 00:00:00.000' and '2025-01-28 23:59:59.999'");
            res1.next();
            int count = res1.getInt(1);
            if (count > 0){
                ResultSet res2 = getDB().select("SELECT * FROM terraleads.states where id = "+state_id);
                res2.next();
                String name_state = res2.getString("name");
                System.out.println(String.format("State name {%s} id {%s} -> count {%s}",name_state,state_id,count));
            }
        }

    }

}
