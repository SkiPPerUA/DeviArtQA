package org.deviartqa.api.courier;

import io.restassured.http.ContentType;
import org.apache.log4j.Logger;
import org.deviartqa.TestScenario;
import org.deviartqa.core.Restful;
import org.deviartqa.helper.ApiHelper;

import static io.restassured.RestAssured.given;

public class CourierSku extends Restful {

    private static final Logger logger = Logger.getLogger(CourierSku.class);

    public void getSkus(String body){
        logger.info("Get Skus");
        request(given()
                .contentType(ContentType.JSON)
                .body(body)
                .header("Authorization-Checksum", "a2ef743ab48601d460b9c0785b9b4fe21c777583")
                .post("http://tl-api.com/courier/courierSku/skus"));
    }
}
