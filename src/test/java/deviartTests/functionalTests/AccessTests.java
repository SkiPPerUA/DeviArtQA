package deviartTests.functionalTests;

import deviartTests.BaseTest;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
import org.deviartqa.pages.main.WelcomePage;
import org.testng.annotations.Test;
import java.util.ArrayList;

@Test
public class AccessTests extends BaseTest {

    int user_1 = 25709;
    int user_2 = 25698;

    public void checkAccessForPages_forDifferentUsers() {
        Widget allPages;
        ArrayList<String> pagesForUser1 = new ArrayList<>();
        ArrayList<String> pagesForUser2 = new ArrayList<>();

        WelcomePage welcomePage = new WelcomePage().open().readyPage();
        welcomePage.header.changeUser(user_1);

        allPages = new Widget(Locators.page.locator("//div[@id='sidebar-menu']//a[contains(@href,'acp')]"));
        for (int i = 0; i < allPages.element.count(); i++){
            pagesForUser1.add(allPages.element.nth(i).getAttribute("href"));
        }
        System.out.print("Страницы доступны "+user_1+" ");
        System.out.println(pagesForUser1);


        welcomePage.header.changeUser(user_2);
        allPages = new Widget(Locators.page.locator("//div[@id='sidebar-menu']//a[contains(@href,'acp')]"));
        for (int i = 0; i < allPages.element.count(); i++){
            pagesForUser2.add(allPages.element.nth(i).getAttribute("href"));
        }
        System.out.print("Страницы доступны "+user_2+" ");
        System.out.println(pagesForUser2);


        ArrayList<String> pagesForUser1_but_not_user2 = (ArrayList<String>) pagesForUser1.clone();
        for (int i = 0; i<pagesForUser2.size(); i++){
            pagesForUser1_but_not_user2.remove(pagesForUser2.get(i));
        }
        System.out.print("Страницы доступны "+user_1+" НО не доступны "+user_2+" ");
        System.out.println(pagesForUser1_but_not_user2);

        ArrayList<String> pagesForUser2_but_not_user1 = (ArrayList<String>) pagesForUser2.clone();
        for (int i = 0; i<pagesForUser1.size(); i++){
            pagesForUser2_but_not_user1.remove(pagesForUser1.get(i));
        }
        System.out.print("Страницы доступны "+user_2+" НО не доступны "+user_1+" ");
        System.out.println(pagesForUser2_but_not_user1);
    }
}
