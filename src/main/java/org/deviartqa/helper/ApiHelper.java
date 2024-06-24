package org.deviartqa.helper;

import org.apache.commons.codec.digest.DigestUtils;
import org.deviartqa.TestScenario;
import org.deviartqa.core.DBconnector;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ApiHelper {

    static String getCheckSum(String body, int userId){
        ResultSet res = new DBconnector().select("SELECT api_key FROM terraleads.users WHERE id = "+userId);
        String api_key = "";
        try {
            res.next();
            api_key = res.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return DigestUtils.sha1Hex(body+api_key);
    }
}
