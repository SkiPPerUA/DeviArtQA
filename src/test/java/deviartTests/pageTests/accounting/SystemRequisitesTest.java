package deviartTests.pageTests.accounting;

import deviartTests.BaseTest;
import org.deviartqa.core.DBconnector;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.helper.TextLocalization;
import org.deviartqa.pages.accounting.systemRequisites.CreateSystemRequisitesPage;
import org.deviartqa.pages.accounting.systemRequisites.SystemRequisitesPage;
import org.deviartqa.pages.accounting.systemRequisites.UpdateSystemRequisitesPage;
import org.deviartqa.pages.accounting.systemRequisites.ViewSystemRequisitesPage;
import org.opentest4j.AssertionFailedError;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Test
public class SystemRequisitesTest extends BaseTest {

    public void test_create_SystemRequisites_mandatoryFields(){
        createSystemRequisitesPage.open().readyPage()
                .setCompany_name("Test"+ DataHelper.getUuid())
                .setRegistration_number(String.valueOf(new Date().getTime()))
                .setLegal_address("Test_Legal_address")
                .setInvoice_number_template("{number} - invoice number, {year} - year")
                .setInvoice_corrective_number_template("{number} - invoice corrective number, {year} - year")
                .setEmail("test@gmai.com")
                .clickSaveButton().readyPage();
    }

    public void test_create_SystemRequisites_allFields() throws SQLException {
        name = "Test"+DataHelper.getUuid();
        regNumber = String.valueOf(new Date().getTime());
        createSystemRequisitesPage.open().readyPage()
                .setCompany_name(name)
                .setRegistration_number(regNumber)
                .setLegal_address("Test_Legal_address")
                .setTax_number("11111111")
                .setInvoice_number_template("{number} - invoice number, {year} - year")
                .setInvoice_corrective_number_template("{number} - invoice corrective number, {year} - year")
                .setEmail("test@gmail.com")
                .clickSaveButton().readyPage();
        ResultSet res = getBD_by("company_name='"+name+"'",false);
        res.next();
        Assert.assertEquals(res.getString("registration_number"),regNumber);
        Assert.assertEquals(res.getString("legal_address"),"Test_Legal_address");
        Assert.assertEquals(res.getString("tax_number"),"11111111");
        Assert.assertEquals(res.getString("email"),"test@gmail.com");
        Assert.assertEquals(res.getString("invoice_number_template"),"{number} - invoice number, {year} - year");
        Assert.assertEquals(res.getString("invoice_corrective_number_template"),"{number} - invoice corrective number, {year} - year");
    }

    public void test_create_SystemRequisites_allPaymentsSystem(){
        String name = "Test"+DataHelper.getUuid();
        String regNumber = String.valueOf(new Date().getTime());
        createSystemRequisitesPage.open().readyPage()
                .setCompany_name(name)
                .setRegistration_number(regNumber)
                .setLegal_address("Test_Legal_address")
                .setTax_number("11111111")
                .setInvoice_number_template("{number} - invoice number, {year} - year")
                .setInvoice_corrective_number_template("{number} - invoice corrective number, {year} - year")
                .setEmail("test@gmail.com");
        pay_systems.forEach(x->{
            createSystemRequisitesPage.clickAddAccountButton()
                    .setPaymentSystemId(x);
        });
        for (int i = 0; i < new Widget(Locators.page.locator("//div[@style=\"display: block;\"]")).element.count(); i++) {
            createSystemRequisitesPage.setCurrency("EUR",i);
            new Widget(Locators.page.locator("//div[@style='display: block;']//input")).element.nth(i).fill("testPaymentField" + i+"@sda.ads");
            new Widget(Locators.page.locator("//input[contains(@name,'[name]')]")).element.nth(i).fill("testPaymentName"+i);
        }
        createSystemRequisitesPage.clickSaveButton().readyPage();
}

    public void test_create_SystemRequisites_PaymentsSystemBank() throws InterruptedException {
        String name = "Test"+DataHelper.getUuid();
        String regNumber = String.valueOf(new Date().getTime());
        createSystemRequisitesPage.open().readyPage()
                .setCompany_name(name)
                .setRegistration_number(regNumber)
                .setLegal_address("Test_Legal_address")
                .setTax_number("11111111")
                .setInvoice_number_template("{number} - invoice number, {year} - year")
                .setInvoice_corrective_number_template("{number} - invoice corrective number, {year} - year")
                .setEmail("test@gmail.com");
        createSystemRequisitesPage.clickAddAccountButton()
                    .setPaymentSystemId("Bank Transfer");
        for (int i = 0; i < new Widget(Locators.page.locator("//div[@style=\"display: block;\"]")).element.count(); i++) {
            new Widget(Locators.page.locator("//div[@style='display: block;']//input")).element.nth(i).fill("testPaymentField" + i);
        }
        createSystemRequisitesPage.setCurrency("USD");
        new Widget(Locators.page.locator("//input[contains(@name,'[name]')]")).fill("testPaymentName");
        Thread.sleep(4000);
        createSystemRequisitesPage.clickSaveButton().readyPage();
    }

