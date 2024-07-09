package org.deviartqa.helper;

import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;
import org.deviartqa.pages.system.PermissionPage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

    static List<String> negative_domain_cases(){
        return List.of("ffsfds","","всыаыв","сывавыю.выа");
    }

    static List<String> positive_domain_cases(){
        return List.of("fsfsd.fsd.ss","fsdf.fsdf/fds/fsd","https://dfsdf.fsd");
    }
}
