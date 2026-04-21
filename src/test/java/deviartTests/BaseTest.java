package deviartTests;

import org.apache.log4j.Logger;
import org.deviartqa.TestScenario;
import org.deviartqa.core.DBconnector;
import org.deviartqa.core.Session;
import org.deviartqa.regions.FactoryRegion;
import org.deviartqa.regions.Region;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.List;

public abstract class BaseTest {

    private DBconnector dBconnector;
    protected static Region region;

    protected static final Logger logger = Logger.getLogger(BaseTest.class);

    @AfterTest
    public void closeSession(){
        Session.close();
        DBconnector.closeConn();
    }

    @BeforeMethod
    public void start(Method method){
        logger.info("---------------------   "+method.getName()+"   ---------------------");
    }

    protected DBconnector getDB(){
        if (dBconnector == null){
            dBconnector = new DBconnector();
        }
        return dBconnector;
    }

    @BeforeSuite
    public void setEnv(){
        FactoryRegion factoryRegion = new FactoryRegion();
        region = factoryRegion.setRegion(TestScenario.region);
    }
}
