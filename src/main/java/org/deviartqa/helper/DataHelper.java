package org.deviartqa.helper;

import org.apache.commons.lang3.time.DateUtils;
import org.deviartqa.core.DBconnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public interface DataHelper {

    static String getUuid(){
        return UUID.randomUUID().toString();
    }

    static String getTime(String pattern){
        Date dt = new Date();
        dt = DateUtils.addHours(dt, 1);
        return new SimpleDateFormat(pattern).format(dt);
    }

    static String getTime(String pattern, int day){
        Date dt = new Date();
        dt = DateUtils.addDays(dt, day);
        return new SimpleDateFormat(pattern).format(dt);
    }

    static int findUserId_byRole(String role){
        try {
            ResultSet res = new DBconnector().select("SELECT id FROM terraleads.users where role = '"+role+"' and status = 1 order by id DESC limit 1");
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
