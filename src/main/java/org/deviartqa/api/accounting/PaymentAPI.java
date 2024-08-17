package org.deviartqa.api.accounting;

import org.apache.log4j.Logger;
import org.deviartqa.TestScenario;
import org.deviartqa.core.Credentials;
import org.deviartqa.core.Restful;

import static io.restassured.RestAssured.given;

public class PaymentAPI extends Restful {

    String url = "/acp/accounting/payment";
    private static final Logger logger = Logger.getLogger(PaymentAPI.class);
    public void changeStatus(int id, PaymentStatus status){
        logger.info("Change status");
        request(given()
                .header("cookie","PHPSESSID="+new Credentials().getCredentials(TestScenario.role))
                .when()
                .get(TestScenario.getUrl()+url+"/changeStatus?id="+id+"&status="+status));
    }

}
