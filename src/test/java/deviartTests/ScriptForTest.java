package deviartTests;

import org.testng.annotations.Test;

@Test
public class ScriptForTest extends BaseTest{

    public void makeOrderForGetInvoiceOrProforma(){
        getDB().update("update terraleads_shipping.`order` set status=3, tracker='SWS46900739', tracker_date='2025-06-02 15:22:50.000' where " +
                "id > 14965799");
    }
}
