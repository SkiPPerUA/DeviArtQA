package org.deviartqa.helper;

import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public interface DataHelper {

    static String getUuid(){
        return UUID.randomUUID().toString();
    }

    static String getTime(String pattern){
        return new SimpleDateFormat(pattern).format(new Date());
    }

    static String getTime(String pattern, int day){
        Date dt = new Date();
        dt = DateUtils.addDays(dt, day);
        return new SimpleDateFormat(pattern).format(dt);
    }
}
