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
            cred = "8fb3ukh2r67rrfsfshvcgp5rmm";
        }else if (role == RoleCred.admin && TestScenario.env.equals("prime")){
            cred = "uajoh6bgs9qtcmfv790m4kbtdg";
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

    public Map<String,String> getWebCreeds(){
        Map<String,String> creeds = new HashMap<>();
        creeds.put("password", "p6&Rvgdl");
        if (TestScenario.role == RoleCred.admin){
            creeds.put("email", "admin_savchukvi12@gmail.com");
        }else if (TestScenario.role == RoleCred.webmaster){
            creeds.put("email", "savchukvi12@gmail.com");
        }
        return creeds;
    }

    public enum RoleCred{
        webmaster,admin
    }
}
