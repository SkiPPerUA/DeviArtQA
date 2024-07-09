package deviartTests.pageTests.clients;

import deviartTests.BaseTest;
import org.deviartqa.TestScenario;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
import org.deviartqa.pages.clients.behaviorFactors.AddBehaviorFactors;
import org.deviartqa.pages.clients.behaviorFactors.BehaviorFactors;
import org.deviartqa.pages.clients.behaviorFactors.UpdateBehaviorFactors;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

@Test
public class BehaviorFactorsTest extends BaseTest {

    BehaviorFactors behaviorFactors = new BehaviorFactors();
    AddBehaviorFactors addBehaviorFactors = new AddBehaviorFactors();
    UpdateBehaviorFactors updateBehaviorFactors = new UpdateBehaviorFactors();
    int id = 1;

    public void check_list() throws SQLException {
        if (TestScenario.env.equals("prime")){
            ResultSet list_base_property = getDB().select("SELECT * FROM terraleads.product_base_property");
            behaviorFactors.open().readyPage();
            while (list_base_property.next()){
                if (TestScenario.local.equals("ru")){
                    new Widget(Locators.page.locator("//tbody/tr/td[text()='"+list_base_property.getString("name_ru")+"']")).isVisible();
                }else {
                    new Widget(Locators.page.locator("//tbody/tr/td[text()='"+list_base_property.getString("name")+"']")).isVisible();
                }
            }
        }else {
            throw new SkipException("Test only for prime");
        }
    }

    public void add_new_property(int id) throws SQLException {
        if (TestScenario.env.equals("prime")){
            ResultSet base_property_value = getDB().select("SELECT count(*) FROM terraleads.product_base_property_value");
            base_property_value.next();
            int oldCount = base_property_value.getInt(1);
            addBehaviorFactors.open(id).readyPage()
                    .fillValue("22","33")
                    .clickSaveButton().readyPage();
            base_property_value = getDB().select("SELECT count(*) FROM terraleads.product_base_property_value");
            base_property_value.next();
            Assert.assertEquals(base_property_value.getInt(1),oldCount+1);
        }else {
            throw new SkipException("Test only for prime");
        }
    }

    public void update_property(int id) throws SQLException {
        if (TestScenario.env.equals("prime")){
            add_new_property(id);
            updateBehaviorFactors.open(id).readyPage();
        }else {
            throw new SkipException("Test only for prime");
        }
    }


}
