package deviartTests.pageTests.noAuth;

import deviartTests.BaseTest;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.helper.TestCases;
import org.deviartqa.pages.noAuth.RegistrationPage1;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.sql.ResultSet;
import java.sql.SQLException;

@Test
public class RegistrationPageTests extends BaseTest {

    RegistrationPage1 registrationPage = new RegistrationPage1();

    public void positive_registration() throws SQLException {
        String login = DataHelper.getUuid()+"@test.ua";
        registrationPage.open().readyPage()
                .setEmail(login)
                .setMessenger("telegram","111")
                .setPassword("123456")
                .setPassword_repeat("123456")
                .clickAgreementCheckBox()
                .clickSendButton().readyPage();
        ResultSet res = getDB().select("SELECT count(*) FROM terraleads.users where email='"+login+"'");
        res.next();
        Assert.assertEquals(res.getInt(1),1);
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
