package deviartTests.pageTests.noAuth;

import deviartTests.BaseTest;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.helper.TestCases;
import org.deviartqa.pages.noAuth.AuthPage;
import org.deviartqa.pages.noAuth.registrationPage.RegistrationPage1;
import org.deviartqa.pages.noAuth.registrationPage.RegistrationPage2;
import org.deviartqa.pages.noAuth.registrationPage.RegistrationPage3;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.sql.ResultSet;
import java.sql.SQLException;

@Test
public class RegistrationPageTests extends BaseTest {

    RegistrationPage1 registrationPage = new RegistrationPage1();
    RegistrationPage2 registrationPage2 = new RegistrationPage2();
    RegistrationPage3 registrationPage3 = new RegistrationPage3();
    AuthPage authPage = new AuthPage();

    public void positive_registration() throws SQLException, InterruptedException {
        ResultSet res;
        String login = DataHelper.getUuid()+"@test.ua";
        registrationPage.open().readyPage()
                .setEmail(login)
                .setMessenger("telegram","111")
                .setPassword("123456")
                .setPassword_repeat("123456")
                .clickAgreementCheckBox()
                .clickSendButton().readyPage();
        res = getDB().select("SELECT * FROM terraleads.users where email='"+login+"'");
        res.next();
        Assert.assertEquals(res.getInt("register_step"),2);
        Assert.assertEquals(res.getInt("status"),3);

        registrationPage2.choiceParameter("SEO")
                .choiceParameter("1 to 3 years")
                .choiceParameter("solo")
                .clickNextButton().readyPage();
        res = getDB().select("SELECT * FROM terraleads.users where email='"+login+"'");
        res.next();
        Assert.assertEquals(res.getInt("register_step"),3);
        Assert.assertEquals(res.getInt("status"),3);

        registrationPage3.choiceParameter("between $1000 and $3000")
                .choiceParameter("media")
                .clickSendButton();
        Thread.sleep(2000);
        res = getDB().select("SELECT * FROM terraleads.users where email='"+login+"'");
        res.next();
        Assert.assertEquals(res.getInt("register_step"),4);
        Assert.assertEquals(res.getInt("status"),3);
    }

    public void test_stepsOnLogin(){
        String login = "dasdas@gmail.com";

        getDB().update("UPDATE terraleads.users set register_step = 2 where email = '"+login+"'");
        authPage.open().readyPage()
                        .makeAuth(login,"123456");
        registrationPage2.readyPage();

        getDB().update("UPDATE terraleads.users set register_step = 3 where email = '"+login+"'");
        authPage.open().readyPage()
                .makeAuth(login,"123456");
        registrationPage3.readyPage();
    }

    public void negative_registration() {
        //email validation
        TestCases.negative_emails().forEach(x-> {
            registrationPage.open().readyPage()
                    .setEmail(x)
                    .setMessenger("telegram","111")
                    .setPassword("123456")
                    .setPassword_repeat("123456")
                    .clickAgreementCheckBox()
                    .clickSendButton();
            registrationPage.readyPage();
        });

        //email is null
        registrationPage.open().readyPage()
                .setEmail("")
                .clickMessengerIcon("telegram")
                .setPassword("123456")
                .setPassword_repeat("123456")
                .clickAgreementCheckBox()
                .clickSendButton();
        registrationPage.readyPage();

        //messenger not select
        registrationPage.open().readyPage()
                .setEmail(DataHelper.getUuid()+"@test.ua")
                .clickMessengerIcon("telegram")
                .setPassword("123456")
                .setPassword_repeat("123456")
                .clickAgreementCheckBox()
                .clickSendButton();
        registrationPage.readyPage();

        //not all active messenger is fill
        registrationPage.open().readyPage()
                .setEmail(DataHelper.getUuid()+"@test.ua")
                .setMessenger("telegram","111")
                .clickMessengerIcon("skype")
                .setPassword("123456")
                .setPassword_repeat("123456")
                .clickAgreementCheckBox()
                .clickSendButton();
        registrationPage.readyPage();

        //messenger is not fill
        registrationPage.open().readyPage()
                .setEmail(DataHelper.getUuid()+"@test.ua")
                .setPassword("123456")
                .setPassword_repeat("123456")
                .clickAgreementCheckBox()
                .clickSendButton();
        registrationPage.readyPage();

        //password is not fill
        registrationPage.open().readyPage()
                .setEmail(DataHelper.getUuid()+"@test.ua")
                .setMessenger("telegram","111")
                .setPassword_repeat("123456")
                .clickAgreementCheckBox()
                .clickSendButton();
        registrationPage.readyPage();

        //password is 1 symbol
        registrationPage.open().readyPage()
                .setEmail(DataHelper.getUuid()+"@test.ua")
                .setMessenger("telegram","111")
                .setPassword("1")
                .setPassword_repeat("1")
                .clickAgreementCheckBox()
                .clickSendButton();
        registrationPage.readyPage();

        //password is not same
        registrationPage.open().readyPage()
                .setEmail(DataHelper.getUuid()+"@test.ua")
                .setMessenger("telegram","111")
                .setPassword("123456")
                .setPassword_repeat("1234567")
                .clickAgreementCheckBox()
                .clickSendButton();
        registrationPage.readyPage();

        //agreement is not check
        registrationPage.open().readyPage()
                .setEmail(DataHelper.getUuid()+"@test.ua")
                .setMessenger("telegram","111")
                .setPassword("123456")
                .setPassword_repeat("123456")
                .clickSendButton();
        registrationPage.readyPage();
    }
}
