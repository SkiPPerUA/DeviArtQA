package deviartTests.pageTests.main;

import deviartTests.BaseTest;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
import org.deviartqa.pages.main.offer.OffersPage;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Test
public class OffersTest extends BaseTest {

    public void positive_multiUpdateRegionGroups(){
        OffersPage page = new OffersPage();

        //find test offers
        page.open().readyPage();
        List<String> orders = new ArrayList<>();
        for (int i = 1; i<5; i++){
            orders.add(new Widget(Locators.page.locator("//tbody/tr["+i+"]/td[2]")).textContent());
        }

        orders.forEach(x -> page.choiceOffer(Integer.parseInt(x)));

        page.clickGroupActionButton();
        page.choiceGroupAction("Africa");

//        Widget regions = new Widget(Locators.page.locator("//a[@data-field='country_group_id']"));
//        for (int i = 0; i<regions.element.count(); i++){
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            page.choiceGroupAction(regions.element.nth(i+1).textContent());
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            page.clickGroupActionButton();
//        }
    }
}
