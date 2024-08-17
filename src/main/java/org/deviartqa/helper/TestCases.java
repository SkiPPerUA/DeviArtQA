package org.deviartqa.helper;

import org.deviartqa.blocks.Header;
import org.deviartqa.core.DBconnector;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
import org.deviartqa.pages.main.WelcomePage;
import org.deviartqa.pages.system.PermissionPage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public interface TestCases {
    PermissionPage permissionPage = new PermissionPage();
    static List<String> test_permissionOFF(HashSet<String> permission, Runnable test){
        List<String> failPermission = new ArrayList<>();
        List<String> permissions = new ArrayList(permission);
        for (int i = 0; i < permissions.size(); i++){
            all_permissionON(permission);
            permissionPage.changePermission(permissions.get(i),false);
            new Widget(Locators.page.locator("//*[@class=\"btn btn-success\"][@type]")).click();
            try {
                test.run();
            }catch (Throwable e){
                failPermission.add(permissions.get(i));
            }
        }
        all_permissionON(permission);
        return failPermission;
    }

    static void all_permissionON(HashSet<String> permission){
        permissionPage.open().readyPage();
        permission.forEach(x-> permissionPage.changePermission(x,true));
    }

    static void all_permissionOFF(HashSet<String> permission){
        permissionPage.open().readyPage();
        permission.forEach(x-> permissionPage.changePermission(x,false));
    }

    static List<String> negative_emails(){
        return List.of("ffsfds","всыаыв@das.da", "fdfds@dwd", "das das@das.ad", "dasd@.da", "dasd@dassd.");
    }

    static void checkAccessToPage(Runnable positiveCase){
        Map<String, Integer> users = new HashMap<>();
        users.put("admin", 25695);
        users.put("advertiser", 25554);
        users.put("webmaster", 25593);
        users.put("call_center_operator", 25561);
        users.put("super_operator", 25558);
        users.put("manager", 25680);
        users.put("frontend_dev", 25596);
        users.put("accountant", 25679);
        users.put("logistician", 25592);
        users.put("technical_specialist", 25570);
        users.put("call_center_controller", 25565);
        users.put("analyst_store", 25580);
        users.put("analyst_call_center", 25624);
        users.put("logistics_curator", 25598);
        users.put("shipping_administrator", 25690);
        users.put("manager_intern", 25698);
        roles().forEach(x->{
            new WelcomePage().open().header.changeUser(users.get(x));
            try {
                positiveCase.run();
                System.out.println(x+ " -> доступ есть");
            }catch (Throwable e){
                System.out.println(x+ " -> доступа нету");
            }
        });
    }

    static List<String> roles(){
        return List.of("admin", "advertiser", "webmaster", "call_center_operator",
                "super_operator", "manager", "frontend_dev", "accountant", "logistician", "technical_specialist",
                "call_center_controller", "analyst_store", "analyst_call_center", "logistics_curator", "shipping_administrator", "manager_intern");
    }

}
