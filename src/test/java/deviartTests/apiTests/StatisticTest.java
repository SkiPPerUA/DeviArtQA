package deviartTests.apiTests;

import deviartTests.BaseTest;
import org.deviartqa.api.StatisticApi;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Test
public class StatisticTest extends BaseTest {

    StatisticApi statisticApi = new StatisticApi();
    ResultSet res;
    JSONObject json;
    List<String> groups = List.of("date", "hours", "stream", "product", "offer", "filter", "country", "country_group", "campaign", "landing", "pre_landing", "user_web", "user_adv", "utm_source", "utm_medium", "utm_campaign", "utm_term", "utm_content", "sub_id", "sub_id_1", "sub_id_2", "sub_id_3", "sub_id_4", "source", "traffic_type", "offer_type", "price_type", "traffic_manger", "user_group", "manager", "product_base_category");

    public void allGroup_web(){
        groups.forEach(x -> {
            statisticApi.web("{\n" +
                    "  \"group_mode\": \""+x+"\"\n" +
                    "}");
            Assert.assertTrue(new JSONObject(statisticApi.getResponse()).getJSONArray("data").length() > 0);
            }
        );
    }

    public void groupBy_web(){
        List.of("product", "offer").forEach(x -> {
            int count = 0;
            statisticApi.web("{\n" +
                    "  \"group_mode\": \""+x+"\",\n" +
                    "  \"filter\": {\n" +
                    "    \"date_from\": \"2024-10-20T11:24:19.305Z\",\n" +
                    "    \"date_to\": \"2024-12-20T11:24:19.305Z\"\n" +
                    "  }\n" +
                    "}\n" +
                    "\n");
            res = getDB().select("SELECT * FROM terraleads.lead WHERE t_created between '2024-10-20T11:24:19.305Z' and '2024-12-20T11:24:19.305Z' group by "+x+"_id");
            while (true){
                try {
                    if (!res.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                count++;
                try {
                    Assert.assertTrue(statisticApi.getResponse().contains("\""+x+"_id\":"+res.getInt(x+"_id")));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            json = new JSONObject(statisticApi.getResponse()).getJSONObject("pagination");
            Assert.assertEquals(json.getInt("total"),count);
            }
        );
    }

    public void filter_web(){
        statisticApi.web("{\n" +
                "  \"group_mode\": \"product\",\n" +
                "  \"filter\": {\n" +
                "    \"date_from\": \"2024-10-22T18:16:36.407Z\",\n" +
                "    \"date_to\": \"2024-12-22T18:16:36.407Z\",\n" +
                "    \"offer_id\": {\n" +
                "      \"in\": [\n" +
                "        8104,\n" +
                "        8105\n" +
                "      ]\n" +
                "    },\n" +
                "    \"user_adv_id\": {\n" +
                "      \"in\": [\n" +
                "        25563,\n" +
                "\t\t\t\t25554\n" +
                "      ]\n" +
                "    },\n" +
                "    \"offer_type\": \"cpa\",\n" +
                "    \"product_base_category_id\": {\n" +
                "      \"in\": [\n" +
                "        7\n" +
                "      ]\n" +
                "    },\n" +
                "    \"country_id\": {\n" +
                "      \"in\": [\n" +
                "        92,\n" +
                "        157\n" +
                "      ]\n" +
                "    },\n" +
                "    \"user_id\": {\n" +
                "      \"in\": [\n" +
                "        25678,\n" +
                "        25695\n" +
                "      ]\n" +
                "    },\n" +
                "    \"dest_type\": 2,\n" +
                "    \"price_type\": 0\n" +
                "  }\n" +
                "}\n");
        json = new JSONObject(statisticApi.getResponse());
        Assert.assertEquals(json.getJSONObject("pagination").getInt("total"),2);
        Assert.assertEquals(json.getJSONObject("total").getInt("lead_all"),68);
    }

    public void allGroup_adv(){
        groups.forEach(x -> {
                    statisticApi.adv("{\n" +
                            "  \"group_mode\": \""+x+"\"\n" +
                            "}");
                    Assert.assertTrue(new JSONObject(statisticApi.getResponse()).getJSONArray("data").length() > 0);
                }
        );
    }

    public void groupBy_adv(){
        List.of("product", "offer").forEach(x -> {
                    int count = 0;
                    statisticApi.adv("{\n" +
                            "  \"group_mode\": \""+x+"\",\n" +
                            "  \"filter\": {\n" +
                            "    \"date_from\": \"2024-10-20T11:24:19.305Z\",\n" +
                            "    \"date_to\": \"2024-12-20T11:24:19.305Z\"\n" +
                            "  }\n" +
                            "}\n" +
                            "\n");
                    res = getDB().select("SELECT * FROM terraleads.lead WHERE t_created between '2024-10-20T11:24:19.305Z' and '2024-12-20T11:24:19.305Z' group by "+x+"_id");
                    while (true){
                        try {
                            if (!res.next()) break;
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        count++;
                        try {
                            Assert.assertTrue(statisticApi.getResponse().contains("\""+x+"_id\":"+res.getInt(x+"_id")));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    json = new JSONObject(statisticApi.getResponse()).getJSONObject("pagination");
                    Assert.assertEquals(json.getInt("total"),count);
                }
        );
    }

    public void filter_adv(){
        statisticApi.adv("{\n" +
                "  \"group_mode\": \"product\",\n" +
                "  \"filter\": {\n" +
                "    \"date_from\": \"2024-10-22T18:16:36.407Z\",\n" +
                "    \"date_to\": \"2024-12-22T18:16:36.407Z\",\n" +
                "    \"offer_id\": {\n" +
                "      \"in\": [\n" +
                "        8104,\n" +
                "        8105\n" +
                "      ]\n" +
                "    },\n" +
                "    \"user_adv_id\": {\n" +
                "      \"in\": [\n" +
                "        25563,\n" +
                "\t\t\t\t25554\n" +
                "      ]\n" +
                "    },\n" +
                "    \"offer_type\": \"cpa\",\n" +
                "    \"product_base_category_id\": {\n" +
                "      \"in\": [\n" +
                "        7\n" +
                "      ]\n" +
                "    },\n" +
                "    \"country_id\": {\n" +
                "      \"in\": [\n" +
                "        92,\n" +
                "        157\n" +
                "      ]\n" +
                "    },\n" +
                "    \"user_id\": {\n" +
                "      \"in\": [\n" +
                "        25678,\n" +
                "        25695\n" +
                "      ]\n" +
                "    },\n" +
                "    \"dest_type\": 2,\n" +
                "    \"price_type\": 0\n" +
                "  }\n" +
                "}\n");
        json = new JSONObject(statisticApi.getResponse());
        Assert.assertEquals(json.getJSONObject("pagination").getInt("total"),2);
        Assert.assertEquals(json.getJSONObject("total").getInt("lead_all"),68);
    }


}
