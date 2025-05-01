package deviartTests.apiTests;

import deviartTests.BaseTest;
import org.deviartqa.TestScenario;
import org.deviartqa.api.main.LeadsAPI;
import org.deviartqa.core.DBconnector;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.helper.TestCases;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Test
public class LeadTest extends BaseTest {
    public LeadsAPI leads = new LeadsAPI();
    DBconnector dBconnector;
    int sleep = 10000;
    public String offer_id = "8122";
    public int user_Id = 25696 ;//TestScenario.userId 25719 25696
    private String phone;
    private String name;
    private String country;

    @Test(invocationCount = 1)
    public void create_lead_positive() throws SQLException, InterruptedException {
        phone = String.valueOf(new Date().getTime());
        name = "test"+ DataHelper.getUuid();
        country = "IT";
        makePositiveOffers(offer_id);
        makeLead();
//        Thread.sleep(sleep);
//        ResultSet res = getDB_byId(leads.getLead_id());
//        res.next();
//        Assert.assertEquals(res.getString("phone"),phone);
//        Assert.assertEquals(res.getString("name"),name);
//        Assert.assertEquals(res.getString("country"),country);
//        Assert.assertEquals(res.getString("offer_id"),offer_id);
//        Assert.assertEquals(res.getString("status"),"expect");
    }

