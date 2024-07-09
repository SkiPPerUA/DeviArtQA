package deviartTests.pageTests.main;

import deviartTests.BaseTest;
import org.deviartqa.TestScenario;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.helper.TextLocalization;
import org.deviartqa.pages.main.stream.CreateStreamsPage;
import org.deviartqa.pages.main.stream.StreamsPage;
import org.deviartqa.pages.main.stream.ViewStreamsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@Test
public class StreamsTest extends BaseTest {
    StreamsPage streamsPage = new StreamsPage();
    CreateStreamsPage createStreamsPage = new CreateStreamsPage();
    ViewStreamsPage viewStreamsPage = new ViewStreamsPage();
    public void filter_test(){
        streamsPage.open().readyPage()
                .filter
                .setId("1");
        streamsPage.filter
                .setCode("2")
                .setName("3")
                .setTraffic_source("Social media")
                .setProduct("VenoNormal")
                .setLanding_page("4")
                .setPrelanding_page("5")
                .setArchived("Yes");
    }
    public void create_stream_mandatoryFields(){
        String name = "Test"+DataHelper.getUuid();
        streamsPage.open().readyPage()
                .clickCreateStream().readyPage()
                .setName(name)
                .setTraffic_source("Facebook ads")
                .setProduct("Slim It")
                .setCountry("EG - Egypt")
                .setLandingPre_pages("Slim It - EG (girlherb)")
                .clickSave().readyPage();
    }
    public void create_stream_allFields(){
        String name = "Test"+DataHelper.getUuid();
        createStreamsPage.open().readyPage()
                .setName(name)
                .setTraffic_source("TikTok")
                .setProduct("Electric foot massager")
                .setCountry("IT - "+TextLocalization.get("italy"))
                .setPartner_landing_pages("Enabled")
                //.setLandingPre_pages("Cystenon (low price) - LV (synergy)")
                //.setLandingPre_pages("Cystenon (low price) - LV (worldorganization)")
                .setURL_Postback("https://postback.com/")
                .setPostbackType("GET")
                .setInformLeadsStatus("Accept", true)
                .setInformLeadsStatus("Expect", false)
                .setRedirectURL("https://redirect.com/")
                .setParameters("sub_id", "sub_id")
                .setParameters("sub_id_1","sub_id_1")
                .setParameters("sub_id_2","sub_id_2")
                .setParameters("sub_id_3","sub_id_3")
                .setParameters("sub_id_4","sub_id_4")
                .setParameters("utm_source", "utm_source")
                .setParameters("utm_medium","utm_medium")
                .setParameters("utm_campaign","utm_campaign")
                .setParameters("utm_term","utm_term")
                .setParameters("utm_content","utm_content")
                .setFB_pixelId("fb_prelanding","fb_prelanding")
                .setFB_pixelId("fb_landing","fb_landing")
                .setFB_pixelId("fb_page_success","fb_page_success")
                .setParameters("iframe_url_preland", "https://iframe_url_preland.com/")
                .setParameters("iframe_url_land","https://iframe_url_land.com/")
                .setParameters("iframe_url_land_success","https://iframe_url_land_success.com/")
                .clickSave().readyPage();

            Assert.assertTrue(viewStreamsPage.getParametersValue(TextLocalization.get("name")).contains(name));
            Assert.assertTrue(viewStreamsPage.getParametersValue(TextLocalization.get("archived")).contains("No"));
            Assert.assertTrue(viewStreamsPage.getParametersValue(TextLocalization.get("code")).contains("uURQ"));
            Assert.assertTrue(viewStreamsPage.getParametersValue(TextLocalization.get("tracker_url")).contains("https://tl-track.com/tracker/uURQ/?sub_id=sub_id&sub_id_1=sub_id_1&sub_id_2=sub_id_2&sub_id_3=sub_id_3&sub_id_4=sub_id_4&utm_source=utm_source&utm_medium=utm_medium&utm_campaign=utm_campaign&utm_term=utm_term&utm_content=utm_content"));
            Assert.assertTrue(viewStreamsPage.getParametersValue(TextLocalization.get("direct_links")).contains("https://inc-stories.com/worldorganization-cystenon-lv-lp/?alstream=uURQ&sub_id=sub_id&sub_id_1=sub_id_1&sub_id_2=sub_id_2&sub_id_3=sub_id_3&sub_id_4=sub_id_4&utm_source=utm_source&utm_medium=utm_medium&utm_campaign=utm_campaign&utm_term=utm_term&utm_content=utm_content"));
            Assert.assertTrue(viewStreamsPage.getParametersValue(TextLocalization.get("traffic_source")).contains("#15 - Google Ads"));
            Assert.assertTrue(viewStreamsPage.getParametersValue(TextLocalization.get("product")).contains("Electric foot massager"));
            Assert.assertTrue(viewStreamsPage.getParametersValue(TextLocalization.get("offers")).contains("5 EUR\t Latvia\n"));
            Assert.assertTrue(viewStreamsPage.getParametersValue(TextLocalization.get("payouts")).contains("25 USD / 1 T-coin"));
            Assert.assertTrue(viewStreamsPage.getParametersValue(TextLocalization.get("landing_pages")).contains("Cystenon (low price) - LV (synergy) - https://lp-lv-cystenon.conter-goods.com"));
            Assert.assertTrue(viewStreamsPage.getParametersValue(TextLocalization.get("partner_landing_pages")).contains("-"));
            Assert.assertTrue(viewStreamsPage.getParametersValue(TextLocalization.get("pre_landing_pages")).contains("Cystenon (low price) - LV (worldorganization) - https://inc-stories.com/worldorganization-cystenon-lv-lp/"));
            Assert.assertTrue(viewStreamsPage.getParametersValue(TextLocalization.get("url_postback")).contains("https://postback.com/"));
            Assert.assertTrue(viewStreamsPage.getParametersValue(TextLocalization.get("postback_type")).contains("GET"));
            Assert.assertTrue(viewStreamsPage.getParametersValue(TextLocalization.get("redirect_url_if_offer_disabled")).contains("https://redirect.com/"));
            Assert.assertTrue(viewStreamsPage.getParametersValue(TextLocalization.get("refresh_time")).contains("-"));
            Assert.assertTrue(viewStreamsPage.getParametersValue(TextLocalization.get("addition_time")).contains(DataHelper.getTime("yyyy-MM-dd k:m:")));
    }
    public void create_stream_alertNotify(){
        createStreamsPage.open().readyPage()
                .clickSave();
        checkAlertText(new Widget(Locators.page
                .locator("//div[@class=\"alert alert-block alert-danger\"]")).textContent());

        //Проверка подсказок возле полей
        if(TestScenario.local.equals("en")){
            Assert.assertTrue(new Widget(Locators.page.locator("//div/*[text()='Name cannot be blank.']")).isVisible());
            Assert.assertTrue(new Widget(Locators.page.locator("//div/*[text()='Traffic source cannot be blank.']")).isVisible());
            Assert.assertTrue(new Widget(Locators.page.locator("//div/*[text()='Country cannot be blank.']")).isVisible());
        }else {
            Assert.assertTrue(new Widget(Locators.page.locator("//div/*[text()='']")).isVisible());
            Assert.assertTrue(new Widget(Locators.page.locator("//div/*[text()='']")).isVisible());
            Assert.assertTrue(new Widget(Locators.page.locator("//div/*[text()='']")).isVisible());

        }

    }
    public void create_stream_TrafficSource_test(){
        List<String> enTrafficSource = List.of("Not set","Facebook ads","Google Ads","Adult advertising networks",
                "Seo, Paid search","Push - traffic","TikTok","Contextual advertising","Motivated traffic",
                "Doorways","Popup / Popunder", "Social media","Mobile applications","Broker traffic",
                "Advertising networks", "Email and SMS mailing", "Bigo Ads");
        List<String> ruTrafficSource = List.of("");
        createStreamsPage.open().readyPage();
        if (TestScenario.local.equals("en")){
            enTrafficSource.forEach(x ->
                    {
                        createStreamsPage.setTraffic_source(x);
                        Assert.assertEquals(new Widget(Locators.page.locator("//button[@data-id='type']")).textContent(),x+" ");
                    }
            );
        }else {
            ruTrafficSource.forEach(x ->
                    {
                        createStreamsPage.setTraffic_source(x);
                        Assert.assertEquals(new Widget(Locators.page.locator("//button[@data-id='type']")).textContent(),x+" ");
                    }
            );
        }
    }
    private void checkAlertText(String data){
        if (TestScenario.local.equals("en")){
            Assert.assertEquals(data, "Please fix the following input errors:\n" +
                    "\n" +
                    "Name cannot be blank.\n" +
                    "Product cannot be blank.\n" +
                    "Product must be a number.\n" +
                    "Product cannot be blank.\n" +
                    "Product must be a number.\n" +
                    "Country cannot be blank.\n" +
                    "Country must be a number.\n" +
                    "Choose of of offers\n" +
                    "Traffic source cannot be blank.\n" +
                    "Traffic source is not in the list.\n");
        }else {
            Assert.assertEquals(data,"");
        }
    }
}
