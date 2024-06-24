package org.deviartqa.core;

import com.microsoft.playwright.Locator;

public class Widget {

    public Locator element;
    public Widget(Locator locator){
        this.element = locator;
    }
    public Widget click(){
        element.click();
        return this;
    }

    public String getAttribute(String attribute){
        return element.getAttribute(attribute);
    }

    public boolean isVisible(){
        return element.isVisible();
    }

    public Widget check(){
        element.check();
        return this;
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
