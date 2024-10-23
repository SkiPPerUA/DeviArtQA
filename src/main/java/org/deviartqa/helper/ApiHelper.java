package org.deviartqa.helper;

import org.apache.commons.codec.digest.DigestUtils;
import org.deviartqa.TestScenario;
import org.deviartqa.core.DBconnector;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ApiHelper {

    static String getCheckSum(String body, int userId){
        ResultSet res = new DBconnector().select("SELECT api_key FROM terraleads.users WHERE id = "+userId);
        String apiKey = "";
        try {
            res.next();
            apiKey = res.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getCheckSum(body,apiKey);
    }

    static String getCheckSum(String body, String apiKey){
        return DigestUtils.sha1Hex(body+apiKey);
    }
}
