package org.deviartqa.api;

import io.restassured.http.ContentType;
import org.apache.log4j.Logger;
import org.deviartqa.core.Restfull;

import static io.restassured.RestAssured.given;

public class StatisticApi extends Restfull {

    private final Logger logger = Logger.getLogger(StatisticApi.class);

    public void web(String body){
        logger.info("Statistic web");
        request(given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("http://212.224.112.44:8111/api/statistic/web"));
    }

    public void adv(String body){
        logger.info("Statistic adv");
        request(given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("http://212.224.112.44:8111/api/statistic/adv"));
    }
}
