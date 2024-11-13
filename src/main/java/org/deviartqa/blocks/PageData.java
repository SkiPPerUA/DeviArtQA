package org.deviartqa.blocks;

import org.deviartqa.core.SiteBlock;
import org.deviartqa.core.Widget;

public class PageData extends SiteBlock {

    public PageData checkBlock(){
        checkBlock(page.locator("//div[@id='dataGrid']"));
        return this;
    }

    public PageData setCheckBox(int id){
        new Widget(page.locator("//td[text()='"+id+"']/..//input[@type='checkbox']")).click();
        return this;
    }
}
