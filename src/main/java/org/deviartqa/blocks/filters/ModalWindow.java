package org.deviartqa.blocks.filters;

import org.deviartqa.core.SiteBlock;

public class ModalWindow extends SiteBlock {

    public void readyBlock(){
        checkBlock(page.locator("//div[@class='md-content']"));
    }

    public ModalWindow clickStatus_operator_processing(){
        super.clickStatus_operator_processing();
        return this;
    }

    public ModalWindow clickSaveButton(){
        super.clickSaveButton();
        return this;
    }
}
