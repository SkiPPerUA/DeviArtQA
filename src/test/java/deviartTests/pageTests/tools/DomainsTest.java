package deviartTests.pageTests.tools;

import com.microsoft.playwright.Locator;
import deviartTests.BaseTest;
import org.deviartqa.TestScenario;
import org.deviartqa.core.DBconnector;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.helper.TestCases;
import org.deviartqa.helper.TextLocalization;
import org.deviartqa.pages.tools.domain.CreateDomainsPage;
import org.deviartqa.pages.tools.domain.DomainPage;
import org.deviartqa.pages.tools.domain.UpdateDomainsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

@Test
public class DomainsTest extends BaseTest {
    @Test(enabled = TestScenario.enable)
    public void test_permission_create(){
            addPermissionList();
            List<String> cases = TestCases.test_permissionOFF(permissions, () -> test_createDomain_mandatoryFields());
            Assert.assertEquals(cases.toString(), "[sys_domain.control, sys_domain.create]");
    }
    @Test(enabled = TestScenario.enable)
    public void test_permission_update(){
            addPermissionList();
            List<String> cases = TestCases.test_permissionOFF(permissions, () -> test_updateDomain());
            Assert.assertEquals(cases.toString(), "[sys_domain.control, sys_domain.update]");
    }
    public void test_createDomain_mandatoryFields(){
        String name = "test"+ DataHelper.getUuid() +".binliz.org.ua";
        createDomainsPage.open().readyPage()
                .setDomain_type(TextLocalization.get("landing_page"))
                .setDomain(name)
                .clickSaveButton();
        ResultSet result = findBD(name);
        try {
            Assert.assertTrue(result.next());
            domain_id = result.getInt("id");
        }catch (Throwable e){
            Assert.fail(e.toString());
        }

    }
    public void test_createDomain_double(){
        String name = "test"+ DataHelper.getUuid() +".binliz.org.ua";
        for (int i = 0; i < 2; i++) {
            createDomainsPage.open().readyPage()
                    .setDomain_type(TextLocalization.get("landing_page"))
                    .setDomain(name);
            try {
                createDomainsPage.clickSaveButton().readyPage();
                Assert.assertEquals(i,0);
            }catch (Throwable e){
                Assert.assertEquals(i,1);
            }

        }
    }
    public void test_createDomain_without_mandatoryFields(){
        String name = "test"+ DataHelper.getUuid() +".binliz.org.ua";
        createDomainsPage.open().readyPage();

        //Без Domain
        createDomainsPage.setDomain_type(TextLocalization.get("landing_page")).clickSaveButton();
        try {
            Assert.assertFalse(findBD(name).next());
        }catch (Throwable e){
            Assert.fail(e.toString());
        }

        //Без Domain_type
        createDomainsPage.open().readyPage().setDomain("dd").clickSaveButton();
        try {
            Assert.assertFalse(findBD(name).next());
        }catch (Throwable e){
            Assert.fail(e.toString());
        }
    }
    public void test_createDomain_allFields(){
        String name = "test"+ DataHelper.getUuid() +".binliz.org.ua";
        String notation = "testNotation";
        createDomainsPage.open().readyPage()
                .setDomain_type(TextLocalization.get("landing_page"))
                .setDomain(name)
                .setNotation(notation)
                .setUser_id("25254")
                .setProduct("#6943 - Sweep Robot Vacuum Cleaner - RO")
                .setModel_ids("#39 - https://domain.org.ua - Sweep Robot Vacuum Cleaner - RO (flyingprod)")
                .clickSaveButton();
        try {
            DBconnector db = new DBconnector();
            ResultSet res = db.select("SELECT * FROM terraleads.system_domains \n" +
                    "WHERE `domain` = '"+name+"'");
            res.next();
            Assert.assertEquals(res.getString("domain"),name);
            Assert.assertEquals(res.getString("virus_status"),"wait");
            Assert.assertEquals(res.getString("notes"),notation);
            Assert.assertEquals(res.getInt("product_id"),6943);
            Assert.assertEquals(res.getInt("model_ids"),39);
            Assert.assertEquals(res.getString("zone"),"org.ua");
            Assert.assertEquals(res.getString("base_domain"),"binliz.org.ua");
        }catch (Throwable e){
            Assert.fail(e.getMessage());
        }
    }
    public void test_updateDomain_testFields(){
        test_createDomain_mandatoryFields();

        //Тест Status update
        List<String> status = List.of("active","disabled","waiting_for_processing","processing","error_of_addition");
        status.forEach(x->{
            updateDomainsPage.open(domain_id).readyPage()
                    .choseStatus(TextLocalization.get(x))
                    .clickSaveButton();
            ResultSet res = findBD(domain_id);
            try {
                res.next();
            }catch (Throwable e){
                Assert.fail(e.toString());
            }
            try {
                if (x.equals("error_of_addition")){
                    Assert.assertEquals(res.getString("status"), "error");
                }else if (x.equals("waiting_for_processing")){
                    Assert.assertEquals(res.getString("status"), "wait");
                }else {
                    Assert.assertEquals(res.getString("status"), x);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        //Тест Domain_type update
        List<String> domain_type = List.of("landing_page", "pre_landing_page");
        domain_type.forEach(x->{
            updateDomainsPage.open(domain_id).readyPage()
                    .setDomain_type(TextLocalization.get(x))
                    .clickSaveButton();
            ResultSet res = findBD(domain_id);
            try {
                res.next();
            }catch (Throwable e){
                Assert.fail(e.toString());
            }
            try {
                Assert.assertEquals(res.getString("type"), x);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        //Тест Domain update - positive
        List<String> domain_positive = List.of("com", "org.ua");
        domain_positive.forEach(x->{
            String domain = DataHelper.getUuid()+"."+x;
            updateDomainsPage.open(domain_id).readyPage()
                    .setDomain(domain)
                    .clickSaveButton();
            ResultSet res = findBD(domain_id);
            try {
                res.next();
            }catch (Throwable e){
                Assert.fail(e.toString());
            }
            try {
                Assert.assertEquals(res.getString("domain"), domain);
                Assert.assertEquals(res.getString("zone"), x);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        //Тест Domain update - negative
        List<String> domain_negative = List.of("c", "orgua","вісві", "аів.іва", "null", " ");
        domain_negative.forEach(x->{
            String domain = DataHelper.getUuid()+"."+x;
            if (x.equals("null")){
                domain = "";
            }else if (x.equals(" ")){
                domain = x;
            }
            updateDomainsPage.open(domain_id).readyPage()
                    .setDomain(domain)
                    .clickSaveButton();
            ResultSet res = findBD(domain_id);
            try {
                res.next();
            }catch (Throwable e){
                Assert.fail(e.toString());
            }
            try {
                Assert.assertNotEquals(res.getString("domain"), domain);
                Assert.assertNotEquals(res.getString("zone"), x);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        //Тест Partner's ID update - positive
        List<String> partnersID_positive = List.of("124","");
        partnersID_positive.forEach(x->{
            updateDomainsPage.open(domain_id).readyPage()
                    .setUser_id(x)
                    .clickSaveButton();
            ResultSet res = findBD(domain_id);
            try {
                res.next();
            }catch (Throwable e){
                Assert.fail(e.toString());
            }
            try {
                Assert.assertEquals(res.getString("user_id"), (x.equals("")) ? null : x );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        //Тест Partner's ID update - negative
        List<String> partnersID_negative = List.of("0","sdf","аів"," ");
        partnersID_negative.forEach(x->{
            updateDomainsPage.open(domain_id).readyPage()
                    .setUser_id(x)
                    .clickSaveButton();
            ResultSet res = findBD(domain_id);
            try {
                res.next();
            }catch (Throwable e){
                Assert.fail(e.toString());
            }
            try {
                Assert.assertNotEquals(res.getString("user_id"), x);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void test_updateDomain(){
        name = DataHelper.getUuid()+".com";
        updateDomainsPage.open(domain_id).readyPage()
                .choseStatus(TextLocalization.get("active"))
                .setDomain_type(TextLocalization.get("landing_page"))
                .setDomain(name)
                .setNotation("Notation")
                .setUser_id("123")
                .setProduct("#6960 - Mini HD Camera - IT")
                .clickSaveButton();
        ResultSet res = findBD(domain_id);
        try {
            res.next();
        }catch (Throwable e){
            Assert.fail(e.toString());
        }
        try {
            Assert.assertEquals(res.getString("status"),"active");
            Assert.assertEquals(res.getString("domain"),name);
            Assert.assertEquals(res.getString("type"),"landing_page");
            Assert.assertEquals(res.getString("notes"),"Notation");
            Assert.assertEquals(res.getString("user_id"),"123");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void test_filter(){
        //test_updateDomain();

        //Test ID
        domainPage.open().readyPage()
                .filter.setId(String.valueOf(domain_id));
        Assert.assertEquals(list().count(),1);
        domainPage.filter.setId("");

        //Test Domain
        domainPage.open().readyPage()
                .filter.setDomain(name);
        Assert.assertEquals(list().count(),1);
        domainPage.filter.setDomain("");

        //Test Domain Type
        String domain_type = TextLocalization.get("pre_landing_page");
        domainPage.open().readyPage()
                .filter.setDomainType(domain_type);
        for (int i = 0; i < list().count(); i++){
            Assert.assertTrue(list().nth(i).textContent().contains(domain_type));
        }
        domainPage.filter.setDomainType(TextLocalization.get("all"));

        //Test Status
        String status = TextLocalization.get("disabled");
        domainPage.open().readyPage()
                .filter.setStatus(status);
        for (int i = 0; i < list().count(); i++){
            Assert.assertTrue(list().nth(i).textContent().contains(status));
        }
        domainPage.filter.setStatus(TextLocalization.get("all"));

        //Test Notes
        domainPage.open().readyPage()
                .filter.setNotes("Notation");
        for (int i = 0; i < list().count(); i++){
            Assert.assertTrue(list().nth(i).textContent().toLowerCase().contains("notation"));
        }
        domainPage.filter.setNotes("");

        //Test Partner's ID
        domainPage.open().readyPage()
                .filter.setUsers("25255");
        for (int i = 0; i < list().count(); i++){
            Assert.assertTrue(list().nth(i).textContent().contains("25255"));
        }
        domainPage.filter.setUsers("");

        //Test Landing_Prelanding
        domainPage.open().readyPage()
                .filter.setLanding_Prelanding("#39 - http://domain.org.ua - Sweep Robot Vacuum Cleaner - RO (flyingprod)");
        for (int i = 0; i < list().count(); i++){
            Assert.assertTrue(list().nth(i).textContent().contains("#39 - http://domain.org.ua - Sweep Robot Vacuum Cleaner - RO (flyingprod)"));
        }
        domainPage.filter.setLanding_Prelanding("");
    }
    private void addPermissionList(){
        permissions.add("sys_domain.control");
        permissions.add("sys_domain.listview");
        permissions.add("sys_domain.update");
        permissions.add("sys_domain.create");
        permissions.add("sys_domain.view");
        permissions.add("sys_domain.delete");
        permissions.add("sys_domain.history");
        permissions.add("sys_domain.check_ban_domain");
    }
    private ResultSet findBD(String name){
        return select("`domain` = '"+name+"'");
    }
    private ResultSet findBD(int id){
        return select("`id` = '"+id+"'");
    }
    private ResultSet select(String condition){
        ResultSet result = null;
        try {
            DBconnector db = new DBconnector();
            result = db.select("SELECT * FROM terraleads.system_domains \n" +
                    "WHERE "+condition);
        }catch (Throwable e){
            Assert.fail(e.getMessage());
        }
        return result;
    }
    private Locator list(){
        return new Widget(Locators.page.locator("//tr[@class=\"odd\"]")).element;
    }
    CreateDomainsPage createDomainsPage = new CreateDomainsPage();
    UpdateDomainsPage updateDomainsPage = new UpdateDomainsPage();
    DomainPage domainPage = new DomainPage();
    private int domain_id = 12081;
    private String name = "09a88e98-6baa-4dc2-9738-7c867ee019fe.com";
    private final HashSet<String> permissions = new HashSet<>();
}
