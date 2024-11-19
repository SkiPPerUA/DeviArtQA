package deviartTests.pageTests.analytics;

import deviartTests.BaseTest;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
import org.deviartqa.pages.analytics.OpexPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

@Test
public class OpexTest extends BaseTest {

    OpexPage opexPage = new OpexPage();

    public void test_allAdv(){
        opexPage.open().readyPage();
        List<String> adv = List.of("it@white-hatters.com","ro@white-hatters.com");
        Assert.assertEquals(new Widget(Locators.page.locator("//tbody/tr")).element.count(),adv.size());
        adv.forEach(x-> new Widget(Locators.page.locator("//td[text()='"+x+"']")));
    }

    public void test_allOffers() throws SQLException {
        opexPage.open().readyPage().clickBoughtRateTab();
        ResultSet res = getDB().select("SELECT x.* FROM terraleads.campaign x\n" +
                "WHERE status = 'active' and status_work = 'active' and mode = 1");
        while (res.next()){
            new Widget(Locators.page.locator("//tbody//td[contains(text(),'"+res.getString("name")+"')]"));
        }
    }

    public void test_parametersOpex() throws SQLException {
        int id = 25554;
        checkOpex(id, 555, 33, 10);
        checkOpex(id, 421, 31, 63);

        List.of(25563,id).forEach(x -> {
            ResultSet res = getDB().select("select count(*) FROM terraleads.lead l\n" +
                    "WHERE l.t_created >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH)\n" +
                    "and l.status_adv = \"confirm\" \n" +
                    "and user_adv_id = "+x);
            try {
                res.next();
                int count = res.getInt(1);
                if (count > 0){
                    Assert.assertEquals(new DecimalFormat("#.##").format(Float.parseFloat(opexPage.getCurrentCpo_value(x))).replace(",","."), new DecimalFormat("#.##").format(Float.parseFloat(opexPage.getOpex(x))/count).replace(",","."));
                }
            } catch (Throwable e) {
                Assert.fail(x+" - бага");
            }
        });
    }

    public void test_parametersBought() throws SQLException {
        int id = 7797;

        opexPage.open().readyPage()
                .clickBoughtRateTab()
                .setBought_rate("1",id)
                .clickSaveButton(id);
        ResultSet res = getDB().select("SELECT * FROM terraleads.offer_bought_rate where offer_id="+id+" order by id desc");
        res.next();
        Assert.assertEquals(res.getInt("bought_rate"),1);

        opexPage.open().readyPage()
                .clickBoughtRateTab()
                .setBought_rate("14",id)
                .clickSaveButton(id);
        res = getDB().select("SELECT * FROM terraleads.offer_bought_rate where offer_id="+id+" order by id desc");
        res.next();
        Assert.assertEquals(res.getInt("bought_rate"),14);
    }

    public void test_searchFields() {
        String resultLoc = "//tbody/tr";
        Widget res;

        //Search by Country - OPEX
        opexPage.open().readyPage()
                .setCountry("IT - Italy")
                .clickShowResultButton();
        res = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i < res.element.count(); i++){
            Assert.assertTrue(res.element.nth(i).textContent().contains("Italy"));
        }

        //Search by Country - Bought Rate
        opexPage.open().readyPage()
                .clickBoughtRateTab()
                .setCountry("IT - Italy")
                .clickShowResultButton();
        res = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i < res.element.count(); i++){
            Assert.assertTrue(res.element.nth(i).textContent().contains("Italy"));
        }

        //Search by Advertiser
        opexPage.open().readyPage()
                .setAdvertiserOrOffer("it@white-hatters.com")
                .clickShowResultButton();
        res = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i < res.element.count(); i++){
            Assert.assertTrue(res.element.nth(i).textContent().contains("it@white-hatters.com"));
        }

        //Search by Offer
        opexPage.open().readyPage()
                .clickBoughtRateTab()
                .setAdvertiserOrOffer("#8122 - Fleece Blanket - RO (Romania) - 10 RON")
                .clickShowResultButton();
        res = new Widget(Locators.page.locator(resultLoc));
        for (int i = 0; i < res.element.count(); i++){
            Assert.assertTrue(res.element.nth(i).textContent().contains("#8122 - Fleece Blanket - RO (Romania) - 10 RON"));
        }

    }

    private void checkOpex(int id, int opex, int capa, int cpApp) throws SQLException {
        opexPage.open().readyPage()
                .setOpex(String.valueOf(opex),id)
                .setCapa(String.valueOf(capa),id)
                .setCpApprove(String.valueOf(cpApp),id)
                .clickSaveButton(id);
        ResultSet res = getDB().select("SELECT * FROM terraleads.opex where advertiser_id="+id+" order by id desc");
        res.next();
        Assert.assertEquals(res.getInt("opex"),opex);
        Assert.assertEquals(res.getInt("capa"),capa);
        Assert.assertEquals(res.getInt("cp_aprove"),cpApp);


        float expected_cpo = opex / ( (capa * (cpApp/100f) ) * 30 );
        String decCpo = new DecimalFormat("#.##").format(expected_cpo).replace(",",".");

        Assert.assertEquals(res.getFloat("cpo"),Float.parseFloat(decCpo));
        Assert.assertEquals(opexPage.getCpo_value(id),decCpo);
    }
}
