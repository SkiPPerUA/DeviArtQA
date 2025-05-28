package org.deviartqa.api;

import io.restassured.http.ContentType;
import org.apache.log4j.Logger;
import org.deviartqa.TestScenario;
import org.deviartqa.core.Restfull;
import org.deviartqa.helper.ApiHelper;

import static io.restassured.RestAssured.given;

public class Ip extends Restfull {

    private static final Logger logger = Logger.getLogger(org.apache.log4j.Category.class);

    public void getIp(int userId){
        logger.info("getIp");
        String body = "{\n" +
                "    \"user_id\":"+userId+",\n" +
                "    \"data\":[\n" +
                "    ]\n" +
                "}";
        request(given().
                contentType(ContentType.JSON)
                .queryParams("check_sum", ApiHelper.getCheckSum(body,userId))
                .body(body)
                .post(TestScenario.getUrl()+"/api/ip/get"));
    }

}
