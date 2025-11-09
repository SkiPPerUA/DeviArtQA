package org.deviartqa.blocks.filters;

import org.deviartqa.core.SiteBlock;
import org.deviartqa.core.Widget;

public class ModalWindow extends SiteBlock {

    public void readyBlock(){
        checkBlock(page.locator("//div[@class='md-content']"));
    }
    String x_path_locator = "";

    public ModalWindow clickStatus_operator_processing(){
        super.clickStatus_operator_processing();
        return this;
    }

    public ModalWindow clickSaveButton(){
        super.clickSaveButton();
        return this;
    }

    public void save(boolean save){
        if (save){
            new Widget(page.locator("//div[@id='result-"+x_path_locator+"-stick-top']//button[@name='ShippingFormCallRo[result]']")).click();
        }else {
            new Widget(page.locator("//div[@id='result-"+x_path_locator+"-stick-top']//a")).click();
        }
    }

    public ModalWindow callProcess(CallResult callResult, ResultAdditional resultAdditional, String comment, OperatorStatus operatorStatus){
        if (callResult == CallResult.approve){
            x_path_locator = "success";
        }else if (callResult == CallResult.techProblem){
            x_path_locator = "technical-problems";
        }else {
            x_path_locator = callResult.toString();
        }

        new Widget(page.locator("//a[@data-modal='result-"+x_path_locator+"-stick-top']")).click();

        //set resultAdditional
        if (callResult == CallResult.trash || callResult == CallResult.busy) {
            new Widget(page.locator("//div[@name='ShippingFormCallRo[result_additional]']/button")).click();
            new Widget(page.locator("//div[@name='ShippingFormCallRo[result_additional]']//li[@data-original-index=\""+resultAdditional.value+"\"]")).click();
        }

        if (comment.toCharArray().length > 0){
            new Widget(page.getByTestId("ShippingFormCallRo[comment]")).fill(comment);
        }

        //set operator status
        Widget operStatus = new Widget(page.locator("//div[@id='result-"+x_path_locator+"-stick-top']//input[@type='radio'][@value="+operatorStatus.value+"]/.."));
        if (!operStatus.element.getAttribute("class").contains("active")){
            operStatus.click();
        }

        return this;
    }

    public enum OperatorStatus{
        continueProcessing(1),pause(3),lunch(4),training(5),techProblem(6),stopProcessing(2);
        private int value;
        OperatorStatus(int i) {
            value = i;
        }
    }

    public enum CallResult{
        approve,busy,reject,trash,techProblem
    }

    public enum ResultAdditional {
        busy_NoAnswer(0),busy_ThrownOff(1),busy_OutsideTheZone(2),busy_SelectCallTime(3),
        trash_NotOrdered(0),trash_DuplicateOrder(1);
        private int value;
        ResultAdditional(int i) {
            value = i;
        }
    }

}