    public void test_create_SystemRequisites_doublePaymentsSystem() {
        int countDouble = 2;
        String name = "Test"+DataHelper.getUuid();
        String regNumber = String.valueOf(new Date().getTime());
        createSystemRequisitesPage.open().readyPage()
                .setCompany_name(name)
                .setRegistration_number(regNumber)
                .setLegal_address("Test_Legal_address")
                .setTax_number("11111111")
                .setInvoice_number_template("{number} - invoice number, {year} - year")
                .setInvoice_corrective_number_template("{number} - invoice corrective number, {year} - year")
                .setEmail("test@gmail.com");
        for(int i = 0; i < countDouble; i++){
            createSystemRequisitesPage.clickAddAccountButton()
                    .setPaymentSystemId("PayPal");
        }
        for (int i = 0; i < new Widget(Locators.page.locator("//div[@style=\"display: block;\"]")).element.count(); i++) {
            new Widget(Locators.page.locator("//div[@style='display: block;']//input")).element.nth(i).fill("testPaymentField@das.da");
            new Widget(Locators.page.locator("//input[contains(@name,'[name]')]")).element.nth(i).fill("testPaymentName");
            createSystemRequisitesPage.setCurrency("EUR",i);
        }
        try {
            createSystemRequisitesPage.clickSaveButton().readyPage();
            throw new RuntimeException("Дубли созданы");
        }catch (AssertionFailedError e){}
    }

    public void test_search_count() throws SQLException {
        systemRequisitesPage.open().readyPage();
        ResultSet res = dBconnector.select("SELECT count(*) FROM terraleads.accounting_system_requisites");
        res.next();
        Assert.assertEquals(new Widget(Locators.page.locator("//tbody/*")).element.count(),res.getInt(1));
    }

    public void test_searchFields() throws SQLException {
        String resultLoc = "//tbody/tr";
        //Search by ID
        systemRequisitesPage.open().readyPage()
                .setID("3").clickShowResultButton();
        Widget result = new Widget(Locators.page.locator(resultLoc));
        Assert.assertTrue(result.textContent().substring(0,2).contains("3"));
        ResultSet sqlRes = getBD_by("id=3",true);
        sqlRes.next();
        Assert.assertEquals(result.element.count(),sqlRes.getInt(1));


        test_create_SystemRequisites_allFields();

        //Search by Name
        systemRequisitesPage.open().readyPage()
                .setCompany_name(name).clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i<result.element.count();i++){
            Assert.assertTrue(result.element.nth(i).textContent().contains(name));
        }
        sqlRes = getBD_by("company_name like ('%"+name+"%')",true);
        sqlRes.next();
        Assert.assertEquals(result.element.count(),sqlRes.getInt(1));