    public void create_lead_allFields_positive() throws SQLException {
        String phone = String.valueOf(new Date().getTime());
        String name = "test"+ DataHelper.getUuid();
        makePositiveOffers(offer_id);
        leads.createLead("{\n" +
                "\"user_id\": "+TestScenario.userId+",\n" +
                "\"data\":{ \n" +
                "\"offer_id\":\""+offer_id+"\",\n" +
                "\"name\":\""+name+"\",\n" +
                "\"country\":\"RO\",\n" +
                "\"phone\":\""+phone+"\",\n" +
                //"\"stream_id\":6,\n" +
                "\"email\":\"email@email.com\",\n" +
                "\"message\":\"message\",\n" +
                "\"additional_info\":\"additional_info\",\n" +
                "\"comment\":\"comment\",\n" +
                "\"comment_web\":\"comment_web\",\n" +
                "\"comment_adv\":\"comment_adv\",\n" +
                "\"click_id\":\"123\",\n" +
                "\"click_code\":\"click_code\",\n" +
                "\"source\":\"source\",\n" +
                "\"referer\":\"referer\",\n" +
                "\"u_params\":\"u_params\",\n" +
                "\"payment_status\":\"created\",\n" +
                "\"address\":\"address\",\n" +
                "\"tz\":\"5\",\n" +
                "\"country_id\":\"6\",\n" +
                "\"count\":\"5\",\n" +
                "\"ip\":\"178.38.218.44\",\n" +
                "\"region\":\"region\", \n" +
                "\"city\":\"city\",\n" +
                "\"user_agent\":\"user_agent\",\n" +
                "\"geo_ip_country_name\":\"geo_ip_country_name\",\n" +
                "\"geo_ip_country_code\":\"geo_ip_country_code\",\n" +
                "\"geo_ip_region\":\"geo_ip_region\",\n" +
                "\"geo_ip_region_name\":\"geo_ip_region_name\",\n" +
                "\"geo_ip_city\":\"geo_ip_city\",\n" +
                "\"utm_source\":\"utm_source\",\n" +
                "\"utm_medium\":\"utm_medium\",\n" +
                "\"utm_campaign\":\"utm_campaign\",\n" +
                "\"utm_term\":\"utm_term\",\n" +
                "\"utm_content\":\"utm_content\",\n" +
                "\"sub_id\":\"sub_id\",\n" +
                "\"sub_id_1\":\"sub_id_1\",\n" +
                "\"sub_id_2\":\"sub_id_2\",\n" +
                "\"sub_id_3\":\"sub_id_3\",\n" +
                "\"sub_id_4\":\"sub_id_4\",\n" +
                "\"extra_data\":\"1\",\n" +
                "\"zip\":\"zip\",\n" +
                "\"alclick\":\"alclick\",\n" +
                "\"alstream\":\"alstream\",\n" +
                "\"campaign_id\":\"9\",\n" +
                "\"order_id\":\"order_id\"\n" +
                "}\n" +
                "}\n");
        ResultSet res = getDB_byId(leads.getLead_id());
        res.next();
        Assert.assertEquals(res.getInt("user_id"),TestScenario.userId);
        Assert.assertEquals(res.getString("offer_id"),offer_id);
        Assert.assertEquals(res.getString("name"),name);
        Assert.assertEquals(res.getString("country"),"RO");
        Assert.assertEquals(res.getString("phone"),phone);
        Assert.assertEquals(res.getString("email"),"email@email.com");
        Assert.assertEquals(res.getString("message"),"message");
        Assert.assertEquals(res.getString("additional_info"),"additional_info");
//        Assert.assertEquals(res.getString("comment"),"comment");
//        Assert.assertEquals(res.getString("comment_web"),"comment_web");
//        Assert.assertEquals(res.getString("comment_adv"),"comment_adv");
        Assert.assertEquals(res.getInt("click_id"),123);
        Assert.assertEquals(res.getString("click_code"),"click_code");
        Assert.assertEquals(res.getString("source"),"source");
//        Assert.assertEquals(res.getString("referer"),"referer");
//        Assert.assertEquals(res.getString("u_params"),"u_params");
        Assert.assertEquals(res.getString("payment_status"),"created");
        Assert.assertEquals(res.getString("address"),"address");
        Assert.assertEquals(res.getString("tz"),"5");
//        Assert.assertEquals(res.getString("country_id"),"6");
        Assert.assertEquals(res.getString("count"),"5");
        Assert.assertEquals(res.getString("ip"),"178.38.218.44");
        Assert.assertEquals(res.getString("region"),"region");
        Assert.assertEquals(res.getString("city"),"city");
        Assert.assertEquals(res.getString("user_agent"),"user_agent");
//        Assert.assertEquals(res.getString("geo_ip_country_name"),"geo_ip_country_name");
//        Assert.assertEquals(res.getString("geo_ip_country_code"),"geo_ip_country_code");
//        Assert.assertEquals(res.getString("geo_ip_region"),"geo_ip_region");
//        Assert.assertEquals(res.getString("geo_ip_region_name"),"geo_ip_region_name");
        Assert.assertEquals(res.getString("geo_ip_city"),"geo_ip_city");
        Assert.assertEquals(res.getString("utm_source"),"utm_source");
        Assert.assertEquals(res.getString("utm_medium"),"utm_medium");
        Assert.assertEquals(res.getString("utm_campaign"),"utm_campaign");
        Assert.assertEquals(res.getString("utm_term"),"utm_term");
        Assert.assertEquals(res.getString("utm_content"),"utm_content");
        Assert.assertEquals(res.getString("sub_id"),"sub_id");
        Assert.assertEquals(res.getString("sub_id_1"),"sub_id_1");
        Assert.assertEquals(res.getString("sub_id_2"),"sub_id_2");
        Assert.assertEquals(res.getString("sub_id_3"),"sub_id_3");
        Assert.assertEquals(res.getString("sub_id_4"),"sub_id_4");
//        Assert.assertEquals(res.getString("extra_data"),"1");
        Assert.assertEquals(res.getString("zip"),"zip");
//        Assert.assertEquals(res.getString("alclick"),"alclick");
//        Assert.assertEquals(res.getString("alstream"),"alstream");
        Assert.assertEquals(res.getString("campaign_id"),"9");
//        Assert.assertEquals(res.getString("order_id"),"order_id");
    }

