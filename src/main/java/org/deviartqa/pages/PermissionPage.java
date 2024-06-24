package org.deviartqa.pages;

import com.microsoft.playwright.Locator;
import org.deviartqa.TestScenario;
import org.deviartqa.core.CabinetPage;
import org.deviartqa.core.Locators;
import org.deviartqa.core.Widget;

public class PermissionPage extends CabinetPage {

    private final Locator readyLocator = page.locator("//a[@href=\"/acp/permission/addUser\"]");

    public PermissionPage readyPage() {
        checkPage(readyLocator);
        return this;
    }

    public PermissionPage open(){
        openPage("/acp/permission/");
        return this;
    }

    public PermissionPage changePermission(String permission, boolean activate){
        if (!page.url().contains("role?name=")) {
            page.navigate(page.url() + "role?name=" + TestScenario.role);
        }
        checkBox(new Widget(Locators.page.locator("//label[@for='"+permission+"']/../div/div")),activate);
        return this;
    }
}
