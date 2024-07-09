package deviartTests.pageTests.noAuth;

import deviartTests.BaseTest;
import org.deviartqa.TestScenario;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Session;
import org.deviartqa.core.Widget;
import org.deviartqa.pages.noAuth.AuthPage;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Test
public class AuthTest extends BaseTest {

    AuthPage authPage = new AuthPage();

    @BeforeTest
    void open(){
        Session.context.clearCookies();
    }

    public void positive_auth(){
        authPage.open()
                .readyPage()
                .makeAuth("savchukvi12@gmail.com","p6&Rvgdl")
                .readyPage();
    }

    public void positive_check_buttons() {
        authPage.open()
                .readyPage()
                .clickRegistrationButton()
                .readyPage();

        authPage.open()
                .readyPage()
                .clickForgotPasswordButton()
                .readyPage();
    }

    public void negative_data(){
        authPage.open()
                .readyPage()
                .makeAuth("savchukvi12@gmail.com1","sdfhkhk@kjhdsf4");
        checkAlert();

        authPage.makeAuth("savchukvi12@gmail.com","sdfhkhk@kjhdsf5");
        checkAlert();
    }

    public void empty_fields() {
        Widget email = new Widget(Locators.email);
        Widget password = new Widget(Locators.password);
        Widget login = new Widget(Locators.submit);
        authPage.open().readyPage();

        checkNotify("email",false);
        checkNotify("password",false);

        login.click();
        checkNotify("email",true);
        checkNotify("password",true);

        email.fill("test@fda.ss");
        checkNotify("email",false);
        checkNotify("password",true);

        password.fill("1234132");
        checkNotify("email",false);
        checkNotify("password",false);

        email.clear();
        login.focus();
        checkNotify("email",true);
        checkNotify("password",false);
    }

    private void checkAlert(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String alert = new Widget(Locators.page
                .locator("//p[@class=\"alert__text\"]")).textContent();
        if (TestScenario.local.equals("en")){
            Assert.assertEquals(alert,"Wrong email or password");
        }else {
            Assert.assertEquals(alert,"");
        }
    }

    private void checkNotify(String field, boolean mustBe){
        String notify = new Widget(Locators.page
                .locator("//input[@name=\""+field+"\"]/../span[contains(@class,\"error\")]"))
                .textContent();
        if (mustBe) {
            if (TestScenario.local.equals("en")) {
                Assert.assertEquals(notify, "This field is required!");
            } else {
                Assert.assertEquals(notify, "");
            }
        }else {
            Assert.assertEquals(notify, "");
        }
    }
}
