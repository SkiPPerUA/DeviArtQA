package deviartTests;

import org.apache.log4j.Logger;
import org.deviartqa.core.DBconnector;
import org.deviartqa.core.Session;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import java.lang.reflect.Method;
import java.util.List;

public abstract class BaseTest {

    protected static final Logger logger = Logger.getLogger(BaseTest.class);

    protected static final List<String> roles = List.of("admin", "advertiser", "webmaster", "call_center_operator",
            "super_operator", "manager", "frontend_dev", "accountant", "logistician", "technical_specialist",
            "call_center_controller", "analyst_store", "analyst_call_center", "logistics_curator", "shipping_administrator");

    @AfterTest
    public void closeSession(){
        Session.close();
        DBconnector.closeConn();
    }

    @BeforeMethod
    public void start(Method method){
        logger.info("---------------------   "+method.getName()+"   ---------------------");
    }
}
