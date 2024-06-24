package org.deviartqa.api;

import io.restassured.http.ContentType;
import org.apache.log4j.Logger;
import org.deviartqa.TestScenario;
import org.deviartqa.core.Restful;
import org.deviartqa.helper.ApiHelper;
import static io.restassured.RestAssured.given;

public class LeadsAPI extends Restful {

    private static final Logger logger = Logger.getLogger(LeadsAPI.class);
    private String lead_id = "";

    public void createLead(String body){
        createLead(body,TestScenario.userId);
    }

    public void createLead(String body, int userId){
        logger.info("Создание лида");
        request(given().
                contentType(ContentType.JSON)
                .body(body)
                .queryParams("check_sum", ApiHelper.getCheckSum(body,userId))
                .post(TestScenario.getUrl()+"/api/lead/create"));
        if (getResponse().contains("\"status\":\"ok\"")) {
            lead_id = response.then().extract().response().jsonPath().getString("data.id");
        }
    }

    public String getLead_id() {
        return lead_id;
    }
}
