package deviartTests;

import org.apache.log4j.Logger;
import org.deviartqa.core.DBconnector;
import org.deviartqa.core.Session;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import java.lang.reflect.Method;
import java.util.List;

public abstract class BaseTest {

    private DBconnector dBconnector;

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

    protected DBconnector getDB(String schema){
        if (dBconnector == null){
            dBconnector = new DBconnector(schema);
        }else {
            if (!dBconnector.getSchema().equals(schema)){
                DBconnector.closeConn();
                getDB(schema);
            }
        }
        return dBconnector;
    }
}
