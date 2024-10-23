package org.deviartqa.api.accounting;

import org.apache.log4j.Logger;
import org.deviartqa.TestScenario;
import org.deviartqa.core.Credentials;
import org.deviartqa.core.Restfull;

import static io.restassured.RestAssured.given;

public class PaymentAPI extends Restfull {

    String url;

    private static final Logger logger = Logger.getLogger(PaymentAPI.class);

    public PaymentAPI (String url){
        this.url = url;
    }

    public void changeStatus(int id, PaymentStatus status){
        logger.info("Change status");
        request(given()
                .header("cookie","PHPSESSID="+new Credentials().getCredentials(TestScenario.role))
                .when()
                .get(TestScenario.getUrl()+url+"/changeStatus?id="+id+"&status="+status));
    }

    public enum PaymentStatus {

        Paid(2),
        Cancel(0),
        Confirm(10);

        private int title;
        PaymentStatus(int s) {
            this.title = s;
        }
        @Override
        public String toString() {
            return String.valueOf(title);
        }
    }

}
