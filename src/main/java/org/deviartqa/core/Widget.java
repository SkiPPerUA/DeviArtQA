package org.deviartqa.core;

import com.microsoft.playwright.Locator;
import org.apache.log4j.Logger;

public class Widget {

    private static Logger logger = Logger.getLogger(Widget.class);
    public Locator element;
    public Widget(Locator locator){
        this.element = locator;
    }
    public Widget click(){
        element.click();
        logger.info("Click element -> "+element);
        return this;
    }

    public String getAttribute(String attribute){
        return element.getAttribute(attribute);
    }

    public boolean isVisible(){
        return element.isVisible();
    }


    public Widget fill(String text){
        element.fill(text);
        return this;
    }
    public Widget clear(){
        element.clear();
        return this;
    }

    public Widget focus(){
        element.focus();
        return this;
    }
    public String textContent(){
        return element.textContent();
    }

}
