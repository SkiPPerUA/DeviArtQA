package org.deviartqa.core;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.deviartqa.helper.TextLocalization;

public class Locators{
    public static final Page page = Session.getPage();
    public static final Locator email = page.getByTestId("email");
    public static final Locator password = page.getByTestId("password");
    public static final Locator submit = page.getByTestId("submit");
    public static final Locator save = page.locator("//button[@id=\"yw0\"]");
    public static final Locator cancel = page.locator("//a[contains(text(),'"+ TextLocalization.get("cancel")+"')]");
    public static final Locator log_out = page.locator("//div[@class='navbar-collapse collapse']//a[@data-modal='logout-modal']");
}
