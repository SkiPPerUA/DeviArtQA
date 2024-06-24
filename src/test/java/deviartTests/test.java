package deviartTests;


import org.deviartqa.api.accounting.PaymentAPI;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Date;


@Test
public class test extends BaseTest {

    @Test
    void test(){
        System.out.println(PaymentAPI.PaymentStatus.Paid);
    }

    public void test1(){
        for (int i = 0; i<10; i++){
            logger.info(i);
        }
    }
}
