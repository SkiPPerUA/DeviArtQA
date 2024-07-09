package org.deviartqa.core;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.Cookie;
import org.deviartqa.TestScenario;
import java.util.ArrayList;
import java.util.List;

public abstract class Session {

    private static Browser browser;
    private static Page page;
    public static BrowserContext context;

    private static void start(){
        if (browser == null) {
            Playwright playwright = Playwright.create();
            playwright.selectors().setTestIdAttribute("name");
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(TestScenario.headless)
                    .setChannel(TestScenario.browser));
            context = browser.newContext();
            addCookies();
            page = context.newPage();
        }
    }

    private static void addCookies(){
        List<Cookie> ls = new ArrayList<>();
        Cookie cookie = new Cookie("PHPSESSID",new Credentials().getCredentials(TestScenario.role));
        cookie.setPath("/");
        cookie.setDomain(TestScenario.getUrl().replace("https://",""));
        ls.add(cookie);
        context.addCookies(ls);
    }

    public static Page getPage(){
        if (page == null){
            start();
        }
        return page;
    }

    public static void close(){
        if(browser != null){
            browser.close();
            browser = null;
        }
    }

    public static BrowserContext getContext() {
        return context;
    }
}
