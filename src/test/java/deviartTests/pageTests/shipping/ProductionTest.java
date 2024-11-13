package deviartTests.pageTests.shipping;

import deviartTests.BaseTest;
import org.deviartqa.pages.shipping.production.Production;
import org.deviartqa.pages.shipping.production.UpdateProduction;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

@Test
public class ProductionTest extends BaseTest {

    Production production = new Production();
    UpdateProduction updateProduction = new UpdateProduction();
    int product_base_id = 1418;
    int store_id = 2;

    public void positive_activeDisable_onProductionPage() throws SQLException {
        getDB().update("UPDATE terraleads_shipping.production SET status = 1 WHERE product_base_id = "+product_base_id+" AND store_id = "+store_id);

        production.open().readyPage()
                .selectedProduct(store_id,product_base_id)
                .clickDisableButton();

        ResultSet res = getDB().select("SELECT * FROM terraleads_shipping.production WHERE product_base_id = "+product_base_id+" AND store_id = "+store_id);
        while (res.next()){
            Assert.assertEquals(res.getInt("status"),2);
        }

        production.open().readyPage()
                .setStatus("Disable")
                .selectedProduct(store_id,product_base_id)
                .clickActiveButton();

        res = getDB().select("SELECT * FROM terraleads_shipping.production WHERE product_base_id = "+product_base_id+" AND store_id = "+store_id);
        while (res.next()){
            Assert.assertEquals(res.getInt("status"),1);
        }
    }

    public void positive_activeDisable_onUpdateProductionPage() throws SQLException {
        getDB().update("UPDATE terraleads_shipping.production SET status = 1 WHERE product_base_id = "+product_base_id+" AND store_id = "+store_id);

        updateProduction.open(store_id,product_base_id).readyPage()
                .setStatus("Disable")
                .clickSaveButton().readyPage();

        ResultSet res = getDB().select("SELECT * FROM terraleads_shipping.production WHERE product_base_id = "+product_base_id+" AND store_id = "+store_id);
        while (res.next()){
            Assert.assertEquals(res.getInt("status"),2);
        }

        updateProduction.open(store_id,product_base_id).readyPage()
                .setStatus("Active")
                .clickSaveButton().readyPage();

        res = getDB().select("SELECT * FROM terraleads_shipping.production WHERE product_base_id = "+product_base_id+" AND store_id = "+store_id);
        while (res.next()){
            Assert.assertEquals(res.getInt("status"),1);
        }
    }
}
