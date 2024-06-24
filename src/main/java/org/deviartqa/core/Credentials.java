package org.deviartqa.core;

import org.deviartqa.TestScenario;

import java.util.HashMap;
import java.util.Map;

public class Credentials {

    public String getCredentials(RoleCred role){
        String cred = "";
        if (role == RoleCred.webmaster && TestScenario.env.equals("prod")){
            cred = "l6ve8pk4e2ttfjp02nsrt6a494";
        }else if (role == RoleCred.webmaster && TestScenario.env.equals("sandbox")){
            cred = "ocf77nqb8vfijlbe96u6sdci76";
        }else if (role == RoleCred.webmaster && TestScenario.env.equals("prime")){
            cred = "ltk1evbomcogdjl3a23btd6get";
        }else if (role == RoleCred.admin && TestScenario.env.equals("sandbox")){
            cred = "ritv15menf66sfp0150imk4i5j";
        }
        return cred;
    }

    public Map getDBcred(){
        Map<String,String> creeds = new HashMap<>();
        creeds.put("user","vlad");
        creeds.put("password", "niVt).Ax");
        if (TestScenario.env.equals("sandbox")){
            creeds.put("port","8319");
        }else if (TestScenario.env.equals("prime")){
            creeds.put("port","8320");
        }
        return creeds;
    }

    public enum RoleCred{
        webmaster,admin
    }
}
