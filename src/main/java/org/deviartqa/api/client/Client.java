package org.deviartqa.api.client;

import org.apache.log4j.Logger;
import org.deviartqa.core.Restfull;

import static io.restassured.RestAssured.given;

public class Client extends Restfull {

    private final Logger logger = Logger.getLogger(Client.class);
    private String host = "http://212.224.112.44:8083";

    public void getClient(String params){
        logger.info("Get client");
        request(given()
                .when()
                .get(host+"/client/index?"+params));
    }

    public void getCountry(){
        logger.info("Get countries");
        request(given()
                .when()
                .get(host+"/client/countries"));
    }

    public void getCard(int id){
        logger.info("Get card");
        request(given()
                .when()
                .get(host+"/client/get/"+id));
    }

    public void getOrders(String params){
        logger.info("Get orders");
        request(given()
                .when()
                .get(host+"/client/orders/"+params));
    }

    public void getCalls(String params){
        logger.info("Get calls");
        request(given()
                .when()
                .get(host+"/client/calls/"+params));
    }
}