    public void create_lead_allFields_test(){
        String name = "test"+ DataHelper.getUuid();
        String offer_id = "8105";
        makePositiveOffers(offer_id);

        //email
        List.of("fsfd","fsdf@fdsf","dsfds.com","fsdfds@.com","вывавы@fsd.fsd").forEach(x->{
            leads.createLead("{\n" +
                    "\"user_id\": "+TestScenario.userId+",\n" +
                    "\"data\":{ \n" +
                    "\"offer_id\":\""+offer_id+"\",\n" +
                    "\"name\":\""+name+"\",\n" +
                    "\"country\":\"RO\",\n" +
                    "\"phone\":\""+new Date().getTime()+"\",\n" +
                    "\"email\":\""+x+"\"\n" +
                    "}\n" +
                    "}\n");
            Assert.assertTrue(leads.getResponse().contains("\"status\":\"error\""));
        });

        //payment_status
        List.of("created","succeeded","dsfds","вывавы").forEach(x->{
            leads.createLead("{\n" +
                    "\"user_id\": "+TestScenario.userId+",\n" +
                    "\"data\":{ \n" +
                    "\"offer_id\":\""+offer_id+"\",\n" +
                    "\"name\":\""+name+"\",\n" +
                    "\"country\":\"RO\",\n" +
                    "\"phone\":\""+new Date().getTime()+"\",\n" +
                    "\"payment_status\":\""+x+"\"\n" +
                    "}\n" +
                    "}\n");
            if(x.equals("created") || x.equals("succeeded")) {
                Assert.assertFalse(leads.getResponse().contains("\"status\":\"error\""));
            }else {
                Assert.assertTrue(leads.getResponse().contains("\"status\":\"error\""));
            }
        });

        //ip
        List.of("2001:0DB8:AA10:0001:0000:0000:0000:00FB","178.38.218.44","dsfds","вывавы").forEach(x->{
            leads.createLead("{\n" +
                    "\"user_id\": "+TestScenario.userId+",\n" +
                    "\"data\":{ \n" +
                    "\"offer_id\":\""+offer_id+"\",\n" +
                    "\"name\":\""+name+"\",\n" +
                    "\"country\":\"RO\",\n" +
                    "\"phone\":\""+new Date().getTime()+"\",\n" +
                    "\"ip\":\""+x+"\"\n" +
                    "}\n" +
                    "}\n");
            Assert.assertFalse(leads.getResponse().contains("\"status\":\"error\""));
        });

        //extra_data
        List.of("0","1","2","dsa").forEach(x->{
            leads.createLead("{\n" +
                    "\"user_id\": "+TestScenario.userId+",\n" +
                    "\"data\":{ \n" +
                    "\"offer_id\":\""+offer_id+"\",\n" +
                    "\"name\":\""+name+"\",\n" +
                    "\"country\":\"RO\",\n" +
                    "\"phone\":\""+new Date().getTime()+"\",\n" +
                    "\"extra_data\":\""+x+"\"\n" +
                    "}\n" +
                    "}\n");
            if(x.equals("0") || x.equals("1")) {
                Assert.assertFalse(leads.getResponse().contains("\"status\":\"error\""));
            }else {
                Assert.assertTrue(leads.getResponse().contains("\"status\":\"error\""));
            }
        });
    }

    public void create_lead_positive_phoneInName() throws SQLException, InterruptedException {
        String name = "test"+ new Date().getTime();
        makePositiveOffers("8105");
        leads.createLead("{\n" +
                "\"user_id\": "+TestScenario.userId+",\n" +
                "    \"data\":{\n" +
                "        \"offer_id\": \"8105\",\n" +
                "        \"name\": \""+name+"\",\n" +
                "        \"country\": \"RO\",\n" +
                "        \"phone\": \"edf\"\n" +
                "        }\n" +
                "}\n");
        Thread.sleep(sleep);
        ResultSet res = getDB_byId(leads.getLead_id());
        res.next();
        Assert.assertEquals(res.getString("status"),"expect");
    }

