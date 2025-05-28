package org.deviartqa.api;

import io.restassured.http.ContentType;
import org.apache.log4j.Logger;
import org.deviartqa.core.Restfull;

import static io.restassured.RestAssured.given;

public class Courier extends Restfull {

    private static final Logger logger = Logger.getLogger(Courier.class);

    public void getSkus(String body){
        logger.info("Get Skus");
        request(given()
                .contentType(ContentType.JSON)
                .body(body)
                .header("Authorization-Checksum", "a2ef743ab48601d460b9c0785b9b4fe21c777583")
                .post("http://tl-api.com/courier/courierSku/skus"));
    }

    public void getTracker(String body){
        logger.info("Get Tracker");
        request(given()
                .contentType(ContentType.JSON)
                .body(body)
                .header("Authorization-Checksum", "74b16055a3c651d3c1e23f41df1a612d085bf057")
                .post("http://tl-api.com/courier/getTracker"));
    }
}
