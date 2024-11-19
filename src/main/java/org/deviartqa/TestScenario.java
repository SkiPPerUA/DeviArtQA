package org.deviartqa;

import org.deviartqa.core.Credentials;
import static org.deviartqa.core.Credentials.RoleCred.*;

public abstract class TestScenario {

    public static final boolean headless = false;
    public static final String browser = "chrome";
    public static String local = "en"; // en ru
    public static String env = "sandbox"; // prod sandbox prime
    public static final Credentials.RoleCred role = admin;
    public static final int userId = 25695;
    public static final boolean enable = false;
    public static String getUrl(){
        String url = "";
        if (env.equals("prod")){
            url = "https://terraleads.com";
        }else if (env.equals("sandbox")){
            url = "https://sandbox.terraleads.com";
        }else if (env.equals("prime")){
            url = "https://sandbox.primestore24.com";
        }

        if (local.equals("ru")){
            url = url +"/ru";
        }
        return url;
    }
}
