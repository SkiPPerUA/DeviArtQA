package deviartTests.pageTests.main;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.impl.TargetClosedError;
import deviartTests.BaseTest;
import org.deviartqa.TestScenario;
import org.deviartqa.core.Session;
import org.deviartqa.core.Widget;
import org.deviartqa.pages.main.Statistics;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;

@Test
public class StatisticsTest extends BaseTest {

    Statistics statisticsPage = new Statistics();
    String name;

    public void checkLinks() throws InterruptedException {
        statisticsPage.open("web")
                .readyPage()
                .setDate_from("2025-05-24")
                .setDate_to("2025-06-06")
                .clickShowButton();
        Thread.sleep(3000);
        Widget widget = new Widget(Session.getPage().locator("//table//a"));
        for (int i = 0; i < 100; i++){
            if (i < widget.element.count()) {
                statisticsPage.open("web")
                        .readyPage()
                        .setDate_from("2025-05-24")
                        .setDate_to("2025-06-06")
                        .clickShowButton();
                checkLink(widget,i);
            }
        }
    }

    public void checkResult() throws InterruptedException {
        String group = "Manager";

        statisticsPage.open("web")
                .readyPage()
                .setDate_from("2025-01-13")
                .setDate_to("2025-01-13")
                .setGroupBy(group)
                .clickShowButton();
        Thread.sleep(3000);
        List<String> old_data = getData();
        Session.getPage().navigate(Session.getPage().url().replace("statistic","statisticNew"));
        statisticsPage.readyPage()
                .setDate_from("2025-01-13")
                .setDate_to("2025-01-13")
                .setGroupBy(group)
                .clickShowButton();
        Thread.sleep(3000);
        List<String> new_data = getData();
        try {
            Assert.assertEquals(new_data,old_data);
        }catch (AssertionError e){
            System.out.println("old > "+old_data);
            System.out.println("new > "+new_data);
        }
    }

    public void findErrorFilter(){
        String pageType = "web";
        //List<String> fill = List.of("dest_type","offer_type","group_mode","price_type");
        List<String> fill = List.of("country_group_id","manager_id","product_base_category_id","user_group_id","country_id");

        fill.forEach(x->{
            String checkLoc = String.format("//button[@data-id='%s']",x);
            statisticsPage.open(pageType).readyPage();
            Widget filter = new Widget(Session.getPage().locator(checkLoc));
            filter.click();
            int count = new Widget(Session.getPage().locator(checkLoc+"/..//a")).element.count();
            for (int i = 1; i < count; i++) {
                statisticsPage.open(pageType).readyPage()
                        .setDate_from("2025-01-13")
                        .setDate_to("2025-01-13");
                filter.click();
                Widget checkWid = new Widget(Session.getPage().locator(checkLoc + "/..//a"));
                Locator el = checkWid.element.nth(i);
                String name = el.textContent();
                el.click();
                filter.click();
                try {
                    statisticsPage.clickShowButton().readyPage();
                    System.out.println(checkWid.element.nth(i).textContent()+" -> {"+new Widget(Session.getPage().locator("//tbody/tr/td")).element.nth(0).textContent()+"}");
                    logger.info(name+ " = OK");
                }catch (Throwable e){
                    System.out.print(x+"  ");
                    System.out.println(name+ " = Error");
                }
            }
        });

    }

    public void checkAllLinks() throws InterruptedException {
        String pageType = "web";
        //List<String> fill = List.of("offer_id","dest_type","offer_type","group_mode","price_type");//"offer_id","dest_type","offer_type","group_mode","price_type"
        List<String> fill = List.of("country_group_id","manager_id","product_base_category_id","user_group_id","country_id","offer_id"); //"country_group_id","manager_id","product_base_category_id","user_group_id","country_id","offer_id"


        statisticsPage.open(pageType)
                .readyPage()
                .setDate_from("2025-01-01")
                .setDate_to("2025-06-06")
                .clickShowButton();
        Thread.sleep(3000);
        Widget widget = new Widget(Session.getPage().locator("//table//a"));

        fill.forEach(x->{
            String checkLoc = String.format("//button[@data-id='%s']",x);
            statisticsPage.open(pageType).readyPage();
            Widget filter = new Widget(Session.getPage().locator(checkLoc));
            filter.click();
            int count = new Widget(Session.getPage().locator(checkLoc+"/..//a")).element.count();
            for (int i = 0; i < count; i++) {
                for (int t = 0; t < 80; t++){
                        statisticsPage.open(pageType).readyPage()
                                .setDate_from("2025-01-01")
                                .setDate_to("2025-06-06");
                        filter.click();

                        Widget checkWid = new Widget(Session.getPage().locator(checkLoc + "/..//a"));
                        Locator el = checkWid.element.nth(i);
                        name = el.textContent();
                        el.click();
                        filter.click();
                        statisticsPage.clickShowButton();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        if (!checkLink(widget,t)){
                            break;
                        }
                }
            }
        });


    }

    private List<String> getData(){
        List<String> data = new ArrayList<>();
        Widget widget = new Widget(Session.getPage().locator("//tbody[@aria-live='polite']//td"));
        for (int i = 0; i < 100; i++){
            if (i < widget.element.count()) {
                String text = widget.element.nth(i).textContent().trim().replace(" ", "");
                char[] arr = text.toCharArray();
                List<String> arrNew = new ArrayList<>();
                for (int y = 0; y < text.length(); y++) {
                    if ((int) arr[y] != 10 && (int) arr[y] != 160) {
                        arrNew.add(String.valueOf(arr[y]));
                    }
                }
                StringBuilder result = new StringBuilder();

                for (String str : arrNew) {
                    result.append(str);
                }
                data.add(result.toString());
            }
        }
        return data;
    }

    private boolean checkLink(Widget widget, int i){
        boolean contin = true;
        Locator loc = widget.element.nth(i);
        if (loc.isVisible()){
            if (i == 0){
                logger.info(name+" ===> ");
            }
            String countStats = loc.textContent();
            char [] arr = countStats.toCharArray();
            List<Integer> arrInt = new ArrayList<>();
            for (char x : arr){
                try {
                    arrInt.add(Integer.parseInt(String.valueOf(x)));
                }catch (Throwable e){
                    break;
                }
            }
            StringBuilder result = new StringBuilder();
            for (int str : arrInt) {
                result.append(str);
            }
            loc.click();

            new Widget(Session.getPage().locator("//a[@class='btn btn-success']")).element.isVisible();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (new Widget(Session.getPage().locator("//div[@class='summary']")).isVisible()){
                String findCount = "null";
                try {
                    findCount = new Widget(Session.getPage().locator("//div[@class='summary']")).textContent();
                    Assert.assertTrue(findCount.contains("of "+result+" result"));
                    logger.info("В стате -> {"+countStats+"} И по ссылке -> "+findCount);
                }catch (AssertionError e){
                    logger.error("В стате -> {"+countStats+"} а по ссылке -> "+findCount);
                }
            }else {
                if (Session.getPage().url().contains("acp/lead")){
                    if (Integer.parseInt(result.toString()) == 0){
                        logger.info("В стате -> {"+countStats+"} И по ссылке -> 0");
                    }else {
                        logger.error("В стате -> {"+countStats+"} а по ссылке -> 0");
                    }
                }else {
                    logger.error("В стате -> {" + countStats + "} а ссылка НЕ acp/lead");
                }
            }
        }else {
            if (i == 0){
                logger.info(name+" ===> стата пустая");
            }
            contin = false;
        }
        return contin;
    }
}
