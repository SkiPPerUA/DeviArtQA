package org.deviartqa.core;

import io.restassured.response.*;
import org.apache.log4j.Logger;
import org.testng.Assert;

public abstract class Restfull {

    private static final Logger logger = Logger.getLogger(Restfull.class);
    protected Response response;
    protected int expectedCode = 200;
    private boolean need_to_check = true;

    protected void request(Response request){
       this.response = request;
       logger.info(getResponse());
       if (need_to_check){
           Assert.assertEquals(response.getStatusCode(),expectedCode);
       }
    }

    protected void request_off_checkCode(Response request){
        need_to_check = false;
        request(request);
    }

    public String getResponse(){
        return response.then().extract().response().asString();
    }

    public void setExpectedCode(int expectedCode){
        this.expectedCode = expectedCode;
    }
}
