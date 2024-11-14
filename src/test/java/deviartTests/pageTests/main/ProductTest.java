package deviartTests.pageTests.main;

import deviartTests.BaseTest;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.pages.main.product.CreateProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

@Test
public class ProductTest extends BaseTest {

    CreateProductPage createProductPage = new CreateProductPage();

    public void positive_createProduct() throws SQLException {
        String uuid = DataHelper.getUuid();
        createProductPage.open().readyPage()
                .setBaseProduct("2635")
                .setStatus("Active")
                .setName("EN"+ uuid, "RU"+uuid)
                .clickSaveButton();

        ResultSet product = getDB().select("SELECT * FROM terraleads.product ORDER BY id DESC");
        product.next();
        Assert.assertEquals(product.getInt("base_product_id"),2635);
        Assert.assertEquals(product.getString("status"),"active");
        Assert.assertNull(product.getObject("type"));
        Assert.assertNull(product.getObject("priority"));

        ResultSet product_lang = getDB().select("SELECT * FROM terraleads.product_lang WHERE language_id = 1 AND product_id = "+product.getInt("id"));
        product_lang.next();
        Assert.assertEquals(product_lang.getString("name"),"EN"+ uuid);
        Assert.assertNull(product_lang.getObject("trust_level"));
        Assert.assertNull(product_lang.getObject("soc_demography"));

        product_lang = getDB().select("SELECT * FROM terraleads.product_lang WHERE language_id = 2 AND product_id = "+product.getInt("id"));
        product_lang.next();
        Assert.assertEquals(product_lang.getString("name"),"RU"+ uuid);
        Assert.assertNull(product_lang.getObject("trust_level"));
        Assert.assertNull(product_lang.getObject("soc_demography"));
    }

}