    public void create_lead_negative_phone() throws InterruptedException {
        List<String> phones = List.of("","fasdfdsafa", "75298347658743658432756432985872311132312",
                "sleep", "select", "waitfor", "sysdate()", "dbms_pipe", ") and "," and (", "null,null", "union all");
        makePositiveOffers("8105");
        List<String> id = new ArrayList<>();
        phones.forEach(x->{leads.createLead("{\n" +
                "\"user_id\": "+TestScenario.userId+",\n" +
                "    \"data\":{\n" +
                "        \"offer_id\": \"8105\",\n" +
                "        \"name\": \"test\",\n" +
                "        \"country\": \"RO\",\n" +
                "        \"phone\": \""+x+"\"\n" +
                "        }\n" +
                "}\n");
            if (x.equals("")){
                Assert.assertTrue(leads.getResponse().contains("[phone] cannot be blank"));
            }else {
                id.add(leads.getLead_id());
            }});

        //Ожидание обработки статусов
        Thread.sleep(sleep);

        id.forEach(x->{
            try {
                ResultSet res = getDB_byId(x);
                res.next();
                String lead_status = res.getString("status");
                Assert.assertEquals(lead_status,"trash");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void create_lead_negative_doublePhone() throws SQLException, InterruptedException {
        String phone = String.valueOf(new Date().getTime());
        makePositiveOffers("8105");
        String body = "{\n" +
        "\"user_id\": "+TestScenario.userId+",\n" +
                "    \"data\":{\n" +
                "        \"offer_id\": \"8105\",\n" +
                "        \"name\": \"test\",\n" +
                "        \"country\": \"RO\",\n" +
                "        \"phone\": \""+phone+"\"\n" +
                "        }\n" +
                "}\n";
        leads.createLead(body);
        leads.createLead(body);
        //Ожидание обработки статусов
        Thread.sleep(sleep);
        ResultSet res = getDB_byId(leads.getLead_id());
        res.next();
        Assert.assertEquals(res.getString("status"),"trash");
    }

    public void create_lead_negative_checkStatusAndRoleUsers(){
        int user_id = 25694;
        List<Integer> negative_status = List.of(0,3,4);
        makePositiveOffers("8105");
        List<String> id = new ArrayList<>();
        negative_status.forEach(status->{
            dBconnector.update("update terraleads.users set status = "+status+" where id ="+user_id);
            TestCases.roles().forEach(role -> {
                dBconnector.update("update terraleads.users set role = '"+role+"' where id ="+user_id);
                leads.createLead("{\n" +
                        "\"user_id\": "+user_id+",\n" +
                        "    \"data\":{\n" +
                        "        \"offer_id\": \"8105\",\n" +
                        "        \"name\": \"test\",\n" +
                        "        \"country\": \"RO\",\n" +
                        "        \"phone\": \""+new Date().getTime()+"\"\n" +
                        "        }\n" +
                        "}\n",user_id);
                if (role.equals("admin") || role.equals("webmaster")) {
                    id.add(leads.getLead_id());
                }else {
                    Assert.assertTrue(leads.getResponse().contains("\"status\":\"error\""));
                }
            });
        });
        id.forEach(x-> {
            try {
                ResultSet res = getDB_byId(x);
                res.next();
                Assert.assertEquals(res.getString("status"),"fail");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void create_lead_positive_checkStatusAndRoleUsers(){
        int user_id = 25690;
        dBconnector.update("update terraleads.users set status = 1 where id ="+user_id);
        makePositiveOffers("8105");
        List<String> id = new ArrayList<>();
            TestCases.roles().forEach(role -> {
                dBconnector.update("update terraleads.users set role = '"+role+"' where id ="+user_id);
                leads.createLead("{\n" +
                        "\"user_id\": "+user_id+",\n" +
                        "    \"data\":{\n" +
                        "        \"offer_id\": \"8105\",\n" +
                        "        \"name\": \"test\",\n" +
                        "        \"country\": \"RO\",\n" +
                        "        \"phone\": \""+new Date().getTime()+"\"\n" +
                        "        }\n" +
                        "}\n",user_id);
                if (role.equals("admin") || role.equals("webmaster")) {
                    id.add(leads.getLead_id());
                }else {
                    Assert.assertTrue(leads.getResponse().contains("\"status\":\"error\""));
                }
            });
        id.forEach(x-> {
            try {
                ResultSet res = getDB_byId(x);
                res.next();
                Assert.assertEquals(res.getString("status"),"expect");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void create_lead_negative_OffersAccess_types() throws InterruptedException {
        String offer = "8120";
        List<String> access_types = List.of("private", "not_public", "integrating");
        makePositiveOffers(offer);
        List<String> id = new ArrayList<>();
        access_types.forEach(x->{
            dBconnector.update("update terraleads.offer set access_type = '"+x+"' where id = "+offer);
            leads.createLead("{\n" +
                "\"user_id\": "+ TestScenario.userId +",\n" +
                "    \"data\":{\n" +
                "        \"offer_id\": \""+offer+"\",\n" +
                "        \"name\": \"test\",\n" +
                "        \"country\": \"RO\",\n" +
                "        \"phone\": \""+new Date().getTime()+"\"\n" +
                "        }\n" +
                "}\n");
            id.add(leads.getLead_id());
        });
        Thread.sleep(sleep);
        id.forEach(x-> {
            try {
                ResultSet res = getDB_byId(x);
                res.next();
                Assert.assertEquals(res.getString("status"),"fail");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void create_lead_positive_OffersAccess_types() throws SQLException, InterruptedException {
        String offer = "8120";
        makePositiveOffers(offer);
        leads.createLead("{\n" +
                    "\"user_id\": "+ TestScenario.userId +",\n" +
                    "    \"data\":{\n" +
                    "        \"offer_id\": \""+offer+"\",\n" +
                    "        \"name\": \"test\",\n" +
                    "        \"country\": \"RO\",\n" +
                    "        \"phone\": \""+new Date().getTime()+"\"\n" +
                    "        }\n" +
                    "}\n");
        Thread.sleep(sleep);
        ResultSet res = getDB_byId(leads.getLead_id());
        res.next();
        Assert.assertEquals(res.getString("status"),"expect");
    }

    public void create_lead_negative_OffersStatus() {
        String offer = "8120";
        makePositiveOffers(offer);
        dBconnector.update("update terraleads.offer set status = 'disabled' where id = "+offer);
        leads.createLead("{\n" +
                    "\"user_id\": "+ TestScenario.userId +",\n" +
                    "    \"data\":{\n" +
                    "        \"offer_id\": \""+offer+"\",\n" +
                    "        \"name\": \"test\",\n" +
                    "        \"country\": \"RO\",\n" +
                    "        \"phone\": \""+new Date().getTime()+"\"\n" +
                    "        }\n" +
                    "}\n");
        Assert.assertTrue(leads.getResponse().contains("\"status\":\"error\""));
    }

    public void create_lead_negative_ProductStatus() throws SQLException, InterruptedException {
        String offer = "8120";
        makePositiveOffers(offer);
        dBconnector.update("update terraleads.product set status = 'disabled' WHERE id = (select product_id from terraleads.offer where id = "+offer+")");
        leads.createLead("{\n" +
                "\"user_id\": "+ TestScenario.userId +",\n" +
                "    \"data\":{\n" +
                "        \"offer_id\": \""+offer+"\",\n" +
                "        \"name\": \"test\",\n" +
                "        \"country\": \"RO\",\n" +
                "        \"phone\": \""+new Date().getTime()+"\"\n" +
                "        }\n" +
                "}\n");
        Thread.sleep(sleep);
        ResultSet res = getDB_byId(leads.getLead_id());
        res.next();
        Assert.assertEquals(res.getString("status"),"fail");
    }

    public void create_lead_PrivateAccess_public(){
        String offer = "8120";
        int denied_user = 25678;
        makePositiveOffers(offer);
        dBconnector.update("update terraleads.offer set private_access_user_id = '"+denied_user+"' where id = "+offer);

        dBconnector.update("update terraleads.offer set access_type = 'public' where id = "+offer);
        leads.createLead("{\n" +
                "\"user_id\": "+denied_user+",\n" +
                "    \"data\":{\n" +
                "        \"offer_id\": \""+offer+"\",\n" +
                "        \"name\": \"test\",\n" +
                "        \"country\": \"RO\",\n" +
                "        \"phone\": \""+new Date().getTime()+"\"\n" +
                "        }\n" +
                "}\n",denied_user);
        try {
            ResultSet res = getDB_byId(leads.getLead_id());
            res.next();
            Assert.assertEquals(res.getString("status"),"fail");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        leads.createLead("{\n" +
                "\"user_id\": "+ TestScenario.userId +",\n" +
                "    \"data\":{\n" +
                "        \"offer_id\": \""+offer+"\",\n" +
                "        \"name\": \"test\",\n" +
                "        \"country\": \"RO\",\n" +
                "        \"phone\": \""+new Date().getTime()+"\"\n" +
                "        }\n" +
                "}\n");
        try {
            ResultSet res = getDB_byId(leads.getLead_id());
            res.next();
            Assert.assertEquals(res.getString("status"),"expect");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<String> access_types = List.of("private", "not_public", "integrating");
        access_types.forEach(x->{
            dBconnector.update("update terraleads.offer set access_type = '"+x+"' where id = "+offer);
            leads.createLead("{\n" +
                    "\"user_id\": "+denied_user+",\n" +
                    "    \"data\":{\n" +
                    "        \"offer_id\": \""+offer+"\",\n" +
                    "        \"name\": \"test\",\n" +
                    "        \"country\": \"RO\",\n" +
                    "        \"phone\": \""+new Date().getTime()+"\"\n" +
                    "        }\n" +
                    "}\n",denied_user);
            try {
                ResultSet res = getDB_byId(leads.getLead_id());
                res.next();
                Assert.assertEquals(res.getString("status"),"expect");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            leads.createLead("{\n" +
                    "\"user_id\": "+ TestScenario.userId +",\n" +
                    "    \"data\":{\n" +
                    "        \"offer_id\": \""+offer+"\",\n" +
                    "        \"name\": \"test\",\n" +
                    "        \"country\": \"RO\",\n" +
                    "        \"phone\": \""+new Date().getTime()+"\"\n" +
                    "        }\n" +
                    "}\n");
            try {
                ResultSet res = getDB_byId(leads.getLead_id());
                res.next();
                Assert.assertEquals(res.getString("status"),"fail");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void confirm_lead_integration(){
        offer_id = "8121";
        user_Id = 2816;
        makeLead();
        String primeId = findPrimeLeadIdByTerraId(leads.getLead_id());
        try {
            TestScenario.env = "prime";
            leads.updateLead(primeId, LeadsAPI.StatusLead.confirm);
        }finally {
            TestScenario.env = "sandbox";
        }

    }

    public void reject_lead_integration(){
        offer_id = "8121";
        user_Id = 2816;
        makeLead();
        String primeId = findPrimeLeadIdByTerraId(leads.getLead_id());
        try {
            TestScenario.env = "prime";
            leads.updateLead(primeId, LeadsAPI.StatusLead.reject);
        }finally {
            TestScenario.env = "sandbox";
        }
    }

    public void trash_lead_integration(){
        offer_id = "8121";
        user_Id = 2816;
        makeLead();
        String primeId = findPrimeLeadIdByTerraId(leads.getLead_id());
        try {
            TestScenario.env = "prime";
            leads.updateLead(primeId, LeadsAPI.StatusLead.trash);
        }finally {
            TestScenario.env = "sandbox";
        }
    }

    public void confirm_lead() {
        offer_id = "8121";
        user_Id = 2816;
        makeLead();
        leads.updateLead(leads.getLead_id(), LeadsAPI.StatusLead.confirm);
    }

    public void reject_lead() {
        offer_id = "8121";
        user_Id = 2816;
        makeLead();
        leads.updateLead(leads.getLead_id(), LeadsAPI.StatusLead.reject);
    }

    public void trash_lead() {
        offer_id = "8121";
        user_Id = 2816;
        makeLead();
        leads.updateLead(leads.getLead_id(), LeadsAPI.StatusLead.trash);
    }

    public void create_Lead(){
        offer_id = "8119";
        user_Id = 2816;
        TestScenario.env = "prime";
        makeLead();
    }

    private String findPrimeLeadIdByTerraId(String terraID){
        String id = "";
        try{
            Thread.sleep(2000);
            TestScenario.env = "prime";
            ResultSet res = new DBconnector().select("SELECT lead_id FROM terraleads.integrated_crm_lead where external_lead_id="+terraID);
            res.next();
            id = String.valueOf(res.getInt(1));
        }catch (Throwable e){
            throw new RuntimeException(e);
        }finally {
            TestScenario.env = "sandbox";
        }
        return id;
    }

    private void makeLead() {
        leads.createLead("{\n" +
                "\"user_id\": "+user_Id+",\n" +
                "    \"data\":{\n" +
                "        \"offer_id\": \""+offer_id+"\",\n" +
                "        \"name\": \""+name+"\",\n" +
                "        \"country\": \""+country+"\",\n" +
                "        \"phone\": \""+phone+"\"\n" +
                "        }\n" +
                "}\n",user_Id);
    }

    private ResultSet getDB_byId(String id){
        return getDB().select("SELECT * FROM terraleads.lead where id="+id);
    }
    private void makePositiveOffers(String offer){
        getDB().update("update terraleads.offer set status = 'active' where id = "+offer);
        getDB().update("update terraleads.offer set private_access_user_id = null where id = "+offer);
        getDB().update("update terraleads.product set status = 'active' WHERE id = (select product_id from terraleads.offer where id = "+offer+")");
        getDB().update("update terraleads.offer set access_type = 'public' where id = "+offer);
    }
}
