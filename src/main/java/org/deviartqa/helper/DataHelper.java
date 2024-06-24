package org.deviartqa.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public interface DataHelper {

    static String getUuid(){
        return UUID.randomUUID().toString();
    }

    static String getTime(String pattern){
        return new SimpleDateFormat(pattern).format(new Date());
    }
}