        //Search by Registration number
        systemRequisitesPage.open().readyPage()
                .setRegistration_number(regNumber).clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i<result.element.count();i++){
            Assert.assertTrue(result.element.nth(i).textContent().contains(regNumber));
        }
        sqlRes = getBD_by("registration_number like ('%"+regNumber+"%')",true);
        sqlRes.next();
        Assert.assertEquals(result.element.count(),sqlRes.getInt(1));

        //Search by Legal address
        systemRequisitesPage.open().readyPage()
                .setLegal_address("Test_Legal_address").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i<result.element.count();i++){
            Assert.assertTrue(result.element.nth(i).textContent().contains("Test_Legal_address"));
        }
        sqlRes = getBD_by("legal_address like ('%Test_Legal_address%')",true);
        sqlRes.next();
        Assert.assertEquals(result.element.count(),sqlRes.getInt(1));

        //Search by Tax number
        systemRequisitesPage.open().readyPage()
                .setTax_number("11111111").clickShowResultButton();
        result = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i<result.element.count();i++){
            Assert.assertTrue(result.element.nth(i).textContent().contains("11111111"));
        }
        sqlRes = getBD_by("tax_number like ('%11111111%')",true);
        sqlRes.next();
        Assert.assertEquals(result.element.count(),sqlRes.getInt(1));
    }

    public void test_viewSystemRequisites() throws SQLException {
        int id = 16;
        viewSystemRequisitesPage.open(id).readyPage();
        ResultSet res = getBD_by("id="+id,false);
        res.next();
        Assert.assertTrue(viewSystemRequisitesPage.getParametersValue("ID").contains(String.valueOf(id)));
        Assert.assertTrue(viewSystemRequisitesPage.getParametersValue(TextLocalization.get("time_added")).contains(res.getString("t_created")));
        Assert.assertTrue(viewSystemRequisitesPage.getParametersValue(TextLocalization.get("company_name")).contains(res.getString("company_name")));
        Assert.assertTrue(viewSystemRequisitesPage.getParametersValue(TextLocalization.get("registration_number")).contains(res.getString("registration_number")));
        Assert.assertTrue(viewSystemRequisitesPage.getParametersValue(TextLocalization.get("legal_address")).contains(res.getString("legal_address")));
        Assert.assertTrue(viewSystemRequisitesPage.getParametersValue(TextLocalization.get("tax_number")).contains(res.getString("tax_number")));
        Assert.assertTrue(viewSystemRequisitesPage.getParametersValue(TextLocalization.get("email")).contains(res.getString("email")));
        Assert.assertTrue(viewSystemRequisitesPage.getParametersValue(TextLocalization.get("invoice_number_template")).contains(res.getString("invoice_number_template")));
        Assert.assertTrue(viewSystemRequisitesPage.getParametersValue(TextLocalization.get("invoice_corrective_number_template")).contains(res.getString("invoice_corrective_number_template")));
    }

    public void test_SystemRequisites_buttons(){
        int id = 16;
        viewSystemRequisitesPage.open(id).readyPage()
                .clickCreateButton().readyPage();

        viewSystemRequisitesPage.open(id).readyPage()
                .clickModifyButton().readyPage();

        viewSystemRequisitesPage.open(id).readyPage()
                .clickListButton().readyPage();

        systemRequisitesPage.open().readyPage()
                .clickCreateButton().readyPage();

        createSystemRequisitesPage.open().readyPage()
                .clickCancelButton().readyPage();

        updateSystemRequisitesPage.open(id).readyPage()
                .clickCancelButton().readyPage();
    }

    public void test_updateSystemRequisites() throws SQLException {
        String uuid = DataHelper.getUuid();
        regNumber = String.valueOf(new Date().getTime());
        updateSystemRequisitesPage.open(5).readyPage()
                .setCompany_name(uuid)
                .setRegistration_number(regNumber)
                .setLegal_address("Test_Legal_address"+uuid)
                .setTax_number("11111111"+uuid)
                .setInvoice_number_template("{number} - invoice number, {year} - year"+uuid)
                .setInvoice_corrective_number_template("{number} - invoice corrective number, {year} - year"+uuid)
                .setEmail("test@gmail.com"+uuid)
                .clickSaveButton().readyPage();
        ResultSet res = getBD_by("company_name='"+uuid+"'",false);
        res.next();
        Assert.assertEquals(res.getString("registration_number"),regNumber);
        Assert.assertEquals(res.getString("legal_address"),"Test_Legal_address"+uuid);
        Assert.assertEquals(res.getString("tax_number"),"11111111"+uuid);
        Assert.assertEquals(res.getString("email"),"test@gmail.com"+uuid);
        Assert.assertEquals(res.getString("invoice_number_template"),"{number} - invoice number, {year} - year"+uuid);
        Assert.assertEquals(res.getString("invoice_corrective_number_template"),"{number} - invoice corrective number, {year} - year"+uuid);
    }

    SystemRequisitesPage systemRequisitesPage = new SystemRequisitesPage();
    CreateSystemRequisitesPage createSystemRequisitesPage = new CreateSystemRequisitesPage();
    ViewSystemRequisitesPage viewSystemRequisitesPage = new ViewSystemRequisitesPage();
    UpdateSystemRequisitesPage updateSystemRequisitesPage = new UpdateSystemRequisitesPage();
    DBconnector dBconnector;
    String name = "";
    String regNumber = "";
    List<String> pay_systems = List.of("PayPal", "Paxum", "Capitalist", "Payoneer", "USDT ERC20", "USDT TRC20",
            "Brocard", TextLocalization.get("cash"), TextLocalization.get("cards")+"(UA)");

    @BeforeTest
    public void openBD(){
        dBconnector = new DBconnector();
    }

    private ResultSet getBD_by(String condition,boolean count){
        String sql = "";
        if(count){
            sql = "SELECT count(*) FROM terraleads.accounting_system_requisites where ";
        }else {
            sql = "SELECT * FROM terraleads.accounting_system_requisites where ";
        }
        return dBconnector.select(sql+condition);
    }

}
